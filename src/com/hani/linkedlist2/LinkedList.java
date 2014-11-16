package com.hani.linkedlist2;

import java.security.acl.LastOwnerException;
import java.util.List;

public class LinkedList<T> {
	
	private ListNode<T> head;
	private ListNode<T> tail;
	private int size;
	
	public LinkedList() {
		clear();
	}
	
	private void clear() {
		size = 0;
		head = new ListNode<T>(null, null, null);
		tail = new ListNode<T>(null, head, null);
		head.next = tail;
	}
	
	public void add(T x) {
		addLast(x);
	}
	
	public void addFirst(T x) {
		add(0, x);
	}
	public void addLast(T x) {
		add(size, x);
	}
	
	public void add(int idx, T x) {
		ListNode<T> n = getNode(idx);
		ListNode<T> node = new ListNode<T>(x, n.prev, n);
		n.prev.next = node;
		n.prev = node;
		size++;
	}
	
	public void remove(int idx) {
		if (idx+1 > size)
			return;
		remove(getNode(idx));
	}
	
	public void remove(ListNode<T> n) {
		n.prev.next = n.next;
		n.next.prev = n.prev;
		size--;
	}
	
	public ListNode<T> getNode(int idx) {
		ListNode<T> n = null;
		if (idx < size / 2) {
			n = head.next;
			for (int i=0; i<idx; i++)
				n = n.next;
		} else {
			n = tail;
			for (int i=size; i>idx; i--)
				n = n.prev;
		}
		return n;
	}
	
	public T set(int idx, T x) {
		ListNode<T> n = getNode(idx);
		T old = n.element;
		n.element = x;
		return old;
	}
	
	public T getLast() {
		return getNode(size()-1).element;
	}
	
	public T getFirst() {
		return getNode(0).element;
	}
	
	public void removeLast() {
		remove(size-1);
	}
	
	public void removeFirst() {
		remove(0);
	}
	
	public int size() {
		return size;
	}
	
	public void print() {
		ListNode<T> n = head.next;
		while (n.next != null) {
			System.out.print(n.element + " ");
			n = n.next;
		}
		System.out.println();
	}
	
	public void test(T x) {
		if (head.next.element == x)
			System.out.println("E");
	}
	
	public static void main(String[] args) {
		
		LinkedList<Integer> l = new LinkedList<>();
		
		l.add(10);
		l.add(20);
		l.add(30);
		l.add(40);
		l.add(50);
		l.add(60);
		l.add(70);
		l.add(80);
		
		l.print();
		
	}

}



