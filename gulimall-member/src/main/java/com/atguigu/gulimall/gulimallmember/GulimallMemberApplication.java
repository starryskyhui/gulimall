package com.atguigu.gulimall.gulimallmember;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * @author 郑国辉
 */
@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignClients(basePackages="com.atguigu.gulimall.gulimallmember.feign")
public class GulimallMemberApplication {

	public static void main(String[] args) {
		SpringApplication.run(GulimallMemberApplication.class, args);
	}

}
