package com.atguigu.gulimall.gulimallorder.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.atguigu.common.utils.PageUtils;
import com.atguigu.gulimall.gulimallorder.entity.OrderEntity;

import java.util.Map;

/**
 * 订单
 *
 * @author zgh
 * @email sunlightcs@gmail.com
 * @date 2023-03-09 19:25:44
 */
public interface OrderService extends IService<OrderEntity> {

    PageUtils queryPage(Map<String, Object> params);
}

