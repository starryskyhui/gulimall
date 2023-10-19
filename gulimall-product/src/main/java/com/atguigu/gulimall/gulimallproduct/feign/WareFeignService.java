package com.atguigu.gulimall.gulimallproduct.feign;

import com.atguigu.common.to.SkuHasStockVo;
import com.atguigu.common.utils.R;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

/**
 * @author 郑国辉
 */
@FeignClient("gulimall-ware")
public interface WareFeignService {
    @PostMapping("/gulimallware/waresku/hasStock")
    R getSkusHasStock(@RequestBody List<Long> skuIds);
}
