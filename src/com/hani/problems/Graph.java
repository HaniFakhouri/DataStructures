package com.hani.problems;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Set;
import java.util.Stack;
import java.util.Vector;

import com.hani.hashtable.HashSet;
import com.hani.heap.PairingHeap.Position;
import com.hani.heap.PairingHeap;
import com.hani.sorting.QuickSort;

public class Graph {

	public static final double INFINITY = Double.MAX_VALUE;

	private Map<String, Vertex> vertexMap;
	private Map<String, Integer> edgeWeights;
	private boolean directed;

	public Graph(boolean directed) {
		vertexMap = new HashMap<String, Vertex>();
		edgeWeights = new HashMap<String, Integer>();
		this.directed = directed;
	}

	public void clearAll() {
		for (Vertex v : vertexMap.values())
			v.reset();
	}

	public int size() {
		return vertexMap.size();
	}

	public void addEdge(String sourceName, String destName, double weight) {
		Vertex u = getVertex(sourceName);
		Vertex v = getVertex(destName);

		Edge e1 = new Edge(u, v, weight);
		u.adj.add(e1);

		edgeWeights.put(sourceName + destName, (int)weight);

		if (!directed) {
			Edge e2 = new Edge(v, u, weight);
			v.adj.add(e2);
			edgeWeights.put(destName + sourceName, (int)weight);
		}
	}

	public void BFS(String startVertex) {
		Vertex start = vertexMap.get(startVertex);
		if (start == null) {
			System.out.println(startVertex + " not found!");
			return;
		}
		clearAll();
		Queue<Vertex> q = new LinkedList<>();
		q.add(start);
		System.out.print(start.name + " ");
		while (!q.isEmpty()) {
			Vertex v = q.poll();
			v.scratch = 1;
			for ( Edge e : v.adj ) {
				Vertex w = e.dest;
				if (w.scratch == 0) {
					System.out.print(w.name + " ");
					w.scratch = 1;
					q.add(w);
				}
			}
		}
		System.out.println();
	}

	public void DFS(String startVertex) {
		Vertex start = vertexMap.get(startVertex);
		if (start == null) {
			System.out.println(startVertex + " not found!");
			return;
		}
		clearAll();
		Stack<Vertex> s = new Stack<>();
		s.add(start);
		while (!s.isEmpty()) {
			Vertex v = s.pop();
			if (v.scratch == 0) { // not yet visited
				v.scratch = 1;
				System.out.print(v.name + " >> ");
				for (Edge e : v.adj) {
					s.push(e.dest);
				}
			}
		}
	}

	public void printGraph() {

		for (Vertex v : vertexMap.values()) {
			System.out.print(v.name + ": ");
			for (Edge e : v.adj) {
				System.out.print(e.dest.name + "(" + e.weight + ") ");
			}
			System.out.println();
		}

	}

	private Vertex getVertex(String vertexName) {
		Vertex v = vertexMap.get(vertexName);
		if (v == null) {
			v = new Vertex(vertexName);
			vertexMap.put(vertexName, v);
		}
		return v;
	}

	/**
	 * PQ (pairing heap) decrease key, find minimum: O(1) amortized worst case time
	 * PQ (pairing heap) delete minimum: O(log N) i.e. O(log |V|)
	 * ==> O(|E| log |V|)
	 */
	public void Prim_MST(String vertexName) {

		PairingHeap<Vertex> pq = new PairingHeap<>();

		for (Vertex v : vertexMap.values()) {
			v.scratch = Integer.MAX_VALUE;
			v.prev = null;
			v.pos = pq.insert(v);
		}

		Vertex v = vertexMap.get(vertexName);
		v.scratch = 0;
		pq.decreaseKey(v.pos, v);

		while ( !pq.isEmpty() ) {

			v = (Vertex) pq.deleteMin();
			if ( v.prev != null ) {
				int w = edgeWeights.get(v.name+v.prev.name);
				System.out.println(v.name + "--" + v.prev.name + " " + w);
			}
			for (Edge e : v.adj) {
				Vertex u = e.dest;
				if (u.scratch > e.weight) {
					u.scratch = (int) e.weight;
					u.prev = v;
					pq.decreaseKey(u.pos, u);
				}
			}
		}

	}

