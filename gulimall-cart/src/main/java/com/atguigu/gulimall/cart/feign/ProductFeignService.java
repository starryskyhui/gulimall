package com.atguigu.gulimall.cart.feign;

import com.atguigu.common.utils.R;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.math.BigDecimal;
import java.util.List;

/**
 * @author 郑国辉
 */
@FeignClient("gulimall-product")
public interface ProductFeignService {
    @RequestMapping("/gulimallproduct/skuinfo/info/{skuId}")
    R getSkuInfo(@PathVariable("skuId") Long skuId);

    @GetMapping("/gulimallproduct/skuinfo/stringlist/{skuId}")
    List<String> getSkuSaleAttrValues(@PathVariable("skuId") Long skuId);

    @GetMapping(value = "/gulimallproduct/skuinfo/{skuId}/price")
    R getPrice(@PathVariable("skuId") Long skuId);
}
