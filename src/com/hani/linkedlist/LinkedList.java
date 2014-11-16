package com.hani.linkedlist;

public class LinkedList<T> {

	private ListNode<T> header;
	private LinkedListIterator<T> it;
	
	public LinkedList() {
		header = new ListNode<T>(null);
		it = new LinkedListIterator<>(header);
	}
	
	public void insert(T x) {
		this.insert(x, it);
	}
	
	public T find(T x) {
		return findp(x).retrieve();
	}
	
	public boolean isEmpty() {
		return header.next == null;
	}
	
	public void makeEmpty() {
		header.next = null;
	}
	
	@SuppressWarnings("unused")
	private LinkedListIterator<T> zeroth() {
		return new LinkedListIterator<T>(header);
	}
	
	private LinkedListIterator<T> first() {
		return new LinkedListIterator<T>(header.next);
	}
	
	private void insert(T x, LinkedListIterator<T> p) {
		if (p != null && p.current != null) {
			p.current.next = new ListNode<T>(x, p.current.next);
		}
	}
	
	private LinkedListIterator<T> findp(T x) {
		ListNode<T> n = header.next;
		while (n != null && !n.element.equals(x)) {
			n = n.next;
		}
		return new LinkedListIterator<T>(n);
	}
	
	private LinkedListIterator<T> findPrevious(T x) {
		ListNode<T> n = header;
		while (n.next!=null && !n.next.element.equals(x)) {
			n = n.next;
		}
		return new LinkedListIterator<T>(n);
	}
	
	public void remove(T x) {
		LinkedListIterator<T> p = findPrevious(x);
		if (p.current.next != null) {
			p.current.next = p.current.next.next;
		}
	}
	
	public void printList(LinkedList<T> theList) {
		if (theList.isEmpty()) {
			System.out.print("Empty List");
		} else {
			LinkedListIterator<T> it = theList.first();
			for (; it.isValid(); it.advance()) {
				System.out.print(it.retrieve() + " ");
			}
		}
		System.out.println();
	}
	
	public static void main(String[] args) {
		
		LinkedList<Integer> l = new LinkedList<>();
		l.insert(10);
		l.insert(50);
		l.insert(501);
		l.insert(500);
		l.insert(150);
		l.printList(l);
		
		
	}
	
}
