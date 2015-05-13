package com.hani.hashtable;

import java.util.AbstractCollection;
import java.util.Collection;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Set;

public class HashSet<T> extends AbstractCollection<T> implements Set<T> {
	
	private static final int TABLE_SIZE = 10;
	
	private HashEntry[] array;
	private int currentSize = 0;
	private int occupied = 0;
	private int modCount = 0;
	
	public HashSet() {
		allocateArray(TABLE_SIZE);
		clear();
	}
	
	public HashSet(int size) {
		allocateArray(size);
		clear();
	}
	
	public HashSet(Collection<? extends T> other) {
		allocateArray( nextPrime( other.size() * 2 ) );
		clear();
		for (T val : other)
			add(val);
	}
	
	private void allocateArray(int size) {
		array = new HashEntry[size];
	}
	
	@Override
	public int size() {
		return currentSize;
	}

	@Override
	public boolean isEmpty() {
		return occupied == 0;
	}

	@Override
	public boolean contains(Object o) {
		int currentPos = findPos(o);
		return isActive(array, currentPos);
	}

	@Override
	public Iterator<T> iterator() {
		return new HashIterator();
	}

	@Override
	public Object[] toArray() {
		return null;
	}

	@SuppressWarnings("hiding")
	@Override
	public <T> T[] toArray(T[] a) {
		return null;
	}

	@Override
	public boolean add(T e) {
		int currentPos = findPos(e);
		if (isActive(array, currentPos))
			return false;
		
		array[currentPos] = new HashEntry(e, true);
		currentSize++;
		occupied++;
		modCount++;
		
		if (occupied > array.length/2)
			rehash();
		
		return true;
		
	}

	@Override
	public boolean remove(Object o) {
		int currentPos = findPos(o);
		if (!isActive(array, currentPos))
			return false;
		
		array[currentPos].isActive = false;
		currentSize--;
		modCount++;
		
		if (currentSize < array.length/8)
			rehash();			
		
		return true;
	}
	
	@SuppressWarnings("unchecked")
	public T getMatch(T x) {
		int currentPos = findPos(x);
		if (isActive(array, currentPos))
			return (T)array[currentPos].data;
		return null;
	}

	@Override
	public boolean containsAll(Collection<?> c) {
		return false;
	}

	@Override
	public boolean addAll(Collection<? extends T> c) {
		return false;
	}

	@Override
	public boolean retainAll(Collection<?> c) {
		return false;
	}

	@Override
	public boolean removeAll(Collection<?> c) {
		return false;
	}

	@Override
	public void clear() {
		currentSize = 0;
		occupied = 0;
		modCount++;
		for (int i=0; i<array.length; i++)
			array[i] = null;
	}
	
	private int findPos(Object x) {
		
		int offset = 1;
		int currentPos = (x==null) ? 0 : Math.abs( x.hashCode() % array.length );
		
		while ( array[currentPos] != null ) {
			if (x == null) {
				if (array[currentPos].data == null)
					break;
			} else if (x.equals(array[currentPos].data))
				break;
			
			// Quadratic probing
			currentPos += offset; // Compute i'th probe
			offset += 2;
			if (currentPos >= array.length) // Implement the mod
				currentPos -= array.length;
			
		}
		
		return currentPos;
		
	}
	
	@SuppressWarnings("unchecked")
	private void rehash() {
		
		HashEntry[] oldArray = array;
		
		allocateArray(4*size());
		currentSize = 0;
		occupied = 0;
		
		for (int i=0; i<oldArray.length; i++) {
			if (isActive(oldArray, i)) {
				add((T) oldArray[i].data);
			}
		}
		
	}
	
	private boolean isActive(HashEntry[] table, int i) {
		return table[i] != null && table[i].isActive;
	}
	
	/**
	 * Trial division algorithm to check if an odd integer is prime
	 * Takes at most O(sqrt(N)) time
	 * @param n to be checked for primality
	 * @return if n is prime
	 */
	private static boolean isPrime(long n) {
		for (int i=3; i*i<=n; n=+2)
			if (n%i==0)
				return false;
		return true;
	}
	
	/**
	 * Finds a prime number at least as large as n
	 * We expect to test only O(logN) numbers until we find a prime number.
	 * isPrime takes at most O(sqrt(N)) so the whole method takes at most
	 * O(sqrt(N)logN). This is faster compared to O(N) cost of transferring
	 * the contents of an old hash table to a new one. I.e. better to use
	 * the new hash to compute new positions at the new hash table. 
	 * @param n the starting number
	 * @return a prime number larger or equal to n
	 */
	private static int nextPrime(int n) {
		if (n%2==0)
			n++;
		for(;!isPrime(n); n+=2)
			;
		return n;
	}
	
	private static class HashEntry {
		
		public Object data;
		public boolean isActive;
		
		@SuppressWarnings("unused")
		public HashEntry(Object data) {
			this(data, false);
		}
		
		public HashEntry(Object data, boolean isActive) {
			this.data = data;
			this.isActive = isActive;
		}
		
	}
	
	public void print() {
		for (HashEntry e : array) {
			if ( e==null )
				System.out.println("null");
			else
				System.out.println(e.data + " " + e.isActive);
		}
	}
	
	private class HashIterator implements Iterator<T> {

		private int expectedModCount = modCount;
		private int visited = 0;
		private int currentPos = -1;
		
		@Override
		public boolean hasNext() {
			return visited != size();
			// return visited < size();
		}

		@Override
		public T next() {
			if (!hasNext())
				throw new NoSuchElementException();
			do {
				currentPos++;
			} while (visited < size() && !isActive(array, currentPos));
			visited++;
			return (T)array[currentPos].data;
		}

		@Override
		public void remove() {
			array[currentPos].isActive = false;
			currentSize--;
			visited--;
			modCount++;
			expectedModCount++;
		}
		
	}
	
	public static void main(String[] args) {
		
		
		
	}

}








