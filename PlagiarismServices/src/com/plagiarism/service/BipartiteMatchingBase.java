package com.plagiarism.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Stack;

import org.springframework.stereotype.Component;

import com.plagiarism.datastructures.Edge;
import com.plagiarism.datastructures.FibHeap;
import com.plagiarism.datastructures.Vertex;
import com.plagiarism.interfaces.BipartiteMatching;

import java.util.ArrayList;

// base implementation for BipartiteMatching algorithm. This implementation closely follows
// algorithm provided in "LEDA: A platform for combinatorial and geometric computing"
// pages 413-442. It is also called Hungarian algorithm

public class BipartiteMatchingBase implements BipartiteMatching {

	// given two sets of nodes which are bipartite and the edges connecting them
	// returns set of edges which such that no two edges share the common end node
	// and sum of weights of all these edges is maximum
	public List<Edge> maxWeightMatching(List<Vertex> A, List<Vertex> B, List<Edge> edges, int sizeA, int sizeB) {

		// Matching result list
		List<Edge> matchingResult = new ArrayList<Edge>();

		if (A.size() == 0 || B.size() == 0 || edges.size() == 0)
			return matchingResult;

		// Every vertex is either free or matched(true or false)
		Map<Vertex, Boolean> free = new HashMap<Vertex, Boolean>(sizeA + sizeB);

		// predecessor edge of a vertex and distance to a vertex
		Map<Vertex, Edge> predecessor = new HashMap<Vertex, Edge>(sizeA + sizeB);
		Map<Vertex, Integer> distance = new HashMap<Vertex, Integer>(sizeA + sizeB);

		// vertex label or potential from the book(pot)
		Map<Vertex, Integer> label = new HashMap<Vertex, Integer>(sizeA + sizeB);

		// Priority Queue based on Fibonacci Node
		FibHeap PQ = new FibHeap(sizeA + sizeB);

		initialize(A, B, free, label, predecessor, distance);
		naiveHeuristic(A, edges, label);

		// Augment each vertex in A
		for (Vertex a : A) {
			if (free.get(a))
				augment(a, distance, predecessor, label, PQ, free);
		}

		// Fetch the matching result
		for (Vertex b : B) {
			if (b.getAdjList() != null) {
				for (Edge e : b.getAdjList())
					matchingResult.add(e);
			}
		}

		for (Edge e : matchingResult)
			e.reverseEdge();

		return matchingResult;
	}

	// @Author: Guiheng Zhou
	// @Date : 11/20/2017
	// naiveHeuristic : Initialize the data structures used in bipartite matching.
	private void initialize(List<Vertex> A, List<Vertex> B, Map<Vertex, Boolean> free, Map<Vertex, Integer> label,
			Map<Vertex, Edge> predecessor, Map<Vertex, Integer> distance) {
		// Set the potential of all the vertices as ZERO
		for (Vertex a : A)
			label.put(a, 0);
		for (Vertex b : B)
			label.put(b, 0);

		// Check that all edges are directed from A to B
		for (Vertex b : B)
			assert b.getAdjList() == null;

		// Mark all the vertices as free
		for (Vertex a : A)
			free.put(a, true);
		for (Vertex b : B)
			free.put(b, true);

		// Set the predecessors of all the edges as null
		for (Vertex a : A)
			predecessor.put(a, null);
		for (Vertex b : B)
			predecessor.put(b, null);

		// Set the distance to all the vertices as zero
		for (Vertex a : A)
			distance.put(a, 0);
		for (Vertex b : B)
			distance.put(b, 0);
	}

	// @Author: Guiheng Zhou
	// @Date : 11/20/2017
	// naiveHeuristic : Find the maximum cost of all the edges and initialize the
	// potential of vertices in A as the
	// maximum cost.
	private void naiveHeuristic(List<Vertex> A, List<Edge> edges, Map<Vertex, Integer> label) {

		int maxCost = 0;

		// Find the maximum cost of all the edges
		for (Edge e : edges) {
			if (e.getCost() > maxCost)
				maxCost = e.getCost();
		}

		// Initialize the potential function as A
		for (Vertex a : A)
			label.put(a, maxCost);
	}

	// @Author: Vinay Nadagoud
	// @Date : 11/15/2017
	// initialize : method to initialize augmentation in the bipartite matching.
	private void augmentInitialization(Vertex a, Map<Vertex, Integer> distance, Map<Vertex, Edge> predecessor,
			Map<Vertex, Integer> label, FibHeap<Vertex> PQ, Stack<Vertex> RB) {
		if (a == null) {
			return;
		}
		// distance of a is set to 0
		distance.put(a, 0);
		// Relax all edges out of a
		relaxEdges(a, distance, predecessor, label, RB, PQ);
	}

