package com.atguigu.gulimall.auth.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.atguigu.common.utils.HttpUtils;
import com.atguigu.common.utils.R;
import com.atguigu.gulimall.auth.feign.MemberFeignService;
import com.atguigu.common.vo.MemberRespVo;
import com.atguigu.gulimall.auth.vo.SocialUser;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpResponse;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;

/**
 * 处理社交登录请求
 * @author 郑国辉
 */
@Slf4j
@Controller
public class OAuth2Controller {

    @Autowired
    MemberFeignService memberFeignService;

    @GetMapping("/oauth2.0/weibo/success")
    public String weibo(@RequestParam("code") String code, HttpSession session) throws Exception {

        Map<String, String> map = new HashMap<>();
        map.put("client_id","2077705774");
        map.put("client_secret","40af02bd1c7e435ba6a6e9cd3bf799fd");
        map.put("grant_type","authorization_code");
        map.put("redirect_uri","http://auth.gulimall.com/oauth2.0/weibo/success");
        map.put("code",code);
        // 1、根据code换取accessToken
        HttpResponse response = HttpUtils.doPost("https://api.weibo.com", "/oauth2/access_token", "post", new HashMap<>(), map, new HashMap<>());

        // 2、处理
        if (response.getStatusLine().getStatusCode() == 200) {
            String json = EntityUtils.toString(response.getEntity());
            SocialUser socialUser = JSON.parseObject(json, SocialUser.class);

            // 知道当前是哪个社交用户
            // 1）、当前用户第一次登录，自动注册进来（当前社交用户自动生成一个会员信息账号，以后这个社交账号就对应指定的用户）
            // 登录或者注册这个社交用户
            R r = memberFeignService.oauthLogin(socialUser);
            if (r.getCode() == 0) {
                MemberRespVo data = r.getData("data", new TypeReference<MemberRespVo>() {
                });
                log.info("登录成功：用户信息{}", data.toString());
                // 1、第一次使用session;命令浏览器保存卡号。JSESSIONID这个cookie;
                // 以后浏览器访问哪个网站就会带上这个网站的cookie
                // 子域之间：gulimall.com auth.gulimall.com
                // 发卡的时候指定域名为父域名，即使是子域系统发的卡，也能让父域直接使用
                session.setAttribute("loginUser", data);
                // 2、登录成功就跳转到首页
                return "redirect:http://gulimall.com";
            } else {
                return "redirect:http://auth.gulimall.com/login.html";
            }
        } else {
            return "redirect:http://auth.gulimall.com/login.html";
        }
    }
}
