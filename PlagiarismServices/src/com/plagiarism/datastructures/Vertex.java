package com.plagiarism.datastructures;

import java.util.ArrayList;

// this class represents a vertex of any graph. It uses adjacency list representation
public class Vertex {

	// Max size of the adjacency list of this vertex.
	int size = 0;

	ArrayList<Edge> adjList = null;

	public Vertex(int size) {
		this.size = size;
	}

	// Make a new edge that has this vertex as source and another vertex
	// as target.
	public Edge insertEdge(Vertex target) {
		if (adjList == null) {
			adjList = new ArrayList<Edge>(size);
		}

		Edge e = new Edge(this, target);
		adjList.add(e);

		return e;
	}

	// Insert a new edge into the adjacency list of this vertex.
	public Edge insertEdge(Edge e) {
		if (adjList == null) {
			adjList = new ArrayList<Edge>(size);
		}

		adjList.add(e);
		return e;
	}

	// Remove an edge from the adjacency list of this vertex.
	public boolean removeEdge(Edge e) {
		if (adjList != null) {
			return adjList.remove(e);
		}

		return false;
	}

	// Return adjacency list.
	public ArrayList<Edge> getAdjList() {
		return adjList;
	}

	// Return the first element in the adjacency list.
	public Edge getFirstAdjEdge() {
		if (adjList != null && adjList.size() != 0) {
			return adjList.get(0);
		}

		return null;
	}

}
