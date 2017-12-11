package com.plagiarism.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.plagiarism.datastructures.Edge;
import com.plagiarism.datastructures.Vertex;

@Service
public class NeedlemanWunsch {

	private int n = 0;
	private int m = 0;

	// The edges
	private Edge edges[][];
	// similarity matrix
	private float S[][];
	// matrix for alignments between the different subtrees
	private float F[][];

	public NeedlemanWunsch() {

	}

	public NeedlemanWunsch(List<Vertex> A, List<Vertex> B) {
		n = A.size();
		m = B.size();

		edges = new Edge[m][n];
		S = new float[m][n];
		F = new float[m + 1][n + 1];

		// All the edge are directed from A to B.
		for (Vertex a : A) {
			// Index of a in A
			int indexA = A.indexOf(a);

			ArrayList<Edge> adjEdges = a.getAdjList();

			if (adjEdges != null) {

				for (Edge e : adjEdges) {

					Vertex b = e.getTarget();
					// Index of b in B
					int indexB = B.indexOf(b);

					// Insert e into the correct cell in edges
					edges[indexB][indexA] = e;

					// The similariy between the subtrees rooted at a and b is given by the
					// similarity between the two subtrees and
					// the size of the max common subtree between them. We could use only the size
					// of the max common subtree, but if we also use
					// similarity then we can find better matches between the subtrees (i.e a
					// subtree rooted at a in A with size 5 has a max common
					// subtree of 3 with a subtree rooted at b in B with size 5 and a subtree rooted
					// at c in B with size 7. We want to map a to b
					// since this mapping has fewer unmatched nodes than a to c. It is a better
					// match. S[b][a] = 0.6 + 3 and s[c][a] = 0.5 + 3).
					S[indexB][indexA] = e.getPercent() + e.getCost();

				}

			}

		}

	}

	public ArrayList<Edge> findMatch() {
		// the gap penalty. it is not used.
		float d = 0.0f;

		for (int i = 0; i <= m; i++) {
			F[i][0] = i * d;
		}

		for (int j = 0; j <= n; j++) {
			F[0][j] = j * d;
		}
		// Find the maximum size of an optimal alignment.
		for (int i = 1; i <= m; i++) {
			for (int j = 1; j <= n; j++) {

				float choice1 = F[i - 1][j - 1] + S[i - 1][j - 1];
				float choice2 = F[i - 1][j] + d;
				float choice3 = F[i][j - 1] + d;

				F[i][j] = Math.max(choice1, Math.max(choice2, choice3));
			}
		}

		// The edges between the nodes that we map to each other
		ArrayList<Edge> list = new ArrayList<Edge>(n);

		int x = m;
		int y = n;

		// Find the actual alignment with the maximum size.
		while (x > 0 && y > 0) {

			float score = F[x][y];
			float scoreDiagonal = F[x - 1][y - 1];
			float scoreUp = F[x][y - 1];
			float scoreLeft = F[x - 1][y];

			// If the two subtrees are part of the alignment, then we add the edge between
			// the two root nodes in the subtrees to list.
			if (score == scoreDiagonal + S[x - 1][y - 1]) {
				if (edges[x - 1][y - 1] != null) {
					list.add(edges[x - 1][y - 1]);
				}

				x--;
				y--;

			} else if (score == scoreLeft + d) {
				x--;
			} else { // score = scoreUp + d
				y--;
			}
		}

		return list;

	}

}
