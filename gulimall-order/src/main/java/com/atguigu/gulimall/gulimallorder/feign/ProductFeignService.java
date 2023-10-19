package com.atguigu.gulimall.gulimallorder.feign;

import com.atguigu.common.utils.R;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * @author 郑国辉
 */
@FeignClient("gulimall-product")
public interface ProductFeignService {
    @GetMapping(value = "/gulimallproduct/spuinfo/skuId/{skuId}")
    R getSpuInfoBySkuId(@PathVariable("skuId") Long skuId);
}
