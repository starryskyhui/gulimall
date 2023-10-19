package com.atguigu.gulimall.gulimallmember.dao;

import com.atguigu.gulimall.gulimallmember.entity.MemberLevelEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * 会员等级
 * 
 * @author zgh
 * @email sunlightcs@gmail.com
 * @date 2023-03-09 19:28:57
 */
@Mapper
public interface MemberLevelDao extends BaseMapper<MemberLevelEntity> {

    MemberLevelEntity getDefaultLevel();
}
