package com.atguigu.gulimall.gulimallproduct.dao;

import com.atguigu.gulimall.gulimallproduct.entity.AttrGroupEntity;
import com.atguigu.gulimall.gulimallproduct.vo.SkuItemVo;
import com.atguigu.gulimall.gulimallproduct.vo.SpuItemAttrGroupVo;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 属性分组
 * 
 * @author zgh
 * @email sunlightcs@gmail.com
 * @date 2023-03-08 21:53:50
 */
@Mapper
public interface AttrGroupDao extends BaseMapper<AttrGroupEntity> {

    List<SpuItemAttrGroupVo> getAttrGroupWithAttrsBySpuId(@Param("spuId") Long spuId, @Param("catalogId") Long catalogId);
}
