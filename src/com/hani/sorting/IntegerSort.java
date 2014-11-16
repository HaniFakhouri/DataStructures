package com.hani.sorting;

public class IntegerSort {

	public static void sort(Integer[] a) {
		
		Integer max_size = (2<<15) - 1;
		Integer[] int_a = new Integer[max_size];
		
		for (int i=0; i<max_size; i++)
			int_a[i] = -1;
		
		for (int i=0; i<a.length; i++) {
			int_a[a[i]] = a[i];
		}
		
		for (int i=0, j=0; i<max_size; i++) {
			while (i<max_size && int_a[i] == -1)
				i++;
			if (i >= max_size)
				break;
			a[j++] = int_a[i];
		}
		
	}
	
}
