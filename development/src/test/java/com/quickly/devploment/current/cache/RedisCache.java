package com.quickly.devploment.current.cache;

import redis.clients.jedis.Jedis;

/**
 * @Author lidengjin
 * @Date 2020/11/19 2:52 下午
 * @Version 1.0
 */
public class RedisCache {
	public static void main(String[] args) {
		String host = "120.78.140.235";
		int port= 6379;
		String password = "redis001";
		Jedis jedis = new Jedis(host, port);
		jedis.auth(password);
//		for (int i = 0; i < 1000; i++) {
//			new Thread(()->{
//				Long key_a = jedis.incr("key_a");
//				System.out.println(key_a);
//			}).start();
//		}

		String key_a = jedis.get("key_a");
		System.out.println("----last----"+key_a);
		System.out.println(jedis.get("name"));
		jedis.close();


	}
}
