package com.hani.fly;

import java.util.Comparator;

import com.hani.sorting.QuickSort;

public class IntertvalScheduling {

	/**
	 * Unweighted version Interval Scheduling Problem:
	 * Given a set of requests {1,2,3,4,...n} where the i'th request starts at s(i)
	 * and finishes at f(i). Give a subsets of the requests such that no two requests
	 * overlap. The subset should be optimal i.e. as many requests as possible.
	 * In this version of the problem many requests are to be processed by only
	 * one available resource.
	 * 
	 * Greedy algorithm that results in an optimal solution:
	 * Schedule the request with earliest finish time
	 */
	public static void intervalScheduling_EarliestFinishFirst(Interval[] requests) {

		// Sort the intervals according to their finishing times
		QuickSort qs = new QuickSort();
		qs.setComparator(new Interval.EarliestFinishTimeComparator());
		qs.sort(requests);

		Interval[] opt = new Interval[requests.length];

		int interval_index = 0;
		int opt_index = 0;

		while ( interval_index < requests.length ) {

			// Choose the interval with the earliest finish time
			// and add it to the optimal solution
			Interval i = requests[interval_index++];
			opt[opt_index++] = i;

			// Skip intervals that overlap with the above chosen interval
			while ( interval_index < requests.length 
					&& requests[interval_index].overlaps(i) )
				interval_index++;

			// OR:
			/*
			while ( interval_index < intervals.length 
					&& intervals[interval_index].start < i.finish )
				interval_index++;
			 */

		}

		for (int i=0; i<opt_index; i++)
			System.out.println(opt[i]);


	}

	/**
	 * Interval Partitioning Problem:
	 * As the above Interval Scheduling Problem but many resources are available now (not only one).
	 * The goal here is to serve ALL the requests, so request can overlap, minimizing the 
	 * number of used resources.
	 * 
	 * Greedy algorithm that results in an optimal solution:
	 * The depth, d, of the set of intervals is the maximum number of intervals that overlap.
	 * Then these set of intervals need at most d different resources.
	 * The algorithm sorts the requests according to their start time and in one pass
	 * assigns each request a label which represents the resource name, so there are d different
	 * labels. For a request j all the requests, in the sorted order which are less than j, are
	 * checked and if a request i overlaps the request j, the label of i is excluded from consideration,
	 * i.e. j and i cannot be assigned to same resource since they overlap.
	 * When all requests lower that j are considered, an available label is assigned to j. 
	 * 
	 */
	public static void intervalPartitioning(Interval[] requests) {

		// Sort the intervals according to their finishing times
		QuickSort qs = new QuickSort();
		qs.setComparator(new Interval.EarliestStartTimeComparator());
		qs.sort(requests);

		// Assume all the intervals overlap
		int[] labels = new int[requests.length];

		for ( int j=0; j<requests.length; j++ ) {

			Interval intrvl = requests[j];

			// For all intervals that are less than j in the sorted order
			for (int i=0; i<j; i++) {
				// If they overlap do not use this label
				if ( intrvl.overlaps(requests[i]) ) {
					labels[ requests[i].label ] = -1; // mark this label as unavailable
				}
			}

			int available_label_index = 0;
			while ( labels[available_label_index] == -1 )
				available_label_index++;
			intrvl.label = available_label_index;

			// Restore the labels
			labels = new int[requests.length];
		}

		for ( Interval i : requests )
			System.out.println( i + " assign to machine " + i.label);

	}

