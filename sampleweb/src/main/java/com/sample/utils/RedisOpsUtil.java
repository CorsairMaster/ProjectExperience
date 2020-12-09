package com.sample.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;

import java.util.concurrent.TimeUnit;

/**
 * @Author lidengjin
 * @Date 2020/11/19 3:39 下午
 * @Version 1.0
 */
public class RedisOpsUtil<T> {

	@Autowired
	private RedisTemplate redisTemplate;

	public void set(String key,T value){
		redisTemplate.opsForValue().set(key,value);
	}

	public void set(String key, T value, long timeout, TimeUnit unit){
		redisTemplate.opsForValue().set(key,value,timeout,unit);
	}

	public <T> T get(String key,Class<?> T){
		return (T)redisTemplate
				.opsForValue().get(key);
	}

	public String get(String key){
		return (String) redisTemplate
				.opsForValue().get(key);
	}

	public Long decr(String key){
		return redisTemplate
				.opsForValue().decrement(key);
	}

	public Long decr(String key,long delta){
		return redisTemplate
				.opsForValue().decrement(key,delta);
	}

	public Long incr(String key){
		return redisTemplate
				.opsForValue().increment(key);
	}

	public Long incr(String key,long delta){
		return redisTemplate
				.opsForValue().increment(key,delta);
	}

	public boolean expire(String key,long timeout,TimeUnit unit){
		return redisTemplate.expire(key,timeout, unit);
	}

	public boolean delete(String key){
		return redisTemplate.delete(key);
	}

	public boolean hasKey(String key){
		return redisTemplate.hasKey(key);
	}

	/**
	 * 发布channel信息
	 * @param channel
	 * @param message
	 */
	public void publish(String channel,Object message){
		redisTemplate.convertAndSend(channel,message);
	}
}
