package com.atguigu.gulimall.gulimallorder.service;

import com.atguigu.gulimall.gulimallorder.vo.OrderConfirmVo;
import com.atguigu.gulimall.gulimallorder.vo.OrderSubmitVo;
import com.atguigu.gulimall.gulimallorder.vo.PayVo;
import com.atguigu.gulimall.gulimallorder.vo.SubmitOrderResponseVo;
import com.baomidou.mybatisplus.extension.service.IService;
import com.atguigu.common.utils.PageUtils;
import com.atguigu.gulimall.gulimallorder.entity.OrderEntity;

import java.util.Map;
import java.util.concurrent.ExecutionException;

/**
 * 订单
 *
 * @author zgh
 * @email sunlightcs@gmail.com
 * @date 2023-03-09 19:25:44
 */
public interface OrderService extends IService<OrderEntity> {

    PageUtils queryPage(Map<String, Object> params);

    /**
     * 订单确认页返回需要的数据
     * @return
     */
    OrderConfirmVo confirmOrder() throws ExecutionException, InterruptedException;

    SubmitOrderResponseVo submitOrder(OrderSubmitVo vo);

    OrderEntity getOrderByOrderSn(String orderSn);

    void closeOrder(OrderEntity orderEntity);

    PayVo getOrderPay(String orderSn);

    PageUtils queryPageWithItem(Map<String, Object> params);
}

