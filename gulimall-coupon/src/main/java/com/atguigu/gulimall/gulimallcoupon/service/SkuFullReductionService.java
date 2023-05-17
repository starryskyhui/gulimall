package com.atguigu.gulimall.gulimallcoupon.service;

import com.atguigu.common.to.SkuReductionTo;
import com.baomidou.mybatisplus.extension.service.IService;
import com.atguigu.common.utils.PageUtils;
import com.atguigu.gulimall.gulimallcoupon.entity.SkuFullReductionEntity;

import java.util.Map;

/**
 * 商品满减信息
 *
 * @author zgh
 * @email sunlightcs@gmail.com
 * @date 2023-03-09 19:17:19
 */
public interface SkuFullReductionService extends IService<SkuFullReductionEntity> {

    PageUtils queryPage(Map<String, Object> params);

    void saveSkuRedution(SkuReductionTo reductionTo);
}

