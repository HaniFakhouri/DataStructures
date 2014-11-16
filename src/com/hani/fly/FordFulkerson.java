package com.hani.fly;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Set;
import java.util.Stack;

import com.hani.hashtable.HashSet;
import com.hani.problems.Graph.Vertex;

public class FordFulkerson {

	public static class Graph {

		private HashMap<String, Vertex> vertexMap;

		public Graph() {
			vertexMap = new HashMap<>();
		}

		public void addEdge(String src, String dest, int cost) {
			Vertex src_v = getVertex(src);
			Vertex dst_v = getVertex(dest);
			Edge e = new Edge(src_v, dst_v, cost);
			src_v.adj.add(e);
			dst_v.adjEntering.add(e);
		}

		private Vertex getVertex(String vertexName) {
			Vertex v = vertexMap.get(vertexName);
			if (v==null) {
				v = new Vertex(vertexName);
				vertexMap.put(vertexName, v);
			}
			return v;
		}

		private void removeVertex(String vertexName) {
			Vertex v = getVertex(vertexName);
			List<String> edgeNames = new ArrayList<>();
			for (Edge e : v.adj)
				edgeNames.add(e.dest.name);
			for (String e : edgeNames)
				removeEdge(v.name, e);
			vertexMap.remove(vertexName);
		}

		private void clearAll() {
			for (Vertex v : vertexMap.values()) {
				v.clear();
				for (Edge e : v.adj)
					e.clear();
				for (Edge e : v.adjEntering)
					e.clear();
			}
		}

		private void unvisitAll() {
			for (Vertex v : vertexMap.values())
				v.visited = false;
		}

		public void printGraph() {

			for (Vertex v : vertexMap.values()) {

				System.out.println("Leaving " + v.name + ":");
				for ( Edge e : v.adj )
					System.out.println(e);

				System.out.println("Entering " + v.name + ":");
				for ( Edge e : v.adjEntering )
					System.out.println(e);
				System.out.println();

			}

		}

		public void DisjoinPaths_directed() {
			int max_flow = MaxFlow_FordFulkerson();

			System.out.println(max_flow + " Edge Disjoint Paths:");

			unvisitAll();

			String source = "s";
			String sink = "t";

			Vertex source_v = getVertex(source);
			Vertex sink_v = getVertex(sink);

			for ( Edge e : source_v.adj ) {

				if ( e.flow == 1 ) {

					System.out.print(source + "->");

					Vertex w = e.dest;
					Stack<Vertex> s = new Stack<>();
					s.push(w);
					while ( !s.isEmpty() ) {

						Vertex u = s.pop();
						u.visited = true;
						if (u.name.equals(sink))
							System.out.print(u.name);
						else
							System.out.print(u.name + "->");
						for (Edge ue : u.adj) {
							if (ue.flow == 1 && !ue.dest.visited) {
								ue.dest.visited = true;
								s.push(ue.dest);
							}
						}

					}
					sink_v.visited = false;
					System.out.println();					
				}

			}
			
			clearAll();
			
		}

		public void MaxBipartiteMatching() {

			if (!this.isBipartite()) {
				System.out.println("This is not a bipartite graph");
				return;
			}

			Vertex source = new Vertex("s");
			Vertex sink = new Vertex("t");

			// Divide the graph into two sets X and Y
			assign_colors();

			// Add edges from source node to X with cost 1
			// Add edges from Y to sink node with cost 1
			List<String> set1 = new ArrayList<>();
			List<String> set2 = new ArrayList<>();
			for (Vertex v : vertexMap.values())
				if (v.color == 1)
					set1.add(v.name);
				else
					set2.add(v.name);

			for (String s : set1)
				addEdge(source.name, s, 1);
			for (String s : set2)
				addEdge(s, sink.name, 1);

			int maxMatching = MaxFlow_FordFulkerson();

			// Print the max matching
			/*
			Vertex s = getVertex(source.name);
			for (Edge es : s.adj) {
				if (es.flow == 1) {
					Vertex u = es.dest;
					for (Edge eu : u.adj)
						if (eu.flow == 1)
							System.out.println(u.name + " <---> " + eu.dest.name);

				}
			}
			*/
			
			

			removeVertex(source.name);
			removeVertex(sink.name);
			clearAll();

			System.out.println("Max Matching = " + maxMatching);

		}

