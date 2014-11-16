package com.hani.stringmanipulation;

public class StringManip {
	
	// Brute force implementation
	public static void isSubString_naive(String s, String p) {
		char[] sc = s.toCharArray();
		char[] pc = p.toCharArray();
		for (int i=0; i<sc.length; i++) {
			int j;
			for (j=0; j<pc.length; j++) {
				if ( sc[i+j] != pc[j] )
					break;
			}
			if (j == pc.length)
				System.out.println("Pattern found at index " + i);
		}
	}
	
	public static void isSubString_rabin_karp(String s, String p) {
		
		int s_length = s.length();
		int p_length = p.length();
		
		int p_hash = p.hashCode();
		int s_hash = s.substring(0, p_length).hashCode();
		
		if (p_hash == s_hash) {
			System.out.println("Patterrn found at index " + 0);
		}
		
		for (int i=p_length; i<s_length; i++) {

			if (p_length + i > s.length())
				break;
			
			s_hash = s.substring(i, i+p_length).hashCode();
			
			if (p_hash == s_hash) {
				System.out.println("Patterrn found at index " + i);
				i += p.length()-1;
			}
			
		}
		
	}
	
	public static void main(String[] args) {
		
		String s = "AABAACAADAABAAABAA";
		String p = "AABA";
		isSubString_rabin_karp(s, p);
		
	}

}
