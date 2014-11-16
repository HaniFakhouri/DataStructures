package com.hani.fly;

public class Main {

	private static class Word implements Comparable<Word> {

		public int freq;
		public String w;

		public Word(String w) {
			this.w = w;
			freq = 0;
		}

		@Override
		public int compareTo(Word o) {
			if ( freq > o.freq )
				return 1;
			if ( freq < o.freq )
				return -1;
			return 0;
		}

		@Override
		public String toString() {
			return w + "(" + freq + ")";
		}

	}

	private static class MyBinaryHeap<T extends Comparable<? super T>> {

		private int currentSize;
		private T[] array;

		@SuppressWarnings("unchecked")
		public MyBinaryHeap(int size) {
			array = (T[]) new Comparable[size];
			currentSize = 0;
		}

		public void insert(T x) {
			// If max size is reached delete the minimum
			// and swap downwards
			if ( currentSize == array.length ) {
				if ( x.compareTo(array[0]) > 0 ) {
					array[0] = x;
					perlocateDown(0);
				}
			} else {
				array[currentSize] = x;
				perlocateUp(currentSize);
				currentSize++;
			}
		}

		public void insert_nonheap(T x) {
			array[currentSize] = x;
			currentSize++;
		}

		public void perlocateDown(int i) {

			int child = 2*i+1;
			if ( array[child+1].compareTo(array[child]) < 0 )
				child++;

			if ( array[i].compareTo(array[child]) > 0 ) {
				T tmp = array[child];
				array[child] = array[i];
				array[i] = tmp;
				if (child < (currentSize-1)/2)
					perlocateDown(child);
			}

		}

		private void perlocateUp(int i) {			
			if ( array[i].compareTo(array[i/2]) < 0 ) {
				T tmp = array[i/2];
				array[i/2] = array[i];
				array[i] = tmp;
				perlocateUp(i/2);
			}

		}

		public T getMin() {
			return array[0];
		}

		public T deleteMin() {
			T min = array[0];
			array[0] = array[--currentSize];
			perlocateDown(0);
			return min;
		}

		public void decreaseKey(int index, T newKey) {
			if (array[index].compareTo(newKey) < 0) {
				System.out.println("New key is not lower than the current key");
				return;
			}
			array[index] = newKey;
			checkHeapOrder(index, newKey);
		}

		public void increaseKey(int index, T newKey) {
			if (array[index].compareTo(newKey) > 0) {
				System.out.println("New key is not higher than the current key");
				return;
			}
			array[index] = newKey;
			checkHeapOrder(index, newKey);
		}

		private void checkHeapOrder(int index, T newKey) {
			T l = leftChild(index);
			T r = rightChild(index);

			if ( (l != null && newKey.compareTo(l)>0) ||
					(r != null && newKey.compareTo(r)>0) )
				perlocateDown(index);
			else
				perlocateUp(index);
		}

		private T leftChild(int index) {
			return array[2*index+1];
		}

		private T rightChild(int index) {
			return array[2*index+2];
		}

		private T parent(int index) {
			return array[index/2]; 
		}

		public void print() {
			for (int i=0; i<currentSize; i++)
				System.out.print(array[i] + " ");
			System.out.println();
		}

		private void buildHeap() {
			for (int i=currentSize/2; i>=0; i--)
				perlocateDown(i);
		}

	}

	private static class MyTrie {

		private TNode root;

		public MyTrie() {
			root = new TNode((char)0);
		}

		public void insert(String s) {
			TNode crawl = root;

			for ( char c : s.toCharArray() ) {

				int index = charToIndex(c);
				if (index < 0)
					return;
				if ( crawl.children[index] == null ) {
					crawl.children[index] = new TNode(c);
					crawl.children[index].parent = crawl;
				}
				crawl = crawl.children[index];				
			}

			crawl.isLeaf = true;
			crawl.freq++;

		}

		public boolean contains(String s) {

			TNode crawl = root;

			for (char c : s.toCharArray()) {
				int index = charToIndex(c);
				if (crawl.children[index] == null)
					return false;
				crawl = crawl.children[index];
			}

			if (crawl.isLeaf)
				return true;

			return false;

		}

