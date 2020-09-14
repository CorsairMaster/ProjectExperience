package com.quickly.devploment.mybean.acticity;

import java.io.Serializable;
import java.util.List;

/**
 * @Author lidengjin
 * @Date 2020/9/14 3:46 下午
 * @Version 1.0
 */
public class WorkTemplate implements Serializable {
	private static final long serialVersionUID = 2019211794230965747L;
	private String name;
	private String roleId;

	private List<Role> roles;

	public List<Role> getRoles() {
		return roles;
	}

	public void setRoles(List<Role> roles) {
		this.roles = roles;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getRoleId() {
		return roleId;
	}

	public void setRoleId(String roleId) {
		this.roleId = roleId;
	}

	@Override
	public String toString() {
		return "WorkTemplate{" + "name='" + name + '\'' + ", roleId='" + roleId + '\'' + ", roles=" + roles + '}';
	}
}
