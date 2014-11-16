package com.hani.fly;

import java.util.Stack;

public class QueueThreeStacks<T> {

	private Stack<T> s1, s2, s3;
	private int size;

	public QueueThreeStacks() {
		s1 = new Stack<>();
		s2 = new Stack<>();
		s3 = new Stack<>();
	}

	public void enqueue(T e) {

		if ( s1.isEmpty() )
			s1.push(e);
		else if ( s2.isEmpty() )
			s2.push(e);
		else {
			
			if ( s2.size() >= s1.size() ) {
				System.out.println("I am here");
				pushIntoButtom(s1, s2.pop());
				pushIntoButtom(s2, e);
			} else {
				pushIntoButtom(s2, e);
			}
			
		}
		size++;		
	}

	public T dequeue() {
		T e = s1.pop();
		
		pushIntoButtom(s1, s2.pop());
		
		size--;
		
		return e;
	}

	public T getFront() {
		return s1.peek();
	}

	public T getBack() {
		return null;
	}
	
	public void print() {
		System.out.print("s1: ");
		while ( !s1.isEmpty() ) {
			s3.push(s1.pop());
		}
		while ( !s3.isEmpty() ) {
			System.out.print(s3.peek() + " ");
			s1.push(s3.pop());
		}
		System.out.println();
		System.out.print("s2: ");
		while ( !s2.isEmpty() ) {
			s3.push(s2.pop());
		}
		while ( !s3.isEmpty() ) {
			System.out.print(s3.peek() + " ");
			s2.push(s3.pop());
		}
	}

	private void pushIntoButtom(Stack<T> s, T e) {
		while ( !s.isEmpty() ) {
			s3.push(s.pop());
		}
		s.push(e);
		while ( !s3.isEmpty() ) {
			s.push(s3.pop());
		}
	}
	
	public static void main(String[] args) {
		
		QueueThreeStacks<Integer> q = new QueueThreeStacks<>();
		
		q.enqueue(1);
		q.enqueue(2);
		q.enqueue(3);
		q.enqueue(4);
		q.enqueue(5);
		q.enqueue(6);
		q.enqueue(7);
		q.enqueue(8);
		q.enqueue(9);
		q.enqueue(10);
		
		System.out.println(q.getFront());
		
		q.print();
		
	}

}
