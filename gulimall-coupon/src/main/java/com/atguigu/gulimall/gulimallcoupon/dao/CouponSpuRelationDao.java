package com.atguigu.gulimall.gulimallcoupon.dao;

import com.atguigu.gulimall.gulimallcoupon.entity.CouponSpuRelationEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * 优惠券与产品关联
 * 
 * @author zgh
 * @email sunlightcs@gmail.com
 * @date 2023-03-09 19:17:19
 */
@Mapper
public interface CouponSpuRelationDao extends BaseMapper<CouponSpuRelationEntity> {
	
}
