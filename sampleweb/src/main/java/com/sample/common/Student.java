package com.sample.common;

import java.io.Serializable;

/**
 * @Author lidengjin
 * @Date 2020/12/9 11:04 上午
 * @Version 1.0
 */
public class Student implements Serializable {

	private static final long serialVersionUID = 5763923269679434297L;

	private String name;
	private int id;
	private String password;


	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	@Override
	public String toString() {
		return "Student{" + "name='" + name + '\'' + ", id=" + id + ", password='" + password + '\'' + '}';
	}

}
