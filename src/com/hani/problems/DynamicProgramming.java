package com.hani.problems;

public class DynamicProgramming {

	private static int min3(int a, int b, int c) {
		return Math.min(Math.min(a, b), c);
	}
	
	private static int max3(int a, int b, int c) {
		return Math.max(Math.max(a, b), c);
	}
	
	private static int min(int a, int b) {
		return Math.min(a, b);
	}
	
	private static int max(int a, int b) {
		return Math.max(a, b);
	}
	
	/**
	 * The Knapsack problem using dynamic programming.
	 * @param weights weights of the items. Are expected to be sorted in increasing order.
	 * @param values values of the items
	 * @param target_weight knapsack weight, i.e. target weight
	 */
	public static void knapsack(int[] weights, int[] values, int target_weight, int n) {
		int i, w;
		int[][] a = new int[n+1][target_weight+1];

		// Initialize zero rows and columns all to 0
		for (int k=0; k<=target_weight; k++)
			a[0][k] = 0;
		for (int k=1; k<=n; k++)
			a[k][0] = 0;

		for (i=1; i<=n; i++) {

			// get item weight and value
			int wi = weights[i-1];
			int vi = values[i-1];

			for (w=0; w<=target_weight; w++) {
				if ( wi <= w ) {
					a[i][w] = Math.max( vi + a[i-1][w-wi], a[i-1][w] );
				} else {
					a[i][w] = a[i-1][w];
				}
			}

		}

		for (int m=0; m<n+1; m++) {
			for (int nn=0; nn<target_weight+1; nn++)
				System.out.print(a[m][nn] + " ");
			System.out.println();
		}

		// Backtrack

		i = n;
		w = target_weight;

		while (i > 0 && w > 0) {
			if ( a[i][w] == a[i-1][w] ) {
				i--;
			} else {
				System.out.println( "(" + i + ") " + weights[i-1] + " " + values[i-1] );
				w = w - weights[i-1];
				i--;
			}
		}

	}

	// More compact version
	public static void knapsack_2(int weights[], int[] values, int W, int n) {
		int i,w;
		int[][] a = new int[n+1][W+1];
		for (i=0; i<=n; i++) {
			for (w=0; w<=W; w++) {
				if (i==0 || w==0)
					a[i][w] = 0;
				else if ( weights[i-1] <= w ) {
					a[i][w] = Math.max( values[i-1] + a[i-1][w-weights[i-1]], a[i-1][w] );
				} else {
					a[i][w] = a[i-1][w];
				}
			}
		}

		i = n;
		w = W;
		while (i>0 && w>0) {
			if (a[i][w] == a[i-1][w]) {
				i--;
			} else {
				System.out.println("("+i+") w:" + weights[i-1] + " v:" + values[i-1]);
				w = w - weights[--i];
			}
		}

	}

	public static void longest_common_subsequence(char[] s1, char[] s2) {

		if (s1.length > s2.length) {
			char[] temp = s1;
			s1 = s2;
			s2 = temp; 
		}

		int i, j;
		int n1 = s1.length;
		int n2 = s2.length;

		int[][] a = new int[n1+1][n2+1];

		for (i=0; i<=n1; i++) {

			for (j=0; j<=n2; j++) {

				if (i==0 || j==0)
					a[i][j] = 0;
				else {
					char c1 = s1[i-1];
					char c2 = s2[j-1];

					if (c1 == c2) {
						a[i][j] = a[i-1][j-1] + 1;
					} else {
						a[i][j] = Math.max( a[i-1][j], a[i][j-1] );
					}
				}

				System.out.print(a[i][j] + " ");

			}

			System.out.println();

		}

		// Backtrack (Not correct)

		i = s1.length;
		j = s2.length;

		char[] lcs = new char[i+j];

		while (i>0 && j>0) {
			if ( a[i][j] == a[i-1][j] ) {
				i--;
			} else {
				lcs[i] = s1[i-1];
				j--;
				i--;
			}
		}
		System.out.println(String.valueOf(lcs).replaceAll("\0", ""));

	}