	public static class Interval implements Comparable<Interval> {
		public static final int UNLABELED = -1;
		public int start, finish, length;
		public String name;
		public int label; // used in the Interval Partitioning Problem
		public Interval(int start, int finish, String name) {
			this.start = start;
			this.finish = finish;
			this.name = name;
			length = finish - start;
			label = UNLABELED;
		}
		public boolean overlaps(Interval i) {
			if ( start == i.start && finish == i.finish ) return true;
			if ( start <= i.start && finish > i.start ) return true;
			if ( i.start <= start && i.finish > start ) return true;
			return false;
		}
		@Override
		public String toString() {
			return name + ": [" + start + ".." + finish + "] (" + length + ")";
		}
		public static class EarliestFinishTimeComparator implements Comparator<Interval> {
			@Override
			public int compare(Interval o1, Interval o2) {
				if ( o1.finish == o2.finish ) return 0;
				if ( o1.finish > o2.finish ) return 1;
				return -1;
			}
		}
		public static class EarliestStartTimeComparator implements Comparator<Interval> {
			@Override
			public int compare(Interval o1, Interval o2) {
				if ( o1.start == o2.start ) return 0;
				if ( o1.start > o2.start ) return 1;
				return -1;
			}
		}
		@Override
		public int compareTo(Interval o) {
			return (new EarliestFinishTimeComparator()).compare(this, o);
		}
	}

	/**
	 * Scheduling to minimize lateness:
	 * Again there are many requests but only one resource.
	 * The requests are more flexible since each request has a deadline and a length.
	 * So a request is to be scheduled before its deadline.
	 * And finish time is start time + length.
	 * Lateness is then the length is greater than the deadline of a request.
	 * Here overall lateness is to be minimized. Perfect lateness would be 0,
	 * i.e. all requests finished before their deadlines.
	 * 
	 * Greedy algorithm that results in an optimal solution:
	 * Schedule requests with Earliest Deadline First
	 */
	public static void intervalScheduling_EarliestDeadlineFirst(IntervalEDF[] requests) {

		QuickSort qs = new QuickSort();
		qs.sort(requests); // Sort according to deadlines

		int lateness = 0;

		requests[0].finish = requests[0].start + requests[0].length;

		for ( int j=1; j<requests.length; j++ ) {

			IntervalEDF i = requests[j];
			i.start = requests[j-1].finish;
			i.finish = i.start + i.length;
			if ( i.finish > i.deadline )
				lateness += (i.finish - i.deadline);

		}

		System.out.println("TOTAL LATENESS: " + lateness);

		for (IntervalEDF i : requests) {
			System.out.println(i);
		}

	}

	/** This class is used with the Earliest Deadline First **/
	public static class IntervalEDF implements Comparable<IntervalEDF> {
		public int start, finish, length, deadline;
		public IntervalEDF(int start, int length, int deadline) {
			this.start = start;
			this.length = length;
			this.deadline = deadline;
			this.finish = 0;
		}
		@Override
		public int compareTo(IntervalEDF o) {
			if ( deadline == o.deadline ) return 0;
			if ( deadline > o.deadline ) return 1;
			return -1;
		}
		@Override
		public String toString() {
			int lateness = 0;
			if ( finish > deadline )
				lateness = finish - deadline;
			return "[S:" + start + ", F:" + finish + ", L:" + length +"]  D:" + deadline + "| LAT: " + lateness;
		}

	}

	/**
	 * Optimal Caching: (Farthest-in-Future Algorithm)
	 * Consider a set U of n pieces of data stored in main memory and a faster memory, the cache.
	 * The cache can hold k < n pieces of data at any time.
	 * Initially the cache holds some set of k items and a sequence D=d1,d2,..,dm from U is presented
	 * to us. In processing them we must decide at all times which k items to keep in the cache.
	 * When item di is presented it can be accessed very fast if it is found in the cache.
	 * Otherwise, di must be brought from main memory and if the cache is full, some other piece must
	 * be evicted from the cache to make room for di. This is called a cache miss.
	 * The goal is to minimize cache misses by deciding what data to evict.
	 * 
	 * Farthest in the future algorithm will always incur the minimum number of cache misses:
	 * When di needs to be bought into the cache, evict the item that is needed the farthest
	 * into the future.
	 */
	public static void FarthestInFuture(int cacheSize, int[] cache, int[] dataSequence) {

		for ( int i=0; i<dataSequence.length; i++ ) {

			int di = dataSequence[i];
			if ( !contains(cache, di) ) {
				// Evict data that is in the cache and farthest in the future
				int index = dataSequence.length-1;
				while ( index > i && !contains(cache, dataSequence[index]) ) {
					index--;
				}
				int evict = dataSequence[index];
				for (int c=0; c<cache.length; c++) {
					if ( cache[c] == evict ) {
						cache[c] = di;
						System.out.println("Evicted " + evict);
						break;
					}
				}

			}

		}

	}

