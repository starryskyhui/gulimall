package com.atguigu.gulimall.gulimallproduct.service.impl;

import com.atguigu.gulimall.gulimallproduct.entity.CategoryBrandRelationEntity;
import com.atguigu.gulimall.gulimallproduct.service.CategoryBrandRelationService;
import com.mysql.cj.log.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.atguigu.common.utils.PageUtils;
import com.atguigu.common.utils.Query;

import com.atguigu.gulimall.gulimallproduct.dao.CategoryDao;
import com.atguigu.gulimall.gulimallproduct.entity.CategoryEntity;
import com.atguigu.gulimall.gulimallproduct.service.CategoryService;
import org.springframework.transaction.annotation.Transactional;


/**
 * @author 郑国辉
 */
@Service("categoryService")
public class CategoryServiceImpl extends ServiceImpl<CategoryDao, CategoryEntity> implements CategoryService {

    @Autowired
    CategoryBrandRelationService categoryBrandRelationService;

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<CategoryEntity> page = this.page(
                new Query<CategoryEntity>().getPage(params),
                new QueryWrapper<CategoryEntity>()
        );

        return new PageUtils(page);
    }

    @Override
    public List<CategoryEntity> listWithTree() {
        List<CategoryEntity> categoryEntities = baseMapper.selectList(null);

        List<CategoryEntity> level1Menus = categoryEntities.stream()
                .filter(categoryEntity -> categoryEntity.getParentCid() == 0)
                .map((categoryEntity) -> {
                    categoryEntity.setChildren(getChildren(categoryEntity, categoryEntities));
                    return categoryEntity;
                })
                .sorted((menu1, menu2) -> (menu1.getSort()==null?0:menu1.getSort())-(menu2.getSort()==null?0:menu2.getSort()))
                .collect(Collectors.toList());

        return level1Menus;
    }

    @Override
    public void removeMenuByIds(List<Long> asList) {
        //TODO 1、检查当前删除的菜单，是否被别的地方引用
        baseMapper.deleteBatchIds(asList);
    }

    @Override
    public Long[] findCatelogPath(Long catelogId) {
        List<Long> paths = new ArrayList<>();
        findPatentPath(catelogId, paths);
        Collections.reverse(paths);
        return paths.toArray(new Long[0]);
    }

    @Transactional
    @Override
    public void updateCascade(CategoryEntity category) {
        updateById(category);
        categoryBrandRelationService.updateCategory(category.getCatId(), category.getName());
    }

    private void findPatentPath(Long catelogId, List<Long> paths) {
        // 1. 收集当前节点的id
        paths.add(catelogId);
        CategoryEntity byId = this.getById(catelogId);
        if (byId.getParentCid() != 0) {
            findPatentPath(byId.getParentCid(), paths);
        }
    }

    private List<CategoryEntity> getChildren(CategoryEntity entity, List<CategoryEntity> all) {
        List<CategoryEntity> children = all.stream()
                .filter(categoryEntity -> categoryEntity.getParentCid().equals(entity.getCatId()))
                .map((categoryEntity) -> {
                    categoryEntity.setChildren(getChildren(categoryEntity, all));
                    return categoryEntity;
                })
                .sorted((menu1, menu2) -> (menu1.getSort()==null?0:menu1.getSort())-(menu2.getSort()==null?0:menu2.getSort()))
                .collect(Collectors.toList());
        return children;
    }

}