		private void assign_colors() {

			// All vertices might not be connected. Iterate through all vertices
			for (Vertex v : vertexMap.values()) {
				if (!v.visited) {
					Queue<Vertex> q = new LinkedList<>();
					v.color = 1;
					v.visited = true;
					q.add(v);

					while ( !q.isEmpty() ) {
						v = q.poll();
						for (Edge e : v.adj) {
							Vertex w = e.dest;
							if (!w.visited) {
								q.add(w);
							}
							w.visited = true;
							if (v.color == 1)
								w.color = 2;
							else
								w.color = 1;
						}
					}
				}
			}

		}

		public boolean isBipartite() {

			boolean isBipartite = true;

			assign_colors();

			for (Vertex vv : vertexMap.values())
				for (Edge e: vv.adj)
					if (vv.color == e.dest.color) {
						isBipartite = false;
						break;
					}

			// Clear colors
			clearAll();

			return isBipartite;
		}

		private int getSmallerPowerOfTwo(int max) {
			if (max <= 8)
				return max;
			int i = 2;
			while ( (1<<i) < max)
				i++;
			return (1<<(i-1));
		}

		public int MaxFlow_FordFulkerson() {

			clearAll();

			Graph residual = this.clone();

			String source = "s";
			String sink = "t";

			// The delta variable is used to optimize the loop that
			// searches for augmented paths, by minimizing the number
			// of loop iterations
			// In the original Ford-Fulkerson algorithm the delta
			// variable is 0
			int delta = Integer.MIN_VALUE;
			Vertex src = residual.getVertex(source);
			for ( Edge e : src.adj )
				if (e.cost > delta)
					delta = e.cost;

			delta = getSmallerPowerOfTwo(delta);
			//delta = 0;

			while (true) {

				// Find an augmented path from source s to sink t
				int bottleneck = augmentPath(residual, source, sink, delta);
				if (bottleneck == -1)
					break;

				// Updated the residual graph
				Vertex t = residual.getVertex(sink);
				while (t!=null) {
					if (t.prev != null)
						residual.addResidualEdge(t.name, t.prev.name, bottleneck, this);
					t = t.prev;
				}

				delta = delta / 2;

			}
			// Clear temporary variable
			residual.clearAll();

			//printGraph();

			int max_flow = 0;
			Vertex sink_v = getVertex(sink);
			for (Edge eIn : sink_v.adjEntering)
				max_flow += eIn.flow;

			//clearAll();

			return max_flow;

		}

		/**
		 * Find an augmented path from sourceVertex to sinkVertex
		 * In the original Ford-Fulkerson algorithm the delta
		 * variable is 0 
		 * return -1 no such path is found
		 */
		private int augmentPath(Graph residual, String sourceVertex, String sinkVeretex, int delta) {
			// Clear temporary variable
			residual.clearAll();
			Vertex v = residual.getVertex(sourceVertex);
			Stack<Vertex> s = new Stack<>();
			s.push(v);

			int bottleneck = Integer.MAX_VALUE;

			boolean pathFound = false;

			while (!s.isEmpty()) {
				v = s.pop();
				v.visited = true;
				// If sink is reached we have found a path from s to t
				if (v.name.equals(sinkVeretex)) {
					pathFound = true;
					break;
				}
				for ( Edge e : v.adj ) {
					if (e.cost >= delta && e.cost < bottleneck)
						bottleneck = e.cost;
					if ( !e.dest.visited ) {
						// Add a parent edge
						// This will be used to determine the path
						// backwards from t to s
						e.dest.prev = v;
						s.push(e.dest);
					}
				}
			}
			if (!pathFound)
				bottleneck = -1;
			return bottleneck;
		}

