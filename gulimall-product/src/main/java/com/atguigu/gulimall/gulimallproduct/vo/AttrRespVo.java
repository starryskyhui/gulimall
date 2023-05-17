package com.atguigu.gulimall.gulimallproduct.vo;

import lombok.Data;

/**
 * @author 郑国辉
 */
@Data
public class AttrRespVo extends AttrVo {
    /**
     * 所属分类名字
     */
    private String catelogName;

    /**
     *  所属分组名字
     */
    private String groupName;

    private Long[] catelogPath;
}
