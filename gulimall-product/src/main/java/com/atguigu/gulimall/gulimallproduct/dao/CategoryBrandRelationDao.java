package com.atguigu.gulimall.gulimallproduct.dao;

import com.atguigu.gulimall.gulimallproduct.entity.CategoryBrandRelationEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * 品牌分类关联
 * 
 * @author zgh
 * @email sunlightcs@gmail.com
 * @date 2023-03-08 21:53:50
 */
@Mapper
public interface CategoryBrandRelationDao extends BaseMapper<CategoryBrandRelationEntity> {

    void updateCategory(@Param("catId") Long catId, @Param("name") String name);
}