	/**
	 * Sorting of edges takes O(N log N) = O(|E| log |E|)
	 * Disjoint set operation take O(M log N) where M are number
	 * operation performed i.e. M = 2*|V| finds and |E| unions = O(|E|)
	 * ==> O(|E| log |E|) = O(|E| log |V|)
	 */
	public void Kruskal_MST() {

		DisjointSet ds = new DisjointSet(vertexMap.size());
		int i = 0;
		List<Edge> edges_list = new ArrayList<>();
		for (Vertex v : vertexMap.values()) {
			v.scratch = i++;
			ds.find(v.scratch);
			for (Edge e : v.adj) {
				if (!edges_list.contains(e))
					edges_list.add(e);
			}
		}

		Edge[] edges = new Edge[edges_list.size()/2];
		edges = edges_list.toArray(edges);

		QuickSort qs = new QuickSort();
		qs.setComparator(new EdgeComparator());
		qs.sort(edges);

		edges_list = new ArrayList<>();

		for (int j=0; j<edges.length; j++) {

			Edge e = edges[j];
			Vertex v1 = e.source;
			Vertex v2 = e.dest;

			if ( !ds.sameSet(v1.scratch, v2.scratch) ) {
				ds.union( ds.find(v1.scratch), ds.find(v2.scratch) );
				edges_list.add(e);
			}

		}

		System.out.println();

		for (Edge e : edges_list) {
			System.out.println(e.toString());
		}

	}

	public void dijkstra(String sourceName) {

		Vertex v = vertexMap.get(sourceName);

		PriorityQueue<Path> pq = new PriorityQueue<>();
		pq.add(new Path(v, 0));
		v.dist = 0;

		while ( !pq.isEmpty() ) {

			Path p = pq.poll();
			Vertex w = p.dest;
			double vw = p.cost;

			if (vw < 0) {
				System.out.println("Negative edge weights not allowed");
				return;
			}

			for (Edge e : w.adj) {
				Vertex u = e.dest;
				if ( u.dist > vw + e.weight) {
					u.dist = vw + e.weight;
					u.prev = w;
					pq.add(new Path(u, u.dist));
				}
			}

		}

	}

	public void printPath(String destinationName) {
		Vertex v = vertexMap.get(destinationName);
		printPath(v);
		System.out.println(v.name + " " + v.dist);
	}

	private void printPath(Vertex v) {
		if (v.prev != null) {
			printPath(v.prev);
			System.out.println(v.prev.name + " " + 
					v.prev.dist);
		}
	}

	private class Path implements Comparable<Path> {
		Vertex dest;
		double cost;
		public Path(Vertex dest, double cost) {
			this.dest = dest;
			this.cost = cost;
		}
		@Override
		public int compareTo(Path o) {
			return (cost > o.cost) ? 1 : (cost < o.cost) ? -1 : 0;
		}
	}

	public static class Vertex implements Comparable<Vertex> {
		public String name;
		public List<Edge> adj;
		public Vertex prev;
		public double dist;
		public int scratch;
		public Position<Vertex> pos;
		int tarjan_index;
		int tarjan_lowlink;
		public Vertex(String name) {
			this.name = name;
			adj = new ArrayList<>();
			reset();
		}
		private void reset() {
			dist = INFINITY;
			scratch = 0;
			prev = null;
			pos = null;
			tarjan_index = -1;
			tarjan_lowlink = 0;
		}
		@Override
		public String toString() {
			return name + " " + scratch;
		}
		@Override
		public int compareTo(Vertex o) {
			int w1 = scratch;
			int w2 = o.scratch;
			if (w1 > w2) return 1;
			if (w1 < w2) return -1;
			return 0;
		}
		@Override
		public int hashCode() {
			char[] c = name.toCharArray();
			int h = 0;
			for (char ch : c)
				h += (int)ch;
			return h;
		}
		@Override
		public boolean equals(Object o) {
			return ((Vertex)o).name.equals(name);
		}
	}

	class Edge implements Comparable<Edge> {
		Vertex source;
		Vertex dest;
		double weight;
		public Edge(Vertex source, Vertex dest, double weight) {
			this.source = source;
			this.dest = dest;
			this.weight = weight;
		}
		@Override
		public int compareTo(Edge o) {
			String s1 = source.name;
			String d1 = dest.name;

			String s2 = o.source.name;
			String d2 = o.dest.name;

			if (s1.equals(s2) && d1.equals(d2)) return 0;
			if (s1.equals(d2) && s2.equals(d1)) return 0;
			return 1;

		}
		@Override
		public boolean equals(Object o) {
			Edge e = (Edge)o;
			String s1 = source.name;
			String d1 = dest.name;

			String s2 = e.source.name;
			String d2 = e.dest.name;

			if (s1.equals(s2) && d1.equals(d2)) return true;
			if (s1.equals(d2) && s2.equals(d1)) return true;
			return false;
		}
		@Override
		public String toString() {
			return source.name + "-" + dest.name + "(" + weight + ")";
		}
	}

