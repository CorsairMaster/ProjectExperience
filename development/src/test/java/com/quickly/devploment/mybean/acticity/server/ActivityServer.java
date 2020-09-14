package com.quickly.devploment.mybean.acticity.server;

import com.alibaba.fastjson.JSON;
import com.quickly.devploment.mybean.acticity.ActivityResponse;
import com.quickly.devploment.mybean.acticity.Role;
import com.quickly.devploment.mybean.acticity.WorkTemplate;
import com.quickly.devploment.mybean.acticity.service.ActivityServiceImpl;
import com.quickly.devploment.mybean.acticity.service.RoleService;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @Author lidengjin
 * @Date 2020/9/14 3:58 下午
 * @Version 1.0
 */
@Slf4j
public class ActivityServer {

	public static void main(String[] args) {
		// 如果是全量的数据 先根据 id 进行分组，这里 模拟的 worKName ---> 映射的work Id
		// 然后进行分组，处理，此时，后续的处理逻辑 ，如同 根据 id ，查询模版结果一样，
		String workId = "work1";
		List<WorkTemplate> workTemplateByWorkId = ActivityServiceImpl.findWorkTemplateByWorkId(workId);
		log.info("所有的 work {}", workTemplateByWorkId);
		Map<String, List<WorkTemplate>> groupByName = workTemplateByWorkId.stream()
				.collect(Collectors.groupingBy(WorkTemplate::getName));
		log.info(" 分组之后 work {}", groupByName);

		List<WorkTemplate> responseAllWorkTemplate = new ArrayList<>();
		// 分组完 ，需要对各个 value 进行处理
		groupByName.entrySet().forEach(workList->{
			//  此时 就是 根据 id 查询 work 的处理逻辑
			List<Role> roleList = new ArrayList<>();
			List<WorkTemplate> value = workList.getValue();
			value.stream().forEach(workTemplate -> {
				roleList.add(RoleService.getRoleByRoleId(workTemplate.getRoleId()));
			});
			value.stream().forEach(workTemplate -> {
				workTemplate.setRoles(roleList);
			});
			responseAllWorkTemplate.addAll(value);
		});


		ActivityResponse activityResponse = new ActivityResponse();
		activityResponse.setWorkTemplates(responseAllWorkTemplate);
		String response = JSON.toJSONString(activityResponse);
		log.info("response {}" ,response);
	}
}
