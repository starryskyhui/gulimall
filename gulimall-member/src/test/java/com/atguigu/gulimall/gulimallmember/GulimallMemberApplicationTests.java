package com.atguigu.gulimall.gulimallmember;

import org.apache.commons.codec.digest.Md5Crypt;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class GulimallMemberApplicationTests {

	@Test
	void contextLoads() {
		String s1 = Md5Crypt.md5Crypt("123456".getBytes(), "$1$qqqqqqqq");

		System.out.println(s1);
	}


}