	class EdgeComparator implements Comparator<Edge> {

		@Override
		public int compare(Edge o1, Edge o2) {
			double w1 = o1.weight;
			double w2 = o2.weight;
			if (w1 > w2) return 1;
			if (w1 < w2) return -1;
			return 0;
		}

	}

	public void complement() {
		Map<String, Vertex> hm = new HashMap<>();
		Vertex v_new = null;
		Vertex w_new = null;

		for ( Vertex v : vertexMap.values() ) {
			v_new = hm.get(v.name);
			if (v_new == null) {
				v_new = new Vertex(v.name);
				hm.put(v_new.name, v_new);
			}

			for ( Edge ev : v.adj ) {

				w_new = hm.get(ev.dest.name);
				if (w_new == null) {
					w_new = new Vertex(ev.dest.name);
					w_new.adj.add(new Edge(w_new, v_new, ev.weight));
					hm.put(w_new.name, w_new);
				} else {
					w_new.adj.add(new Edge(w_new, v_new, ev.weight));
				}

			}
		}

		vertexMap.clear();
		vertexMap = hm;

	}

	private int tarjan_index;
	private Stack<Vertex> tarjan_stack;

	public void Tarjan() {

		tarjan_index = 0;
		tarjan_stack = new Stack<>();
		for (Vertex v : vertexMap.values()) {
			if ( v.tarjan_index == -1 )
				tarjan_strong_connect(v.name);
		}

	}

	private void tarjan_strong_connect(String vertexName) {

		Vertex v = vertexMap.get(vertexName);

		v.tarjan_index = tarjan_index;
		v.tarjan_lowlink = tarjan_index;

		tarjan_index++;
		tarjan_stack.push(v);

		for (Edge e : v.adj) {

			Vertex w = e.dest;
			if ( w.tarjan_index == -1 ) {
				tarjan_strong_connect(w.name);
				v.tarjan_lowlink = Math.min(v.tarjan_lowlink, w.tarjan_lowlink);
			} else if (tarjan_stack.contains(w)) {
				v.tarjan_lowlink = Math.min(v.tarjan_lowlink, w.tarjan_index);
			}

		}

		if ( v.tarjan_lowlink == v.tarjan_index ) {
			System.out.println("*** NEW STRONGLY CONNECTED COMPONENT(GRAPH) ***");
			Vertex w = null;
			do {
				w = tarjan_stack.pop();
				System.out.println(w.name);
			} while (w.name != v.name);
		}

	}

	// Color adjacent vertices with different colors
	public boolean isBipartite() {

		clearAll();
		
		Queue<Vertex> q = new LinkedList<>();
		
		Set<Vertex> s1 = new HashSet<>();
		Set<Vertex> s2 = new HashSet<>();

		Vertex v = vertexMap.get(vertexMap.values().iterator().next().name);
		v.scratch = 1;
		q.add(v);

		while ( !q.isEmpty() ) {
			v = q.poll();
			for ( Edge e : v.adj ) {
				Vertex w = e.dest;
				if (w.scratch == 0)
					q.add(w);

				if (v.scratch == 1) {
					w.scratch = 2;
					s2.add(w);
				} else {
					w.scratch = 1;
					s1.add(w);
				}
			}
		}

		for (Vertex vv : vertexMap.values()) {
			for (Edge e : vv.adj)
				if (vv.scratch == e.dest.scratch) {
					System.out.println("Is not bipartite");
					return false;
				}
		}
		
		System.out.print("Set 1: ");
		for (Vertex vv : s1)
			System.out.print(vv.name + " ");
		System.out.println();
		System.out.print("Set 2: ");
		for (Vertex vv : s2)
			System.out.print(vv.name + " ");
		System.out.println();

		System.out.println("Is bipartite");
		return true;

	}
	
	public void printConnectedComponents() {
		Queue<Vertex> q = new LinkedList<>();
		List<List<Vertex>> allComponents = new ArrayList<>();
		for ( Vertex v : vertexMap.values() ) {
			List<Vertex> l = new ArrayList<>();
			if ( v.scratch == 0 ) { // This vertex has not been visited
				v.scratch = 1;
				q.add(v);
				l.add(v);
				while (!q.isEmpty()) {
					Vertex vv = q.poll();
					for (Edge e : vv.adj) {
						if (e.dest.scratch == 0) {
							q.add(e.dest);
							l.add(e.dest);
						}
						e.dest.scratch = 1;
					}
				}
				allComponents.add(l);
			}
		}
		for ( List<Vertex> l : allComponents ) {
			System.out.print("Connected components: ");
			for ( Vertex v : l )
				System.out.print(v.name + " ");
			System.out.println();
		}
	}
	
