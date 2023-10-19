package com.atguigu.gulimall.gulimallmember.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.atguigu.common.utils.HttpUtils;
import com.atguigu.gulimall.gulimallmember.dao.MemberLevelDao;
import com.atguigu.gulimall.gulimallmember.entity.MemberLevelEntity;
import com.atguigu.gulimall.gulimallmember.exception.PhoneExistException;
import com.atguigu.gulimall.gulimallmember.exception.UserNameExistException;
import com.atguigu.gulimall.gulimallmember.vo.MemberLoginVo;
import com.atguigu.gulimall.gulimallmember.vo.MemberRegistVo;
import com.atguigu.gulimall.gulimallmember.vo.SocialUser;
import org.apache.http.HttpResponse;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.atguigu.common.utils.PageUtils;
import com.atguigu.common.utils.Query;

import com.atguigu.gulimall.gulimallmember.dao.MemberDao;
import com.atguigu.gulimall.gulimallmember.entity.MemberEntity;
import com.atguigu.gulimall.gulimallmember.service.MemberService;


@Service("memberService")
public class MemberServiceImpl extends ServiceImpl<MemberDao, MemberEntity> implements MemberService {

    @Autowired
    MemberLevelDao memberLevelDao;

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<MemberEntity> page = this.page(
                new Query<MemberEntity>().getPage(params),
                new QueryWrapper<MemberEntity>()
        );

        return new PageUtils(page);
    }

    @Override
    public void register(MemberRegistVo vo) {
        MemberDao memberDao = this.baseMapper;
        MemberEntity entity = new MemberEntity();

        // 设置默认等级
        MemberLevelEntity levelEntity = memberLevelDao.getDefaultLevel();
        entity.setLevelId(levelEntity.getId());

        // 检查用户名和手机号是否唯一 为了让controller感知异常，使用异常机制
        checkPhoneUnique(vo.getPhone());
        checkUserNameUnique(vo.getUserName());

        entity.setMobile(vo.getPhone());
        entity.setUsername(vo.getUserName());

        entity.setNickname(vo.getUserName());

        // 密码进行加密存储
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String encode = passwordEncoder.encode(vo.getPassword());
        entity.setPassword(encode);

        // 其他默认信息

        // 保存
        memberDao.insert(entity);
    }

    @Override
    public void checkPhoneUnique(String phone) throws PhoneExistException {
        MemberDao memberDao = this.baseMapper;
        Long count = memberDao.selectCount(new QueryWrapper<MemberEntity>().eq("mobile", phone));
        if (count > 0) {
            throw new PhoneExistException();
        }
    }

    @Override
    public void checkUserNameUnique(String userName) throws UserNameExistException {
        MemberDao memberDao = this.baseMapper;
        Long count = memberDao.selectCount(new QueryWrapper<MemberEntity>().eq("username", userName));
        if (count > 0) {
            throw new UserNameExistException();
        }
    }

    @Override
    public MemberEntity login(MemberLoginVo vo) {
        String loginacct = vo.getLoginacct();
        String password = vo.getPassword();

        // 1.去数据库查询
        MemberDao memberDao = this.baseMapper;
        MemberEntity entity = memberDao.selectOne(new QueryWrapper<MemberEntity>().eq("username", loginacct)
                .or().eq("mobile", loginacct));
        if (entity == null) {
            // 登录失败
            return null;
        } else {
            // 1、获取到数据库的password
            String passwordDb = entity.getPassword();
            BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
            // 2、密码匹配
            boolean matches = passwordEncoder.matches(password, passwordDb);
            if (matches) {
                return entity;
            } else {
                return null;
            }
        }
    }

    /**
     * 社交登录
     * @param socialUser
     * @return
     */
    @Override
    public MemberEntity login(SocialUser socialUser) throws Exception {
        // 登录与注册合并逻辑
        String uid = socialUser.getUid();
        // 1、判断当前社交用户是否已经登录过系统
        MemberDao memberDao = this.baseMapper;
        MemberEntity memberEntity = memberDao.selectOne(new QueryWrapper<MemberEntity>().eq("social_uid", uid));

        if (memberEntity != null) {
            // 这个用户已经注册
            MemberEntity update = new MemberEntity();
            update.setId(memberEntity.getId());
            update.setAccessToken(socialUser.getAccess_token());
            memberEntity.setExpiresIn(socialUser.getExpires_in());

            memberDao.updateById(update);

            memberEntity.setAccessToken(socialUser.getAccess_token());
            memberEntity.setExpiresIn(socialUser.getExpires_in());
            return memberEntity;
        } else {
            //2、没有查到当前社交用户对应的记录我们就需要注册一个
            MemberEntity register = new MemberEntity();
            try {
                //3、查询当前社交用户的社交账号信息（昵称、性别等）
                Map<String,String> query = new HashMap<>();
                query.put("access_token",socialUser.getAccess_token());
                query.put("uid",socialUser.getUid());
                HttpResponse response = HttpUtils.doGet("https://api.weibo.com", "/2/users/show.json", "get", new HashMap<String, String>(), query);

                if (response.getStatusLine().getStatusCode() == 200) {
                    //查询成功
                    String json = EntityUtils.toString(response.getEntity());
                    JSONObject jsonObject = JSON.parseObject(json);
                    String name = jsonObject.getString("name");
                    String gender = jsonObject.getString("gender");
                    String profileImageUrl = jsonObject.getString("profile_image_url");

                    register.setNickname(name);
                    register.setGender("m".equals(gender) ? 1 : 0);
                    register.setHeader(profileImageUrl);
                    register.setCreateTime(new Date());
                }
            } catch(Exception e) {}

            register.setSocialUid(socialUser.getUid());
            register.setAccessToken(socialUser.getAccess_token());
            register.setExpiresIn(socialUser.getExpires_in());

            //把用户信息插入到数据库中
            memberDao.insert(register);
            return register;
        }
    }
}