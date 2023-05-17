package com.atguigu.gulimall.gulimallmember.feign;

import com.atguigu.common.utils.R;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author 郑国辉
 */
@FeignClient("gulimall-coupon")
public interface CouponFeignService {

    @RequestMapping("/gulimallcoupon/coupon/member/list")
    public R memberCoupons();

}
