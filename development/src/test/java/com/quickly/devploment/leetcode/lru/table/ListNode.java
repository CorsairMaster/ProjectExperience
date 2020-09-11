package com.quickly.devploment.leetcode.lru.table;

import lombok.extern.slf4j.Slf4j;

import java.util.Calendar;
import java.util.Date;

/**
 * @Author lidengjin
 * @Date 2020/9/9 8:43 下午 求链表的中间节点  快慢指针 同时执行
 * @Version 1.0
 */
@Slf4j
public class ListNode {
	int val;
	ListNode next;

	ListNode(int x) {
		val = x;
	}

	/**
	 * 寻找链表的中间节点
	 *
	 * @param
	 * @return
	 */
	public static ListNode middleNode(ListNode head) {
		if (head == null || head.next == null) {
			return head;
		}

		ListNode slow = head;
		ListNode fast = head.next;

		while (fast != null && fast.next != null) {
			slow = slow.next;
			fast = fast.next.next;
		}

		return fast == null ? slow : slow.next;
	}


	public static void main(String[] args) {
		//		List<String> list = Arrays.asList("1","2","3");
		//		StringBuffer or = new StringBuffer();
		// 		for (int i = 0; i < list.size() - 1 ; i++) {
		//			or.append(list.get(i)+",");
		//		}
		// 		or.append(list.get(list.size()-1));
		//		System.out.println(or.toString());
		//	}

		int terms = 12;

		Date dateTime = new Date();
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(dateTime);
		calendar.set(Calendar.DAY_OF_MONTH,28);
		int date = calendar.get(Calendar.DAY_OF_MONTH);
		int month = calendar.get(Calendar.MONTH) + 1;
		int year = calendar.get(Calendar.YEAR);
		log.info("还款日 {}" , date);
		log.info("还款月 {}" , month);
		log.info("还款年 {}" , year);

		calendar.set(Calendar.MONTH, month+terms);
		date = calendar.get(Calendar.DAY_OF_MONTH);
		month = calendar.get(Calendar.MONTH);
		year = calendar.get(Calendar.YEAR);
		log.info("还款日 {}" , date);
		log.info("还款月 {}" , month);
		log.info("还款年 {}" , year);

		Calendar calendar1 = Calendar.getInstance();
		log.info("year {}",calendar1.get(Calendar.YEAR)+"");
		log.info("year {}",(calendar1.get(Calendar.MONTH) + 1) + "");
		log.info("year {}",calendar1.get(Calendar.DAY_OF_MONTH) +1+"");





	}
}