		public void print() {
			print(root);
		}

		public void print(TNode n) {
			for ( TNode tn : n.children ) {
				if (tn!=null) {
					if (tn.isLeaf) {
						TNode p = tn;
						String s = "";
						while ( p.key != (char)0 ) {
							s += p.key;
							p = p.parent;
						}
						System.out.println(reverse(s) + " " + tn.freq);
					}
					print(tn);
				}
			}
		}

		public void fillHeap(MyBinaryHeap<Word> h) {
			fillHeap(root, h);
		}

		private void fillHeap(TNode n, MyBinaryHeap<Word> h) {
			for ( TNode tn : n.children ) {
				if (tn!=null) {
					if (tn.isLeaf) {
						TNode p = tn;
						String s = "";
						while ( p.key != (char)0 ) {
							s += p.key;
							p = p.parent;
						}
						Word w = new Word(reverse(s));
						w.freq = frequency(w.w);
						h.insert(w);
					}
					fillHeap(tn, h);
				}
			}
		}

		private String reverse(String s) {
			int l = s.length()-1;
			char[] c = s.toCharArray();
			char[] r = new char[s.length()];
			for ( int i=0; i<s.length(); i++ ) {
				r[i] = c[l-i];
			}
			return String.valueOf(r);
		}

		public int frequency(String s) {
			if (!contains(s))
				return 0;
			TNode crawl = root;

			for (char c : s.toCharArray()) {
				int index = charToIndex(c);
				crawl = crawl.children[index];
			}

			return crawl.freq;

		}

		private class TNode {

			private static final int ALPHABET = 256;

			public char key;
			public TNode[] children;
			public TNode parent;
			public int freq;
			public boolean isLeaf;

			public TNode(char key) {
				this.key = key;
				children = new TNode[ALPHABET];
				freq = 0;
				isLeaf = false;
				parent = null;
			}

		}

		private static int charToIndex(char c) {
			return (int)c - 97;
		}

	}

	public static int binarySearch(int[] a, int s) {
		return binarySearch_3(a, s, 0, a.length-1);
	}

	private static int binarySearch(int[] a, int s, int l, int r) {

		int m;

		while ( l <= r ) {

			m = l + (r-l)/2;

			if ( a[m] == s )
				return m;

			if ( a[m] < s )
				l = m + 1;
			else
				r = m - 1;

		}

		// Not found
		return -1;

	}

	private static int binarySearch_2(int[] a, int s, int l, int r) {

		int m = -1;

		while ( l <= r ) {

			m = l + (r-l)/2;

			if ( a[m] < s )
				l = m + 1;
			else
				r = m - 1;

		}

		if (a[m] == s)
			return m;

		// Not found
		return -1;

	}

	// recursive version
	private static int binarySearch_3(int[] a, int s, int l, int r) {

		if ( l > r )
			return -1; // Not found

		int m = l + (r-l)/2;

		if ( a[m] < s )
			return binarySearch_3(a, s, m+1, r);
		else if ( a[m] > s )
			return binarySearch_3(a, s, l, m-1);
		else
			return m;

	}

	public static int[] merge(int[] a, int[] b) {

		int[] m = new int[a.length + b.length];

		int a_index = 0;
		int b_index = 0;

		int i = 0;

		while ( i < m.length ) {

			if ( a[a_index] < b[b_index] )
				m[i++] = a[a_index++];
			else if ( b[b_index] < a[a_index] )
				m[i++] = b[b_index++];

			if (a_index >= a.length) {
				while ( b_index < b.length )
					m[i++] = b[b_index++];
				break;
			} else if ( b_index >= b.length ) {
				while ( a_index < a.length )
					m[i++] = a[a_index++];
				break;
			}

		}

		return m;	

	}

