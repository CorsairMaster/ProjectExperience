package com.quickly.devploment.controller;

import com.quickly.devploment.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;

/**
 * @ClassName UserController-用户服务
 * @Description
 * @Author LiDengJin
 * @Date 2019/9/30 11:11
 * @Version V-1.0
 **/
@Controller
@RequestMapping("/user")
@Slf4j
public class UserController {
	@Resource(name = "userServiceImpll")
	private UserService userService;

	/**
	 *  获取用户名称 根据用户名
	 * @param name 用户名
	 * @return 用户名+时间
	 */
	@RequestMapping("/getusername")
	@ResponseBody
	public String getUserName(String name){
		log.info("访问了 "+name);
		return userService.getName(name);
	}

	/**
	 * 插入用户
	 * @param name 用户名
	 * @return 保存的用户
	 */
	@RequestMapping("/insertuser")
	@ResponseBody
	public String saveUser(String name){
		return userService.saveUser(name);
	}
}
