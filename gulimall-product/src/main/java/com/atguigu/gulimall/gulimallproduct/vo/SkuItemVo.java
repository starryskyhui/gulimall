package com.atguigu.gulimall.gulimallproduct.vo;

import com.atguigu.gulimall.gulimallproduct.entity.SkuImagesEntity;
import com.atguigu.gulimall.gulimallproduct.entity.SkuInfoEntity;
import com.atguigu.gulimall.gulimallproduct.entity.SpuInfoDescEntity;
import lombok.Data;

import java.util.List;

/**
 * @author 郑国辉
 */
@Data
public class SkuItemVo {
    // 1、sku基本信息获取 pms_sku_info
    SkuInfoEntity info;

    boolean hasStock = true;

    // 2、sku图片信息 pms_sku_images
    List<SkuImagesEntity> images;

    // 3、获取spu的销售属性组合
    List<SkuItemSaleAttrVo> saleAttr;

    // 4、获取spu的介绍
    SpuInfoDescEntity desp;

    // 5、获取spu的规格参数信息
    List<SpuItemAttrGroupVo> groupAttrs;

    //6、秒杀商品的优惠信息
    private SeckillSkuVo seckillSkuVo;

}
