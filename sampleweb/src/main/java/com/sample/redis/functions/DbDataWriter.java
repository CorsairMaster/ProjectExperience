package com.sample.redis.functions;

/**
 * @Author lidengjin
 * @Date 2020/12/9 10:51 上午
 * @Version 1.0
 */
@FunctionalInterface
public interface DbDataWriter<T> {
	T writeDb();
}
