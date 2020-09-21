package com.quickly.devploment.cache;

import lombok.extern.slf4j.Slf4j;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @Author lidengjin
 * @Date 2020/9/21 5:28 下午
 * @Version 1.0
 */
@Slf4j
public class ConcurrentHashMapCacheUtils {


	/**
	 * 缓存对象
	 */
	private static final Map<String, CacheObj> CACHE_OBJECT_MAP = new ConcurrentHashMap<>();
	/**
	 * 缓存最大个数
	 */
	private static final Integer CACHE_MAX_NUMBER = 1000;
	/**
	 * 初始缓存个数,用于累加,达到缓存最大个数时，再添加会触发删除操作
	 */
	private static Integer CURRENT_SIZE = 0;

	/**
	 * 这个记录了缓存使用的最后一次的记录，最近使用的在最前面
	 */
	private static final List<String> CACHE_USE_LOG_LIST = new LinkedList<>();

	/**
	 * 设置缓存
	 */
	public static void setCache(String cacheKey, Object cacheValue) {
		setCache(cacheKey, cacheValue, -1L);
	}

	/**
	 * 设置缓存
	 * @param cacheKey 缓存Key
	 * @param cacheValue 缓存Value
	 * @param cacheTime 小于0时永久缓存, 大于0时缓存时间当前系统时间加指定的毫秒数
	 */
	public static void setCache(String cacheKey, Object cacheValue, long cacheTime) {
		Long ttlTime = null;
		if (cacheTime <= 0L) {
			if (cacheTime == -1L) {
				ttlTime = -1L;
			} else {
				return;
			}
		}
		//检查大小,是否超过缓存最大个数
		checkSize();
		//保存记录,放在最前面
		saveCacheUseLog(cacheKey);
		CURRENT_SIZE = CURRENT_SIZE + 1;
		if (ttlTime == null) {
			ttlTime = System.currentTimeMillis() + cacheTime;
		}
		CacheObj cacheObj = new CacheObj(cacheValue, ttlTime);
		CACHE_OBJECT_MAP.put(cacheKey, cacheObj);
		log.info("have set key :" + cacheKey);
	}
	/**
	 * 获取缓存
	 */
	public static Object getCache(String cacheKey) {
		if (checkCache(cacheKey)) {
			saveCacheUseLog(cacheKey);
			return CACHE_OBJECT_MAP.get(cacheKey).getCacheValue();
		}
		return null;
	}
	/**
	 * 判断缓存在不在,过没过期
	 */
	private static boolean checkCache(String cacheKey) {
		CacheObj cacheObj = CACHE_OBJECT_MAP.get(cacheKey);
		if (cacheObj == null) {
			return false;
		}
		//永久缓存
		if (cacheObj.getTtlTime() == -1L) {
			return true;
		}
		//缓存超时
		if (cacheObj.getTtlTime() < System.currentTimeMillis()) {
			deleteCache(cacheKey);
			return false;
		}
		return true;
	}
	/**
	 * 删除某个缓存
	 */
	public static void deleteCache(String cacheKey) {
		Object cacheValue = CACHE_OBJECT_MAP.remove(cacheKey);
		if (cacheValue != null) {
			log.info("have delete key :" + cacheKey);
			//缓存个数减一
			CURRENT_SIZE = CURRENT_SIZE - 1;
		}
	}
	/**
	 * 删除所有缓存
	 */
	public static void clear() {
		log.info("have clean all key !");
		CACHE_OBJECT_MAP.clear();
		CURRENT_SIZE = 0;
	}
	/**
	 * 检查大小
	 * 当前大小如果已经达到最大值
	 * 首先删除过期缓存，如果过期缓存删除过后还是达到最大缓存数目
	 * 删除最久未使用缓存
	 */
	private static void checkSize() {
		if (CURRENT_SIZE >= CACHE_MAX_NUMBER) {
			deleteTimeOut();
		}
		if (CURRENT_SIZE >= CACHE_MAX_NUMBER) {
			deleteLRU();
		}
	}

	/**
	 * 删除最近最久未使用的缓存
	 */
	private static void deleteLRU() {
		log.info("delete Least recently used run!");
		String cacheKey = null;
		synchronized (CACHE_USE_LOG_LIST) {
			if (CACHE_USE_LOG_LIST.size() >= CACHE_MAX_NUMBER - 10) {
				cacheKey = CACHE_USE_LOG_LIST.remove(CACHE_USE_LOG_LIST.size() - 1);
			}
		}
		if (cacheKey != null) {
			deleteCache(cacheKey);
		}
	}
	/**
	 * 删除过期的缓存
	 */
	static void deleteTimeOut() {
		log.info("delete time out run!");
		List<String> deleteKeyList = new LinkedList<>();
		for(Map.Entry<String, CacheObj> entry : CACHE_OBJECT_MAP.entrySet()) {
			if (entry.getValue().getTtlTime() < System.currentTimeMillis() && entry.getValue().getTtlTime() != -1L) {
				deleteKeyList.add(entry.getKey());
			}
		}
		for (String deleteKey : deleteKeyList) {
			deleteCache(deleteKey);
		}
		log.info("delete cache count is :" + deleteKeyList.size());
	}

	/**
	 * 保存缓存的使用记录
	 */
	private static synchronized void saveCacheUseLog(String cacheKey) {
		synchronized (CACHE_USE_LOG_LIST) {
			CACHE_USE_LOG_LIST.remove(cacheKey);
			CACHE_USE_LOG_LIST.add(0,cacheKey);
		}
	}

	public static void main(String[] args) {
		for (int i = 0; i <1000 ; i++) {
			int j=i;
			new Thread(new Runnable() {
				@Override
				public void run() {
					ConcurrentHashMapCacheUtils.setCache("key"+j,"qaz",5000);
					System.out.println("T1======set========"+Thread.currentThread().getName());
					try {
						Thread.sleep(1000);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					System.out.println("T1======end========"+Thread.currentThread().getName());
				}
			},"T1-"+i).start();
		}
		for (int i = 0; i <1000 ; i++) {
			int j=i;
			new Thread(new Runnable() {
				@Override
				public void run() {
					System.out.println("T2======start========"+Thread.currentThread().getName());
					try {
						Thread.sleep(1000);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					Object value=ConcurrentHashMapCacheUtils.getCache("key"+j);
					System.out.println("T2 value=============="+Thread.currentThread().getName()+value);
					System.out.println("T2======end========"+Thread.currentThread().getName());

				}
			},"T2-"+i).start();
		}

	}
}

/**
 * 缓存类
 */
class CacheObj {
	/**
	 * 缓存对象
	 */
	private Object CacheValue;
	/**
	 * 缓存过期时间
	 */
	private Long ttlTime;

	CacheObj(Object cacheValue, Long ttlTime) {
		CacheValue = cacheValue;
		this.ttlTime = ttlTime;
	}

	Object getCacheValue() {
		return CacheValue;
	}

	Long getTtlTime() {
		return ttlTime;
	}

	@Override
	public String toString() {
		return "CacheObj {" +
				"CacheValue = " + CacheValue +
				", ttlTime = " + ttlTime +
				'}';
	}
}