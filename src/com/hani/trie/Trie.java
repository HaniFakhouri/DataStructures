package com.hani.trie;

import java.util.Stack;

public class Trie {

	public static final int ALPHABET = 26;

	private TNode root;

	public void insert(String s) {

		if (root == null)
			root = new TNode('x');

		TNode crawl = root;
		int index;

		for ( char c : s.toCharArray() ) {
			index = charToIndex(c);
			if ( crawl.children[index] == null ) {
				crawl.children[index] = new TNode(c);
				crawl.children[index].parent = crawl;
			}
			crawl = crawl.children[index];
		}

		crawl.isLeaf = true;

	}

	public void insert(String[] values) {
		for (String v : values)
			insert(v);
	}

	public void print(char key) {
		int index = charToIndex(key);
		if ( root.children[index] == null ) {
			System.out.println(key + " not found");
			return;
		}
		print(root.children[index], key);
	}

	private void print(TNode n, char key) {
		System.out.println(key);
		for (TNode tn : n.children)
			if (tn!=null)
				print(tn, tn.key);
	}

	public boolean contains(String s) {
		TNode crawl = root;
		
		if (crawl == null)
			return false;
		
		String res = "";
		for ( char c : s.toCharArray() ) {
			int index = charToIndex(c);
			if ( crawl.children[index] != null ) {
				crawl = crawl.children[index];
				res += c;
			} else {
				res = "";
				break;
			}
		}
		if (crawl.isLeaf && !res.isEmpty())
			return true;
		return false;
	}

	public void pattern(String s) {
		TNode crawl = root;
		String res = "";
		for ( char c : s.toCharArray() ) {
			int index = charToIndex(c);
			if ( crawl.children[index] != null ) {
				crawl = crawl.children[index];
				res += c;
			} else {
				res = "";
				break;
			}
		}
		if (!res.isEmpty()) {
			System.out.println("Found: " + res);
		} else {
			System.out.println(s + " not found");
		}				
	}

	public Trie buildSuffixTree(String s) {

		Trie suffixTree = new Trie();

		for (int i=0; i<s.length(); i++) {
			String suffix = s.substring(i) + "x";
			suffixTree.insert(suffix);
		}

		return suffixTree;

	}

	private static int charToIndex(char c) {
		return (int)c - 97;
	}

	public static class TNode {

		public char key;
		public boolean isLeaf;
		public TNode[] children;
		public TNode parent;

		public TNode(char key) {
			this.key = key;
			reset();
		}

		private void reset() {
			children = new TNode[ALPHABET];
			isLeaf = false;
			parent = null;
		}

		@Override
		public String toString() {
			return "";
		}

	}

	public void test(char key) {
		int index = charToIndex(key);
		TNode crawl = root.children[index];
		
		Stack<TNode> s = new Stack<>();
		s.push(crawl);
		
		while (!s.isEmpty()) {
			
			TNode n = s.pop();
			System.out.print(n.key + ": ");
			for (TNode tn : n.children) {
				if (tn!=null) {
					System.out.print(tn.key + " ");
					s.push(tn);
				}
			}
			System.out.println();
		}
		
		
	}

}












