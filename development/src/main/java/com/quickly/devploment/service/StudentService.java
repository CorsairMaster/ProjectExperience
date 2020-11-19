package com.quickly.devploment.service;

import com.quickly.devploment.pojo.StudentPojo;

import java.util.List;

/**
 * @ClassName StudentService
 * @Description 用户服务
 * @Author LiDengJin
 * @Date 2019/10/26 11:09
 * @Version V-1.0
 **/
public interface StudentService {
	/**
	 * 查看所有的学生
	 *
	 * @param pageNum 当前页
	 * @param pageSize 数量
	 * @return
	 */
	List<StudentPojo> geuAllStudentPojo(Integer pageNum, Integer pageSize);

	/**
	 * 根据stream 查看学生
	 * @param pageNum 当前学生页数
	 * @param pageSize 每页的学生数量
	 * @return
	 */
	List<StudentPojo> geuAllStudentPojoByStream(Integer pageNum, Integer pageSize);
}
