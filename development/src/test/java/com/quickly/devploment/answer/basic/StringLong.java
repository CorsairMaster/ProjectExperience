package com.quickly.devploment.answer.basic;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

/**
 * @Author lidengjin
 * @Date 2020/10/12 5:26 下午
 * @Version 1.0
 */
@Slf4j
public class StringLong {


	@Test
	public void testStringAddress() {
		String s = "hello";
		s = "world";
	}

	@Test
	public void testSubstring(){
//		String path = "jar:file:/Users/lidengjin/study/sourceofgithub/mygithub/ProjectExperience/sampleweb/target/sampleweb-1.0-SNAPSHOT.jar!/BOOT-INF/classes!/static/WebsocketDanMu.html";
		String path = "file:/Users/lidengjin/study/sourceofgithub/mygithub/ProjectExperience/sampleweb/target/sampleweb-1.0-SNAPSHOT.jar!/BOOT-INF/classes!/static/WebsocketDanMu.html";
		String subString = !path.contains("file:") ? path : path.substring(path.indexOf("/"));
		System.out.println(subString);
//		int i = path.indexOf("/");
//		System.out.println(i);
	}
}
