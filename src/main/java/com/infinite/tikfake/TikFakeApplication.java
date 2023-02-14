package com.infinite.tikfake;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@MapperScan("com.infinite.tikfake.mapper	")
@SpringBootApplication
public class TikFakeApplication {

	public static void main(String[] args) {
		SpringApplication.run(TikFakeApplication.class, args);
	}

}
