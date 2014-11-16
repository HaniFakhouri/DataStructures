package com.hani.graph;

import java.util.LinkedList;
import java.util.List;

import com.hani.heap.PairingHeap.Position;

public class Vertex {
	
	/**
	 * The scratch variable is used differently for the various
	 * shortest path algorithms.
	 * 
	 */
	
	public String name;
	public List<Edge> adj;
	public double dist;
	public Vertex prev;
	public int scratch;
	public Position pos;
	
	public Vertex(String name) {
		this.name = name;
		adj = new LinkedList<>();
		reset();
	}
	
	public void reset() {
		dist = Graph.INFINITY;
		prev = null;
		scratch = 0;
		pos = null;
	}

}
