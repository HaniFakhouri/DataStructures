package com.hani.tree;

abstract class TreeIterator<T> {
	
	protected BinaryTree<T> t;
	protected BinaryNode<T> current;
	
	public TreeIterator(BinaryTree<T> theTree) {
		t = theTree;
		current = null;
	}
	
	abstract public void first();
	abstract public void advance();

}
