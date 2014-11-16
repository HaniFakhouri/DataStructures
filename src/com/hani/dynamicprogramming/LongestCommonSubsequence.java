package com.hani.dynamicprogramming;

/**
 * Longest common subsequences of m="AGCAT" and n="GAC" are AC, GA and GC
 * each of length 2. 
 * 
 * @author Hani
 *
 */

public class LongestCommonSubsequence {
	
	public static String LCS_BruteForce(String m, String n) {
		if (m.equals(n))
			return m;
		return String.valueOf(LCS_BruteForce(m.toCharArray(), n.toCharArray()));
	}
	
	private static char[] LCS_BruteForce(char[] m, char[] n) {
		char[] res = "__".toCharArray();
		
		int lcs_max_size = Integer.MIN_VALUE;
		
		char[] longer, shorter;
		if (m.length >= n.length) {
			longer = m;
			shorter = n;
		} else {
			longer = n;
			shorter = m;
		}
		
		return res;
	}
	
	private static char[] subString(char[] m, int startIndex, int endIndex) {
		char[] res = new char[ endIndex-startIndex ];
		for (int i=0; i<endIndex-startIndex; i++)
			res[i] = m[i+startIndex];
		return res;
	}
	
	private static void subSequence(char[] s, int start, int end) {
		if ( s.length == start && s.length == end ) {
			return;
		} else {
			if ( end == s.length + 1 ) {
				subSequence(s, start+1, start+1);
			} else {
				if ( start != end )
					System.out.println( String.valueOf(subString(s, start, end)) );
				subSequence(s, start, end+1);
			}
		}
	}
	
	private static void subSet(char[] s) {
		if ( s.length == 0 ) {
			System.out.println("");
			return;
		}
		char[] ss = subString(s, 1, s.length);
		System.out.println(String.valueOf(ss));
		subSet(ss);
	}
	
	public static void main(String[] args) {
		String s = "ABCD";
		char[] c = s.toCharArray();
		subSet(c);
	}

}




