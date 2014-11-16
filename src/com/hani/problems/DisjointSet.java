package com.hani.problems;

public class DisjointSet {

	private int[] s;

	public DisjointSet(int nrElements) {
		s = new int[nrElements];
		for (int i=0; i<nrElements; i++)
			s[i] = -1;
	}

	// Union by height. Height of the tree never becomes greater that log N
	// O(log N)
	// A sequence of M find operations takes O(M log N)
	// root1 and root2 represent set names
	public void union(int root1, int root2) {
		if (root1 == root2)
			throw new IllegalArgumentException();

		if (s[root2] < s[root1])
			s[root1] = root2;
		else {
			if (s[root1] == s[root2])
				s[root1]--;
			s[root2] = root1;
		}
	}

	// Perform a find with path compression which guarantees O(log N) worst case
	// O(log N)
	// A sequence of M find operations takes O(M log N)
	public int find(int x) {
		if (s[x] < 0)
			return x;
		else {
			s[x] = find(s[x]);
			return s[x];
		}
	}
	
	public boolean sameSet(int root1, int root2) {
		return find(root1) == find(root2);
	}
	
	public int size() {
		return s.length;
	}

	public void print() {
		for (int i=0; i<s.length; i++)
			System.out.println("[ " + i + " ] : " + s[i]);
	}
	
	public static class Ob {
		public int representative;
		public String data;
		public Ob(String data, int representative) {
			this.data = data;
			this.representative = representative;
		}
		@Override
		public String toString() {
			return representative + " " + data;
		}
	}
	
	public static void main(String[] args) {
		
		DisjointSet s = new DisjointSet(6);
		
		// Create 6 disjoint sets
		for (int i=0; i<6; i++)
			s.find(i);
		
		int set1 = s.find(1);
		int set2 = s.find(2);
		int set3 = s.find(3);
		int set4 = s.find(4);
		
		// Combine sets 1,2 and 4 into one set
		s.union(set1, set2);
		s.union(set1, set4);
		
		System.out.println(s.sameSet(set1, set2));
		System.out.println(s.sameSet(set2, set4));
		System.out.println(s.sameSet(set2, set3));
		
	}

}
