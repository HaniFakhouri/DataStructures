package com.hani.linkedlist;

public class ListNode<T> {
	T element;
	ListNode<T> next;
	public ListNode(T element) {
		this(element, null);
	}
	public ListNode(T element, ListNode<T> next) {
		this.element = element;
		this.next = next;
	}
}
