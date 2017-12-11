package com.plagiarism.interfaces;

import java.util.List;

import com.plagiarism.datastructures.Edge;
import com.plagiarism.datastructures.Vertex;

// BipartiteMatching is an object of any class that implements BipartiteMatching interface

// Interpretation: BipartiteMatching represents any class that implements maximum weighted 
// matching in bipartite graph.
public interface BipartiteMatching {

	// given two sets of nodes which are bipartite and the edges connecting them
	// returns set of edges which such that no two edges share the common end node
	// and sum of weights of all these edges is maximum
	public List<Edge> maxWeightMatching(List<Vertex> A, List<Vertex> B, List<Edge> edges, int sizeA, int sizeB);
}
