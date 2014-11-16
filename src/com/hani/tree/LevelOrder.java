package com.hani.tree;

import com.hani.queue.MyArrayQueue;

public class LevelOrder<T> extends TreeIterator<T> {

	private MyArrayQueue<BinaryNode<T>> q;
	
	public LevelOrder(BinaryTree<T> theTree) {
		super(theTree);
		q = new MyArrayQueue<>();
	}

	@Override
	public void first() {
		q.makeEmpty();
		if (t.getRoot() != null) {
			q.enqueque(t.getRoot());
			advance();
		}
	}

	@Override
	public void advance() {
		
		if (q.isEmpty()) {
			if (current == null)
				return;
			current = null;
			return;
		}
		
		current = q.dequeue();
		System.out.println("Visited: " + current.getData());
		
		if (current.getLeft() != null)
			q.enqueque(current.getLeft());
		if (current.getRight() != null)
			q.enqueque(current.getRight());
		
	}

}
