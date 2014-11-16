package com.hani.tree;

import com.hani.stack.MyArrayStack;

public class PostOrder<T> extends TreeIterator<T> {
	
	protected MyArrayStack<StNode<T>> s;
	
	protected static class StNode<T> {
		BinaryNode<T> node;
		int timesPopped;
		StNode(BinaryNode<T> n) {
			node = n;
			timesPopped = 0;
		}
	}

	public PostOrder(BinaryTree<T> theTree) {
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
			
			if (++n.timesPopped == 3) {
				current = n.node;
				System.out.println("Visited: " + current.getData().toString());
				return;
			}
			
			s.push(n);
			if (n.timesPopped == 1) {
				if (n.node.getLeft() != null)
					s.push(new StNode<>(n.node.getLeft()));
			} else {
				if (n.node.getRight() != null)
					s.push(new StNode<>(n.node.getRight()));
			}
		}
		
	}

}
