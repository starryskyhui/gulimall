package com.atguigu.gulimall.gulimallware.vo;

import lombok.Data;

import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * @author 郑国辉
 */
@Data
public class PurchaseDoneVo {
    @NotNull
    private Long id; // 采购单id
    private List<PurchaseItemDoneVo> items;
}
