package com.hani.linkedlist2;

public class ListNode<T> {
	T element;
	ListNode<T> next;
	ListNode<T> prev;
	public ListNode(T element) {
		this(element, null, null);
	}
	public ListNode(T element, ListNode<T> prev, ListNode<T> next) {
		this.element = element;
		this.prev = prev;
		this.next = next;
	}

}
