package com.quickly.devploment.forkjoin;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.stream.Collectors;

/**
 * @Author lidengjin
 * @Date 2020/9/24 2:54 下午
 * @Version 1.0
 */
public class ManagedForkJoinPoolTest {
	public static void main(String[] args) {
		List<String> names = Arrays.asList("1", "2", "3", "4", "5", "6","1", "2", "3", "4", "5", "6");
		List<Callable<MyTask>> collect = names.stream().<Callable<MyTask>>map(name -> () -> getMyTask(name))
				.collect(Collectors.toList());
		long start = System.currentTimeMillis();
		System.out.println("start " + start);
		List<MyTask> myTasks = ManagedForkJoinPool.executeAndWaitResult(collect);
		myTasks.stream().forEach(myTask -> {
			System.out.println(myTask.getName());
		});
		System.out.println("forkJoin time " + (System.currentTimeMillis() - start));


		start = System.currentTimeMillis();
		List<MyTask> serialization = names.stream().map(name -> {
			return getMyTask(name);
		}).collect(Collectors.toList());
		serialization.stream().forEach(myTask -> {
			System.out.println(myTask.getName());
		});
		System.out.println("serialization time " + (System.currentTimeMillis() - start));


	}

	static class MyTask {

		private String name;

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		@Override
		public String toString() {
			return "MyTask{" + "name='" + name + '\'' + '}';
		}
	}

	public static MyTask getMyTask(String name) {
		try {
			// 假设业务执行的时间
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		MyTask myTask = new MyTask();
		myTask.setName(name);
		return myTask;
	}
}
