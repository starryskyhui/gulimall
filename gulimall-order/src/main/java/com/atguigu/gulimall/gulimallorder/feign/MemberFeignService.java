package com.atguigu.gulimall.gulimallorder.feign;

import com.atguigu.gulimall.gulimallorder.vo.MemberAddressVo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

/**
 * @author 郑国辉
 */
@FeignClient("gulimall-member")
public interface MemberFeignService {

    @GetMapping(value = "/gulimallmember/memberreceiveaddress/{memberId}/address")
    List<MemberAddressVo> getAddress(@PathVariable("memberId") Long memberId);
}
