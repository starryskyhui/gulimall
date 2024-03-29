package com.atguigu.gulimall.gulimallproduct;

//import com.aliyun.oss.OSSClient;
import com.atguigu.gulimall.gulimallproduct.entity.BrandEntity;
import com.atguigu.gulimall.gulimallproduct.service.BrandService;
import com.atguigu.gulimall.gulimallproduct.service.CategoryService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Arrays;

@Slf4j
@SpringBootTest
class GulimallProductApplicationTests {

	@Autowired
	BrandService brandService;

//	@Autowired
//	OSSClient ossClient;

	@Autowired
	CategoryService categoryService;

	@Test
	public void testFindPath() {
		Long[] catelogPath = categoryService.findCatelogPath(225L);
		log.info("完整路径：{}", Arrays.asList(catelogPath));
	}

	@Test
	void contextLoads() {
		BrandEntity brandEntity = new BrandEntity();
		brandEntity.setName("华为3");
		brandService.save(brandEntity);
		System.out.println("保存成功");
		BrandEntity byId = brandService.getById(1);
		System.out.println(byId.getName());
	}

//	@Test
//	public void testUpload() throws FileNotFoundException {
//		InputStream inputStream = new FileInputStream("E:\\BaiduNetdiskDownload\\谷粒商城\\Guli Mall\\分布式基础\\资源\\pics\\23d9fbb256ea5d4a.jpg");
//		ossClient.putObject("gulimall-starrysky", "23d9fbb256ea5d4a.jpg", inputStream);
//		ossClient.shutdown();
//		System.out.println("上传完成。。。");
//	}

}
