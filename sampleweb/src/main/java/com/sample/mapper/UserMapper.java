package com.sample.mapper;

import com.sample.common.Student;
import org.springframework.data.repository.query.Param;

/**
 * @Author lidengjin
 * @Date 2020/12/9 1:32 下午
 * @Version 1.0
 */
public interface UserMapper {
	Student getStudentById(@Param("id") int id);

	void saveStudent(Student student);

	void updateStudent(Student student);
}
