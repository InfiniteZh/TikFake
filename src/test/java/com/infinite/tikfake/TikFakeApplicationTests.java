package com.infinite.tikfake;

import com.infinite.tikfake.entity.Video;
import com.infinite.tikfake.service.VideoService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class TikFakeApplicationTests {
	@Autowired
	VideoService videoService;

	@Test
	void contextLoads() {
	}

	@Test
	void testVideoService(){
		Video videoDemo = videoService.getVideoDemo();
		System.out.println(videoDemo);
	}
}
