package com.quickly.devploment.mybean.acticity;

import java.util.List;

/**
 * @Author lidengjin
 * @Date 2020/9/14 3:51 下午
 * @Version 1.0
 */
public class ActivityResponse {

	private List<WorkTemplate> workTemplates;

	public List<WorkTemplate> getWorkTemplates() {
		return workTemplates;
	}

	public void setWorkTemplates(List<WorkTemplate> workTemplates) {
		this.workTemplates = workTemplates;
	}

	@Override
	public String toString() {
		return "ActivityResponse{" + "workTemplates=" + workTemplates + '}';
	}
}
