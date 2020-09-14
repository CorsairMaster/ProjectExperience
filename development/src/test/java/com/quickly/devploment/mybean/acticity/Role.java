package com.quickly.devploment.mybean.acticity;

import java.io.Serializable;

/**
 * @Author lidengjin
 * @Date 2020/9/14 3:50 下午
 * @Version 1.0
 */
public class Role implements Serializable {
	private static final long serialVersionUID = 2655687579713453862L;
	private String roleId;
	private String roleName;

	public String getRoleId() {
		return roleId;
	}

	public void setRoleId(String roleId) {
		this.roleId = roleId;
	}

	public String getRoleName() {
		return roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

	@Override
	public String toString() {
		return "Role{" + "roleId='" + roleId + '\'' + ", roleName='" + roleName + '\'' + '}';
	}
}
