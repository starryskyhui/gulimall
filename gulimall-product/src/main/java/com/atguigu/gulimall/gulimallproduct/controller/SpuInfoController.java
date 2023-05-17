package com.atguigu.gulimall.gulimallproduct.controller;

import java.util.Arrays;
import java.util.Map;

import com.atguigu.gulimall.gulimallproduct.vo.SpuSaveVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.atguigu.gulimall.gulimallproduct.entity.SpuInfoEntity;
import com.atguigu.gulimall.gulimallproduct.service.SpuInfoService;
import com.atguigu.common.utils.PageUtils;
import com.atguigu.common.utils.R;



/**
 * spu信息
 *
 * @author zgh
 * @email sunlightcs@gmail.com
 * @date 2023-03-08 20:10:13
 */
@RestController
@RequestMapping("gulimallproduct/spuinfo")
public class SpuInfoController {
    @Autowired
    private SpuInfoService spuInfoService;

    /**
     * 列表
     */
    @RequestMapping("/list")
//    @RequiresPermissions("gulimallproduct:spuinfo:list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = spuInfoService.queryPageByCondition(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
//    @RequiresPermissions("gulimallproduct:spuinfo:info")
    public R info(@PathVariable("id") Long id){
		SpuInfoEntity spuInfo = spuInfoService.getById(id);

        return R.ok().put("spuInfo", spuInfo);
    }

    /**
     * 保存
     */
    @PostMapping("/save")
//    @RequiresPermissions("gulimallproduct:spuinfo:save")
    public R save(@RequestBody SpuSaveVo spuInfo){
//		spuInfoService.save(spuInfo);

        spuInfoService.saveSpuInfo(spuInfo);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
//    @RequiresPermissions("gulimallproduct:spuinfo:update")
    public R update(@RequestBody SpuInfoEntity spuInfo){
		spuInfoService.updateById(spuInfo);

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
//    @RequiresPermissions("gulimallproduct:spuinfo:delete")
    public R delete(@RequestBody Long[] ids){
		spuInfoService.removeByIds(Arrays.asList(ids));

        return R.ok();
    }

}
