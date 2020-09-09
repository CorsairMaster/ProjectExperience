package com.quickly.devploment.leetcode.lru.table;

/**
 * @Author lidengjin
 * @Date 2020/9/9 8:43 下午 求链表的中间节点  快慢指针 同时执行
 * @Version 1.0
 */

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
}