	// 1 2 3 5 8
	// O(n) time O(n) space
	public static int fibonacci(int n) {
		if (n==0)
			return n;
		int[] fib = new int[n+1];

		fib[0] = 1;
		fib[1] = 2;

		for (int i=2; i<=n; i++)
			fib[i] = fib[i-1] + fib[i-2];

		return fib[n];
	}

	public static int fibonacci_2(int n) {
		if (n==0)
			return n;
		int a = 1;
		int b = 2;
		int f = a + b;
		for (int i=2; i<=n; i++) {
			f = a + b;
			a = b;
			b = f;
		}
		return f;
	}

	/**
	 * Get the minimum number of coins needed to get a change from the
	 * set coins[] available.
	 * This recursive algorithm is very inefficient.
	 * @param coins
	 * @param change
	 * @return
	 */
	public static int makeChange_1(int[] coins, int change) {
		int minCoins = change;

		for (int i=0; i<coins.length; i++)
			if (coins[i]==change)
				return 1;

		for (int j=1; j<change; j++) {

			int thisCoins = makeChange_1(coins, j) + makeChange_1(coins, change - j);
			if (thisCoins < minCoins)
				minCoins = thisCoins;

		}
		return minCoins;
	}

	public static void makeChange_2(int[] coins, int change, int[] coinsUsed, int[] lastCoin) {

		int nrCoins = coins.length;

		coinsUsed[0] = 0;
		lastCoin[0] = 1;

		for (int cents = 1; cents <= change; cents++) {

			int minCoins = cents;
			int newCoin = 1;

			for (int j=0; j<nrCoins; j++) {

				if (coins[j] > cents)
					continue;
				if ( coinsUsed[ cents - coins[j] ] + 1 < minCoins ) {
					minCoins = coinsUsed[ cents - coins[j] ] + 1;
					newCoin = coins[j];
				}

			}

			coinsUsed[cents] = minCoins;
			lastCoin[cents] = newCoin;

		}

	}

	/**
	 * a[0..n-1]
	 * L(i) length of LIS till index i s.t. a[i] belongs to LIS and a[i] last in LIS then
	 * L(i) = 1 + L(j) where j<i and a[j]<a[i] if such j exists.
	 * Otherwise L(i) = 1
	 * ==> L(i) = MAX ( L(i) ) 0<i<n 
	 */
	private static int max_lis = 1;
	public static int LongestIncreasingSubsequence_recursive(int[] a, int n) {

		if (n==1)
			return 1;

		int res;
		int max_ending = 1; // Max LIS ending at a[n-1]

		for (int i=1; i<n; i++) {
			res = LongestIncreasingSubsequence_recursive(a, i);
			if ( a[i-1] < a[n-1] && res + 1 > max_ending)
				max_ending = res + 1;
		}

		if (max_ending > max_lis)
			max_lis = max_ending;

		return max_ending;

	}

	public static int LongestIncreasingSubsequence_dynamic(int[] a) {
		int n = a.length;
		int[] lis = new int[n];

		for (int i=0; i<n; i++)
			lis[i] = 1;

		for (int i=1; i<n; i++) {
			for (int j=0; j<i; j++) {
				if ( a[j]<a[i] && lis[i] < lis[j]+1 )
					lis[i] = lis[j] + 1;
			}
		}

		int max_lis = 1;
		for (int i=0; i<n; i++)
			if (lis[i] > max_lis)
				max_lis = lis[i];

		return max_lis;
	}

	/**
	 * LCS Problem Statement: 
	 * Given two sequences, find the length of longest subsequence present in both of them. 
	 * A subsequence is a sequence that appears in the same relative order, but not necessarily contiguous.
	 * let X[0..n-1] and Y[0..m-1] be two strings and L(X[0..n-1],Y[0..m-1]) be the longest
	 * common subsequence. Then: L(X[0..n-1],Y[0..m-1]) = 
	 * 		1. 1 + L(X[0..n-2],Y[0..m-2]) if X[n-1] == Y[m-1] i.e. both characters at the indices are equal
	 * 		2. MAX ( L(X[0..n-1],Y[0..m-1]), L(X[0..n-1],Y[0..m-2]) )
	 */

