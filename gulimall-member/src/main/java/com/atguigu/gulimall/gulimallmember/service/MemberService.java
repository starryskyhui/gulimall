package com.atguigu.gulimall.gulimallmember.service;

import com.atguigu.gulimall.gulimallmember.vo.MemberLoginVo;
import com.atguigu.gulimall.gulimallmember.vo.MemberRegistVo;
import com.atguigu.gulimall.gulimallmember.vo.SocialUser;
import com.baomidou.mybatisplus.extension.service.IService;
import com.atguigu.common.utils.PageUtils;
import com.atguigu.gulimall.gulimallmember.entity.MemberEntity;

import java.util.Map;

/**
 * 会员
 *
 * @author zgh
 * @email sunlightcs@gmail.com
 * @date 2023-03-09 19:28:57
 */
public interface MemberService extends IService<MemberEntity> {

    PageUtils queryPage(Map<String, Object> params);

    void register(MemberRegistVo vo);

    void checkPhoneUnique(String phone);

    void checkUserNameUnique(String userName);

    MemberEntity login(MemberLoginVo vo);

    MemberEntity login(SocialUser socialUser) throws Exception;
}

