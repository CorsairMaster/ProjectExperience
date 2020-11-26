package com.quickly.devploment.interfaces;

/**
 * @Author lidengjin
 * @Date 2020/11/26 3:52 下午
 * @Version 1.0
 */
public class AbstractTestFinalClass implements TestFinalInterface{

	@Override
	final public String sayHello() {
		return " ---";
	}
}
