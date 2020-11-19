package com.quickly.devploment.service;

/**
 * @ClassName UserService
 * @Description
 * @Author LiDengJin
 * @Date 2019/9/30 11:12
 * @Version V-1.0
 **/
public interface UserService {

	/**
	 * 得到用户名
	 *
	 * @param name 查询的用户名
	 * @return
	 */
	String getName(String name);

	/**
	 * 保存用户
	 *
	 * @param saveUser 保存的用户名
	 * @return
	 */
	String saveUser(String saveUser);


	/**
	 * hello
	 *
	 * @return
	 */
	String hello();
}
