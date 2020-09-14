package com.quickly.devploment.mybean.acticity.service;

import com.quickly.devploment.mybean.acticity.WorkTemplate;

import java.util.Arrays;
import java.util.List;

/**
 * @Author lidengjin
 * @Date 2020/9/14 3:53 下午
 * @Version 1.0
 */
public class ActivityServiceImpl {

	public static List<WorkTemplate> findWorkTemplateByWorkId(String workId) {
		WorkTemplate workTemplate1 = new WorkTemplate();
		WorkTemplate workTemplate2 = new WorkTemplate();
		WorkTemplate workTemplate3 = new WorkTemplate();
		workTemplate1.setName("work-1");
		workTemplate2.setName("work-2");
		workTemplate3.setName("work-2");
		workTemplate1.setRoleId("role1");
		workTemplate2.setRoleId("role2");
		workTemplate3.setRoleId("role3");
		return Arrays.asList(workTemplate1,workTemplate2,workTemplate3);
	}
}
