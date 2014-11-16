package com.hani.stack;

public interface MyStack<E> {

	public void push(E x);
	public void pop();
	public E top();
	public E topAndPop();
	public boolean isEmpty();
	public void makeEmpty();
	
}
