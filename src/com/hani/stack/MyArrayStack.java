package com.hani.stack;

public class MyArrayStack<T> implements MyStack<T> {

	private static final int DEFAULT_CAPACITY = 5;
	
	private T[] theArray;
	private int topOfStack;
	
	@SuppressWarnings("unchecked")
	public MyArrayStack() {
		theArray = (T[])new Object[DEFAULT_CAPACITY];
		topOfStack = -1;
	}
	
	@Override
	public void push(T x) {
		if (topOfStack+1 == theArray.length) {
			doubleStack();
		}
		theArray[++topOfStack] = x;
	}

	@Override
	public void pop() {
		if (isEmpty())
			return;
		topOfStack--;
	}

	@Override
	public T top() {
		if (isEmpty())
			return null;
		return theArray[topOfStack];
	}

	@Override
	public T topAndPop() {
		if (isEmpty())
			return null;
		return theArray[topOfStack--];
	}

	@Override
	public boolean isEmpty() {
		return topOfStack == -1;
	}

	@Override
	public void makeEmpty() {
		topOfStack = -1;
	}
	
	public void print() {
		System.out.println(theArray[topOfStack] + " <-- top of stack");
		for (int i=topOfStack-1; i>=0; i--) {
			System.out.println(theArray[i]);
		}
		System.out.println("Capacity: " + theArray.length);
		System.out.println();
	}
	
	private void doubleStack() {
		int newSize = 2 * theArray.length;
		@SuppressWarnings("unchecked")
		T[] newArray = (T[])new Object[newSize];
		for (int i=0; i<theArray.length; i++)
			newArray[i] = theArray[i];
		theArray = newArray;
	}
	
	public static void main(String[] args) {
		
		MyArrayStack<Integer> s = new MyArrayStack<>();
		s.push(10);
		s.push(20);
		s.push(15);
		s.print();
		s.push(10);
		s.push(101);
		s.push(50);
		s.print();
		System.out.println(s.top());
		s.print();
		System.out.println(s.topAndPop());
		System.out.println(s.top());
	}

}
