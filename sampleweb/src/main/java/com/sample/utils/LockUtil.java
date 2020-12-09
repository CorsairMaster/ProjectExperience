package com.sample.utils;

import com.sample.redis.functions.WithinLockExecutor;
import lombok.extern.slf4j.Slf4j;
import org.redisson.RedissonRedLock;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * @Author lidengjin
 * @Date 2020/12/9 10:46 上午
 * @Version 1.0
 */
@Component
@Slf4j
public class LockUtil<T> {

	private static final String LOCK_PREFIX = "SERVER_LOCK:";
	private static final int WAIT_TIME = 20 * 100;
	private static final int TRY_TIMES = 3;
	private static String CONCURRENT_LOCK_PREFIX = "redisConcurrentLock";


	@Resource
	private RedissonClient redissonClient;


	/**
	 * @param key
	 * @param lockTime 根据具体业务处理时间传参
	 */
	public boolean tryLock(String key, long lockTime) throws InterruptedException {
		Assert.notNull(key, "key can not be null");
		String lockKey = this.getLockKey(key);
		try {
			// 获取锁
			RedissonRedLock redissonRedLock = getRedissonRedLock(lockKey);
			return redissonRedLock.tryLock(WAIT_TIME, lockTime, TimeUnit.SECONDS);
		} catch (InterruptedException e) {
			// 异常
			log.info("获取分布式锁失败 key={}", lockKey);
			throw e;
		}
	}

	public boolean lock(String key, long lockTime, int tryTimes) {
		int times = tryTimes;

		while (times > 0) {
			try {
				if (this.tryLock(key, lockTime)) {
					return true;
				}
				--times;
				TimeUnit.MILLISECONDS.sleep(WAIT_TIME);
			} catch (InterruptedException e) {

			}
		}
		log.info("重试次数已用完，但是抢锁失败,key={}", key);

		return false;
	}


	public void unlock(String key) {
		Assert.notNull(key, "key can not be null");
		String lockKey = this.getLockKey(key);
		try {
			// 释放锁
			RedissonRedLock redissonRedLock = this.getRedissonRedLock(lockKey);
			if (!this.isHeldByCurrentThread(lockKey)) {
				throw new Exception("Lock is not held by current thread for key: " + lockKey);
			} else {
				redissonRedLock.unlock();
			}
		} catch (Exception e) {
			log.info("释放分布式锁 失败 key ={}", key, e);
		}
	}

	public <T> T executeWithinLock(String key, long lockTime, WithinLockExecutor<T> executor) {
		lock(key, lockTime, TRY_TIMES);
		try {
			return executor.execute();
		} finally {
			unlock(key);
		}
	}


	public void runWithinLock(String key, long lockTime, Runnable runnable) {
		lock(key, lockTime, TRY_TIMES);
		try {
			runnable.run();
		} finally {
			unlock(key);
		}
	}


	private String getLockKey(String key) {
		return LOCK_PREFIX + key;
	}

	private List<RLock> getRLocks(String concurrentKey) {
		List<RLock> rLocks = new ArrayList();
		rLocks.add(this.redissonClient.getLock(
				RedisKeyUtils.keyBuilder(CONCURRENT_LOCK_PREFIX, concurrentKey + "0", new String[]{"global"})));
		return rLocks;
	}

	private RedissonRedLock getRedissonRedLock(String concurrentKey) {
		RLock lock1 = this.redissonClient
				.getLock(RedisKeyUtils.keyBuilder(CONCURRENT_LOCK_PREFIX, concurrentKey + "0", new String[]{"global"}));
		return new RedissonRedLock(new RLock[]{lock1});
	}

	public boolean isHeldByCurrentThread(String concurrentKey) {
		int held = 0;
		List<RLock> rLocks = this.getRLocks(concurrentKey);
		Iterator var4 = rLocks.iterator();
		while (var4.hasNext()) {
			RLock rLock = (RLock) var4.next();
			if (rLock.isHeldByCurrentThread()) {
				++held;
			}
		}
		return held > rLocks.size() / 2;
	}


}
