package com.atguigu.gulimall.gulimallthirdparty;

import com.atguigu.gulimall.gulimallthirdparty.component.SmsComponent;
import com.atguigu.gulimall.gulimallthirdparty.util.HttpUtils;
import org.apache.http.HttpResponse;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.HashMap;
import java.util.Map;

@SpringBootTest
class GulimallThirdPartyApplicationTests {

	@Autowired
	SmsComponent smsComponent;

	@Test
	void contextLoads() {
	}

	@Test
	void sendMessage() {
		smsComponent.sendMessage("19821216995", "1234");
	}

}
