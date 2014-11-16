package com.hani.stack;

public class MyListStack<T> implements MyStack<T> {

	private ListNode<T> topOfStack;
	
	public MyListStack() {
		topOfStack = null;
	}
	
	@Override
	public void push(T x) {
		topOfStack = new ListNode<T>(x, topOfStack);
	}

	@Override
	public void pop() {
		if (isEmpty())
			return;
		topOfStack = topOfStack.next;
	}

	@Override
	public T top() {
		if (isEmpty())
			return null;
		return topOfStack.element;
	}

	@Override
	public T topAndPop() {
		if (isEmpty())
			return null;
		T returnValue = topOfStack.element;
		topOfStack = topOfStack.next;
		return returnValue;
	}

	@Override
	public boolean isEmpty() {
		return topOfStack == null;
	}

	@Override
	public void makeEmpty() {
		topOfStack = null;
	}
	
	public void print() {
		ListNode<T> n = topOfStack;
		while (n != null) {
			System.out.print(n.element + "  ");
			n = n.next;
		}
		System.out.println();
	}

	@SuppressWarnings("hiding")
	private class ListNode<T> {
		private ListNode<T> next;
		private T element;
		@SuppressWarnings("unused")
		public ListNode(T element) {
			this(element, null);
		}
		public ListNode(T element, ListNode<T> next) {
			this.element = element;
			this.next = next;
		}
	}
	
	public static void main(String[] args) {
		
		MyListStack<Integer> s = new MyListStack<>();
		s.push(10);
		s.push(20);
		s.push(15);
		s.print();
		System.out.println(s.top());
		System.out.println(s.topAndPop());
		s.print();
		
	}
	
}
