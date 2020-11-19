package com.quickly.devploment.controller;

import com.quickly.devploment.pojo.StudentPojo;
import com.quickly.devploment.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Date;
import java.util.List;

/**
 * @ClassName UserController 用户服务
 * @Description
 * @Author LiDengJin
 * @Date 2019/9/30 11:11
 * @Version V-1.0
 **/
@Controller
@RequestMapping("/student")
public class StudentController {
	@Autowired
	private StudentService studentService;

	/**
	 *  用户服务-获取所有的用户
	 *
	 * @param pageNum 当前页数
	 * @param pageSize 页数大小
	 * @return 用户集合
	 */
	@RequestMapping("/getAllstudent")
	@ResponseBody
	public List<StudentPojo> getAllStudent(Integer pageNum,Integer pageSize){
		pageNum = pageNum == null ? 1 : pageNum;
		pageSize = pageSize == null ? 500 : pageSize;
		return studentService.geuAllStudentPojo(pageNum,pageSize);
	}


	/**
	 * 获取所有的学生ByStream controller
	 *
	 * 有jvm 的服务
	 * @param pageNum
	 * @param pageSize
	 * @return 学生用户集合
	 */
	@RequestMapping("/getAllstudentByStream")
	@ResponseBody
	public List<StudentPojo> getAllStudentByStream(Integer pageNum,Integer pageSize){
		pageNum = pageNum == null ? 1 : pageNum;
		pageSize = pageSize == null ? 500 : pageSize;
		return studentService.geuAllStudentPojoByStream(pageNum,pageSize);
	}

	/**
	 *  测试demo
	 * @param name 学生名
	 * @return 学生名的hello + date
	 */
	@RequestMapping("/test")
	@ResponseBody
	public String getAllStudentByStreamByTest(String name){
		return "hello + : "+name+ new Date();
	}

}
