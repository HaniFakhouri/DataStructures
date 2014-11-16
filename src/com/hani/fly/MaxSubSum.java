package com.hani.fly;

public class MaxSubSum {
	
	// Contiguous
	public static int maximum_contiguous_subsequence_sum(int[] a) {
		
		int max_sum = 0;
		int current_sum = 0;
		
		int seqStart=0, seqEnd=0;
		
		for (int j=0, i=0; i<a.length; i++) {
			
			current_sum += a[i];

			if (current_sum > max_sum) {
				max_sum = current_sum;
				seqStart = j;
				seqEnd = i;
			} else if (current_sum < 0) {
				j = i + 1;
				current_sum = 0;
			}
			
		}
		
		System.out.println("[" + seqStart + ".." + seqEnd + "]");
		
		return max_sum;
	}

	public static void main(String[] args) {
		
		int[] a = new int[]{-2,11,-4,13,-5,2};
		System.out.println(maximum_contiguous_subsequence_sum(a));
		
	}
	
}
