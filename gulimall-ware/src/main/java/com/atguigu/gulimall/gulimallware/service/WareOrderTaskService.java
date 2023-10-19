package com.atguigu.gulimall.gulimallware.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.atguigu.common.utils.PageUtils;
import com.atguigu.gulimall.gulimallware.entity.WareOrderTaskEntity;

import java.util.Map;

/**
 * 库存工作单
 *
 * @author zgh
 * @email sunlightcs@gmail.com
 * @date 2023-03-09 19:31:24
 */
public interface WareOrderTaskService extends IService<WareOrderTaskEntity> {

    PageUtils queryPage(Map<String, Object> params);

    WareOrderTaskEntity getOrderTaskByOrderSn(String orderSn);
}

