package com.atguigu.gulimall.gulimallware.service;

import com.atguigu.common.to.OrderTo;
import com.atguigu.common.to.SkuHasStockVo;
import com.atguigu.common.to.mq.StockLockedTo;
import com.atguigu.gulimall.gulimallware.vo.WareSkuLockVo;
import com.baomidou.mybatisplus.extension.service.IService;
import com.atguigu.common.utils.PageUtils;
import com.atguigu.gulimall.gulimallware.entity.WareSkuEntity;

import java.util.List;
import java.util.Map;

/**
 * 商品库存
 *
 * @author zgh
 * @email sunlightcs@gmail.com
 * @date 2023-03-09 19:31:24
 */
public interface WareSkuService extends IService<WareSkuEntity> {

    PageUtils queryPage(Map<String, Object> params);

    void addStock(Long skuId, Long wareId, Integer skuNum);

    List<SkuHasStockVo> getSkusHasStock(List<Long> skuIds);

    boolean orderLockStock(WareSkuLockVo vo);

    /**
     * 解锁库存
     * @param to
     */
    void unlockStock(StockLockedTo to);

    /**
     * 解锁订单
     * @param orderTo
     */
    void unlockStock(OrderTo orderTo);
}

