package com.atguigu.gulimall.gulimallware.vo;

import lombok.Data;

import java.util.List;

/**
 * @author 郑国辉
 */
@Data
public class MergeVo {
    private Long purchaseId;
    private List<Long> items;
}
