package com.quickly.devploment.mybean.acticity.service;

import com.quickly.devploment.mybean.acticity.Role;

/**
 * @Author lidengjin
 * @Date 2020/9/14 3:55 下午
 * @Version 1.0
 */
public class RoleService {
	public static Role getRoleByRoleId(String roleId){
		Role role = new Role();
		role.setRoleId("role" + roleId);
		role.setRoleName("roleName" + roleId);
		return role;
	}
}
