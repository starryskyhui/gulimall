package com.atguigu.gulimall.gateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/*
*
*
* 3、JSR303
*  1)、给Bean添加校验注解：jacax.validation.constraints,并定义自己的message提示
*  2）、开启校验功能@Valid
* 	效果，校验错误后会有默认的响应；
*  3)、给校验的bean后紧跟一个BindingResult，就可以获取到校验的结果
*  4)、分组校验（多场景的复杂校验）
* 		1)、@NotBlank(message="品牌名必须提交", group={})
* 		2)、@Validated({AddGroup.class})
* 		3)、默认没有指定分组的校验注解@NotBlank,在分组校验情况@Validated({AddGroup.class})下不生效
*  5）、自定义校验
* 	1）、编写一个自定义校验注解
*   2）、编写一个自定义的校验器
*   3）、关联自定义的校验器和自定义的校验注解
* */
@EnableDiscoveryClient
@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})
public class GulimallGatewayApplication {

	public static void main(String[] args) {
		SpringApplication.run(GulimallGatewayApplication.class, args);
	}

}
