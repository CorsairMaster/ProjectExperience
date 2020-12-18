package com.sample.config.mvc;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @Author lidengjin
 * @Date 2020/12/18 3:28 下午
 * @Version 1.0
 */

@Component
public class WebConfig implements WebMvcConfigurer {
	/*
	 * 添加静态资源文件，外部可以直接访问地址
	 *
	 * @param registry
	 */

	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		registry.addResourceHandler("/static/**").addResourceLocations("classpath:/static/");
	}
}

