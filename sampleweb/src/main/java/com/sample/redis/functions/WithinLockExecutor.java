package com.sample.redis.functions;

/**
 * @Author lidengjin
 * @Date 2020/12/9 11:01 上午
 * @Version 1.0
 */
@FunctionalInterface
public interface WithinLockExecutor<T> {
	T execute();
}