		/**
		 * Add/Update edge in the residual graph and update the flow in the original graph
		 * u---flow--->v
		 * E.g. update flow in to 2, then a backward edge is added to
		 * u---3--->v
		 * ==>
		 * u---1--->v
		 * u<---2---v
		 * If the flow is updated to 3:
		 * u<---3---v
		 */
		public void addResidualEdge(String src, String dest, int flow, Graph originalGraph) {
			Vertex src_v = getVertex(src);
			Vertex dst_v = getVertex(dest);

			for ( int i=0; i<src_v.adjEntering.size(); i++ ) {
				Edge eIn = src_v.adjEntering.get(i);
				if ( eIn.src.name.equals(dest) ) {
					int edgeCost = eIn.cost;
					if ( edgeCost == flow ) {
						removeEdge(dest, src);
						break;
					} else {
						eIn.cost = eIn.cost - flow;
						break;
					}
				}
			}

			boolean found = false;
			for ( int i=0; i<src_v.adj.size(); i++ ) {
				Edge eOut = src_v.adj.get(i);
				if ( eOut.dest.name.equals(dest) ) {
					eOut.cost += flow;
					found = true;

					originalGraph.updateEdgeFlow(dest, src, eOut.cost);

					break;
				}
			}

			if (!found) {
				Edge e = new Edge(src_v, dst_v, flow);
				src_v.adj.add(e);
				dst_v.adjEntering.add(e);
				originalGraph.updateEdgeFlow(dest, src, flow);
			}

		}

		private void updateEdgeFlow(String src, String dest, int new_flow) {
			Vertex src_v = getVertex(src);
			for ( Edge e : src_v.adj ) {
				if (e.dest.name.equals(dest)) {
					e.flow = new_flow;
					break;
				}
			}
		}

		private void removeEdge(String src, String dst) {

			Vertex src_v = getVertex(src);
			Vertex dst_v = getVertex(dst);

			for (int i=0; i<src_v.adj.size(); i++) {
				Edge eOut = src_v.adj.get(i);
				if ( eOut.dest.name.equals(dst) ) {
					src_v.adj.remove(i);
					break;
				}
			}

			for (int i=0; i<dst_v.adjEntering.size(); i++) {
				Edge eIn = dst_v.adjEntering.get(i);
				if ( eIn.src.name.equals(src) ) {
					dst_v.adjEntering.remove(i);
					break;
				}
			}


		}

		public Graph clone() {
			Graph clone = new Graph();
			for (Vertex v : vertexMap.values()) {
				for (Edge e : v.adj)
					clone.addEdge(e.src.name, e.dest.name, e.cost);
			}
			return clone;
		}

		public void BFS(String startVertex) {
			Vertex v = vertexMap.get(startVertex);
			Queue<Vertex> q = new LinkedList<>();
			q.add(v);
			while (!q.isEmpty()) {
				v = q.poll();
				v.visited = true;
				System.out.print(v.name + " ");
				for (Edge e : v.adj) {
					if (!e.dest.visited) {
						e.dest.visited = true;
						q.add(e.dest);
					}
				}
			}
			System.out.println();
		}

		public void DFS(String startVertex) {
			Vertex v = vertexMap.get(startVertex);
			Stack<Vertex> s = new Stack<>();
			s.add(v);
			while (!s.isEmpty()) {
				v = s.pop();
				v.visited = true;
				System.out.print(v.name + " ");
				for (Edge e : v.adj) {
					if (!e.dest.visited) {
						e.dest.visited = true;
						s.add(e.dest);
					}
				}
				System.out.println();
			}

		}

	}

	private static class Vertex {
		public String name;
		public Vertex prev;
		public List<Edge> adj;
		public List<Edge> adjEntering;
		public boolean visited;
		public int color;
		public Vertex(String name) {
			this.name = name;
			adj = new ArrayList<>();
			adjEntering = new ArrayList<>();
			clear();
		}
		public void clear() {
			visited = false;
			prev = null;
			color = 0;
		}
	}

