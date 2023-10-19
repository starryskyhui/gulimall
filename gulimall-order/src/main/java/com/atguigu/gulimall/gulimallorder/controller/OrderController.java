package com.atguigu.gulimall.gulimallorder.controller;

import java.util.Arrays;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.atguigu.gulimall.gulimallorder.entity.OrderEntity;
import com.atguigu.gulimall.gulimallorder.service.OrderService;
import com.atguigu.common.utils.PageUtils;
import com.atguigu.common.utils.R;



/**
 * 订单
 *
 * @author zgh
 * @email sunlightcs@gmail.com
 * @date 2023-03-09 19:25:44
 */
@RestController
@RequestMapping("gulimallorder/order")
public class OrderController {
    @Autowired
    private OrderService orderService;

    /**
     * 分页查询当前登录用户的所有订单信息
     * @param params
     * @return
     */
    @PostMapping("/listWithItem")
    //@RequiresPermissions("order:order:list")
    public R listWithItem(@RequestBody Map<String, Object> params){
        PageUtils page = orderService.queryPageWithItem(params);

        return R.ok().put("page", page);
    }

    /**
     * 根据订单编号查询订单状态
     * @param orderSn
     * @return
     */
    @GetMapping(value = "/status/{orderSn}")
    public R getOrderStatus(@PathVariable("orderSn") String orderSn) {
        OrderEntity orderEntity = orderService.getOrderByOrderSn(orderSn);
        return R.ok().setData(orderEntity);
    }

    /**
     * 列表
     */
    @RequestMapping("/list")
    //@RequiresPermissions("gulimallorder:order:list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = orderService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    //@RequiresPermissions("gulimallorder:order:info")
    public R info(@PathVariable("id") Long id){
		OrderEntity order = orderService.getById(id);

        return R.ok().put("order", order);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    //@RequiresPermissions("gulimallorder:order:save")
    public R save(@RequestBody OrderEntity order){
		orderService.save(order);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    //@RequiresPermissions("gulimallorder:order:update")
    public R update(@RequestBody OrderEntity order){
		orderService.updateById(order);

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    //@RequiresPermissions("gulimallorder:order:delete")
    public R delete(@RequestBody Long[] ids){
		orderService.removeByIds(Arrays.asList(ids));

        return R.ok();
    }

}
