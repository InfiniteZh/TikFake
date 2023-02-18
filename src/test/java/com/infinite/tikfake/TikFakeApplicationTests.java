package com.infinite.tikfake;

import com.infinite.tikfake.service.VideoService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.util.DigestUtils;

@SpringBootTest
class TikFakeApplicationTests {
	@Autowired
	VideoService videoService;

	@Test
	void contextLoads() {
	}

	@Test
	void md5Test(){
		String md5Hash = DigestUtils.md5DigestAsHex("123456".getBytes());
		System.out.println(md5Hash);
	}
}
