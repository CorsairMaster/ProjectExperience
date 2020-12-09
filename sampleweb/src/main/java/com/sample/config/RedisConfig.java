package com.sample.config;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sample.utils.RedisOpsUtil;
import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

/**
 * @Author lidengjin
 * @Date 2020/11/19 3:23 下午
 * @Version 1.0
 */
@Configuration
public class RedisConfig {

	@Autowired
	private RedisConnectionFactory connectionFactory;

	@Value("${redisson.address}")
	private String addressUrl;
	@Value("${redisson.password}")
	private String password;


	@Bean
	@Primary
	public RedisTemplate<String,Object> redisTemplate(){
		RedisTemplate<String,Object> template = new RedisTemplate();
		template.setConnectionFactory(connectionFactory);
		// 序列化工具
		Jackson2JsonRedisSerializer<Object> jackson2JsonRedisSerializer = new Jackson2JsonRedisSerializer<Object>(Object.class);
		ObjectMapper om = new ObjectMapper();
		om.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
		om.enableDefaultTyping(ObjectMapper.DefaultTyping.NON_FINAL);
		jackson2JsonRedisSerializer.setObjectMapper(om);

		StringRedisSerializer stringRedisSerializer = new StringRedisSerializer();
		template.setKeySerializer(stringRedisSerializer);
		template.setValueSerializer(jackson2JsonRedisSerializer);

		template.setHashKeySerializer(jackson2JsonRedisSerializer);
		template.setHashValueSerializer(jackson2JsonRedisSerializer);

		template.afterPropertiesSet();
		return template;
	}

	@Bean
	public RedisOpsUtil redisOpsUtil(){
		return new RedisOpsUtil();
	}

	@Bean
	public RedissonClient getRedisson() {
		Config config = new Config();
		config.useSingleServer()
				.setAddress(addressUrl).setPassword(password)
				.setReconnectionTimeout(10000)
				.setRetryInterval(5000)
				.setTimeout(10000)
				.setConnectTimeout(10000);
		return Redisson.create(config);
	}

}
