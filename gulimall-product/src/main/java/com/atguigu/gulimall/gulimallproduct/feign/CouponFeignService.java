package com.atguigu.gulimall.gulimallproduct.feign;

import com.atguigu.common.to.SkuReductionTo;
import com.atguigu.common.to.SpuBoundTo;
import com.atguigu.common.utils.R;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * @author 郑国辉
 */
@FeignClient("gulimall-coupon")
public interface CouponFeignService {

    /**
     * 只要json数据模型是兼容的，双方服务无需使用同一个to
     * @param spuBoundTo
     * @return
     */
    @PostMapping("/gulimallcoupon/spubounds/save")
    R saveSpuBounds(@RequestBody SpuBoundTo spuBoundTo);

    @PostMapping("/gulimallcoupon/skufullreduction/saveInfo")
    R saveSkuReduction(@RequestBody SkuReductionTo skuReductionTo);
}
