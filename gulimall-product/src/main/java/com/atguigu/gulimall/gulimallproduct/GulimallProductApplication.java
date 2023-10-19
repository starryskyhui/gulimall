package com.atguigu.gulimall.gulimallproduct;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;

/**
 * @author 郑国辉
 *
 * 5、模板引擎
 *  1）、thymeleaf-starter: 关闭缓存
 *  2）、静态资源放在static文件夹下就可以按照路径直接访问
 *  3）、页面放在templates下，直接访问
 *  	Spring boot，访问项目的时候，默认会找index
 *  4）、页面修改不重启服务器实时更新
 *  	1）、引入dev-tools
 *  	2）、修改完页面后 ctrl+F9 编译后自动生动生效
 * 6、整合redis
 * 	1）、引入data-redis-starter
 * 	2）、简单配置redis的host等信息
 * 	3）、使用SpringBoot自动配置好的StringRedisTemplate来操作redis
 * 7、整合redisson作为分布式锁等功能框架
 * 	1）、引入依赖
 * 	2）、Redisson优势：锁的自动续期，如果业务超长，运行期间自动给续上新的30秒。不用担心业务时间长，锁自动过期被删掉；
 * 					 加锁的业务只要运行完成，就不会给当前锁续期，即使不手动解锁，锁默认在30秒以后自动删除
 * 8、整合SpringCache简化缓存开发
 * 	1）、引入依赖spring-boot-starter-cache spring-boot-starter-data-redis
 * 	2）、写配置
 *		（1）、自动配置了哪些
 *		（2）、配置使用redis作为缓存
 * 3）、测试使用缓存
 * 		1）、开始缓存
 * 		2）、使用注解
 */
@EnableRedisHttpSession
@EnableCaching
@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignClients(basePackages = "com.atguigu.gulimall.gulimallproduct.feign")
public class GulimallProductApplication {

	public static void main(String[] args) {
		SpringApplication.run(GulimallProductApplication.class, args);
	}

}
