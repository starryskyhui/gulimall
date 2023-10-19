package com.atguigu.gulimall.auth.feign;

import com.atguigu.common.utils.R;
import com.atguigu.gulimall.auth.vo.SocialUser;
import com.atguigu.gulimall.auth.vo.UserLoginVo;
import com.atguigu.gulimall.auth.vo.UserRegistVo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * @author 郑国辉
 */
@FeignClient("gulimall-member")
public interface MemberFeignService {

    @PostMapping("/gulimallmember/member/regist")
    R register(@RequestBody UserRegistVo vo);

    @PostMapping("/gulimallmember/member/login")
    R login(@RequestBody UserLoginVo vo);

    @PostMapping("/gulimallmember/member/oauth2/login")
    R oauthLogin(@RequestBody SocialUser socialUser) throws Exception;
}
