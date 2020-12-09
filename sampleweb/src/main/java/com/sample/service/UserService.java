package com.sample.service;

import com.sample.common.Student;

/**
 * @Author lidengjin
 * @Date 2020/12/9 1:31 下午
 * @Version 1.0
 */
public interface UserService {
	Student getStudentById(int id);

	void saveStudent(Student student);

	void updateStudent(Student student);
}