	private static boolean contains(int[] data, int num) {
		for (int i=0; i<data.length; i++) {
			if (data[i] == num) {
				return true;
			}
		}
		return false;
	}

	public static int bits(int num) {
		int bits = 0;
		while (num > 0) {
			num = num >> 1;
			bits++;
		}
		return bits;
	}
	
	public static int rec_multi(int x, int y) {
		
		int N = Math.max(bits(x), bits(y));
		
		if ( N < 3 )
			return x*y;
		
		N = (N / 2) + (N % 2);
		
		int x1 = x>>N; int kx = 1<<N; int x0 = (x & (1<<N)-1);
		int y1 = y>>N; int ky = 1<<N; int y0 = (y & (1<<N)-1);
		
		int p = rec_multi(x1+x0, y1+y0);
		int x1y1 = rec_multi(x1, y1);
		int x0y0 = rec_multi(x0, y0);
		
		return x1y1 * (1<<N) + (p-x1y1-x0y0)*(1<<N) + x0y0;
		
	}
	
	public static void main(String[] args) {
		
		int x = 6;
		int y = 70;
		
		System.out.println(x*y);
		System.out.println(rec_multi(x, y));
		
		
		
		

		/*
		Interval i1 = new Interval(0, 10);
		Interval i2 = new Interval(0, 1);
		Interval i3 = new Interval(2, 5);
		Interval i4 = new Interval(7, 9);
		Interval i5 = new Interval(12, 15);
		Interval i6 = new Interval(0, 3);
		Interval i7 = new Interval(4, 6);
		Interval i8 = new Interval(8, 11);
		Interval i9 = new Interval(13, 14);

		Interval[] intervals = new Interval[]{i1,i2,i3,i4,i5,i6,i7,i8,i9};

		intervalScheduling_EarliestFinishFirst(intervals);
		 */

		/*
		Interval ii1 = new Interval(2, 5, "e");
		Interval ii2 = new Interval(8, 9, "j");
		Interval ii3 = new Interval(0, 1, "c");
		Interval ii4 = new Interval(2, 3, "d");
		Interval ii5 = new Interval(4, 7, "g");
		Interval ii6 = new Interval(0, 3, "b");
		Interval ii7 = new Interval(6, 9, "h");
		Interval ii8 = new Interval(0, 1, "a");
		Interval ii9 = new Interval(4, 7, "f");
		Interval ii10 = new Interval(8, 9, "i");

		Interval[] intervals2 = new Interval[]{ii1,ii2,ii3,ii4,ii5,ii6,ii7,ii8,ii9,ii10};

		intervalPartitioning(intervals2);
		 */

		/*
		IntervalEDF iii1 = new IntervalEDF(0, 1, 2);
		IntervalEDF iii2 = new IntervalEDF(0, 2, 4);
		IntervalEDF iii3 = new IntervalEDF(0, 3, 6);

		IntervalEDF[] intervals3 = new IntervalEDF[]{iii1, iii2, iii3};

		intervalScheduling_EarliestDeadlineFirst(intervals3);
		 */

		/*
		int cacheSize = 3;
		int[] cache = new int[]{1,2,3};
		int[] D = new int[]{1,2,3,4,1,4,5,1,4,2,3};

		FarthestInFuture(cacheSize, cache, D);
		 */
	}

}















