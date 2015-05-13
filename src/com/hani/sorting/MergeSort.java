package com.hani.sorting;

public class MergeSort {
	
	public static <T extends Comparable<? super T>> void sort(T[] a) {
		@SuppressWarnings("unchecked")
		T[] tmpArray = (T[])new Comparable[a.length];
		mergeSort(a, tmpArray, 0, a.length-1);
	}
	
	private static <T extends Comparable<? super T>> void mergeSort(T[] a, T[] tmpArray, 
			int left, int right) {
		
		if (left < right) {

			int center = (left + right) / 2;
			mergeSort(a, tmpArray, left, center);
			mergeSort(a, tmpArray, center+1, right);
			merge(a, tmpArray, left, center+1, right);
			
		}
		
	}
	
	private static <T extends Comparable<? super T>> void merge(T[] a, T[] tmpArray,
			int leftPos,  int rightPos, int rightEnd) {
		
		int leftEnd = rightPos - 1;
		int tmpPos = leftPos;
		int numElements = rightEnd - leftPos + 1;
		
		while ( leftPos <= leftEnd && rightPos <= rightEnd )
			if ( a[leftPos].compareTo(a[rightPos]) <= 0 )
				tmpArray[tmpPos++] = a[leftPos++];
			else
				tmpArray[tmpPos++] = a[rightPos++];
		
		while ( leftPos <= leftEnd )
			tmpArray[tmpPos++] = a[leftPos++];
		
		while ( rightPos <= rightEnd )
			tmpArray[tmpPos++] = a[rightPos++];
		
		for (int i=0; i<numElements; i++, rightEnd--)
			a[rightEnd] = tmpArray[rightEnd];
		
		printArray(a);
		
	}
	
	public static void main(String[] args) {
		
		//Integer[] a = new Integer[]{1,13,24,26,2,15,27,38,0};
		Integer[] a = new Integer[]{150,13,100,200,0,0,2,7,12,0,4,8,-1};
		
		MergeSort.sort(a);
		
		printArray(a);
		
	}
	
	public static <T> void printArray(T[] a) {
		for (T e : a)
			System.out.print(e + " ");
		System.out.println();
	}

}
