package com.hani.tree;

public class BinaryTree<T> {

	private BinaryNode<T> root;
	
	public BinaryTree() {
		root = null;
	}
	
	public BinaryTree(T data) {
		this(data, null, null);
	}
	
	public BinaryTree(T data, BinaryNode<T> lt, BinaryNode<T> rt) {
		root = new BinaryNode<T>(data, lt, rt);
	}
	
	public BinaryNode<T> getRoot() {
		return root;
	}
	
	public int size() {
		return BinaryNode.size(root);
	}
	
	public int height() {
		return BinaryNode.height(root);
	}
	
	public void printPreOrder() {
		if (!isEmpty())
			root.printPreOrder();
	}
	
	public void printPostOrder() {
		if (!isEmpty())
			root.printPostOrder();
	}
	
	public void printInOrder() {
		if (!isEmpty())
			root.printInOrder();
	}
	
	public boolean isEmpty() {
		return root == null;
	}
	
	public void merge(T rootItem, BinaryTree<T> t1, BinaryTree<T> t2) {
		
		if (t1.root == t2.root && t1!=null)
			return;
		
		root = new BinaryNode<T>(rootItem, t1.root, t2.root);
		
		if (this != t1)
			t1.root = null;
		if (this != t2)
			t2.root = null;
		
	}
	
	public void makeEmpty() {
		root = null;
	}
	
}
