package com.atguigu.gulimall.gulimallproduct.service;

import com.atguigu.gulimall.gulimallproduct.vo.SkuItemVo;
import com.baomidou.mybatisplus.extension.service.IService;
import com.atguigu.common.utils.PageUtils;
import com.atguigu.gulimall.gulimallproduct.entity.SkuInfoEntity;

import java.util.List;
import java.util.Map;

/**
 * sku信息
 *
 * @author zgh
 * @email sunlightcs@gmail.com
 * @date 2023-03-08 20:10:13
 */
public interface SkuInfoService extends IService<SkuInfoEntity> {

    PageUtils queryPage(Map<String, Object> params);

    void saveSkuInfo(SkuInfoEntity skuInfoEntity);

    PageUtils queryPageByCondition(Map<String, Object> params);

    List<SkuInfoEntity> getSkusBySpuId(Long spuId);

    SkuItemVo item(Long skuId);
}