	private static String lcs = "";
	public static int LongestCommonSubsequence_recursive(char[] x, char[] y, int n, int m) {

		if ( n == 0 || m == 0 )
			return 0;

		if ( x[n-1] == y[m-1] ) {
			if (!lcs.contains(x[n-1]+""))  lcs += x[n-1];
			return 1 + LongestCommonSubsequence_recursive(x, y, n-1, m-1);
		}

		return Math.max(
				LongestCommonSubsequence_recursive(x, y, n, m-1),
				LongestCommonSubsequence_recursive(x, y, n-1, m)
				);

	}

	/**
	 * LCS has both properties: Optimal Substructure and Overlapping subproblems
	 * so it can be solved using dynamic programming  
	 */
	public static void LongestCommonSubsequence_dynamic(char[] x, char[] y) {

		int n = x.length;
		int m = y.length;

		int[][] lcs = new int [n+1][m+1];

		for (int i=0; i<=n; i++) {

			for (int j=0; j<=m; j++) {

				if (i==0 || j==0)
					lcs[i][j] = 0;
				else {
					if ( x[i-1] == y[j-1] )
						lcs[i][j] = 1 + lcs[i-1][j-1];
					else
						lcs[i][j] = Math.max( lcs[i-1][j] , lcs[i][j-1] );
				}

			}

		}

		for (int i=0; i<=n; i++) {
			for (int j=0; j<=m; j++)
				System.out.print(lcs[i][j] + " ");
			System.out.println();
		}

		int i = x.length;
		int j = x.length;

		char[] res = new char[i+j];

		while (i>0 && j>0) {
			if ( lcs[i][j] == lcs[i-1][j] ) {
				i--;
			} else {
				res[i] = x[i-1];
				j--;
				i--;
			}
		}
		System.out.println("LCS: " + String.valueOf(res).replaceAll("\0", ""));
	}
	
	/**
	 * Edit Distance Problem:
	 * Given two strings of size m, n and set of operations replace (R), insert (I) and delete (D) all at equal cost. 
	 * Find minimum number of edits (operations) required to convert one string into another.
	 * s1 into s2
	 */
	private static int minDis = 100;
	public static int EditDistance_recursive(char[] s1, char[] s2, int m, int n) {
		if (m==0 && n==0)
			return 0;
		if (m==0)
			return n;
		if (n==0)
			return m;
		int left = EditDistance_recursive(s1, s2, m-1, n) + 1;
		int right = EditDistance_recursive(s1, s2, m, n-1) + 1;
		int corner = EditDistance_recursive(s1, s2, m-1, n-1);
		if ( s1[m-1] != s2[n-1] )
			corner += 1;
		
		int res = Math.min(Math.min(left, right), corner);
		if (res < minDis)
			minDis = res;
		
		return res;
		
	}
	
	private static final int EDIT_COST = 1;
	public static void EditDistance_dynamic(char[] s1, char[] s2) {
		
		int n1 = s1.length;
		int n2 = s2.length;
		
		int[][] tbl = new int[n1+1][n2+1];
		
		for (int i=0; i<=n1; i++) {
			for (int j=0; j<=n2; j++)
				tbl[i][j] = -1;
		}
		
		for (int i=0; i<=n1; i++)
			tbl[i][0] = i;
		for (int i=0; i<=n2; i++)
			tbl[0][i] = i;
		
		for (int i=1; i<=n1; i++) {
			for (int j=1; j<=n2; j++) {
				
				// left cell, deletion
				int leftCell = tbl[i][j-1] += EDIT_COST;
				
				// top cell, insertion
				int topCell = tbl[i-1][j] += EDIT_COST;
				
				// left corner cell
				int cornerCell = tbl[i-1][j-1]; 
				if ( s1[i-1] != s2[j-1 ]) {
					tbl[i-1][j-1] += EDIT_COST; // replacement
					cornerCell += EDIT_COST;
				}
				
				tbl[i][j] = Math.min(Math.min(leftCell, topCell), cornerCell);
				
			}
		}
		
		for (int i=0; i<=n1; i++) {
			for (int j=0; j<=n2; j++)
				System.out.print(tbl[i][j] + " ");
			System.out.println();
		}
		
		System.out.println("Minimum number of operations: " + tbl[n1][n2]);
		
	}
	