	private static void merge(int[] a, int[] tmp, int leftStart, int rightStart, int rightEnd) {

		int leftEnd = rightStart - 1;
		int pos = leftStart;
		int nrElements = rightEnd - leftStart + 1;

		while ( leftStart<=leftEnd && rightStart<=rightEnd )
			if ( a[leftStart] < a[rightStart] )
				tmp[pos++] = a[leftStart++];
			else
				tmp[pos++] = a[rightStart++];

		while ( leftStart <= leftEnd )
			tmp[pos++] = a[leftStart++];

		while ( rightStart <= rightEnd )
			tmp[pos++] = a[rightStart++];

		for (int i=0; i<nrElements; i++, rightEnd-- )
			a[rightEnd] = tmp[rightEnd];

	}

	public static void sort(int[] a) {
		int[] temp = new int[a.length];
		mergeSort(a, temp, 0, a.length-1);
	}

	private static void mergeSort(int[] a, int[] tmp, int left, int right) {

		if ( left < right ) {

			int m = (left + right) / 2;

			mergeSort(a, tmp, left, m);
			mergeSort(a, tmp, m+1, right);
			merge(a, tmp, left, m+1, right);

		}

	}

	public static void insertionSort(int[] a) {

		for (int i=1; i<a.length; i++) {
			int j = i;
			int tmp = a[j];
			while ( j>0 && tmp < a[j-1]) {
				a[j] = a[j-1];
				j--;
			}
			a[j] = tmp;

		}

	}

	public static void insertionSort(int[] a, int low, int high) {

		System.out.println("INS: " + low + "  " + high + " " + (high-low));

		for (int i=low+1; i<=high; i++) {

			int temp = a[i];
			int j = i;
			while ( j>0 && temp < a[j-1] ) {
				a[j] = a[j-1];
				j--;
			}
			a[j] = temp;

		}

	}

	private static final int CUTOFF = 50;

	public static void quicksort(int[] a, int low, int high) {

		while ( low + CUTOFF <= high ) {
			int mid = (low + high) / 2;

			if ( a[mid] < a[low] )
				swap(a, low, mid);
			if ( a[high] < a[low] )
				swap(a, high, low);
			if ( a[high] < a[mid] )
				swap(a, mid, high);

			swap(a, mid, high-1);
			int pivot = a[high - 1];

			int i,j;
			for (i=low, j=high-1;;) {

				while ( a[++i] < pivot )
					;

				while ( a[--j] > pivot )
					;

				if ( i>=j )
					break;

				swap(a, i, j);

			}

			swap(a, i, high-1);

			if ( i - low > high - i ) {
				quicksort(a, low, i-1);
				low = i + 1;
			} else {
				quicksort(a, i+1, high);
				high = i - 1;
			}

			//quicksort(a, low, i-1);
			//quicksort(a, i+1, high);
		}

		insertionSort(a, low, high);
		//insertionSort(a);

	}

	private static void swap(int[] a, int i1, int i2) {
		int tmp = a[i1];
		a[i1] = a[i2];
		a[i2] = tmp;
	}

	/**
	 * Given an int array, which may contain duplicates, find the longest
	 * sequence in it.
	 * e.g. [1,6,10,4,7,9,5] => 4,5,6,7
	 * @param a
	 */
	public static void longestSequence(int[] a) {

		int max = -1;
		for (int i : a)
			if (i > max)
				max = i;

		int[] tmp = new int[max + 1];
		int max_seq = -1;
		int current_seq = 0;
		for (int i=0; i<a.length; i++)
			tmp[a[i]] = a[i];

		int seq_start = -1;
		int seq_end = -1;
		int j = 0;
		for (int i=0; i<tmp.length; i++) {
			j = i;
			current_seq = 0;
			while ( j<tmp.length && tmp[j] != 0 ) {
				j++;
				current_seq++;
			}
			if (current_seq > max_seq) {
				max_seq = current_seq;
				seq_start = j - max_seq;
				seq_end = j;
				i = j;
			}
			if (j >= tmp.length)
				break;
		}

		System.out.println("Longest sequence: " + max_seq);
		if ( seq_start != seq_end ) {
			for (int i=seq_start; i<seq_end; i++)
				System.out.print(tmp[i] + " ");
			System.out.println();
		}

	}

