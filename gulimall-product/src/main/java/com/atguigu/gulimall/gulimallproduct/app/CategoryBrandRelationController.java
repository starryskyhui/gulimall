package com.atguigu.gulimall.gulimallproduct.app;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.atguigu.gulimall.gulimallproduct.entity.BrandEntity;
import com.atguigu.gulimall.gulimallproduct.vo.BrandVo;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.atguigu.gulimall.gulimallproduct.entity.CategoryBrandRelationEntity;
import com.atguigu.gulimall.gulimallproduct.service.CategoryBrandRelationService;
import com.atguigu.common.utils.PageUtils;
import com.atguigu.common.utils.R;



/**
 * 品牌分类关联
 *
 * @author zgh
 * @email sunlightcs@gmail.com
 * @date 2023-03-08 21:53:50
 */
@RestController
@RequestMapping("gulimallproduct/categorybrandrelation")
public class CategoryBrandRelationController {
    @Autowired
    private CategoryBrandRelationService categoryBrandRelationService;

    /**
     * 列表
     */
    @GetMapping("/catelog/list")
    //@RequiresPermissions("gulimallproduct:categorybrandrelation:list")
    public R catelogList(@RequestParam("brandId") Long brandId){
        List<CategoryBrandRelationEntity> data = categoryBrandRelationService.list(
                new QueryWrapper<CategoryBrandRelationEntity>().eq("brand_id", brandId)
        );

        return R.ok().put("data", data);
    }

    /**
     *
     * 1. Controller: 处理请求，接受和校验数据
     * 2. Service：接受controller传来的数据，进行业务处理
     * 3. Controller: 接受Service处理完的数据，封装页面指定的vo
     */
    @GetMapping("/brands/list")
    public R relationBrandsList(@RequestParam(value="catId", required = true) Long catId) {
        List<BrandEntity> entities = categoryBrandRelationService.getBrandsByCatId(catId);
        List<BrandVo> collect = entities.stream().map(item -> {
            BrandVo brandVo = new BrandVo();
            brandVo.setBrandName(item.getName());
            brandVo.setBrandId(item.getBrandId());
            return brandVo;
        }).collect(Collectors.toList());

        return R.ok().put("data", collect);
    }

    /**
     * 列表
     */
    @RequestMapping("/list")
    //@RequiresPermissions("gulimallproduct:categorybrandrelation:list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = categoryBrandRelationService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    //@RequiresPermissions("gulimallproduct:categorybrandrelation:info")
    public R info(@PathVariable("id") Long id){
		CategoryBrandRelationEntity categoryBrandRelation = categoryBrandRelationService.getById(id);

        return R.ok().put("categoryBrandRelation", categoryBrandRelation);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    //@RequiresPermissions("gulimallproduct:categorybrandrelation:save")
    public R save(@RequestBody CategoryBrandRelationEntity categoryBrandRelation){
		categoryBrandRelationService.saveDetail(categoryBrandRelation);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    //@RequiresPermissions("gulimallproduct:categorybrandrelation:update")
    public R update(@RequestBody CategoryBrandRelationEntity categoryBrandRelation){
		categoryBrandRelationService.updateById(categoryBrandRelation);

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    //@RequiresPermissions("gulimallproduct:categorybrandrelation:delete")
    public R delete(@RequestBody Long[] ids){
		categoryBrandRelationService.removeByIds(Arrays.asList(ids));

        return R.ok();
    }

}