	/**
	 * Egg Dropping Problem
	 * n - number of eggs, k- number of floors
	 * Return minimum number of trials in worst case for n eggs
	 */
	public static int EggDropping_recursive(int n, int k) {

		// If floor 0 no trials are needed
		// If floor 1, 1 trial is needed
		if (k==0 || k==1)
			return k;

		// If only one egg is available then every floor must be tested
		// In worst case all the k floors
		if (n==1)
			return k;

		int min = Integer.MAX_VALUE;

		// Consider every drop from floor 1 to floor k
		for ( int x=1; x<=k; x++ ) {
			// If the egg did not survive, go down 1 floor with 1 less eggs
			int broken = EggDropping_recursive(n-1, x-1);
			// If the egg survives, test the upper floors
			int survived = EggDropping_recursive(n, k-x);
			// MAX since worst case
			int res = Math.max(survived, broken);

			if (res < min)
				min = res;
		}
		return min + 1;
	}

	public static int EggDropping_dynamic(int n, int k) {

		int[][] eggFloor = new int[n+1][k+1];

		// 1 trial for 1 floor, 0 trials for 0 floors
		for ( int i=0; i<=n; i++ ) {
			eggFloor[i][1] = 1;
			eggFloor[i][0] = 0;
		}

		// Always i floors for 1 egg
		for ( int i=0; i<=k; i++ )
			eggFloor[1][i] = i;

		for (int i = 2; i <= n; i++){
			for (int j = 2; j <= k; j++){
				
				eggFloor[i][j] = Integer.MAX_VALUE;
				
				for (int x = 1; x <= j; x++){
					int res = 1 + Math.max(eggFloor[i-1][x-1], eggFloor[i][j-x]);
					if (res < eggFloor[i][j])
						eggFloor[i][j] = res;
				}
				
			}
		}

		return eggFloor[n][k];

	}
	
	/**
	 * Given a cost matrix cost[][] and a position (m, n) in cost[][], write a function that returns cost 
	 * of minimum cost path to reach (m, n) from (0, 0). 
	 * Each cell of the matrix represents a cost to traverse through that cell. 
	 * Total cost of a path to reach (m, n) is sum of all the costs on that path (including both source and destination).
	 * You can only traverse down, right and diagonally lower cells from a given cell, i.e., from a given cell (i, j), 
	 * cells (i+1, j), (i, j+1) and (i+1, j+1) can be traversed. You may assume that all costs are positive integers.
	 */
	public static int MinCostPath_recursive(int[][] cost, int m, int n) {

		if (m<0 || n<0)
			return Integer.MAX_VALUE;
		
		if (m==0 && n==0)
			return cost[m][n];
		
		return cost[m][n] + min3( MinCostPath_recursive(cost, m, n-1),
								 MinCostPath_recursive(cost, m-1, n),
								 MinCostPath_recursive(cost, m-1, n-1) );		
		
	}
	
	public static void MiCostPath_dynamic(int[][] cost, int m, int n) {
		
		int[][] minCost = new int[m+1][n+1];
		
		for (int i=0; i<=m; i++)
			for (int j=0; j<=n; j++)
				minCost[i][j] = Integer.MAX_VALUE;
		
		minCost[0][0] = 0;
		
		for (int i=1; i<=m; i++) {
			for (int j=1; j<=n; j++) {
				minCost[i][j] = cost[i-1][j-1] + min3( minCost[i-1][j-1],
									 			  minCost[i-1][j],
									 			  minCost[i][j-1] );
			}
		}
		
		System.out.println(minCost[m][n]);
		
	}
	
