package com.atguigu.gulimall.gulimallware.vo;

import lombok.Data;

/**
 * @author 郑国辉
 */
@Data
public class PurchaseItemDoneVo {
    private Long itemId;
    private Integer status;
    private String reason;
}
