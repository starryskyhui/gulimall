package com.atguigu.gulimall.gulimallproduct.app;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import com.atguigu.gulimall.gulimallproduct.entity.AttrEntity;
import com.atguigu.gulimall.gulimallproduct.service.AttrAttrgroupRelationService;
import com.atguigu.gulimall.gulimallproduct.service.AttrService;
import com.atguigu.gulimall.gulimallproduct.service.CategoryService;
import com.atguigu.gulimall.gulimallproduct.vo.AttrGroupRelationVo;
import com.atguigu.gulimall.gulimallproduct.vo.AttrGroupWithAttrsVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.atguigu.gulimall.gulimallproduct.entity.AttrGroupEntity;
import com.atguigu.gulimall.gulimallproduct.service.AttrGroupService;
import com.atguigu.common.utils.PageUtils;
import com.atguigu.common.utils.R;



/**
 * 属性分组
 *
 * @author zgh
 * @email sunlightcs@gmail.com
 * @date 2023-03-08 21:53:50
 */
@RestController
@RequestMapping("gulimallproduct/attrgroup")
public class AttrGroupController {
    @Autowired
    private AttrGroupService attrGroupService;

    @Autowired
    private CategoryService categoryService;

    @Autowired
    AttrService attrService;

    @Autowired
    AttrAttrgroupRelationService relationService;

    @PostMapping("/attr/relation")
    public R addRelation(@RequestBody List<AttrGroupRelationVo> vos) {

        relationService.saveBatch(vos);
        return R.ok();
    }

    @GetMapping("/{attrGroupId}/attr/relation")
    public R attrRelation(@PathVariable("attrGroupId") Long attrGroupId) {
        List<AttrEntity> entities =attrService.getRalationAttr(attrGroupId);
        return R.ok().put("data", entities);
    }

    @GetMapping("/{attrGroupId}/noattr/relation")
    public R attrNoRelation(@RequestParam Map<String, Object> params,
                            @PathVariable("attrGroupId") Long attrGroupId) {
        PageUtils pageUtils = attrService.getNoRalationAttr(params, attrGroupId);
        return R.ok().put("page", pageUtils);
    }

    @GetMapping("/{catelogId}/withattr")
    public R getAttrGroupWithAttrs(@PathVariable("catelogId") Long catlogId) {
        // 1.查询当前分类下的所有属性分组
        // 2.查出每个属性分组的所有属性
        List<AttrGroupWithAttrsVo> vos = attrGroupService.getAttrGroupWithAttrByCatelogId(catlogId);
        return R.ok().put("data", vos);
    }
    @PostMapping("/attr/relation/delete")
    public R deleteRelation(@RequestBody AttrGroupRelationVo[] vos) {
        attrService.deleteRelation(vos);
        return R.ok();
    }

    /**
     * 列表
     */
    @RequestMapping("/list/{catelogId}")
    //@RequiresPermissions("gulimallproduct:attrgroup:list")
    public R list(@RequestParam Map<String, Object> params,
                  @PathVariable("catelogId") Long catelogId){
        PageUtils page = attrGroupService.queryPage(params, catelogId);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{attrGroupId}")
    //@RequiresPermissions("gulimallproduct:attrgroup:info")
    public R info(@PathVariable("attrGroupId") Long attrGroupId){
		AttrGroupEntity attrGroup = attrGroupService.getById(attrGroupId);

        Long catelogId = attrGroup.getCatelogId();
        Long[] path = categoryService.findCatelogPath(catelogId);

        attrGroup.setCatelogPath(path);

        return R.ok().put("attrGroup", attrGroup);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    //@RequiresPermissions("gulimallproduct:attrgroup:save")
    public R save(@RequestBody AttrGroupEntity attrGroup){
		attrGroupService.save(attrGroup);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    //@RequiresPermissions("gulimallproduct:attrgroup:update")
    public R update(@RequestBody AttrGroupEntity attrGroup){
		attrGroupService.updateById(attrGroup);

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    //@RequiresPermissions("gulimallproduct:attrgroup:delete")
    public R delete(@RequestBody Long[] attrGroupIds){
		attrGroupService.removeByIds(Arrays.asList(attrGroupIds));

        return R.ok();
    }

}