	// @Author: Vinay Nadagoud
	// @Date : 11/15/2017
	// augment : method calculates shortest paths starting from a
	private void augment(Vertex a, Map<Vertex, Integer> distance, Map<Vertex, Edge> predecessor,
			Map<Vertex, Integer> label, FibHeap<Vertex> PQ, Map<Vertex, Boolean> free) {

		// Initially minA = Potential(A), bestNodeInA = a, PQ contains all neighbors of
		// a
		int minA = label.get(a);
		int delta;
		Vertex bestNodeInA = a;

		// Stack RA and RB collects all nodes in v (in A and B respectively) that are
		// added to PQ.
		Stack<Vertex> RA = new Stack<Vertex>();
		RA.push(a);
		Stack<Vertex> RB = new Stack<Vertex>();

		// Augment Initialization
		augmentInitialization(a, distance, predecessor, label, PQ, RB);

		// break as soon as an augmenting path is found
		while (true) {
			// select from PQ node b with minimal distance db
			Vertex b = null;
			int db = Integer.MIN_VALUE;
			// delete node with smallest distance from PQ and assign to b
			if (!PQ.empty()) {
				b = PQ.extractMin();
				db = distance.get(b);
			}

			// Distinguish three cases
			// case 1: b does not exist (PQ empty or db >= minA)
			if (b == null || db >= minA) {
				delta = minA;
				// augment by path to best node in A : Reverses direction of all
				// edges on the path from a to bestNodeInA
				augmentPathTo(bestNodeInA, predecessor);
				// below order is important
				// a is matched
				free.put(a, false);
				// bestNodeInA is unmatched
				free.put(bestNodeInA, true);
				break;
			}

			else {
				// case 2: b exists, b is free and db < minA
				if (free.get(b)) {
					delta = db;
					// augment by path to b
					augmentPathTo(b, predecessor);
					// declare a and b matched
					free.put(a, false);
					free.put(b, false);
					break;
				}
				// case 3 : b exists, b is matched, db < minA
				else {
					// continue shortest path computation
					Edge e = b.getFirstAdjEdge();
					Vertex a1 = e.getTarget();
					predecessor.put(a1, e);
					RA.push(a1);
					distance.put(a1, db);
					if (db + label.get(a1) < minA) {
						// As db + potential(a1) < minA update minA and bestNodeInA
						bestNodeInA = a1;
						minA = db + label.get(a1);
					}
					// Relax all edges out of a1
					relaxEdges(a1, distance, predecessor, label, RB, PQ);
				}
			}
		}

		// potential update and re-initialization
		potentialUpdateAndReinitialisation(distance, predecessor, label, PQ, RB, RA, delta);
		return;

	}

	// @Author: Vinay Nadagoud
	// @Date : 11/15/2017
	// potentialUpdateAndReinitialisation : this method, for all v belongs to RA U
	// RB, resets
	// predecessor to null, removes it from PQ, update its potential. RA U RB
	// contains all nodes
	// that have been reached in the shortest path computation. Delta contains value
	// required for
	// potential updates.
	private void potentialUpdateAndReinitialisation(Map<Vertex, Integer> distance, Map<Vertex, Edge> predecessor,
			Map<Vertex, Integer> label, FibHeap<Vertex> PQ, Stack<Vertex> RB, Stack<Vertex> RA, int delta) {

		// for every a belongs to RA
		while (!RA.empty()) {
			Vertex a = RA.pop();
			// Update predecessor as null
			predecessor.put(a, null);
			// Update potential of a
			int potChange = delta - distance.get(a);
			if (potChange <= 0) {
				continue;
			}
			label.put(a, label.get(a) - potChange);
		}
		// for every b in RB
		while (!RB.empty()) {
			Vertex b = RB.pop();
			// Update predecessor as null
			predecessor.put(b, null);
			// Delete b from priority Queue
			if (PQ.member(b)) {
				PQ.delete(b);
			}
			// Update potential of b
			int potChange = delta - distance.get(b);
			if (potChange <= 0) {
				continue;
			}
			label.put(b, label.get(b) + potChange);
		}
	}

	// @Author: Guiheng Zhou
	// @Date : 11/20/2017
	// relaxEdges : Relax all edges out of a1 by improving the shortest distance
	// from a1 to its adjacent nodes
	private void relaxEdges(Vertex a1, Map<Vertex, Integer> distance, Map<Vertex, Edge> predecessor,
			Map<Vertex, Integer> label, Stack<Vertex> RB, FibHeap PQ) {
		if (a1.getAdjList() != null) {
			// For each edge beginning from a1
			for (Edge e : a1.getAdjList()) {
				// Fetch the end node b of edge
				Vertex b = e.getTarget();
				// Update the distance to b using potential function
				int db = distance.get(a1) + (label.get(a1) + label.get(b) - e.getCost());

				// If node b has no predecessor
				if (predecessor.get(b) == null) {
					// Add the distance to b into HashMap
					distance.put(b, db);
					// Update the predecessor edge of b
					predecessor.put(b, e);

					RB.push(b);
					// Insert node b with key db into FibHeap
					PQ.insert(b, db);

					// If node b has predecessor
				} else if (db < distance.get(b)) {
					// Update the distance to b to db
					distance.put(b, db);
					// Update the predecessor edge of b
					predecessor.put(b, e);
					// Decrease key of b in the FibHeap
					PQ.decreaseKey(b, db);
				}
			}
		}
	}

	// @Author: Guiheng Zhou
	// @Date : 11/20/2017
	// augmentPathTo : Augment path to vertex v
	private void augmentPathTo(Vertex v, Map<Vertex, Edge> predecessor) {
		// Fetch the predecessor edge of vertex v
		Edge e = predecessor.get(v);
		// If the edge exists
		while (e != null) {
			// Reverse the edge from (u -> v) to (v -> u)
			e.reverseEdge();
			// Continue fetching the predecessor edge of vertex u
			e = predecessor.get(e.getTarget());
		}
	}

}
