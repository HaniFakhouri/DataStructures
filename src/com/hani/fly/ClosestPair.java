package com.hani.fly;

import java.util.Arrays;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;

public class ClosestPair {

    // closest pair of points and their Euclidean distance
    private Point best1, best2;
    private double bestDistance = Double.POSITIVE_INFINITY;

    public ClosestPair(Point[] points) {
        int N = points.length;
        if (N <= 1) return;

        // sort by x-coordinate (breaking ties by y-coordinate)
        Point[] pointsByX = new Point[N];
        for (int i = 0; i < N; i++) pointsByX[i] = points[i];
        Arrays.sort(pointsByX, new Point.XCoordComparator());
        
        // sort by y-coordinate (but not yet sorted) 
        Point[] pointsByY = new Point[N];
        for (int i = 0; i < N; i++) pointsByY[i] = pointsByX[i];

        // auxiliary array
        Point[] aux = new Point[N];

        closest(pointsByX, pointsByY, aux, 0, N-1);
    }

    // find closest pair of points in pointsByX[lo..hi]
    // precondition:  pointsByX[lo..hi] and pointsByY[lo..hi] are the same sequence of points
    // precondition:  pointsByX[lo..hi] sorted by x-coordinate
    // postcondition: pointsByY[lo..hi] sorted by y-coordinate
    private double closest(Point[] pointsByX, Point[] pointsByY, Point[] aux, int lo, int hi) {
        if (hi <= lo) return Double.POSITIVE_INFINITY;

        int mid = lo + (hi - lo) / 2;
        Point median = pointsByX[mid];

        // compute closest pair with both endpoints in left subarray or both in right subarray
        double delta1 = closest(pointsByX, pointsByY, aux, lo, mid);
        double delta2 = closest(pointsByX, pointsByY, aux, mid+1, hi);
        double delta = Math.min(delta1, delta2);

        // merge back so that pointsByY[lo..hi] are sorted by y-coordinate
        merge(pointsByY, aux, lo, mid, hi);

        // aux[0..M-1] = sequence of points closer than delta, sorted by y-coordinate
        int M = 0;
        for (int i = lo; i <= hi; i++) {
            if (Math.abs(pointsByY[i].x - median.x) < delta)
            	aux[M++] = pointsByY[i];
        }
        
        // compare each point to its neighbors with y-coordinate closer than delta
        for (int i = 0; i < M; i++) {
            // a geometric packing argument shows that this loop iterates at most 7 times
            for (int j = i+1; (j < M) && (aux[j].y - aux[i].y < delta); j++) {
                double distance = aux[i].distanceTo(aux[j]);
                if (distance < delta) {
                    delta = distance;
                    if (distance < bestDistance) {
                        bestDistance = delta;
                        best1 = aux[i];
                        best2 = aux[j];
                        // StdOut.println("better distance = " + delta + " from " + best1 + " to " + best2);
                    }
                }
            }
        }
        return delta;
    }

    public Point either() { return best1; }
    public Point other()  { return best2; }

    public double distance() {
        return bestDistance;
    }

    // is v < w ?
    private static boolean less(Comparable v, Comparable w) {
        return (v.compareTo(w) < 0);
    }

    // stably merge a[lo .. mid] with a[mid+1 ..hi] using aux[lo .. hi]
    // precondition: a[lo .. mid] and a[mid+1 .. hi] are sorted subarrays
    private static void merge(Comparable[] a, Comparable[] aux, int lo, int mid, int hi) {
        // copy to aux[]
        for (int k = lo; k <= hi; k++) {
            aux[k] = a[k];
        }
    
        // merge back to a[] 
        int i = lo, j = mid+1;
        for (int k = lo; k <= hi; k++) {
            if      (i > mid)              a[k] = aux[j++];
            else if (j > hi)               a[k] = aux[i++];
            else if (less(aux[j], aux[i])) a[k] = aux[j++];
            else                           a[k] = aux[i++];
        }
    }

