package com.atguigu.gulimall.gulimallproduct.web;

import com.atguigu.gulimall.gulimallproduct.entity.CategoryEntity;
import com.atguigu.gulimall.gulimallproduct.service.CategoryService;
import com.atguigu.gulimall.gulimallproduct.vo.Catalog2Vo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
import java.util.Map;

/**
 * @author 郑国辉
 */
@Controller
public class IndexController {
    @Autowired
    CategoryService categoryService;

    @GetMapping({"/", "/index.html"})
    public String indexPage(Model model) {
        // 1.查出所有的分类
        List<CategoryEntity> categoryEntities = categoryService.getLevel1Categorys();

        // 视图解析器进行拼串
        // thymeleaf默认路径 "classpath:/templates/" ".html";
        model.addAttribute("categories", categoryEntities);
        return "index";
    }

    @ResponseBody
    @GetMapping("/index/catalog.json")
    public Map<String, List<Catalog2Vo>> getCatalogJson() {

       Map<String,List<Catalog2Vo>> catalogJson =  categoryService.getCatalogJson();
        return catalogJson;
    }
}
