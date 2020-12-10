package com.sample.interceptor;

import lombok.extern.slf4j.Slf4j;
import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;

/**
 * @Author lidengjin
 * @Date 2020/12/9 5:56 下午
 * @Version 1.0
 */
@Slf4j
public class MyBatisInterceptor implements MethodInterceptor {

	@Override
	public Object invoke(MethodInvocation methodInvocation) throws Throwable {
		String arguments = "";
		Object[] var6 = methodInvocation.getArguments();
		int var7 = var6.length;

		for(int var8 = 0; var8 < var7; ++var8) {
			Object obj = var6[var8];
			arguments = arguments + obj.toString() + ", ";
		}
		log.info(" mybatis arguments {}", arguments);
		Object returnValue = methodInvocation.proceed();
		if (returnValue != null) {
			log.info("mybatis returnValue {}", returnValue.toString());
		}else {
			log.info("mybatis returnValue = null");
		}
		return returnValue;
	}
}