    public static class Point implements Comparable<Point> {
		public double x,y;
		public String name;
		public Point(double x, double y, String name) {
			this.x = x;
			this.y = y;
			this.name = name;
		}
		public double distanceTo(Point p) {
			double xx = Math.abs( x - p.x );
			double yy = Math.abs( y - p.y );
			return Math.sqrt( xx*xx + yy*yy );
		}
		@Override
		public String toString() {
			return name + ":(" + x + "," + y + ")";
		}
		public static class XCoordComparator implements Comparator<Point> {
			@Override
			public int compare(Point o1, Point o2) {
				if (o1.x == o2.x) return 0;
				if (o1.x > o2.x) return 1;
				return -1;
			}
		}
		public static class YCoordComparator implements Comparator<Point> {
			@Override
			public int compare(Point o1, Point o2) {
				if (o1.y == o2.y) return 0;
				if (o1.y > o2.y) return 1;
				return -1;
			}
		}
		@Override
		public int compareTo(Point o) {
			int dist = (int) this.distanceTo(o);
			if ( dist == 0 ) return 0;
			return 1;
		}
		@Override
		public boolean equals(Object o) {
			Point p = (Point)o;
			return p.distanceTo(this) == 0;
		}
		@Override
		public int hashCode() {
			return (int) (10*x + y);
		}
	}
    
    public static Point[] closestPair_BruteForce(Point[] points, boolean print) {
		Point p1 = null;
		Point p2 = null;
		double min_dist = Integer.MAX_VALUE - 50;
		for ( int i=0; i<points.length; i++ ) {
			Point pt1 = points[i];
			for ( int j=i; j<points.length; j++ ) {
				Point pt2 = points[j]; 
				double dist = pt1.distanceTo(pt2);
				if ( !pt1.equals(pt2) && dist < min_dist ) {
					p1 = pt1;
					p2 = pt2;
					min_dist = dist;
				}
			}
		}
		
		if (print) {
			System.out.println("Closest Pair:");
			System.out.println(p1);
			System.out.println(p2);
			System.out.println("Distance: " + min_dist);
			System.out.println();
		}
		
		return new Point[]{p1, p2};		
		
	}
    
    private static void printPoints(Point[] points) {
		for (Point p : points)
			System.out.println(p);
		System.out.println();
	}

    public static void main(String[] args) {
        /*
    	int N = StdIn.readInt();
        Point[] points = new Point[N];
        for (int i = 0; i < N; i++) {
            double x = StdIn.readDouble();
            double y = StdIn.readDouble();
            points[i] = new Point(x, y);
        }
        ClosestPair closest = new ClosestPair(points);
        StdOut.println(closest.distance() + " from " + closest.either() + " to " + closest.other());
        */
    	
    	/*
    	Random rand = new Random();
		int max = 50;
		int min = 0;
		int nrPoints = 20;
		
		Point[] points = new Point[nrPoints];
		Set<Point> setOfPoints = new HashSet<>();
		
		for (int i=0; i<nrPoints; i++) {
			int xrand = rand.nextInt((max - min) + 1) + min;
			int yrand = rand.nextInt((max - min) + 1) + min;
			Point p = new Point(xrand, yrand);
			while ( setOfPoints.contains(p) ) {
				xrand = rand.nextInt((max - min) + 1) + min;
				yrand = rand.nextInt((max - min) + 1) + min;
				p = new Point(xrand, yrand);
			}
			setOfPoints.add(p);
			points[i] = new Point(xrand, yrand);
			System.out.println(points[i]);
		}
		*/
    	
    	Random rand = new Random();
		int max = 50000;
		int min = 0;
		int nrPoints = 20000;

		Point[] points = new Point[nrPoints];
		Set<Point> setOfPoints = new HashSet<>();

		for (int i=0; i<nrPoints; i++) {
			int xrand = rand.nextInt((max - min) + 1) + min;
			int yrand = rand.nextInt((max - min) + 1) + min;
			Point p = new Point(xrand, yrand, "");
			while ( setOfPoints.contains(p) ) {
				xrand = rand.nextInt((max - min) + 1) + min;
				yrand = rand.nextInt((max - min) + 1) + min;
				p = new Point(xrand, yrand, "");
			}
			setOfPoints.add(p);
			points[i] = new Point(xrand, yrand, "P" + i);
			//System.out.println(points[i]);
		}
		System.out.println("Points generated");

		ClosestPair closest = new ClosestPair(points);
        System.out.println(closest.distance() + " from " + closest.either() + " to " + closest.other());
		System.out.println();
		closestPair_BruteForce(points, true);
    	
    	/*
    	Point p1 = new Point(1, 3, "P1");
		Point p2 = new Point(7, 3, "P3");
		Point p3 = new Point(4, 3, "P2");
		Point p4 = new Point(11, 3, "P5");
		Point p5 = new Point(8, 3, "P4");
		Point p6 = new Point(14, 3, "P6");
		
		Point[] points = new Point[]{p1,p2,p3,p4,p5,p6};
		
		ClosestPair closest = new ClosestPair(points);
        System.out.println(closest.distance() + " from " + closest.either() + " to " + closest.other());
		*/
    }

}