	/**
	 * Cutting a Rod:
	 * Given a rod of length n inches and an array of prices that contains prices of all pieces of size smaller than n. 
	 * Determine the maximum value obtainable by cutting up the rod and selling the pieces.
	 * For example, if length of the rod is 8 and the values of different pieces are given as following, 
	 * then the maximum obtainable value is 22 (by cutting in two pieces of lengths 2 and 6)
	 *	
	 *	length   | 1   2   3   4   5   6   7   8  
	 *		--------------------------------------------
	 *	price    | 1   5   8   9  10  17  17  20
	 *	
	 * And if the prices are as following, then the maximum obtainable value is 24 (by cutting in eight pieces of length 1)
	 *	
	 *	length   | 1   2   3   4   5   6   7   8  
	 *	--------------------------------------------
	 *	price    | 3   5   8   9  10  17  17  20
	 * 
	 * n - length of the rod
	 * p[i] - price of rod piece of length i
	 */
	public static int CutRod_recursive(int[] p, int n) {
		
		if (n==0)
			return 0;
		
		int max_value = Integer.MIN_VALUE;
		for (int i=0; i<n; i++)
			max_value = max( max_value, p[i] + CutRod_recursive(p, n-i-1) );
		
		return max_value;
		
	}
	
	public static int CutRod_dynamic(int[] p, int n) {
		
		int[] val = new int[n+1];
		val[0] = 0;
		
		for ( int i=1; i<=n; i++ ) {
			int max_value = Integer.MIN_VALUE;
			for (int j=0; j<i; j++)
				max_value = max( max_value, p[j] + val[i-j-1] );
			val[i] = max_value;
		}
		
		return val[n];
		
	}
	
	/**
	 * Given n items each with a nonnegative weight w and a bound W.
	 * Find a maximum subset of the items so that their sum <= W.
	 */
	public static int SubsetSum_recursive(int[] weights, int W, int n) {
		if (W<=0 || n<=0)
			return 0;
		
		int wi = weights[n-1];
		if ( wi > W )
			return SubsetSum_recursive(weights, W, n-1);
		return max(
				SubsetSum_recursive(weights, W, n-1),
				wi + SubsetSum_recursive(weights, W-wi, n-1)
				);
		
	}
	
	// Returns true if there is a subset that sums up to exactly W
	public static boolean SubsetSum2_recursive(int[] weights, int W, int n) {
		if (n==0 && W!=0)
			return false;
		if (W==0)
			return true;
		
		int wi = weights[n-1];
		if ( wi > W )
			return SubsetSum2_recursive(weights, W, n-1);
		return SubsetSum2_recursive(weights, W, n-1) ||
				SubsetSum2_recursive(weights, W-wi, n-1);
		
	}
	
	// W is like sum
	public static int SubsetSum_dynamic(int weights[], int sum, int n) {
		
		int[][] subset = new int[sum+1][n+1];
		
		// If sum is zero the answer is 0
		for (int i=0; i<=n; i++)
			subset[0][i] = 0;
		
		// If there are no items the sum is 0
		for (int i=0; i<=sum; i++)
			subset[i][0] = 0;
		
		for (int i=1; i<=sum; i++) {
			for (int j=1; j<=n; j++) {
				
				subset[i][j] = subset[i][j-1];
				if ( i >= weights[j-1] )
					subset[i][j] = max(
										subset[i][j],
										weights[j-1] + subset[ i - weights[j-1] ][j-1]
										);
				
			}
		}
		
		for (int i=0; i<=sum; i++) {
			for (int j=0; j<=n; j++)
				System.out.print(subset[i][j] + " ");
			System.out.println();
		}
		
		return subset[sum][n];
		
	}
	
	public static boolean SubsetSum2_dynamic(int weights[], int sum, int n) {

		boolean[][] subset = new boolean[sum+1][n+1];
		
		// If sum is zero the answer is 0
		for (int i=0; i<=n; i++)
			subset[0][i] = true;
		
		// If there are no items the sum is 0
		for (int i=0; i<=sum; i++)
			subset[i][0] = false;
		
		for (int i=1; i<=sum; i++) {
			for (int j=1; j<=n; j++) {
				
				subset[i][j] = subset[i][j-1];
				if ( i >= weights[j-1] )
					subset[i][j] = subset[i][j] ||
								   subset[ i - weights[j-1] ][j-1];
				
			}
		}
		
		for (int i=0; i<=sum; i++) {
			for (int j=0; j<=n; j++)
				System.out.print(subset[i][j] + " ");
			System.out.println();
		}
		
		return subset[sum][n];
		
	}
	
