package com.atguigu.gulimall.gulimallorder.feign;

import com.atguigu.gulimall.gulimallorder.vo.OrderItemVo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * @author 郑国辉
 */
@FeignClient("gulimall-cart")
public interface CartFeignService {
    @GetMapping(value = "/currentUserCartItems")
    @ResponseBody
    List<OrderItemVo> getCurrentCartItems();
}
