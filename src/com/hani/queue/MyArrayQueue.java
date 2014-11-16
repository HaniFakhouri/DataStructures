package com.hani.queue;

public class MyArrayQueue<T> {

	private static final int DEFAULT_CAPACITY = 5;
	
	private T[] theArray;
	private int front, back, currentSize;
	
	@SuppressWarnings("unchecked")
	public MyArrayQueue() {
		theArray = (T[]) new Object[DEFAULT_CAPACITY];
		makeEmpty();
	}
	
	public void makeEmpty() {
		front = currentSize = 0;
		back = -1;
	}
	
	public boolean isEmpty() {
		return currentSize == 0;
	}
	
	public void enqueque(T x) {
		if (currentSize == theArray.length)
			doubleQueue();
		back = increment(back);
		currentSize++;
		theArray[back] = x;
	}
	
	public T dequeue() {
		if (isEmpty())
			return null;
		T returnValue = theArray[front];
		front = increment(front);
		currentSize--;
		return returnValue;
	}
	
	public T getFront() {
		if (isEmpty())
			return null;
		return theArray[front];
	}
	
	public T getBack() {
		if (isEmpty())
			return null;
		return theArray[back];
	}
	
	public void print() {
		for (int i=0; i<theArray.length; i++) {
			System.out.print(theArray[i] + "  ");
		}
		System.out.println(" capacity: " + theArray.length);
	}
	
	private int increment(int x) {
		if (++x == theArray.length) {
			x = 0;
		}
		return x;
	}
	
	private void doubleQueue() {
		int newSize = 2 * theArray.length;
		@SuppressWarnings("unchecked")
		T[] newArray = (T[]) new Object[newSize];
		for (int i=0; i<currentSize; i++, front = increment(front)) {
			newArray[i] = theArray[i];
		}
		theArray = newArray;
		front = 0;
		back = currentSize - 1;
	}
	
	public static void main(String[] args) {
		
		MyArrayQueue<Integer> q = new MyArrayQueue<>();
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







