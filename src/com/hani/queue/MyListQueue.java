package com.hani.queue;

public class MyListQueue<T> {

	private ListNode<T> front;
	private ListNode<T> back;
	
	public MyListQueue() {
		front = back = null;
	}
	
	public boolean isEmpty() {
		return front == null;
	}
	
	public void enqueque(T x) {
		if (isEmpty()) {
			front = back = new ListNode<T>(x);
		} else {
			back.next = new ListNode<T>(x);
			back = back.next;
		}
	}
	
	public T dequeue() {
		if (isEmpty())
			return null;
		T returnValue = front.element;
		front = front.next;
		return returnValue;
	}
	
	public T getFront() {
		if (isEmpty())
			return null;
		return front.element;
	}
	
	public T getBack() {
		if (isEmpty())
			return null;
		return back.element;
	}
	
	public void makeEmpty() {
		front = null;
		back = null;
	}
	
	public void print() {
		ListNode<T> n = front;
		while (n != null) {
			System.out.print(n.element + " ");
			n = n.next;
		}
		System.out.println();
	}
	
	@SuppressWarnings("hiding")
	private class ListNode<T> {
		private ListNode<T> next;
		private T element;
		public ListNode(T element) {
			this(element, null);
		}
		public ListNode(T element, ListNode<T> next) {
			this.element = element;
			this.next = next;
		}
	}
	
	public static void main(String[] args) {
		
		MyListQueue<Integer> q = new MyListQueue<>();
		q.enqueque(10);
		q.enqueque(40);
		q.enqueque(50);
		q.enqueque(120);
		q.enqueque(530);
		q.enqueque(5440);
		q.print();
		System.out.println(q.dequeue());
		System.out.println(q.getFront());
		System.out.println(q.getBack());
		
	}
	
}
