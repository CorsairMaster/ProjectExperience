package com.quickly.devploment.draw;

import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author lidengjin
 * @Date 2020/11/9 5:13 下午
 * @Version 1.0
 */
public class GroupDraw {
	// 组名
	private String drawName;
	// 改组名下几个小组
	private int groupNum;

	private int limitStudent;

	private int currentStudent;

	public int getCurrentStudent() {
		return currentStudent;
	}

	public void setCurrentStudent(int currentStudent) {
		this.currentStudent = currentStudent;
	}

	public int getLimitStudent() {
		return limitStudent;
	}

	public void setLimitStudent(int limitStudent) {
		this.limitStudent = limitStudent;
	}

	private List<Student> students;

	public GroupDraw() {
	}

	public String getDrawName() {
		return drawName;
	}

	public void setDrawName(String drawName) {
		this.drawName = drawName;
	}

	public int getGroupNum() {
		return groupNum;
	}

	public void setGroupNum(int groupNum) {
		this.groupNum = groupNum;
	}

	public List<Student> getStudents() {
		if(CollectionUtils.isEmpty(students)){
			return new ArrayList<>();
		}
		return students;
	}

	public void setStudents(List<Student> students) {
		this.students = students;
	}

	@Override
	public String toString() {
		return "GroupDraw{" + "drawName='" + drawName + '\'' + ", groupNum=" + groupNum + ", limitStudent="
				+ limitStudent + ", currentStudent=" + currentStudent + ", students=" + students.toString() + '}';
	}
}
