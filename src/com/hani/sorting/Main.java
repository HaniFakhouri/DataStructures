package com.hani.sorting;

import java.util.HashSet;
import java.util.Random;
import java.util.Set;

public class Main {

	public static void main(String[] args) {

		Integer[] aa = new Integer[]{10,255,30,4,5,6};
		IntegerSort.sort(aa);
		for (int i : aa)
			System.out.print(i + " ");
		
		System.out.println();
		
		Set<Integer> set = new HashSet<>();
		Random rand = new Random();
		int max = 2000000000;
		int min = 0;
		System.out.println("Generating random numbers...");
		for (int i=0; i<1000000; i++) {
			set.add(rand.nextInt((max - min) + 1) + min);
		}
		System.out.println("Random numbers generated\n");

		long start = System.currentTimeMillis(); 
		int threshold = set.size();
		int running_times = 10;

		boolean include_insertion_sort = false;
		boolean include_shell_sort = false;
		boolean include_merge_sort = true;
		boolean include_quick_sort = true;

		boolean print_sorted_array = false;

		Integer[] a_insertion = null;
		Integer[] a_shell = null;
		Integer[] a_merge = null;
		Integer[] a_quick = null;
		
		for (int run=0; run<running_times; run++) {

			System.out.println("Run " + (run+1));

			if (include_insertion_sort) {
				a_insertion = set.toArray(new Integer[set.size()]);
				InsertionSort.sort(a_insertion);
				System.out.println("Insertion sort >> " + ((System.currentTimeMillis()) - start));
				if (print_sorted_array) {
					for (int i=a_insertion.length-threshold; i<a_insertion.length; i++)
						System.out.print(a_insertion[i] + ",");
				}
			}

			if (include_shell_sort) {
				a_shell = set.toArray(new Integer[set.size()]);
				start = System.currentTimeMillis();
				ShellSort.sort(a_shell);
				System.out.println("Shell sort     >> " + ((System.currentTimeMillis()) - start));
				if (print_sorted_array) {
					for (int i=a_shell.length-threshold; i<a_shell.length; i++)
						System.out.print(a_shell[i] + ",");
				}
			}

			if (include_merge_sort) {
				a_merge = set.toArray(new Integer[set.size()]);
				start = System.currentTimeMillis();
				MergeSort.sort(a_merge);
				System.out.println("Merge sort     >> " + ((System.currentTimeMillis()) - start));
				if (print_sorted_array) {
					for (int i=a_merge.length-threshold; i<a_merge.length; i++)
						System.out.print(a_merge[i] + ",");
				}
			}

			if (include_quick_sort) {
				a_quick = set.toArray(new Integer[set.size()]);
				start = System.currentTimeMillis();
				QuickSort.sort(a_quick);
				System.out.println("Quick sort     >> " + ((System.currentTimeMillis()) - start));
				if (print_sorted_array) {
					for (int i=a_quick.length-threshold; i<a_quick.length; i++)
						System.out.print(a_quick[i] + ",");
				}
			}

			System.out.println();

		}


	}

}