	public static class Edge {
		public Vertex src, dest;
		public int cost, flow;
		public Edge(Vertex src, Vertex dest, int cost) {
			this.src = src;
			this.dest = dest;
			this.cost = cost;
			clear();
		}
		public void clear() {
			flow = 0;
		}
		@Override
		public String toString() {
			return src.name + " -- " + flow + "/" + cost + "--> " + dest.name;
		}
	}

	public static void main(String[] args) {

		Graph g = new Graph();

		/*
		g.addEdge("s", "1", 1);
		g.addEdge("s", "2", 1);
		g.addEdge("1", "4", 1);
		g.addEdge("1", "3", 1);
		g.addEdge("2", "3", 1);
		g.addEdge("3", "6", 1);
		g.addEdge("4", "5", 1);
		g.addEdge("5", "3", 1);
		g.addEdge("5", "7", 1);
		g.addEdge("6", "2", 1);
		g.addEdge("6", "7", 1);
		g.addEdge("6", "8", 1);
		g.addEdge("7", "8", 1);
		g.addEdge("6", "t", 1);
		g.addEdge("7", "t", 1);
		g.addEdge("8", "t", 1);
		*/
		
		/*
		g.addEdge("s", "1", 1);
		g.addEdge("s", "2", 1);
		g.addEdge("s", "3", 1);
		g.addEdge("1", "3", 1);
		g.addEdge("1", "4", 1);
		g.addEdge("2", "1", 1);
		g.addEdge("3", "4", 1);
		g.addEdge("3", "5", 1);
		g.addEdge("4", "2", 1);
		g.addEdge("2", "t", 1);
		g.addEdge("4", "t", 1);
		g.addEdge("5", "t", 1);
		*/

		//g.DisjoinPaths_directed();

		/*
		g.addEdge("1", "8", 1);
		g.addEdge("1", "9", 1);
		g.addEdge("3", "7", 1);
		g.addEdge("3", "10", 1);
		g.addEdge("4", "9", 1);
		g.addEdge("5", "9", 1);
		g.addEdge("5", "10", 1);
		g.addEdge("6", "12", 1);
		*/
		
		g.addEdge("a", "d", 1);
		g.addEdge("a", "e", 1);
		g.addEdge("b", "e", 1);
		g.addEdge("c", "f", 1);

		g.MaxBipartiteMatching();
		//System.out.println(g.MaxFlow_FordFulkerson());

		/*
		g.addEdge("s", "a", 1);
		g.addEdge("s", "c", 1);
		g.addEdge("a", "b", 1);
		g.addEdge("c", "d", 1);
		g.addEdge("b", "t", 1);
		g.addEdge("d", "t", 1);
		 */

		/*
		g.addEdge("s", "a", 1);
		g.addEdge("s", "c", 1);
		g.addEdge("s", "f", 1);

		g.addEdge("a", "b", 1);
		g.addEdge("a", "d", 1);
		g.addEdge("c", "b", 1);
		g.addEdge("c", "d", 1);
		g.addEdge("c", "e", 1);
		g.addEdge("f", "e", 1);

		g.addEdge("b", "t", 1);
		g.addEdge("d", "t", 1);
		g.addEdge("e", "t", 1);
		 */

		/*
		g.addEdge("s", "u", 100);
		g.addEdge("s", "v", 100);
		g.addEdge("u", "v", 1);
		g.addEdge("u", "t", 100);
		g.addEdge("v", "t", 100);
		 */

		/*
		g.addEdge("s", "o", 3);
		g.addEdge("s", "p", 3);
		g.addEdge("o", "p", 2);
		g.addEdge("o", "q", 3);
		g.addEdge("p", "r", 2);
		g.addEdge("q", "r", 4);
		g.addEdge("q", "t", 2);
		g.addEdge("r", "t", 3);
		 */

		/*
		g.addEdge("s", "u", 20);
		g.addEdge("s", "v", 10);
		g.addEdge("u", "v", 30);
		g.addEdge("u", "t", 10);
		g.addEdge("v", "t", 20);
		 */

	}

}






