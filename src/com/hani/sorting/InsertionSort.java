package com.hani.sorting;

public class InsertionSort {
	
	public static<T extends Comparable<? super T>> void sort(T[] a) {
		
		for (int pos=1; pos<a.length; pos++) {
			
			T tmp = a[pos];
			int j = pos;
			
			for ( ; j>0 && tmp.compareTo(a[j-1]) < 0; j-- )
				a[j] = a[j-1];
			a[j] = tmp;
		}
		
	}
	
}
