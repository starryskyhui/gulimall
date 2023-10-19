package com.atguigu.gulimall.search.service;

import com.atguigu.common.to.es.SkuEsModel;

import java.io.IOException;
import java.util.List;

/**
 * @author 郑国辉
 */
public interface ProductSaveService {
    boolean productStatusUp(List<SkuEsModel> skuEsModels) throws IOException;
}