	/**
	 * Find the minimum difference of any two elements of an array
	 */
	public static int MinDiff(Integer[] a) {
		int min_1 = Integer.MAX_VALUE;
		int min_2 = Integer.MAX_VALUE;
		for (int i=0; i<a.length; i++) {
			if ( a[i] < min_2 ) {
				min_2 = a[i];
				if (min_2 < min_1) {
					int tmp = min_1;
					min_1 = min_2;
					min_2 = tmp;
				}
			}
		}
		int minDiff = min_2 - min_1;
		System.out.println(min_2 + " - " + min_1 + " = " + minDiff);
		return minDiff;
	}

	public static void main(String[] args) {

		/*
		System.out.println(EggDropping_dynamic(2, 36));

		Integer[] a = new Integer[]{1,5,4,-2,4,-11,-1};

		System.out.println(MinDiff(a));

		QuickSort qs = new QuickSort();
		qs.sort(a);
		System.out.println(a[0] + " " + a[1]);

		System.out.println();

		MyBinaryHeap<Integer> h = new MyBinaryHeap<>(50);

		h.insert(10);
		h.insert(15);
		h.insert(50);
		h.insert(40);
		h.insert(20);

		h.print();

		h.increaseKey(0, 25);

		h.print();
		*/

		/*
		int[] a = new int[]{11,1,6,1,1,9,9,9,10,4,7,9,5,8,0};

		longestSequence(a);
		 */

		/*
		//int[] a = new int[]{11,10,9,8,7,6,5,4,3,2,1};

		int[] a = new int[1000];
		Random rand = new Random();
		int max = 500000;
		int min = 0;
		for (int i=0; i<1000; i++) {
			a[i] = rand.nextInt((max - min) + 1) + min;
		}


		quicksort(a, 0, a.length-1);
		//insertionSort(a);
		//insertionSort(a, 0, a.length-1);

		for (int i=0; i<a.length; i++) {
			System.out.print(a[i] + "  ");
			if ( i % 5 == 0)
				System.out.println();
		}
		 */

		/*
		String s = "find all the anagrams of a given word in the least amount of time you are provided a dictionary which u can preprocess in any way"
				+ "logical an algorithm may be viewed as controlled logical deduction this notion may be expressed as algorithm logic control the logic component expresses the axioms that may be used in the computation and the control component determines the way in which deduction is applied to the axioms this is the basis for the logic programming paradigm in pure logic programming languages the control component is fixed and algorithms are specified by supplying only the logic component the appeal of this approach is the elegant semantics a change in the axioms has a well defined change in the algorithm"
				+ "serial or parallel or distributed algorithms are usually discussed with the assumption that computers execute one instruction of an algorithm at a time those computers are sometimes called serial computers an algorithm designed for such an environment is called a serial algorithm as opposed to parallel algorithms or distributed algorithms parallel algorithms take advantage of computer architectures where several processors can work on a problem at the same time whereas distributed algorithms utilize multiple machines connected with a network parallel or distributed algorithms divide the problem into more symmetrical or asymmetrical subproblems and collect the results back together the resource consumption in such algorithms is not only processor cycles on each processor but also the communication overhead between the processors sorting algorithms can be parallelized efficiently but their communication overhead is expensive iterative algorithms are generally parallelizable some problems have no parallel algorithms and are called inherently serial problems";

		String[] text = s.split(" ");

		MyTrie t = new MyTrie();

		for ( int i=0; i<text.length; i++ ) {
			t.insert(text[i]);
		}

		//t.print();

		MyBinaryHeap<Word> h = new MyBinaryHeap<>(10);

		t.fillHeap(h);
		 */

		//h.print();

		/*

		String sin = "C:\\Users\\Hani\\Desktop\\text.txt";

		File fin = new File(sin);

		try {
			BufferedReader br = new BufferedReader(new FileReader(fin));

			String line;
			MyTrie t = new MyTrie();

			while ( (line = br.readLine()) != null ) {
				if ( !line.isEmpty() ) {
					line = line.replaceAll("  ", "");
					line = line.replaceAll("\"", "");

					String[] text = line.split(" ");

					for ( String s : text )
						t.insert(s);

				}
			}

			MyBinaryHeap<Word> h = new MyBinaryHeap<>(10);
			t.fillHeap(h);
			h.print();

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		 */


	}

}








