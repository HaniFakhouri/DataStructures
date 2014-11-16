package com.hani.graph;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Stack;

import com.hani.heap.PairingHeap;

/**
 * For most graphs there is likely at most one edge from any vertex v to any other vertex w.
 * Consequently, |E|<=|V|^2. When most edges are present |E|=O(|V|^2), i.e. a dense graph.
 * In most applications however the graph is sparse so |E|=O(|V|) or a little bit more.
 * The graph is implemented as a directed graph
 * 
 * @author Hani
 *
 */

public class Graph {

	public static final double INFINITY = Double.MAX_VALUE;

	private Map<String, Vertex> vertexMap;

	public Graph() {
		vertexMap = new HashMap<>();
	}

	public void clearAll() {
		for (Vertex v : vertexMap.values())
			v.reset();
	}

	public void addEdge(String sourceName, String destName, double weight) {
		Vertex u = getVertex(sourceName);
		Vertex v = getVertex(destName);
		u.adj.add(new Edge(v, weight));
	}

	public void printPath(String destName) {
		Vertex v = vertexMap.get(destName);
		if (v.dist == INFINITY) {
			System.out.println(destName + " is unreachable");
		} else {
			System.out.print(" Cost: " + v.dist + " ");
			printPath(v);
			System.out.println();
		}
	}

	/**
	 * Finds the shortest path from a source vertex to all vertices.
	 * All the edges are unweighted, which is equivalent
	 * to all edges having weight 1. This is done using
	 * breadth first search on the graph.
	 * Running time O(|E|)
	 * @param startName name of the start vertex from which
	 * shortest path begins
	 */
	public void unweighted(String startName) {

		clearAll();

		Vertex start = vertexMap.get(startName);
		if (start == null) {
			System.out.println("Start vertex not found");
			return;
		}

		Queue<Vertex> q = new LinkedList<>();
		start.dist =  0;
		q.add(start);

		while (!q.isEmpty()) {

			Vertex v = q.remove();

			for (Edge e : v.adj) {

				Vertex u = e.dest;

				if (u.dist == INFINITY) {
					u.dist = v.dist + 1;
					u.prev = v;
					q.add(u);
				}

			}

		}

	}

	/**
	 * Dijkstras algorithm.
	 * Finds shortest path from a source vertex to all vertices. 
	 * The weight of the edges must be non-negative.
	 * The scratch variable is used here to mark a vertex as
	 * processed.
	 * Running time O( |E| log|V|)
	 * @param startName name of the start vertex from which
	 * shortest path begins
	 */
	public void dijkstra(String startName) {

		clearAll();

		Vertex start = vertexMap.get(startName);
		if (start == null) {
			System.out.println("Start vertex not found");
			return;
		}

		PriorityQueue<Path> pq = new PriorityQueue<>();
		pq.add(new Path(start, 0));
		start.dist = 0;

		int nodesSeen = 0;
		while (!pq.isEmpty() && nodesSeen < vertexMap.size()) {

			Path vpath = pq.remove();
			Vertex v = vpath.dest;
			if (v.scratch != 0) // already processed
				continue;
			
			v.scratch = 1;
			nodesSeen++;

			for (Edge e : v.adj) {

				Vertex w = e.dest;
				double cvw = e.weight;
				if (cvw < 0) {
					System.out.println("Dijkstra cannot process negative weights");
					break;
				}

				if (w.dist > cvw + v.dist) {
					w.dist = cvw + v.dist;
					w.prev = v;
					pq.add(new Path(w, w.dist));
				}

			}

		}

	}
	
