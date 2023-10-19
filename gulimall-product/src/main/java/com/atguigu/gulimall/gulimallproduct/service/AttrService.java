package com.atguigu.gulimall.gulimallproduct.service;

import com.atguigu.gulimall.gulimallproduct.vo.AttrGroupRelationVo;
import com.atguigu.gulimall.gulimallproduct.vo.AttrRespVo;
import com.atguigu.gulimall.gulimallproduct.vo.AttrVo;
import com.baomidou.mybatisplus.extension.service.IService;
import com.atguigu.common.utils.PageUtils;
import com.atguigu.gulimall.gulimallproduct.entity.AttrEntity;

import java.util.List;
import java.util.Map;

/**
 * 商品属性
 *
 * @author zgh
 * @email sunlightcs@gmail.com
 * @date 2023-03-08 21:57:00
 */
public interface AttrService extends IService<AttrEntity> {

    PageUtils queryPage(Map<String, Object> params);

    void saveAttr(AttrVo attr);

    PageUtils queryBaseAttrPage(Map<String, Object> params, Long catelogId, String attrType);

    AttrRespVo getAttrInfo(Long attrId);

    void updateAttr(AttrVo attr);

    List<AttrEntity> getRalationAttr(Long attrGroupId);

    void deleteRelation(AttrGroupRelationVo[] vos);

    PageUtils getNoRalationAttr(Map<String, Object> params, Long attrGroupId);

    /**
     * 在指定的所有属性集合挑出检索属性
     * @param attrIds
     * @return
     */
    List<Long> selectSearchAttrs(List<Long> attrIds);
}

