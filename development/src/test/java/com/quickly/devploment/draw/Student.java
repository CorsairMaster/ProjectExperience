package com.quickly.devploment.draw;

/**
 * @Author lidengjin
 * @Date 2020/11/9 5:11 下午
 * @Version 1.0
 */
public class Student {
	// 学生姓名
	private String name;
	// 属于几组
	private int group;
	// 属于什么类型
	private GroupDraw groupDraw;

	public Student() {
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getGroup() {
		return group;
	}

	public void setGroup(int group) {
		this.group = group;
	}

	public GroupDraw getGroupDraw() {
		return groupDraw;
	}

	public void setGroupDraw(GroupDraw groupDraw) {
		this.groupDraw = groupDraw;
	}

	@Override
	public String toString() {
//		return "Student{" + "name='" + name + '\'' + ", group=" + group + ", groupDraw=" + groupDraw.getDrawName() + '}';
		return "Student{" + "name='" + name + '\'' + ", group=" + group + ", groupDraw=" ;
	}
}