	public void dijkstra_DecKey(String startName) {

		clearAll();

		Vertex start = vertexMap.get(startName);
		if (start == null) {
			System.out.println("Start vertex not found");
			return;
		}

		PairingHeap<Path> pq = new PairingHeap<>();
		start.pos = pq.insert(new Path(start, 0)); 
		start.dist = 0;

		while (!pq.isEmpty()) {

			Path vpath = pq.deleteMin();
			Vertex v = vpath.dest;

			for (Edge e : v.adj) {

				Vertex w = e.dest;
				double cvw = e.weight;
				if (cvw < 0) {
					System.out.println("Dijkstra cannot process negative weights");
					break;
				}

				if (w.dist > cvw + v.dist) {
					w.dist = cvw + v.dist;
					w.prev = v;
					
					Path newVal = new Path(w, w.dist);
					if (w.pos == null)
						w.pos = pq.insert(newVal);
					else
						pq.decreaseKey(w.pos, newVal);
					
				}

			}

		}

	}

	/**
	 * Bellman-Ford algorithm.
	 * Finds shortest path from a source vertex to all vertices.
	 * The weights of the edges can be negative.
	 * Also the algorithm determines if there are negative cycles
	 * in the shortest path and in that case terminates.
	 * The scratch variable is used here to count number of times
	 * a vertex has been dequeued from the queue. An odd scratch
	 * means that a vertex is in the queue and scratch/2 tells
	 * how many times it has left the queue. If a vertex is already
	 * in the queue it is not added again. 
	 * Running time O(|E|.|V|)
	 * @param startName name of the start vertex from which
	 * shortest path begins
	 */
	public void bellmanFord(String startName) {

		clearAll();

		Vertex start = vertexMap.get(startName);
		if (start == null) {
			try {
				throw new Exception("Start vertex not found");
			} catch (Exception e) {
				e.printStackTrace();
			}
			return;
		}

		Queue<Vertex> q = new LinkedList<>();
		q.add(start);
		start.dist = 0;
		start.scratch++;

		while (!q.isEmpty()) {

			Vertex v = q.remove();
			if (v.scratch++ > 2*vertexMap.size()) {
				try {
					throw new Exception("Negative cycle detected");
				} catch (Exception e) {
					e.printStackTrace();
				}
				return;
			}

			for (Edge e : v.adj) {

				Vertex w = e.dest;
				double cvw = e.weight;

				if (w.dist > cvw + v.dist) {
					w.dist = cvw + v.dist;
					w.prev = v;
					if (w.scratch++ % 2 == 0)
						q.add(w);
					else
						w.scratch--;
				}

			}

		}

	}

