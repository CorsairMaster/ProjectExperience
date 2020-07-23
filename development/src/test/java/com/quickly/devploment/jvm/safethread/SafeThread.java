package com.quickly.devploment.jvm.safethread;

/**
 * @Author lidengjin
 * @Date 2020/7/23 9:57 上午
 * @Version 1.0
 */
public class SafeThread {
	/*
	 1 java 语言中的线程安全 ，安全程度 强 --> 弱 5类
	 {
	 	1 不可变 ，不可变的对象一定是线程安全的，
	 	2 绝对线程安全
	 	3 相对线程安全 ，就是所谓的线程安全，需要保证对这个对象的单次操作 是线程安全的。如 Vector HashTable
	 	4 线程兼容 对象本身不是线程安全的，但是可以通过 在调用端正确的使用 同步手段来保证对象在并发环境 中可以安全的使用。如 HashMap ArrayList 等。
	 	5 线程对立 就是无论采取什么样的措施 都不能保证线程安全。如 System.setIn(),System.setOut();
	 }
	 2 线程安全的实现方法
	  1 互斥同步 ，同步是指 在多线程并发访问共享数据时 保证共享数据在同一时刻 只被一条线程使用。
	 	互斥 是实现同步的一种手段。互斥同步的手段 最基本的就是 synchronized 。
	 3 java 线程中的线程 最后会被映射到操作系统的原生内核线程之上
	 4 ReentrantLock 可重入 ，等待可中断（当持有锁的线程长期不释放锁的时候，正在等待的线程可以放弃等待，改为处理其他事情），可实现公平锁 以及锁绑定多个条件 （Condition）
	 	公平锁比非公平锁 性能低的原因 公平锁要维护一个队列，后来的线程要加锁，即使锁空闲，也要先检查有没有其他线程在 wait，如果有自己要挂起，加到队列后面，然后唤醒队列最前面的线程。这种情况下相比较非公平锁多了一次挂起和唤醒线程切换的开销，其实就是非公平锁效率高于公平锁的原因，因为非公平锁减少了线程挂起的几率，后来的线程有一定几率逃离被挂起的开销
	 5 互斥同步 面临的主要是 进行线程堵塞和唤醒 所带来的性能开销；这种又称为 堵塞同步。
	 */
}