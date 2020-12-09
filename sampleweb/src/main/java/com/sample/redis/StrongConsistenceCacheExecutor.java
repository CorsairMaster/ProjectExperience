package com.sample.redis;

import com.sample.redis.functions.DbDataReader;
import com.sample.redis.functions.DbDataWriter;
import com.sample.utils.LockUtil;
import com.sample.utils.RedisOpsUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.concurrent.TimeUnit;

/**
 * 强⼀一致性缓存读写器器
 *
 * @author
 */
@Component
public class StrongConsistenceCacheExecutor<T> {
	private final Logger logger = LoggerFactory.getLogger(getClass());

	private final int LOCK_TTL = 2 * 1000;
	@Resource
	private LockUtil lockUtil;

	@Resource
	private RedisOpsUtil redisOpsUtil;

	/**
	 * <p>
	 * write request--> try lock--> delete cache --> write db --> write cache --> unlock
	 * + | | | | ↑
	 * + | fail | fail | fail | fail |
	 * + | | | | |
	 * + return |------------|------------|----------------|
	 * </p>
	 */
	public T write(String key, long ttl, DbDataWriter<T> dbDataWriter) {
		lockUtil.executeWithinLock(key, LOCK_TTL, () -> {
			logger.debug("[write] try lock success, key={}", key);
			redisOpsUtil.delete(key);
			logger.debug("[write] delete cache success, key={}", key);
			T t = dbDataWriter.writeDb();
			logger.debug("[write] write into db success, data={}", t);
			redisOpsUtil.set(key, t, ttl, TimeUnit.SECONDS);
			logger.debug("[write] write into cache success, data={}", t);
			return t;
		});
		return null;
	}

	/**
	 * <p>
	 * read request-->read cache -fail-> read db --> try lock --> write cache --> unlock
	 * + | | | | ↑
	 * + | success | fail | fail | fail |
	 * + | | | | |
	 * + return return return |----------------|
	 * </p>
	 */
	public T read(String key, long ttl, Class<T> clazz, DbDataReader<T> dbDataReader) {
		T v = (T) redisOpsUtil.get(key, clazz);
		if (v != null) {
			logger.debug("[read] get data from cache, v={}", v);
			return v;
		}
		logger.debug("[read] null from cache, key={}", key);
		v = dbDataReader.readFromDb();
		if (v != null) {
			try {
				final T vFinal = v;
				lockUtil.executeWithinLock(key, LOCK_TTL, () -> {
					logger.debug("[read] try lock success, key={}", key);
					redisOpsUtil.set(key, vFinal, ttl, TimeUnit.SECONDS);
					logger.debug("[read] set redis success,v={}", vFinal);
					return null;
				});
			} catch (Exception e) {
				logger.error("[read] try lock/set redis error ", e);
			}
			return v;
		}
		return null;
	}



}