package com.quickly.devploment.draw;

import org.apache.commons.lang3.RandomUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @Author lidengjin
 * @Date 2020/11/9 5:11 下午
 * @Version 1.0
 * @Description 抽签小程序 简单demo
 */
public class DrawDemo {

	static List<Student> students;
	static List<GroupDraw> groupDraws;

	static {
		init();
	}

	private static void init() {
		students = new ArrayList<>();
		groupDraws = new ArrayList<>();
		for (int i = 0; i < 25; i++) {
			Student student = new Student();
			student.setName("name_" + i);
			students.add(student);
		}
		GroupDraw groupDraw = new GroupDraw();
		groupDraw.setDrawName("sayRussian");
		groupDraw.setGroupNum(1);
		groupDraw.setLimitStudent(5);
		GroupDraw groupDraw_ = new GroupDraw();
		groupDraw_.setDrawName("sayRussian");
		groupDraw_.setGroupNum(2);
		groupDraw_.setLimitStudent(5);
		GroupDraw groupDraw1 = new GroupDraw();
		groupDraw1.setDrawName("sayChinese");
		groupDraw1.setGroupNum(1);
		groupDraw1.setLimitStudent(4);
		GroupDraw groupDraw1_ = new GroupDraw();
		groupDraw1_.setDrawName("sayChinese");
		groupDraw1_.setGroupNum(2);
		groupDraw1_.setLimitStudent(4);
		GroupDraw groupDraw2 = new GroupDraw();
		groupDraw2.setDrawName("sayEnglish");
		groupDraw2.setGroupNum(1);
		groupDraw2.setLimitStudent(4);
		groupDraws.add(groupDraw2);
		groupDraws.add(groupDraw1);
		groupDraws.add(groupDraw_);
		groupDraws.add(groupDraw1_);
		groupDraws.add(groupDraw);
	}

	static AtomicInteger atomicInteger = new AtomicInteger(0);

	public static void main(String[] args) {

		students.stream().forEach(student -> {
			int i = atomicInteger.incrementAndGet();
			Integer integer = groupDraws.stream().map(GroupDraw::getLimitStudent).reduce((a, b) -> {
				return a + b;
			}).get();
			if (i > integer) {
				System.out.println(" 当前学生 大于总共分组人数 ---student" + student.getName());
				return;
			}
			group(student, groupDraws);
		});

		groupDraws.stream().forEach(groupDraw -> {
			System.out.println(groupDraw.toString());
		});


	}

	private static void group(Student student, List<GroupDraw> groupDraws) {
		if (groupDraws.size() <= 0) {
			System.out.println("组已经没有名额了");
			return;
		}
		ArrayList<GroupDraw> dymGroupDraws = new ArrayList<>(groupDraws);
		GroupDraw groupDraw = dymGroupDraws.get(RandomUtils.nextInt(0, groupDraws.size()));
		int limitStudent = groupDraw.getLimitStudent();
		int currentStudent = groupDraw.getCurrentStudent();
		if (currentStudent >= limitStudent) {
			System.out.println("该组已满 " + groupDraw.getDrawName() + "_" + groupDraw.getGroupNum() + "_" + groupDraw
					.getCurrentStudent());
			// 重新选组
			dymGroupDraws.remove(groupDraw);
			group(student, dymGroupDraws);
		} else {
			setStudentAndGroup(student, groupDraw);
		}
	}

	private static void setStudentAndGroup(Student student, GroupDraw groupDraw) {
		List<Student> students = groupDraw.getStudents();
		students.add(student);
		groupDraw.setStudents(students);
		groupDraw.setCurrentStudent(groupDraw.getCurrentStudent() + 1);
		student.setGroupDraw(groupDraw);
		student.setGroup(groupDraw.getGroupNum());
	}

}
