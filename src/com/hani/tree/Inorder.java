package com.hani.tree;

import com.hani.stack.MyArrayStack;

public class Inorder<T> extends PostOrder<T> {

	public Inorder(BinaryTree<T> theTree) {
		super(theTree);
		s = new MyArrayStack<>();
		s.push(new StNode<>(t.getRoot()));
	}

	@Override
	public void first() {
		s.makeEmpty();
		if (t.getRoot() != null) {
			s.push(new StNode<>(t.getRoot()));
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
		
		StNode<T> n = null;
		for (;;) {
			n = s.topAndPop();
			
			if (++n.timesPopped == 2) {
				current = n.node;
				System.out.println("Visited: " + current.getData().toString());
				if (n.node.getRight() != null)
					s.push(new StNode<>(n.node.getRight()));
				return;
			}
			
			s.push(n);
			if (n.node.getLeft() != null)
				s.push(new StNode<>(n.node.getLeft()));
		}
		
	}

}
