package com.hani.sorting;

public class ShellSort {
	
	public static<T extends Comparable<? super T>> T[] sort(T[] a) {
		
		for ( int gap=a.length/2; gap > 0;
				gap = (int) (gap==2 ? 1 : gap/2.2) ) {
			
			for (int pos=gap; pos<a.length; pos++) {
				
				T tmp = a[pos];
				int j = pos;
				
				for ( ; j>=gap && tmp.compareTo(a[j-gap]) < 0; j-=gap )
					a[j] = a[j-gap];
				a[j] = tmp;
				
			}
			
		}
		
		return a;
		
	}

}
