package com.hani.fly;

import java.util.Stack;

public class QueueTwoStacks<T> {
	
	private Stack<T> s1, s2;
	private int size;
	
	public QueueTwoStacks() {
		s1 = new Stack<>();
		s2 = new Stack<>();
		size = 0;
	}
	
	public void enqueue(T e) {
		if ( s1.isEmpty() )
			s1.push(e);
		else
			s2.push(e);
		size++;
	}
	
	public T dequeue() {
		T front = s1.pop();
		while ( !s2.isEmpty() ) {
			s1.push(s2.pop());
		}
		T top = s1.pop();
		while ( !s1.isEmpty() ) {
			s2.push(s1.pop());
		}
		s1.push(top);
		size--;
		return front;
	}
	
	public T getFront() {
		return s1.peek();
	}
	
	public T getBack() {
		return s2.peek();
	}
	
	public static void main(String[] args) {
		
		QueueTwoStacks<Integer> q = new QueueTwoStacks<>();
		
		q.enqueue(1);
		q.enqueue(2);
		q.enqueue(3);
		q.enqueue(4);
		
		System.out.println(q.getFront()); // 1
		System.out.println(q.getBack());  // 4
		
		System.out.println(q.dequeue());
		
		System.out.println(q.getFront()); // 2
		System.out.println(q.getBack());  // 4
		
		q.enqueue(5);
		q.enqueue(6);
		q.enqueue(7);
		
		System.out.println(q.getFront()); // 2
		System.out.println(q.getBack());  // 7
		
		q.dequeue();
		q.dequeue();
		
		System.out.println(q.getFront()); // 4
		System.out.println(q.getBack());  // 7
		
	}

}
