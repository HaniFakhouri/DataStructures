package com.hani.problems;

public class MyStack<T> {
	
	public static final int INITIAL_CAPACITY = 3;
	
	private int topOfStack;
	private T[] a; 
	
	public MyStack() {
		a = (T[])new Object[INITIAL_CAPACITY];
		topOfStack = -1;
	}
	
	public boolean isEmpty() {
		return topOfStack == -1;
	}
	
	public void makeEmpty() {
		topOfStack = -1;
	}
	
	public int size() {
		return topOfStack+1;
	}
	
	public T pop() {
		return a[topOfStack--];
	}
	
	public T top() {
		return a[topOfStack];
	}
	
	public void push(T element) {
		//if (topOfStack+1 == a.length)
		//	make_larger();
		a[++topOfStack] = element;
	}
	
	public void printStack() {
		for (int i=topOfStack; i>=0; i--)
			System.out.print(a[i] + " ");
		System.out.println();
	}
	
	private void make_larger() {
		int new_size = 2 * a.length;
		T[] tmp = (T[]) new Object[new_size];
		for (int i=0; i<a.length; i++)
			tmp[i] = a[i];
		a = tmp;
	}
	
	public static void main(String[] args) {
		
		MyStack<Integer> s = new MyStack<>();
		s.push(1);
		s.push(2);
		s.push(3);
		s.push(4);
		s.push(5);
		s.push(6);
		s.push(7);
		s.printStack();
		
	}

}
