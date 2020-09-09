package com.quickly.devploment.leetcode.lru.table;

/**
 * @Author lidengjin
 * @Date 2020/9/9 8:37 下午
 * @Version 1.0 删除倒数第 n 个节点
 * 	两个指针一块儿执行 先让一个走到 n  的位置，然后两个指针同时走，当另一个走到头的时候，该指针指向的就是 倒数第 n 个节点
 */

public class Solution {
	public Datanode removeNthFromEnd(Datanode head, int n) {
		if (head == null)
			return head;
		Datanode p = head;
		Datanode q = head;
		//用count记录走了多少步，和最终链表的长度
		int count = 0;
		while (q.next != null) {
			count++;
			//前n步只让q指针走
			if (count <= n) {
				q = q.next;
			} else {
				q = q.next;
				p = p.next;
			}
		}
		//循环结束时p到达了倒数n个元素的前面一个元素
		//两个特殊情况，即链表只有一个元素和要删除的为头节点的情况
		if (head.next == null || count + 1 == n) {
			head = head.next;
		} else {
			p.next = p.next.next;
		}
		return head;
	}
}