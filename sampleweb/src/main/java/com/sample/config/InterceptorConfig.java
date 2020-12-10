package com.sample.config;

import com.sample.config.aware.MapperInstantiationAwareBeanProcessor;
import com.sample.interceptor.MyBatisInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @Author lidengjin
 * @Date 2020/12/9 6:02 下午
 * @Version 1.0
 */
@Configuration
public class InterceptorConfig {

	@Bean
	public MapperInstantiationAwareBeanProcessor mapperInstantiationAwareBeanProcessor(MyBatisInterceptor myBatisInterceptor){
		MapperInstantiationAwareBeanProcessor mapperInstantiationAwareBeanProcessor = new MapperInstantiationAwareBeanProcessor();
		mapperInstantiationAwareBeanProcessor.setInterceptorNames("myBatisInterceptor");
		return mapperInstantiationAwareBeanProcessor;
	}

	@Bean
	public MyBatisInterceptor myBatisInterceptor(){
		return new MyBatisInterceptor();
	}
}
