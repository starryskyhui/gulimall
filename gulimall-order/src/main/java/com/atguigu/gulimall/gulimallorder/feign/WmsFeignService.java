package com.atguigu.gulimall.gulimallorder.feign;

import com.atguigu.common.utils.R;
import com.atguigu.gulimall.gulimallorder.vo.WareSkuLockVo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * @author 郑国辉
 */
@FeignClient("gulimall-ware")
public interface WmsFeignService {
    @PostMapping("/gulimallware/waresku/hasStock")
    R getSkusHasStock(@RequestBody List<Long> skuIds);

    @GetMapping(value = "/gulimallware/wareinfo/fare")
    R getFare(@RequestParam("addrId") Long addrId);

    @PostMapping(value = "/gulimallware/wareinfo/lock/order")
    R orderLockStock(@RequestBody WareSkuLockVo vo);
}
