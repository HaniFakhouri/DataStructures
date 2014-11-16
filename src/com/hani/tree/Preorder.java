package com.hani.tree;

import com.hani.stack.MyArrayStack;

public class Preorder<T> extends TreeIterator<T> {

	private MyArrayStack<BinaryNode<T>> s;
	
	public Preorder(BinaryTree<T> theTree) {
		super(theTree);
		s = new MyArrayStack<>();
		s.push(t.getRoot());
	}

	@Override
	public void first() {
		s.makeEmpty();
		if (t.getRoot() != null) {
			s.push(t.getRoot());
			advance();
		}
	}

	@Override
	public void advance() {
		
		if (s.isEmpty()) {
			if (current == null)
				return;
			current = null;
			return;
		}
		
		current = s.topAndPop();
		System.out.println("Visited: " + current.getData().toString());
		
		if (current.getRight() != null)
			s.push(current.getRight());
		if (current.getLeft() != null)
			s.push(current.getLeft());
		
	}

}
