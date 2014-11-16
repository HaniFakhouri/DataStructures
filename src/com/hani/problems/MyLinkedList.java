package com.hani.problems;

import java.util.LinkedList;

/**
 * Singly linked list
 * @author Hani
 *
 */

public class MyLinkedList<T> {

	private Node<T> first, last;
	private int size;

	public MyLinkedList() {
		clear();
	}

	public boolean isEmpty() {
		return first.next == null;
	}

	public void makeEmpty() {
		clear();
	}

	public int size() {
		return size;
	}

	public void add(T data) {

		Node<T> n = new Node<T>(data);

		if (isEmpty()) {
			first.next = n;
			last.next = n;
		} else {
			Node<T> l = last.next;
			l.next = n;
			last.next = n;
		}

		size++;

	}

	public boolean remove(T data) {
		if (isEmpty())
			return false;

		Node<T> n = first;
		while (n!=null) {
			if (n.next.data.equals(data)) {
				n.next = n.next.next;
				size--;
				return true;
			}
			n = n.next;
		}

		return false;
	}

	public T get(Node<T> n) {
		return get(n.data);
	}

	public T get(T d) {
		if (isEmpty())
			return null;
		Node<T> n = first.next;
		while (n!=null) {
			if (n.data.equals(d))
				return d;
			n = n.next;
		}
		return null;
	}

	public void printList() {
		Node<T> n = first.next;
		while (n != null) {
			System.out.print(n.data);
			n = n.next;
			if (n!=null)
				System.out.print("->");
		}
		System.out.println();
	}

	private void clear() {
		first = new Node<T>(null);
		last = new Node<T>(null);
		size = 0;
	}

	private static class Node<T> {
		public T data;
		public Node<T> next;
		public Node(T data, Node<T> next) {
			this.next = next;
			this.data = data;
		}
		public Node(T data) {
			this(data, null);
		}
	}

	public void removeDuplicates() {
		Node<T> current = first.next;
		Node<T> runner = current;
		while (current != null) {

			while (runner.next != null) {
				if (runner.next.data.equals(current.data)) {
					runner.next = runner.next.next;
					size--;
				}
				runner = runner.next;
			}

			current = current.next;
			runner = current;

			if (current.next == null)
				break;

		}
	}

	public T nth_last(int nth) {
		if (nth > size)
			return null;
		Node<T> n = first;
		for (int i=0; i<size-nth; i++) {
			n = n.next;
		}
		return n.next.data;
	}

	public T nthToLast(int n) {
		Node<T> head = first.next;
		if (head == null || n < 1) {
			return null;
		}
		Node<T> p1 = head;
		Node<T> p2 = head;
		for (int j = 0; j < n - 1; ++j) { // skip n-1 steps ahead
			if (p2 == null) {
				return null; // not found since list size < n
			}
			p2 = p2.next;
		}
		while (p2.next != null) {
			p1 = p1.next;
			p2 = p2.next;
		}
		return p1.data;
	}
	
	// (3->1->5)->(5->9->2)
	public void reverseAdd(MyLinkedList<Integer> l) {
		this.printList();
		l.printList();
		
		MyLinkedList<Integer> res_l = new MyLinkedList<Integer>();
		
		Node<Integer> f1 = (Node<Integer>) this.getHead().next;
		Node<Integer> f2 = (Node<Integer>) l.getHead().next;
		
		int carry = 0;
		
		while (f1!=null) {
			
			int res = f1.data + f2.data + carry;
			if (res >= 10) {
				res = res-10;
				carry = 1;
			} else {
				carry = 0;
			}
			
			res_l.add(res);

			f1 = f1.next;
			f2 = f2.next;
			
		}
		
		res_l.printList();
		
	}
	
	// Recursively add lists
	public static Node<Integer> addLists(Node<Integer> l1, Node<Integer> l2, int carry) {
		
		if (l1 == null || l2 == null)
			return null;
		
		Node<Integer> res = new Node<Integer>(carry);
		int value = carry;
		
		if (l1 != null)
			value += l1.data;
		if (l2 != null)
			value += l2.data;
		
		res.data = value % 10;
		
		Node<Integer> more = addLists(
				l1==null ? null : l1.next, 
				l2==null ? null : l2.next, 
				value > 10 ? 1 : 0);
		
		res.next = more;
		return res;
		
	}
	
	public Node<T> getHead() {
		return first;
	}
	
	public void reverse() {
		Node<T> a = first.next;
		Node<T> b = a.next;  

		a.next = null;
		last = a;
		
		if (b == null)
			return;  

		while (b != null) {
			Node<T> NextNode = b.next;
			b.next = a;
			a = b;
			b = NextNode;
		}
		first.next = a;

	}

	public static void main(String[] args) {

		MyLinkedList<Integer> l1 = new MyLinkedList<>();
		MyLinkedList<Integer> l2 = new MyLinkedList<>();
		
		l1.add(1);
		l1.add(0);
		l1.add(2);
		
		l2.add(1);
		l2.add(0);
		l2.add(2);
		
		l1.reverseAdd(l2);
		Node<Integer> n = addLists(l1.getHead().next, l2.getHead().next, 0);
		while (n!=null) {
			System.out.print(n.data + "->");
			n = n.next;
		}
			
		
	}

}
