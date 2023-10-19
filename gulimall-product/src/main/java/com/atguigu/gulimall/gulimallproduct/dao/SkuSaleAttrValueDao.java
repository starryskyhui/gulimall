package com.atguigu.gulimall.gulimallproduct.dao;

import com.atguigu.gulimall.gulimallproduct.entity.SkuSaleAttrValueEntity;
import com.atguigu.gulimall.gulimallproduct.vo.SkuItemSaleAttrVo;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * sku销售属性&值
 * 
 * @author zgh
 * @email sunlightcs@gmail.com
 * @date 2023-03-08 20:10:13
 */
@Mapper
public interface SkuSaleAttrValueDao extends BaseMapper<SkuSaleAttrValueEntity> {

    List<SkuItemSaleAttrVo> getSaleAttrsBySpuId(@Param("spuId") Long spuId);

    List<String> getSkuSaleAttrValuesAsStringList(@Param("skuId") Long skuId);
}
