package com.hani.problems;

import java.util.LinkedList;
import java.util.List;

public class SetOfStacks<T> {

	private List<MyStack<T>> set;
	private int nrStacks;
	
	public SetOfStacks() {
		set = new LinkedList<>();
		init();
	}
	
	private void init() {
		nrStacks=0;
		set.add(new MyStack<T>());
	}
	
	public void push(T element) {
		if ( set.get(nrStacks).size() == MyStack.INITIAL_CAPACITY ) {
			addNewStack();
		}
		set.get(nrStacks).push(element);
	}
	
	public T pop() {
		T res = set.get(nrStacks).pop();
		if (set.get(nrStacks).size() == 0)
			nrStacks--;
		return res;
	}
	
	public T popAt(int index) {
		if (index > nrStacks)
			return null;
		T res = set.get(index).pop();
		if (set.get(index).size()==0)
			nrStacks--;
		if (nrStacks >= index+1) {
			T elem_from_topper_stack = set.get(index+1).pop();
			if (set.get(index+1).size() == 0) {
				nrStacks--;
			}
			set.get(index).push(elem_from_topper_stack);
		}
		return res;
		
	}
	
	public T top() {
		return set.get(nrStacks).top();
	}
	
	public int nrStacks() {
		return nrStacks+1;
	}
	
	public void printStack() {
		for (MyStack<T> s : set)
			s.printStack();
	}
	
	private void addNewStack() {
		nrStacks++;
		set.add(new MyStack<T>());
	}
	
	public static void main(String[] args) {
		
		SetOfStacks<Integer> s = new SetOfStacks<>();
		for (int i=0; i<10; i++)
			s.push(i+1);
		
		System.out.println(s.nrStacks() + " stacks");
		s.printStack();
		
	}

}