	/**
	 * Sequence Alignment problem
	 * Beta  - gap penalty
	 * Alpha - mismatch cost 
	 */
	private static int Alpha(char a, char b) { return a==b ? 0 : 1; }
	private static final int Beta = 1;
	public static int SequenceAlignment_recursive(char[] x, char[] y, int i, int j) {
		
		if (i==0 || j==0)
			return Alpha(x[i], y[j]);
		
		return min3(
						Alpha(x[i], y[j]) + SequenceAlignment_recursive(x, y, i-1, j-1),
						Beta + SequenceAlignment_recursive(x, y, i-1, j),
						Beta + SequenceAlignment_recursive(x, y, i, j-1)
					);
		
	}
	
	public static void SequenceAlignment_dynamic(char[] x, char[] y) {
		
		int m = x.length;
		int n = y.length;
		
		int[][] A = new int[m+1][n+1];
		
		for (int i=0; i<=m; i++)
			A[i][0] = i*Beta;
		for (int i=0; i<=n; i++)
			A[0][i] = i*Beta;
		
		for (int i=1; i<=m; i++) {
			for (int j=1; j<=n; j++) {
				A[i][j] = min3(
								Alpha(x[i-1], y[j-1]) + A[i-1][j-1],
								Beta + A[i-1][j],
								Beta + A[i][j-1]
							);
			}
			
		}
		
		System.out.print("- - ");
		for (int j=0; j<n; j++) {
			System.out.print(y[j] + " ");
		}
		System.out.println("");
		for (int i=0; i<=m; i++) {
			if(i>0) System.out.print(x[i-1] + " ");
			else System.out.print("- ");
			for (int j=0; j<=n; j++) {
				System.out.print(A[i][j] + " ");
			}
			System.out.println();
		}
		
	}

	public static void main(String[] args) {
		
		String x = "ocurrance";
		String y = "occurence";
		
		System.out.println(SequenceAlignment_recursive(x.toCharArray(), y.toCharArray(), x.length()-1, y.length()-1));
		SequenceAlignment_dynamic(x.toCharArray(), y.toCharArray());
		
		/*
		int[] weights = new int[]{3, 34, 4, 12, 5, 2};
		int W = 11;
		System.out.println(SubsetSum_recursive(weights, W, weights.length));
		System.out.println(SubsetSum2_dynamic(weights, W, weights.length));
		*/
		
		/*
		String x = "sunday";
		String y = "saturday";
		
		EditDistance_dynamic(x.toCharArray(), y.toCharArray());
		//EditDistance_recursive(x.toCharArray(), y.toCharArray(), x.length(), y.length());
		System.out.println(minDis);
		*/
		
		/*
		//LongestCommonSubsequence_recursive(x.toCharArray(), y.toCharArray(), x.length(), y.length());
		LongestCommonSubsequence_dynamic(x.toCharArray(), y.toCharArray());

		System.out.println(lcs);
		*/

		/*
		int[] a = new int[]{10,22,9,33,21,50,41,60,80};
		int[] b = new int[]{3,2,1,10,1,12,13,17,9};
		int[] c = new int[]{1,2,3,4,9,5,4};

		LongestIncreasingSubsequence_recursive(a, a.length);
		System.out.println(max_lis);

		System.out.println(LongestIncreasingSubsequence_dynamic(a));
		 */

		/*
		int[] weights = new int[]{5,6,4,3};
		int[] values = new int[]{10,30,40,50};
		int target = 10;

		//knapsack(weights, values, target, 4);
		//XMJYAUZ� and Y be �MZJAWXU
		// nematode knowledge" and "empty bottle
		String s1 = "nematode-knowledge";
		String s2 = "empty-bottle";

		//longest_common_subsequence(s1.toCharArray(), s2.toCharArray());

		//System.out.println(fibonacci(700));

		int[] coins = new int[]{1,3,10};
		int change = 15;
		System.out.println( makeChange_1(coins, change) + " coins needed(minimum)" );

		int[] coinsUsed = new int[change+1];
		int[] lastCoin = new int[change+1];

		makeChange_2(coins, change, coinsUsed, lastCoin);
		System.out.println(coinsUsed[change]);
		 */

	}

}































