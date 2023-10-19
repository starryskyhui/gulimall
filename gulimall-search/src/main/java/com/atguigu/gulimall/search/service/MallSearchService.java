package com.atguigu.gulimall.search.service;

import com.atguigu.gulimall.search.vo.SearchParam;
import com.atguigu.gulimall.search.vo.SearchResult;

import java.io.IOException;

/**
 * @author 郑国辉
 */
public interface MallSearchService {
    SearchResult search(SearchParam param) throws IOException;
}
