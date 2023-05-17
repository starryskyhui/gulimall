package com.atguigu.common.to;

import lombok.Data;

import java.math.BigDecimal;

/**
 * @author 郑国辉
 */
@Data
public class SpuBoundTo {
    private Long spuId;
    private BigDecimal buyBounds;
    private BigDecimal growBounds;
}
