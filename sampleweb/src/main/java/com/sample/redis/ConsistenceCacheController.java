package com.sample.redis;

import com.sample.common.Student;

import javax.annotation.Resource;

/**
 * @Author lidengjin
 * @Date 2020/12/9 10:46 上午
 * @Version 1.0
 */
public class ConsistenceCacheController {

	private final static String key_prefix = "student:";
	private final static long TTL_SECONDS = 24 * 60 * 60;

	@Resource
	private StrongConsistenceCacheExecutor consistenceCacheExecutor;

	public Student getByIdCatchable(Integer id) {
		String key = key_prefix + id;
		return (Student) consistenceCacheExecutor.read(key, TTL_SECONDS, Student.class, () -> getById(id));
	}

	private Student getById(Integer id) {
		return null;
	}

	public void updateCatchable(Student student) {
		String key = key_prefix + student.getId();
		consistenceCacheExecutor.write(key, TTL_SECONDS, () -> {
			saveOrUpdate(student);
			return student;
		});
	}

	private void saveOrUpdate(Student student) {
		// save
	}

}
