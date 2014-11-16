package com.hani.heap;

import java.util.Comparator;

/**
 * Binary heap implementing a priority queue.
 * Position 0 of the array is reserved.
 * If an element is found at array[i] its left child is found at
 * array[2i] and it right child at position array[2i+1]
 * @author Hani
 *
 */

public class BinaryHeap<T extends Comparable<? super T>> {

	public static final int INITIAL_CAPACITY = 15;

	private int currentSize;
	private T[] array;
	private Comparator<? super T> cmp;

	@SuppressWarnings("unchecked")
	public BinaryHeap() {
		array = (T[]) new Comparable[INITIAL_CAPACITY];
		currentSize = 0;
		cmp = null;
	}

	@SuppressWarnings("unchecked")
	public BinaryHeap(Comparator<? super T> c) {
		array = (T[]) new Comparable[INITIAL_CAPACITY];
		currentSize = 0;
		cmp = c;
	}

	public int size() {
		return currentSize;
	}

	public void clear() {
		currentSize = 0;
	}

	public boolean isEmpty() {
		return currentSize == 0;
	}

	public void print() {
		for (int i=1; i<=currentSize; i++)
			System.out.print(array[i].toString() + " ");
		System.out.println();
	}

	public T getMin() {
		if (isEmpty())
			return null;
		return array[1];
	}

	public boolean add(T x) {
		
		if (currentSize+1 == array.length)
			doubleArray();

		// Prelocate up
		int hole = ++currentSize;
		array[0] = x;

		for ( ; compare(x, array[hole/2]) < 0; hole /= 2 )
			array[hole] = array[hole/2];
		array[hole] = x;
		
		return true;

	}
	
	public void buildHeap() {		
		for (int i=currentSize/2; i>0; i--) {
			prelocateDown(i);
		}
	}

	public T deleteMin() {
		T minElem = getMin();
		array[1] = array[currentSize--];
		prelocateDown(1);
		return minElem;
	}
	
	private void prelocateDown(int hole) {

		int child;
		T tmp = array[hole];

		for( ; hole*2 <= currentSize; hole = child ) {
			child = hole * 2;
			if ( child != currentSize &&
					compare(array[child+1], array[child]) < 0 )
				child++;
			if ( compare(array[child], tmp) < 0 )
				array[hole] = array[child];
			else
				break;
		}
		array[hole] = tmp;

	}

	public void remove(T elem) {
		remove(elem, 1);
	}

	private void remove(T elem, int pos) {
		T root = array[pos];
		if ( elem.compareTo(root) == 0 ) {
			array[pos] = array[currentSize--];
			prelocateDown(pos);
		} else {
			remove(elem, pos+1);
		}
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	private int compare(T lhs, T rhs) {
		if (cmp == null)
			return ((Comparable)lhs).compareTo(rhs);
		return cmp.compare(lhs, rhs);
	}

	private void doubleArray() {
		int new_size = 2 * array.length;
		@SuppressWarnings("unchecked")
		T[] temp = (T[]) new Comparable[new_size];
		for (int i=0; i<array.length; i++)
			temp[i] = array[i];
		array = temp;
	}

	public static void main(String[] args) {

		BinaryHeap<Integer> h = new BinaryHeap<>();
		
		h.add(1);
		h.add(2);
		h.add(3);
		h.add(4);
		h.add(6);
		h.add(5);
		
		h.print();

	}

}
