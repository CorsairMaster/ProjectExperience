package com.sample.interceptor;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cglib.core.Constants;

/**
 * @Author lidengjin
 * @Date 2020/12/10 2:03 下午
 * @Version 1.0
 */
public class HadesInterceptor implements MethodInterceptor, Constants {

	private final Logger logger = LoggerFactory.getLogger(getClass());

	@Override
	public Object invoke(MethodInvocation invocation) throws Throwable {
		logger.info("hades  begin ");
		Object proceed = invocation.proceed();
		logger.info("hades end .return value is {}", proceed.toString());
		return proceed;
	}
}