	/**
	 * Finds shortest path from a source vertex to all vertices.
	 * Sorts the vertices according to their topological order
	 * and then using the produced topological order finds
	 * the shortest path.
	 * As a topological order must be present the algorithm
	 * terminates if a cycle is detected.
	 * Note that this algorithm uses a for loop when calculating
	 * the shortest path, unlike the above other algorithms
	 * which use a while loop(to determine if the queue has
	 * become empty).
	 * The scratch variable is used here to count number of incoming
	 * edges to a vertex. Vertices with zero incoming edges are added
	 * to the queue.
	 * Running time O(|E|)
	 * @param startName name of the start vertex from which
	 * shortest path begins
	 */
	public void topological(String startName) {

		clearAll();

		Vertex start = vertexMap.get(startName);
		if (start == null) {
			try {
				throw new Exception("Start vertex not found");
			} catch (Exception e) {
				e.printStackTrace();
			}
			return;
		}
		
		Queue<Vertex> q = new LinkedList<>();
		start.dist = 0;
		
		/**
		 * Build the topological order of the vertices
		 */
		
		// Set number of incoming edges(indegrees) of each vertex
		for (Vertex v : vertexMap.values())
			for (Edge e : v.adj)
				e.dest.scratch++;
		
		// Add vertices with 0 incoming edges
		for (Vertex v : vertexMap.values())
			if (v.scratch == 0)
				q.add(v);
		
		/****************************************************/
		
		int iterations;
		for (iterations=0; !q.isEmpty(); iterations++) {
			
			Vertex v = q.remove();
			
			for (Edge e : v.adj) {
				Vertex w = e.dest;
				double cvw = e.weight;
				
				if (--w.scratch == 0)
					q.add(w);
				
				if (v.dist == INFINITY)
					continue;
				
				if ( w.dist > v.dist + cvw ) {
					w.dist = v.dist + cvw;
					w.prev = v;
				}
				
			}
			
		}
		
		if (iterations != vertexMap.size()) {
			try {
				throw new Exception("Graph has a cycle!");
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		}

	}

	private Vertex getVertex(String vertexName) {
		Vertex v = vertexMap.get(vertexName);
		if (v==null) {
			v = new Vertex(vertexName);
			vertexMap.put(vertexName, v);
		}
		return v;
	}

	private void printPath(Vertex dest) {
		if (dest.prev != null) {
			printPath(dest.prev);
			System.out.print(" to ");
		}
		System.out.println(dest.name);
	}

	public void printNeighbors(String vertexName) {
		Vertex v = vertexMap.get(vertexName);
		if (v==null) {
			System.out.println(vertexName + " was not found");
			return;
		}
		for (Edge e : v.adj) {
			System.out.println(e.dest.name + " " + e.weight);
		}
	}

	public void BFS(String findVertex) {
		Vertex start = vertexMap.get("v2");
		if (start==null) {
			System.out.println(start + " was not found");
			return;
		}

		clearAll();

		Queue<Vertex> q = new LinkedList<>();
		q.add(start);
		System.out.print(start.name + " ");

		while (!q.isEmpty()) {

			Vertex v = q.poll();

			if (v.name.equals(findVertex)) {
				System.out.println("Vertex found");
				return;
			}

			v.scratch = 1;

			for (Edge e : v.adj) {
				Vertex w = e.dest;
				if (w.scratch == 0) {
					System.out.print(w.name + " ");
					w.scratch = 1;
					q.add(w);
				}
			}

		}

		clearAll();

	}

	public void DFS(String startVertex) {
		Vertex start = vertexMap.get(startVertex);
		if (start==null) {
			System.out.println(start + " was not found");
			return;
		}

		clearAll();

		Stack<Vertex> s = new Stack<>();
		s.push(start);

		while (!s.isEmpty()) {

			Vertex v = s.pop();

			if (v.scratch == 0) {
				v.scratch = 1;
				System.out.print(v.name + " ");
				for (Edge e : v.adj) {
					s.push(e.dest);
				}
			}

		}


	}

	private class Path implements Comparable<Path> {

		public Vertex dest;
		public double cost;

		public Path(Vertex dest, double cost) {
			this.dest = dest;
			this.cost = cost;
		}

		@Override
		public int compareTo(Path o) {
			double otherCost = o.cost;
			return cost < otherCost ? -1 : cost > otherCost ? 1 : 0;
		}
		
		@Override
		public String toString() {
			return dest.name + " " + cost;
		}

	}

	public static void main(String[] args) {

		Graph g = new Graph();

		g.addEdge("v2", "v0", 4);
		g.addEdge("v0", "v1", 2);
		g.addEdge("v1", "v4", 10);
		g.addEdge("v4", "v6", 6);
		g.addEdge("v6", "v5", 1);
		g.addEdge("v2", "v5", 5);
		g.addEdge("v0", "v3", 1);
		g.addEdge("v3", "v2", 2);
		g.addEdge("v3", "v5", 8);
		g.addEdge("v3", "v6", 4);
		g.addEdge("v3", "v4", 2);
		g.addEdge("v1", "v3", 3);
		
		/*
		g.addEdge("A", "B", 1);
		g.addEdge("B", "C", 2);
		g.addEdge("B", "D", 1);
		g.addEdge("D", "C", 0);
		g.addEdge("C", "E", 1);
		g.addEdge("D", "E", 1);
		*/

		g.dijkstra_DecKey("v0");
		g.printPath("v6");
		
	}

}









