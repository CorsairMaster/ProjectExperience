package com.sample.utils;

/**
 * @Author lidengjin
 * @Date 2020/12/9 3:07 下午
 * @Version 1.0
 */
public class RedisKeyUtils {
	private static final String KEY_SPLIT_CHAR = ":";

	public RedisKeyUtils() {
	}

	public static String keyBuilder(String module1, String key, String... otherModules) {
		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append(module1);
		String[] var4 = otherModules;
		int var5 = otherModules.length;

		for (int var6 = 0; var6 < var5; ++var6) {
			String otherModule = var4[var6];
			stringBuilder.append(":").append(otherModule);
		}

		stringBuilder.append(":").append(key);
		return stringBuilder.toString();
	}
}
