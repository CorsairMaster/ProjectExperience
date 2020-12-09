package com.sample.controller.student;

import com.sample.common.Student;
import com.sample.redis.StrongConsistenceCacheExecutor;
import com.sample.service.UserService;
import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.Random;

/**
 * @Author lidengjin
 * @Date 2020/12/9 3:34 下午
 * @Version 1.0
 */
@RestController
@RequestMapping("/consistence")
public class StrongConsistenceCacheController {
	private final static String key_prefix = "student:";
	private final static long TTL_SECONDS = 30 * 1000;
	//	private final static long TTL_SECONDS = 24 * 60 * 60;
	@Resource
	private UserService userService;

	@Resource
	private StrongConsistenceCacheExecutor consistenceCacheExecutor;

	@RequestMapping("/getstudentbyid/{id}")
	public Student getStudentById(@Param("id") int id) {
		String key = key_prefix + id;
		return (Student) consistenceCacheExecutor.read(key, TTL_SECONDS, Student.class, () -> getStudentByDB(id));
	}

	private Student getStudentByDB(int id) {
		return userService.getStudentById(id);
	}

	@RequestMapping(path = "/savestudent", method = RequestMethod.POST)
	public Student saveStudent(@RequestBody Student student) {
		String key = key_prefix + student.getId();
		return (Student) consistenceCacheExecutor.write(key, TTL_SECONDS, () -> saveStudentByDB(student));

	}

	private Student saveStudentByDB(Student student) {
		// save
		student.setPassword(student.getPassword() + new Random().nextInt(1000000));
		userService.saveStudent(student);
		return student;
	}

	@RequestMapping(path = "/updatestudent", method = RequestMethod.POST)
	public Student updateStudent(@RequestBody Student student) {
		String key = key_prefix + student.getId();
		return (Student) consistenceCacheExecutor.write(key, TTL_SECONDS, () -> updateStudentByDB(student));

	}

	private Student updateStudentByDB(Student student) {
		// save
		student.setPassword(student.getPassword() + "_update");
		userService.updateStudent(student);
		return student;
	}
}
