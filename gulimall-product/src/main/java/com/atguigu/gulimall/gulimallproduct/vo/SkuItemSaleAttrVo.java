package com.atguigu.gulimall.gulimallproduct.vo;

import lombok.Data;

import java.util.List;

/**
 * @author 郑国辉
 */
@Data
public class SkuItemSaleAttrVo {
    private Long attrId;
    private String attrName;
    private List<AttrValueWithSkuIdVo> attrValues;
}
