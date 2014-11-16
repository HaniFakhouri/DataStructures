package com.hani.problems;

import java.util.LinkedList;
import java.util.Queue;

public class BST<T extends Comparable<? super T>> {

	private BNode<T> root;
	
	public BST(BNode<T> root) {
		this.root = root;
	}
	
	public BST(T data) {
		root = new BNode<T>(data);
	}
	
	public void insert(T x) {
		root = insert(x, root);
	}
	
	public void remove(T x) {
		root = remove(x, root);
	}
	
	public void removeMin() {
		root = removeMin(root);
	}
	
	public void removeMax() {
		root = removeMax(root);
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
	
	public BNode<T> find(T x, BNode<T> node) {
		if (node != null) {
			if ( x.equals(node.data) ) {
				return node;
			} else if ( x.compareTo(node.data) > 0 ) {
				return find(x, node.right);
			} else {
				return find(x, node.left);
			}
		}
		return null;
	}
	
	public void printPostOrder() {
		printPostOrder(root);
		System.out.println();
	}
	
	public void printInOrder() {
		printInOrder(root);
		System.out.println();
	}
	
	public void printPreOrder() {
		printPreOrder(root);
		System.out.println();
	}
	
	public void printLevelOrder() {
		printLevelOrder(root);
		System.out.println();
	}
	
	public void printInOrderReversed() {
		printInOrderReversed(root);
		System.out.println();
	}
	
	private void printPreOrder(BNode<T> node) {
		if (node == null)
			return;
		System.out.print(node.data + " ");
		if (node.left != null)
			printPreOrder(node.left);
		if (node.right != null)
			printPreOrder(node.right);
	}
	
	private void printPostOrder(BNode<T> node) {
		if (node == null)
			return;
		if (node.left != null)
			printPostOrder(node.left);
		if (node.right != null)
			printPostOrder(node.right);
		System.out.print(node.data + " ");
	}
	
	private void printInOrder(BNode<T> node) {
		if (node == null)
			return;
		if (node.left != null)
			printInOrder(node.left);
		System.out.print(node.data + " ");
		if (node.right != null)
			printInOrder(node.right);
	}
	
	private void printLevelOrder(BNode<T> node) {
		if (node == null)
			return;
		
		Queue<BNode<T>> q = new LinkedList<>();
		q.add(node);
		
		while (!q.isEmpty()) {
			
			BNode<T> n = q.poll();
			System.out.print(n.data + " ");
			
			if (n.left != null)
				q.add(n.left);
			if (n.right != null)
				q.add(n.right);
			
		}
		
	}
	
	private void printInOrderReversed(BNode<T> node) {
		if (node == null)
			return;
		if (node.right != null)
			printInOrderReversed(node.right);
		System.out.print(node.data + " ");
		if (node.left != null)
			printInOrderReversed(node.left);
		
	}
	
	public T findMin() {
		return findMin(root).data;
	}
	
	public T findMax() {
		return findMax(root).data;
	}
	
	public int size() {
		return size(root);
	}
	
	public int height() {
		return height(root);
	}
	
	private BNode<T> findMin(BNode<T> node) {
		if (node != null) {
			while (node.left != null)
				node = node.left;
		}
		return node;
	}
	
	private BNode<T> findMax(BNode<T> node) {
		if (node != null) {
			while (node.right != null)
				node = node.right;
		}
		return node;
	}
	
	private BNode<T> insert(T data, BNode<T> node) {
		if (node == null) {
			node = new BNode<T>(data, null, null);
		} else if ( data.compareTo(node.data) > 0) {
			node.right = insert(data, node.right);
		} else if ( data.compareTo(node.data) < 0 ) {
			node.left = insert(data, node.left);
		} else {
			return null;
		}
		return node;
	}
	
	private BNode<T> removeMin(BNode<T> node) {
		if ( node == null )
			return null;
		else if ( node.left != null ) {
			node.left = removeMin(node.left);
			return node;
		} else {
			return node.right;
		}
	}
	
	private BNode<T> removeMax(BNode<T> node) {
		if ( node == null )
			return null;
		else if ( node.right != null ) {
			node.right = removeMax(node.right);
			return node;
		} else {
			return node.left;
		}
	}
	
	private BNode<T> remove(T x, BNode<T> node) {
		if (node == null)
			return null;
		if ( x.compareTo(node.data) > 0 ) {
			node.right = remove(x, node.right);
		} else if ( x.compareTo(node.data) < 0 ) {
			node.left = remove(x, node.left);
		} else {
			
			if ( node.left != null && node.right != null ) {
				
				node.data = findMin(node.right).data;
				node.right = removeMin(node.right);				
				
			} else {
				
				node = (node.left != null) ? node.left : node.right;
				
			}
			
		}
		return node;
	}
	
	private int size(BNode<T> node) {
		if (node == null)
			return 0;
		return 1 +
				size(node.left) + size(node.right);
	}
	
	private int height(BNode<T> node) {
		if (node == null)
			return -1;
		return 1 +
				Math.max(height(node.left), height(node.right));
	}
	
	public int depth(T node) {
		BNode<T> n = find(node, root);
		if (n == null)
			return -1;
		return depth(root, n);
	}
	
	public int depth_2(T node) {
		BNode<T> n = find(node, root);
		if (n == null)
			return -1;
		return depth(root, n, 0);
	}
	
	private int depth(BNode<T> root, BNode<T> node, int depth) {
		
		if (root==null) 
			return 0;
		if (root == node) 
			return depth;
		
		int d = depth(root.left, node, depth+1);
		if (d!=0) 
			return d;
		
		return depth(root.right, node, depth+1);
		
	}
	
	private int depth(BNode<T> root, BNode<T> node) {
		if (root.data.compareTo(node.data) > 0) {
			return 1 + depth(root.left, node);
		} else if (root.data.compareTo(node.data) < 0) {
			return 1 + depth(root.right, node);
		} else {
			return 0;
		}
	}
	
	public boolean isBalanced() {
		return isBalanced(root);
	}
	
	public int MinHeight() {
		return MinHeight(root);
	}
	
	public int MaxHeight() {
		return MaxHeight(root);
	}
	
	private int MinHeight(BNode<T> node) {
		return
				Math.min(height(node.left), height(node.right));
	}
	
	private int MaxHeight(BNode<T> node) {
		return
				Math.max(height(node.left), height(node.right));
	}
	
	private boolean isBalanced(BNode<T> node) {
		return (MaxHeight(node) - MinHeight(node)) <= 1;
	}
	
	public boolean isInTree(T root, T node) {		
		BNode<T> r = find(root, this.root);
		BNode<T> n = find(node, this.root);
		if (r == null || n == null)
			throw new IllegalArgumentException("Node not found in tree");
		return isInTree(r, n);
	}
	
	private boolean isInTree(BNode<T> root, BNode<T> node) {
		if (root == null)
			return false;
		if (root==node)
			return true;
		return isInTree(root.left, node) || isInTree(root.right, node);
	}
	
	public T getLowestCommonAncestor(T node1, T node2) {
		BNode<T> n1 = find(node1, root);
		BNode<T> n2 = find(node2, root);
		if (n1 == null || n2 == null)
			throw new IllegalArgumentException("Node not found in tree");
		return getLowestCommonAncestor(root, n1, n2).data;
	}
	
	private BNode<T> getLowestCommonAncestor(BNode<T> root, BNode<T> node1, BNode<T> node2) {
		
		if (root == null)
			return null;
		if (root == node1 || root == node2)
			return root;
		
		BNode<T> left = getLowestCommonAncestor(root.left, node1, node2);
		BNode<T> right = getLowestCommonAncestor(root.right, node1, node2);
		
		if (left !=null && right!=null)
			return root;
		
		return (left!=null) ? left : right;
		
	}
	
	public boolean areSiblings(T node1, T node2) {
		BNode<T> n1 = find(node1, root);
		BNode<T> n2 = find(node2, root);
		if (n1==null || n2==null)
			return false;
		return areSiblings(root, n1, n2);
	}
	
	private boolean areSiblings(BNode<T> root, BNode<T> n1, BNode<T> n2) {
		if (root == null || n1==n2)
			return false;
		return root.left==n1 && root.right==n2
				|| root.right==n1 && root.left==n2 
				|| areSiblings(root.left, n1, n2)
				|| areSiblings(root.right, n1, n2);
	}
	
	public boolean areSameLevel(T node1, T node2) {
		BNode<T> n1 = find(node1, root);
		BNode<T> n2 = find(node2, root);
		if (n1==null || n2==null)
			return false;
		return depth(node1) == depth(node2);
	}
	
	public boolean areCousins(T node1, T node2) {
		return areSameLevel(node1, node2) && !areSiblings(node1, node2);
	}
	
	@SuppressWarnings("hiding")
	class BNode<T> {
		public T data;
		public BNode<T> left;
		public BNode<T> right;
		public BNode(T data, BNode<T> left, BNode<T> right) {
			this.data = data;
			this.left = left;
			this.right = right;
		}
		public BNode(T data) {
			this(data, null, null);
		}
	}
	
	public BNode<T> getRoot() {
		return root;
	}
	
	/**
	 * Given a BST, transform it into greater sum tree where each node contains sum of all nodes greater than that node.
	 */
	public void sumUp() {
		sumUp(root, 0);
	}
	
	private int sumUp(BNode<T> node, int sum) {
		
		if (node == null)
			return 0;
		
		int rSum = sumUp(node.right, sum);
		int lSum = sumUp(node.left, sum + rSum + ((Integer)node.data));
		
		int temp = (Integer)node.data;
		node.data = (T)( (Integer)( rSum + sum ));
		
		return temp +  rSum + lSum;
		
		/*
		if (node.right != null) {
			sumUp(node.right, sum);
		}

		sum = sum + (Integer)node.data;
		node.data = (T)( (Integer)(sum - (Integer)(node.data) ));
		
		if (node.left != null) {
			sumUp(node.left, sum);
		}
		*/
		
	}
	
	public static void main(String[] args) {
		
		/*
		BST<Integer> t = new BST<>(30);
		t.insert(25);
		t.insert(35);
		t.insert(20);
		t.insert(27);
		t.insert(31);
		t.insert(40);
		t.insert(50);
		*/
		
		BST<Integer> t = new BST<>(11);
		t.insert(2);
		t.insert(1);
		t.insert(7);
		t.insert(29);
		t.insert(15);
		t.insert(40);
		t.insert(35);
		
		t.printInOrder();
		
		t.sumUp();
		
		t.printInOrder();
		
	}
	
}

















