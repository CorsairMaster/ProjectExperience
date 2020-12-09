package com.sample;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @Author lidengjin
 * @Date 2020/11/19 3:19 下午
 * @Version 1.0
 */
@SpringBootApplication(scanBasePackages = {"com.sample"})
@MapperScan("com.sample.mapper")
public class SampleStartAPP {
	public static void main(String[] args) {
		SpringApplication.run(SampleStartAPP.class, args);
	}

}
