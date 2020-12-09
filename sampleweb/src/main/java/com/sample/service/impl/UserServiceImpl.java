package com.sample.service.impl;

import com.sample.common.Student;
import com.sample.mapper.UserMapper;
import com.sample.service.UserService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @Author lidengjin
 * @Date 2020/12/9 1:31 下午
 * @Version 1.0
 */
@Service
public class UserServiceImpl implements UserService {

	@Resource
	private UserMapper userMapper;

	@Override
	public Student getStudentById(int id) {
		return userMapper.getStudentById(id);
	}

	@Override
	public void saveStudent(Student student) {
		userMapper.saveStudent(student);
	}

	@Override
	public void updateStudent(Student student) {
		userMapper.updateStudent(student);
	}
}
