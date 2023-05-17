package com.atguigu.gulimall.gulimallware.feign;

import com.atguigu.common.utils.R;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author 郑国辉
 */
@FeignClient("gulimall-product")
public interface ProductFeignService {

    /**
     * 过网关
     * @FeignClient("gulimall-gateway")
     * /api/product/skuinfo/info/{skuId}
     *
     * 直接请求
     * @FeignClient("gulimall-product")
     * /gulimallproduct/skuinfo/info/{skuId}
     *
     * @param skuId
     * @return
     */
    @RequestMapping("/gulimallproduct/skuinfo/info/{skuId}")
    public R info(@PathVariable("skuId") Long skuId);
}
