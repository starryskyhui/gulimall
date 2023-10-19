package com.atguigu.gulimall.gulimallproduct.vo;

import lombok.Data;
import lombok.ToString;

import java.util.List;

/**
 * @author 郑国辉
 */
@ToString
@Data
public class SpuItemAttrGroupVo {
    private String groupName;
    private List<Attr> attrs;
}