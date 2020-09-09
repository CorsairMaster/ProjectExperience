package com.quickly.devploment.leetcode.lru.table;

/**
 * @Author lidengjin
 * @Date 2020/9/9 8:20 下午  合并连个有序链表
 * @Version 1.0
 */
/*
 * 合并两个有序的单链表，输出的也为有序的链表  并且不改变原来两个链表的结构
 * 这里就只在节点中设置一个data域 按照data域大小排序
 * 这里假定头结点的data值为0
 *
 */
public class SortLinkedList {
	public static void main(String[] args) {
		Linkedlist linkedlist1 = new Linkedlist();
		Linkedlist linkedlist2 = new Linkedlist();
		linkedlist1.addnode(new Datanode(1));
		linkedlist1.addnode(new Datanode(3));
		linkedlist1.addnode(new Datanode(5));
		linkedlist2.addnode(new Datanode(2));
		linkedlist2.addnode(new Datanode(6));
		linkedlist2.addnode(new Datanode(7));
		System.out.println("现在开始打印两个有序的单链表");
		linkedlist1.show();
		linkedlist2.show();
		System.out.println("=====================");
		System.out.println("合并两个有序链表");
		CombineLinkedList(linkedlist1.getHead(), linkedlist2.getHead());
		linkedlist1.show();
		linkedlist2.show();
	}


	//定义函数 合并两个有序的单链表
	//@param  两个单链表的头结点
	//@return 返回一个合并后的有序单链表并打印
	public static void CombineLinkedList(Datanode head1, Datanode head2) {
		if (head1.next == null) {
			System.out.println("单链表为空");
			return;
		}
		if (head2.next == null) {
			System.out.println("单链表为空");
			return;
		}
		Linkedlist linkedlist = new Linkedlist(); //新的链表
		Datanode temp1 = head1.next;
		Datanode temp2 = head2.next;
		while (temp1 != null && temp2 != null)     //两个指针分别遍历两条单链表
		{
			if (temp1.data <= temp2.data) {
				linkedlist.addnode(new Datanode(temp1.data));
				temp1 = temp1.next;
			} else {
				linkedlist.addnode(new Datanode(temp2.data));
				temp2 = temp2.next;
			}
		}
		if (temp1 == null)     //如果link1遍历结束，则把第二条链表直接插进去即可
		{
			while (temp2 != null) {
				linkedlist.addnode(new Datanode(temp2.data));
				temp2 = temp2.next;
			}
		}
		if (temp2 == null) {       //如果link2遍历结束，则把第一条链表直接插进去即可
			while (temp1 != null) {
				linkedlist.addnode(new Datanode(temp1.data));
				temp1 = temp1.next;
			}
		}

		linkedlist.show();
		return;

	}
}

class Linkedlist {

	Datanode head = new Datanode(0);

	//获取头结点
	public Datanode getHead() {
		return head;
	}

	//创建方法添加节点
	public void addnode(Datanode node) {
		if (head.next == null) {
			head.next = node;
		}     //如果单链表中此时只有头结点,则直接将信赖的结点插入头结点之后
		else {
			Datanode temp = head;
			while (temp.next != null) {
				temp = temp.next;
			}             //循环遍历找到链表尾部最后一个节点
			temp.next = node;
			//尾部插入新进来的节点
			System.out.println("节点插入成功");
		}
	}

	//打印单链表的节点情况
	public void show() {
		Datanode temp = head.next;
		System.out.println("现在开始打印节点：");
		while (temp != null) {
			System.out.println(temp);
			temp = temp.next;
		}
	}

}


//节点的创建
class Datanode {
	public int data;
	public Datanode next;

	public Datanode(int data) {
		this.data = data;
	}

	@Override
	public String toString() {
		return "Datanode [data=" + data + "]";
	}

}

