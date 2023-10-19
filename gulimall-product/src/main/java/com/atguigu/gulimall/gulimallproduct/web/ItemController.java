package com.atguigu.gulimall.gulimallproduct.web;

import com.atguigu.gulimall.gulimallproduct.service.SkuInfoService;
import com.atguigu.gulimall.gulimallproduct.vo.SkuItemVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * @author 郑国辉
 */
@Controller
public class ItemController {

    @Autowired
    SkuInfoService skuInfoService;

    /**
     * 展示当前sku的详情
     * @param skuId
     * @return
     */
    @GetMapping("/{skuId}.html")
    public String skuItem(@PathVariable("skuId") Long skuId, Model model) {
        System.out.println("准备查询" + skuId + "详情");

        SkuItemVo vo = skuInfoService.item(skuId);
        model.addAttribute("item", vo);

        return "item";
    }
}
