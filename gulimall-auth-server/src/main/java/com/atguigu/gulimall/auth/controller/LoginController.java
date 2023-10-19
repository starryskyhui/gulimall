package com.atguigu.gulimall.auth.controller;

import com.alibaba.fastjson.TypeReference;
import com.atguigu.common.Exception.BizCodeEnum;
import com.atguigu.common.constant.AuthServerConstant;
import com.atguigu.common.utils.HttpUtils;
import com.atguigu.common.utils.R;
import com.atguigu.common.vo.MemberRespVo;
import com.atguigu.gulimall.auth.feign.MemberFeignService;
import com.atguigu.gulimall.auth.feign.ThirdPartFeignService;
import com.atguigu.gulimall.auth.vo.UserLoginVo;
import com.atguigu.gulimall.auth.vo.UserRegistVo;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

/**
 * @author 郑国辉
 */
@Controller
public class LoginController {

    @Autowired
    ThirdPartFeignService thirdPartFeignService;

    @Autowired
    StringRedisTemplate redisTemplate;

    @Autowired
    MemberFeignService memberFeignService;

    /**
     * 发送一个请求，直接跳转到一个页面
     * SpringMVC viewcontroller; 将请求和页面映射过来
     * @return
     */

    @ResponseBody
    @GetMapping("/sms/sendCode")
    public R sendCode(@RequestParam("phone") String phone) {

        // todo 1、接口防刷

        String redisCode = redisTemplate.opsForValue().get(AuthServerConstant.SMS_CODE_CACHE_PREFIX + phone);
        if (!StringUtils.isEmpty(redisCode)) {
            Long l = Long.parseLong(redisCode.split("_")[1]);
            if (System.currentTimeMillis() - l < 60000) {
                // 60秒内不能再发
                return R.error(BizCodeEnum.SMS_CODE_EXCEPTION.getCode(), BizCodeEnum.SMS_CODE_EXCEPTION.getMsg());
            }
        }

        // 2、验证码的再次校验(过期)，redis。存key-phone,value-code sms:code:19821216995 -》 45678
        String code = UUID.randomUUID().toString().substring(0, 5);
        String substring = code + "_" + System.currentTimeMillis();

        // redis缓存验证码，防止同一个手机号在60秒内再发送验证码
        redisTemplate.opsForValue().set(AuthServerConstant.SMS_CODE_CACHE_PREFIX+phone, substring, 10, TimeUnit.MINUTES);

        thirdPartFeignService.sendCode(phone, code);
        return R.ok();
    }

    /**
     * TODO 重定向携带数据，利用session原理。将数据放在session中。
     * 只要跳到下一个页面取出这个数据以后，session里面的数据就会被删掉
     *
     * TODO 1、分布式下的session问题
     * RedirectAttributes redirectAttributes: 模拟重定向携带数据 防止表单重复提交 http://www.gimoo.net/t/1905/5cd7b66269672.html
     * @param vo
     * @param result
     * @param redirectAttributes
     * @return
     */
    @PostMapping("/register")
    public String register(@Valid UserRegistVo vo, BindingResult result,
                           RedirectAttributes redirectAttributes) {

        if (result.hasErrors()) {
            Map<String,String> errors = result.getFieldErrors().stream().collect(Collectors.toMap(FieldError::getField, FieldError::getDefaultMessage));

            redirectAttributes.addFlashAttribute("errors", errors);
            // 校验出错，转发到注册页
            return "redirect:http://auth.gulimall.com/reg.html";
        }

        // 调用远程服务进行注册
        //1、校验验证码
        String code = vo.getCode();
        String s = redisTemplate.opsForValue().get(AuthServerConstant.SMS_CODE_CACHE_PREFIX + vo.getPhone());

        if (!StringUtils.isEmpty(s)) {
            if (code.equals(s.split("_")[0])) {
                // 删除验证码；令牌机制
                redisTemplate.delete(AuthServerConstant.SMS_CODE_CACHE_PREFIX + vo.getPhone());
                // 验证码通过，开始调用远程服务注册
                R r = memberFeignService.register(vo);
                if (r.getCode() == 0) {
                    // 成功
                    return "redirect:http://auth.gulimall.com/login.html";
                } else {
                    HashMap<String, String> errors = new HashMap<>();
                    errors.put("msg", r.getData("msg", new TypeReference<String>(){}));
                    redirectAttributes.addFlashAttribute("errors", errors);
                    return "redirect:http://auth.gulimall.com/reg.html";
                }
            } else {
                Map<String, String> errors = new HashMap<>();
                errors.put("code", "验证码错误");
                redirectAttributes.addFlashAttribute("errors", errors);
                return "redirect:http://auth.gulimall.com/reg.html";
            }
        } else {
            Map<String, String> errors = new HashMap<>();
            errors.put("code", "验证码错误");
            redirectAttributes.addFlashAttribute("errors", errors);
            // 校验出错，转发到注册页
            return "redirect:http://auth.gulimall.com/reg.html";
        }

        // 注册成功后返回首页，回到登录页
//        return "redirect:/login.html";
    }

    @GetMapping("/login.html")
    public String loginPage(HttpSession session) {
        Object attribute = session.getAttribute(AuthServerConstant.LOGIN_USER);
        if (attribute == null) {
            return "login";
        } else {
            return "redirect:http://gulimall.com";
        }
    }

    // 此处为表单提交，不用加@RequestBody
    @PostMapping("/login")
    public String login(UserLoginVo vo, RedirectAttributes redirectAttributes, HttpSession session) {

        // 远程登录
        R login = memberFeignService.login(vo);
        if (login.getCode() == 0) {
            // 成功
            MemberRespVo data = login.getData("data", new TypeReference<MemberRespVo>() {
            });
            session.setAttribute(AuthServerConstant.LOGIN_USER, data);
            return "redirect:http://gulimall.com";
        } else {
            HashMap<String, String> errors = new HashMap<>();
            errors.put("msg", login.getData("msg", new TypeReference<String>(){}));
            redirectAttributes.addFlashAttribute("errors", errors);
            return "redirect:http://auth.gulimall.com/login.html";
        }
    }
}
