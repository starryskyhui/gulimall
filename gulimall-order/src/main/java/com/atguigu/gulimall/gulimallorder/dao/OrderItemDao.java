package com.atguigu.gulimall.gulimallorder.dao;

import com.atguigu.gulimall.gulimallorder.entity.OrderItemEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * 订单项信息
 * 
 * @author zgh
 * @email sunlightcs@gmail.com
 * @date 2023-03-09 19:25:44
 */
@Mapper
public interface OrderItemDao extends BaseMapper<OrderItemEntity> {
	
}
