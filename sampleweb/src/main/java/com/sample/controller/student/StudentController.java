package com.sample.controller.student;

import com.sample.common.Student;
import com.sample.service.UserService;
import com.sample.utils.LockUtil;
import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @Author lidengjin
 * @Date 2020/12/9 2:01 下午
 * @Version 1.0
 */
@RestController
@RequestMapping("/student")
public class StudentController {

	@Resource
	private UserService userService;

	@Resource
	private LockUtil lockUtil;

	@RequestMapping("/getstudentbyid/{id}")
	public Student getStudentById(@Param("id") int id) {
		return (Student) lockUtil.executeWithinLock(id + "", 3000, () -> userService.getStudentById(id));
	}

	@RequestMapping(path = "/savestudent", method = RequestMethod.POST)
	public String saveStudent(@RequestBody Student student) {
		userService.saveStudent(student);
		return "save success";
	}

	@RequestMapping(path = "/updatestudent", method = RequestMethod.POST)
	public String updateStudent(@RequestBody Student student) {
		userService.updateStudent(student);
		return "update success";
	}
}
