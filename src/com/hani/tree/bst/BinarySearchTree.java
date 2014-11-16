package com.hani.tree.bst;

public class BinarySearchTree<T extends Comparable<? super T>> {
	
	private BinaryNode<T> root;
	
	public BinarySearchTree() {
		root = null;
	}
	
	public BinarySearchTree(T x) {
		root = new BinaryNode<T>(x);
	}
	
	public BinarySearchTree(BinaryNode<T> root) {
		this.root = root;
	}
	
	public T getRoot() {
		return root.data;
	}
	
	public void insert(T x) {
		root = insert(x, root);
	}
	
	public T findMin() {
		return findMin(root).data;
	}
	
	public T findMax() {
		return findMax(root).data;
	}
	
	public boolean isEmpty() {
		return root == null;
	}
	
	public void makeEmpty() {
		root = null;
	}
	
	public T find(T x) {
		return find(x, root).data;
	}
	
	public void removeMin() {
		root = removeMin(root);
	}
	
	public void removeMax() {
		root = removeMax(root);
	}
	
	public void remove(T x) {
		root = remove(x, root);
	}
	
	public void printPreOrder() {
		printPreOrder(root);
		System.out.println();
	}
	
	protected void printPreOrder(BinaryNode<T> node) {
		System.out.println(node.data);
		if (node.left != null)
			printPreOrder(node.left);
		if (node.right != null)
			printPreOrder(node.right);
	}
	
	private BinaryNode<T> find(T x, BinaryNode<T> node) {
		while (node != null) {
			if (x.compareTo(node.data) > 0) {
				node = node.right;
			} else if (x.compareTo(node.data) < 0) {
				node = node.left;
			} else {
				return node;
			}
		}
		return null;
	}
	
	protected BinaryNode<T> insert(T x, BinaryNode<T> node) {
		if (node == null) {
			node = new BinaryNode<T>(x);
		} else if ( x.compareTo(node.data) > 0 ) {
			node.right = insert(x, node.right);
		} else if ( x.compareTo(node.data) < 0 ) {
			node.left = insert(x, node.left);
		} else {
			return null; // No duplicates allowed
		}
		return node;
	}
	
	protected BinaryNode<T> remove(T x, BinaryNode<T> node) {
		if (node == null) {
			return null;
		} else if (x.compareTo(node.data) > 0) {
			node.right = remove(x, node.right);
		} else if (x.compareTo(node.data) < 0) {
			node.left = remove(x, node.left);
		} else if (node.left != null && node.right != null) {
			// Node has two children:
			// replace the node with the minimum node of the right subtree
			// then remove the minimum node in the right subtree
			node.data = findMin(node.right).data;
			node.right = removeMin(node.right);
		} else {
			node = (node.left != null) ? node.left : node.right;
		}
		return node;
	}
	
	protected BinaryNode<T> removeMin(BinaryNode<T> node) {
		if (node == null)
			return null;
		else if (node.left != null) {
			node.left = removeMin(node.left);
			return node;
		} else {
			return node.right;
		}
	}
	
	protected BinaryNode<T> removeMax(BinaryNode<T> node) {
		if (node == null)
			return null;
		if (node.right != null) {
			node.right = removeMax(node.right);
			return node;
		} else {
			return node.left;
		}
	}
	
	protected BinaryNode<T> findMin(BinaryNode<T> node) {
		if (node != null) {
			while (node.left != null)
				node = node.left;
		}
		return node;
	}
	
	private BinaryNode<T> findMax(BinaryNode<T> node) {
		if (node != null) {
			while (node.right != null)
				node = node.right;
		}
		return node;
	}
	
	// Single right rotation
	// Outside insertion: left -> left
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static BinaryNode rotateWithLeftChild(BinaryNode k2) {
		BinaryNode k1 = k2.left;
		k2.left = k1.right;
		k1.right = k2;
		return k1;
	}
	
	// Single left rotation
	// Outside insertion: right -> right
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static BinaryNode rotateWithRightChild(BinaryNode k1) {
		BinaryNode k2 = k1.right;
		k1.right = k2.left;
		k2.left = k1;
		return k2;
	}
	
	// Double rotation
	// Inside insertion: right -> left
	// First rotate left child with its right child;
	// then rotate k3 with the new left child
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static BinaryNode doubleRotateWithLeftChild(BinaryNode k3) {
		k3.left = rotateWithRightChild(k3.left);
		return rotateWithLeftChild(k3);
	}

	// Double rotation
	// Inside insertion: left -> right
	// First rotate right child with its left child;
	// then rotate k1 with the new right child
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static BinaryNode doubleRotateWithRightChild(BinaryNode k1) {
		k1.right = rotateWithLeftChild(k1.left);
		return rotateWithRightChild(k1);
	}
	
	public static void main(String[] args) {
		
		BinarySearchTree<Integer> bst = new BinarySearchTree<>();

		bst.insert(7);
		bst.insert(9);
		bst.insert(2);
		bst.insert(1);
		bst.insert(5);
		bst.insert(3);
		
		bst.printPreOrder();

		bst.removeMin();
		bst.removeMax();
		
		bst.printPreOrder();
		
		System.out.println(bst.findMin());
		System.out.println(bst.findMax());
		System.out.println(bst.find(5));
		
	}
	
}
