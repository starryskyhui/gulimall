package com.atguigu.gulimall.gulimallproduct.dao;

import com.atguigu.gulimall.gulimallproduct.entity.SpuInfoEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * spu信息
 * 
 * @author zgh
 * @email sunlightcs@gmail.com
 * @date 2023-03-08 20:10:13
 */
@Mapper
public interface SpuInfoDao extends BaseMapper<SpuInfoEntity> {

    void updateSpuStatus(@Param("spuId") Long spuId, @Param("code") int code);
}
