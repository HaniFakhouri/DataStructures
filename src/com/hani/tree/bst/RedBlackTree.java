package com.hani.tree.bst;

public class RedBlackTree<T extends Comparable<? super T>> {

	public static final int RED = 0;
	public static final int BLACK = 1;
	
	private static class RedBlackNode<T> {
		RedBlackNode<T> left;
		RedBlackNode<T> right;
		T data;
		int color;
		public RedBlackNode(T data) {
			this(data, null, null);
		}
		public RedBlackNode(T data, RedBlackNode<T> left, RedBlackNode<T> right) {
			this.data = data;
			this.left = left;
			this.right = right;
			color = RedBlackTree.BLACK;
		}
	}
	
	/**
	 * 
	 * @param item the item we are inserting
	 * @param parent the parent of the item
	 * @return the new node recursively
	 */
	private RedBlackNode<T> rotate(T item, RedBlackNode<T> parent) {
		if ( compare(item, parent) < 0 ) { // item data < parent data -> go left
			if ( compare(item, parent.left) < 0 ) { // item data < parent.left data -> go left
				return parent.left = rotateWithLeftChild(parent.left); // inserting outside node: LL
			} else { // item data > parent.left data -> go right
				return parent.left = rotateWithRightChild(parent.left); // inserting inside node: LR
			}
		} else { // item data > parent data -> go right
			if ( compare(item, parent.right) < 0 ) { // item data < parent.right data -> go left
				return parent.right = rotateWithLeftChild(parent.right); // inserting inside node: RL
			} else { // item data > parent.right data -> go right
				return parent.right = rotateWithRightChild(parent.right); // inserting inside node: RR
			}
		}
	}
	
	private int compare(T x, RedBlackNode<T> t) {
		return compare(new RedBlackNode<T>(x), t);
	}
	
	private int compare(RedBlackNode<T> n1, RedBlackNode<T> n2) {
		return n1.data.compareTo(n2.data);
	}
	
	// Single right rotation
	// Outside insertion: left -> left
	private RedBlackNode<T> rotateWithLeftChild(RedBlackNode<T> k2) {
		RedBlackNode<T> k1 = k2.left;
		k2.left = k1.right;
		k1.right = k2;
		return k1;
	}

	// Single left rotation
	// Outside insertion: right -> right
	private RedBlackNode<T> rotateWithRightChild(RedBlackNode<T> k1) {
		RedBlackNode<T> k2 = k1.right;
		k1.right = k2.left;
		k2.left = k1;
		return k2;
	}

}
