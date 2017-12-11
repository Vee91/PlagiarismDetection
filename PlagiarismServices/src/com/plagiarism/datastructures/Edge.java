package com.plagiarism.datastructures;

// this class represents a edge in graph
public class Edge {

	private Vertex source = null;
	private Vertex target = null;

	private int cost = 0;
	
	// The similarity between the two subtrees that are rooted on source and target.
	private float percent = 0.0f;

	public Edge(Vertex source, Vertex target) {
		this.source = source;
		this.target = target;
	}

	public Edge(Vertex source, Vertex target, int cost) {
		this.source = source;
		this.target = target;
		this.cost = cost;
	}

	public Vertex getSource() {
		return source;
	}

	public Vertex getTarget() {
		return target;
	}

	public void setCost(int cost) {
		this.cost = cost;
	}

	public int getCost() {
		return cost;
	}

	public float getPercent() {
		return percent;
	}
	
	public void setPercent(float percent) {
		this.percent = percent;
	}

	// Reverse the edge. Used in the Max Bipartite Matching algorithm.
	public void reverseEdge() {
		if (source.removeEdge(this)) {
			target.insertEdge(this);

			Vertex tmp = source;
			source = target;
			target = tmp;
		}

	}

}