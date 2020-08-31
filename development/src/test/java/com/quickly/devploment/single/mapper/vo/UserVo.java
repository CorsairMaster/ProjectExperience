package com.quickly.devploment.single.mapper.vo;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

/**
 * @Author lidengjin
 * @Date 2020/8/31 4:48 下午
 * @Version 1.0
 */
public class UserVo implements Serializable {

	private static final long serialVersionUID = -4371742264310758072L;
	private String username;
	private String password;
	private Integer age;
	private BigDecimal amount;
	private List<Student> students;
	private List<String> names;

	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	public List<Student> getStudents() {
		return students;
	}

	public void setStudents(List<Student> students) {
		this.students = students;
	}

	public List<String> getNames() {
		return names;
	}

	public void setNames(List<String> names) {
		this.names = names;
	}

	public Integer getAge() {
		return age;
	}

	public void setAge(Integer age) {
		this.age = age;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	@Override
	public String toString() {
		return "UserVo{" + "username='" + username + '\'' + ", password='" + password + '\'' + ", age=" + age
				+ ", amount=" + amount + ", students=" + students + ", names=" + names + '}';
	}

	static class Student{
		private String name;
		private Integer age;
		private List<String> debit;

		@Override
		public String toString() {
			return "Student{" + "name='" + name + '\'' + ", age=" + age + ", debit=" + debit + '}';
		}

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		public Integer getAge() {
			return age;
		}

		public void setAge(Integer age) {
			this.age = age;
		}

		public List<String> getDebit() {
			return debit;
		}

		public void setDebit(List<String> debit) {
			this.debit = debit;
		}
	}
}
