package com.hani.problems;

import java.io.ObjectInputStream.GetField;
import java.util.HashMap;
import java.util.Map;
import java.util.Stack;

public class Cracking {

	/**
	 * Implement an algorithm to determine if a string has all unique characters.
	 * What if you can not use additional data structures?
	 */
	// Assumes ASCII characters
	public static boolean unique(String s) {
		int[] ascii = new int[256];
		for (int i=0; i<s.length(); i++) {
			char c = s.charAt(i);
			if ( ascii[(int)c] == 1 )
				return false;
			ascii[(int)c] = 1;
		}
		return true;
	}

	public static boolean unique2(String s) {
		s = s.toLowerCase();
		char[] cs = s.toCharArray();
		Map<Integer, Integer> hm = new HashMap<Integer, Integer>();
		for (int i=0; i<cs.length; i++) {
			if (hm.put((int)cs[i], (int)cs[i]) != null)
				return false;
		}
		return true;
	}

	/**
	 * Write code to reverse a C-Style String. 
	 * (C-String means that �abcd� is represented as five characters, including the null character.)
	 */
	public static String reverse(String s) {
		char[] cs = s.toCharArray();
		Stack<String> stack = new Stack<>();
		for (int i=0; i<cs.length-1; i++)
			stack.push(String.valueOf(cs[i]));
		for (int i=0; i<cs.length-1; i++)
			cs[i] = stack.pop().charAt(0);
		return String.valueOf(cs);
	}

	public static String reverse2(String s) {
		char[] cs = s.toCharArray();
		char tmp;
		for (int i=0, j = cs.length - (i + 2);
				i<cs.length/2; i++, j--) {
			tmp = cs[i];
			cs[i] = cs[j];
			cs[j] = tmp;
		}
		return String.valueOf(cs); 
	}

	/**
	 * Design an algorithm and write code to remove the duplicate characters in a string without
	 * using any additional buffer. 
	 * NOTE: One or two additional variables are fine. An extra copy of the array is not.
	 */

	public static void removeDuplicates(String s) {

		char[] str = s.toCharArray();

		if (str == null) return;
		int len = str.length;
		if (len < 2) return;

		int tail = 1;

		for (int i = 1; i < len; ++i) {
			int j;
			for (j = 0; j < tail; ++j) {
				if (str[i] == str[j]) break;
			}
			if (j == tail) {
				str[tail] = str[i];
				++tail;
			}
		}
		str[tail] = 0;
		for (int i=0; i<tail; i++)
			System.out.print(str[i]);
		System.out.println();

	}

	/**
	 * Write a method to decide if two strings are anagrams or not.
	 * 
	 * An anagram is a type of word play, the result of rearranging the letters of a word or phrase to
	 * produce a new word or phrase, using all the original letters exactly once;
	 * for example "Doctor Who" can be rearranged into "Torchwood".
	 */

	public static boolean areAnagrams(String s1, String s2) {

		s1 = s1.toLowerCase();
		s2 = s2.toLowerCase();
		s1 = s1.replaceAll(" ", "");
		s2 = s2.replaceAll(" ", "");

		if (s1.length() != s2.length())
			return false;

		int[] letters = new int[256];

		char[] s1_c = s1.toCharArray();
		for (int i=0; i<s1_c.length;i++) {
			char c = s1_c[i];
			letters[c]++;
		}

		for (int i=0; i<s2.length(); i++) {
			char c = s2.charAt(i);
			if ( letters[c] == 0 ) {
				return false;
			}
			letters[c]--;
		}

		return true;
	}

	/**
	 * Write a method to replace all spaces in a string with �%20�.
	 */

	public static void replaceWith20(String s) {

		char[] cs = s.toCharArray();
		int nrSpaces = 0;
		for (int i=0; i<cs.length; i++) {
			if (cs[i] == ' ')
				nrSpaces++;
		}

		char[] new_s = new char[ 2 * nrSpaces * s.length() ];
		for (int i=0, j=0; i<cs.length; i++, j++) {

			if ( cs[i] == ' ' ) {
				new_s[j] = '%';
				new_s[j+1] = '2';
				new_s[j+2] = '0';
				j += 2;
			} else {
				new_s[j] = cs[i];
			}

		}

		System.out.println(String.valueOf(new_s));

	}

	/**
	 * Write an algorithm such that if an element in an MxN matrix is 0, its entire row and column is set to 0.
	 */
	
	public static void setZeros(int[][] matrix) {
		int[] row = new int[matrix.length];
		int[] col = new int[matrix[0].length];
		
		for (int i=0; i<row.length; i++) {
			for (int j=0; j<col.length; j++) {
				System.out.print(matrix[i][j] + " ");
			}
			System.out.println();
		}
		
		for (int i=0; i<row.length; i++) {
			for (int j=0; j<col.length; j++) {
				if (matrix[i][j] == 0) {
					row[i] = 1;
					col[j] = 1;
				}
			}
		}
		
		for (int i=0; i<row.length; i++) {
			for (int j=0; j<col.length; j++) {
				if (row[i] == 1 || col[j] == 1)
					matrix[i][j] = 0;
			}
		}
		
		System.out.println();
		for (int i=0; i<row.length; i++) {
			for (int j=0; j<col.length; j++) {
				System.out.print(matrix[i][j] + " ");
			}
			System.out.println();
		}
		
		
	}
	
	public static class BitSet {
		
		private int[] bitset;
		int size = 0;
		
		public BitSet(int size) {
			bitset = new int[ size >> 5 ]; // divide by 32
		}
		
		public void set(int pos) {
			int wordNumber = (pos >> 5); // divide by 32
			int bitNumber = (pos & 0x1F); // mod 32
			bitset[ wordNumber ] = (bitset[ wordNumber ] | 1 << bitNumber);
			size++;
		}
		
		public boolean get(int pos) {
			int wordNumber = (pos >> 5);
			int bitNumber = (pos & 0x1F);
			return (bitset[wordNumber] & (1<<bitNumber)) != 0;
		}
		
		private int get2(int pos) {
			int wordNumber = (pos >> 5);
			int bitNumber = (pos & 0x1F);
			return (bitset[wordNumber] & (1<<bitNumber));
		}
		
		public void print() {
			for (int i=0; i<size; i++)
				System.out.println(bitset[i]);
		}
		
	}

	public static void main(String[] args) {

		System.out.println(unique("ABCDEFGHX--"));
		removeDuplicates("ABCABCXABCABCffF");

		//  William Shakespeare = I am a weakish speller 
		System.out.println(areAnagrams("Doctor xWho", "Torchwood"));
		System.out.println(areAnagrams("William Shakespeare", "I am a weakish speller"));
		replaceWith20("ha e a");
		
		int[][] matrix = new int[][]{
				{1,2,3,4,5},
				{6,7,8,9,10},
				{11,12,13,14,15},
				{16,17,0,19,20},
				{21,22,23,24,25},
				};
		setZeros(matrix);
		
		
		BitSet bs = new BitSet(32000);
		System.out.println("-----------");
		bs.set(0);
		bs.set(1);
		bs.set(2);
		bs.set(3);
		bs.print();
		
		System.out.println("-----------");
		
		System.out.println(bs.get(0));
		
	}

}







