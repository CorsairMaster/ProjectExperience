package com.sample.controller;

import com.sample.utils.RedisOpsUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.concurrent.TimeUnit;

/**
 * @Author lidengjin
 * @Date 2020/11/19 3:20 下午
 * @Version 1.0
 */
@RestController
@RequestMapping("/limit")
public class LimitController {


	@Autowired
	private RedisOpsUtil redisOpsUtil;

	@RequestMapping("/redis")
	private String limitServer(HttpServletRequest request, @RequestParam("url") String url) {
		String key = request.getRequestURI() + url;
		Long incr = redisOpsUtil.incr(key);
		try {
			Thread.sleep(10);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		if (incr == 1) {
			redisOpsUtil.expire(key, 60, TimeUnit.SECONDS);
		} else if (incr > 10000) {
			return "限流" + incr + "--" + key;
		}
		return "success" + incr + "=== " + key;
	}
}
