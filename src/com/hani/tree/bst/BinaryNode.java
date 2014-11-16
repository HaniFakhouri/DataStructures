package com.hani.tree.bst;

public class BinaryNode<T> {
	
	T data;
	BinaryNode<T> left;
	BinaryNode<T> right;
	
	public BinaryNode(T data) {
		this.data = data;
		left = right = null;
	}

}