	public int nrConnectedComponent() {
		int nr = 0;
		Queue<Vertex> q = new LinkedList<>();
		for ( Vertex v : vertexMap.values() ) {
			if ( v.scratch == 0 ) { // This vertex has not been visited
				nr++;
				v.scratch = 1;
				q.add(v);
				while (!q.isEmpty()) {
					Vertex vv = q.poll();
					for (Edge e : vv.adj) {
						if (e.dest.scratch == 0)
							q.add(e.dest);
						e.dest.scratch = 1;
					}
				}
			}
		}
		System.out.println(nr);
		return nr;
	}
	
	public void getAPerfectMatch() {
		
		clearAll();
		
		Vertex v = vertexMap.get(vertexMap.values().iterator().next().name);
		System.out.println(v.name);
		Queue<Vertex> q = new LinkedList<>();
		
		q.add(v);
		
		while (!q.isEmpty()) {
			
			v = q.poll();
			
			for (Edge e : v.adj) {
				Vertex w = e.dest;
				if ( w.prev == null ) {
					q.add(w);
					if (v.prev == null) {
						w.prev = v;
						v.prev = w;
						//System.out.println(v.name + " -- " + w.name);
					}
				}
			}
			
		}
		
		for (Vertex vv : vertexMap.values()) {
			if (vv.prev != null) {
				if ( vv.scratch==0 ) {
					vv.scratch = 1;
					vv.prev.scratch = 1;
					System.out.println(vv.name + " -- " + vv.prev.name);
				}
			}
			else
				System.out.println(vv.name + " is lonely!");
		}
		
	}
	
	// Network Flow Graph
	public void printPath(String startVertex, String finishVertex) {
		
		Vertex s = vertexMap.get(startVertex);
		
		Stack<Vertex> st = new Stack<>();
		
		st.push(s);
		
		while ( !st.isEmpty() ) {
			
			Vertex v = st.pop();
			System.out.print(v.name + " ");
			for (Edge e : v.adj) {
				st.push(e.dest);
			}
			if (v.adj.size()==0) { // This is the destination
				System.out.println();
				System.out.print(s.name + " ");
			}
		}
		System.out.println();
		
	}

	public static void main(String[] args) {

		Graph g = new Graph(true);

		g.addEdge("s", "a", 1);
		g.addEdge("s", "b", 1);
		g.addEdge("s", "c", 1);
		g.addEdge("s", "d", 1);
		
		g.addEdge("a", "t", 1);
		g.addEdge("b", "t", 1);
		g.addEdge("c", "t", 1);
		g.addEdge("d", "t", 1);
		
		g.addEdge("b", "c", 1);
		
		g.DFS("s");

		/*
		g.addEdge("a", "b", 4);
		g.addEdge("b", "c", 6);
		g.addEdge("c", "d", 3);
		g.addEdge("d", "e", 2);
		g.addEdge("e", "f", 4);
		g.addEdge("f", "a", 2);
		g.addEdge("b", "f", 5);
		g.addEdge("c", "f", 1);
		 */

		/*
		g.addEdge("0", "1", 2);
		g.addEdge("1", "4", 10);
		g.addEdge("4", "6", 6);
		g.addEdge("6", "5", 1);
		g.addEdge("5", "2", 5);
		g.addEdge("2", "0", 4);
		g.addEdge("3", "0", 1);
		g.addEdge("3", "1", 3);
		g.addEdge("3", "4", 2);
		g.addEdge("3", "6", 4);
		g.addEdge("3", "5", 8);
		g.addEdge("3", "2", 2);
		 */

		/*
		g.addEdge("A", "B", 24);
		g.addEdge("B", "C", 9);
		g.addEdge("C", "D", 8);
		g.addEdge("D", "E", 28);
		g.addEdge("E", "A", 10);
		g.addEdge("A", "D", 25);
		g.addEdge("E", "B", 35);
		 */

		/*
		g.addEdge("A", "B", 1);
		g.addEdge("B", "C", 1);
		g.addEdge("C", "D", 1);
		g.addEdge("D", "A", 1);
		g.addEdge("X", "X", 1);
		g.addEdge("Y", "Y", 1);

		g.printGraph();
		System.out.println();

		g.Tarjan();
		 */

	}

}










