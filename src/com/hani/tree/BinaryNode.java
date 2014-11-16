package com.hani.tree;

public class BinaryNode<T> {
	
	private T data;
	private BinaryNode<T> left;
	private BinaryNode<T> right;
	
	public BinaryNode(T data) {
		this(data, null, null);
	}
	
	public BinaryNode(T data, BinaryNode<T> lt, BinaryNode<T> rt) {
		this.data = data;
		this.left = lt;
		this.right = rt;
	}
	
	public T getData() {
		return data;
	}
	
	public BinaryNode<T> getLeft() {
		return left;
	}
	
	public BinaryNode<T> getRight() {
		return right;
	}
	
	public void setData(T data) {
		this.data = data;
	}
	
	public void setLeft(BinaryNode<T> t) {
		this.left = t;
	}
	
	public void setRight(BinaryNode<T> t) {
		this.right = t;
	}
	
	public static <T> int size(BinaryNode<T> t) {
		if (t == null) {
			return 0;
		}
		return 1 + size(t.getLeft()) + size(t.getRight());
	}
	
	public BinaryNode<T> duplicate() {
		BinaryNode<T> root = new BinaryNode<T>(data, null, null);
		if (left != null) {
			root.left = left.duplicate();
		}
		if (right != null) {
			root.right = right.duplicate();
		}
		return root;
	}
	
	public void printPreOrder() {
		// node left right
		System.out.println(data);
		if (left != null)
			left.printPreOrder();
		if (right != null)
			right.printPreOrder();
	}
	
	public void printPostOrder() {
		// left right node
		if (left != null)
			left.printPostOrder();
		if (right != null)
			right.printPostOrder();
		System.out.println(data);
	}
	
	public void printInOrder() {
		// left node right
		if (left != null)
			left.printPostOrder();
		System.out.println(data);
		if (right != null)
			right.printPostOrder();
	}
	
	public static <T> int height(BinaryNode<T> t) {
		if (t == null) {
			return -1;
		}
		return 1 + Math.max(height(t.getLeft()), height(t.getRight()));
	}
	
	
	
}
