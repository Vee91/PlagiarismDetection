package com.plagiarism.service;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.github.javaparser.JavaParser;
import com.github.javaparser.ParseProblemException;
import com.github.javaparser.ast.Node;
import com.github.javaparser.ast.stmt.BlockStmt;
import com.plagiarism.datastructures.Edge;
import com.plagiarism.datastructures.Vertex;
import com.plagiarism.interfaces.Algorithm;
import com.plagiarism.interfaces.BipartiteMatching;
import com.plagiarism.response.Highlighter;
import com.plagiarism.response.Response;
import com.plagiarism.utility.ASTUtility;

@Service
public class AlgorithmBase implements Algorithm {

	// The root nodes that represents tree1 and tree2
	private Node tree1, tree2;
	// Map B - All possible mappings
	private HashMap<Node, ArrayList<Node>> bMap;
	// Map M - The actual mapping
	private HashMap<Node, Node> mMap;
	private HashMap<Node, ArrayList<Float>> costMap;
	private Map<Integer, Integer> sizeMap1;
	private Map<Integer, Integer> sizeMap2;
	private Map<Node, Float> cMap;

	// boolean to keep track of files corresponding to trees
	private boolean exchanged;

	public Response<Highlighter> run(MultipartFile f1, MultipartFile f2) {
		Response<Highlighter> result = new Response<Highlighter>();
		float similarity = 0;
		try {
			// convert multipart file to file
			File file1 = convert(f1);
			File file2 = convert(f2);

			// generate ASTs
			Node r1 = JavaParser.parse(file1);
			Node r2 = JavaParser.parse(file2);

			// The sizes of the subtrees rooted at r1 and r2
			ASTUtility.subTreeSize(r1);
			Map<Integer, Integer> sizeMap1 = ASTUtility.sizeMap;
			ASTUtility.sizeMap = new HashMap<Integer, Integer>(); // reset to new map for second ast
			ASTUtility.subTreeSize(r2);
			Map<Integer, Integer> sizeMap2 = ASTUtility.sizeMap;

			// initialize algorithm
			initializeAlgorithm(r1, r2, sizeMap1, sizeMap2);
			similarity = findSimilarity();
			findLineNumbers(result, similarity);
		} catch (FileNotFoundException e) {
			result.setCode(402);
			result.setError("There was an error in uploading files. Please try again");
			return result;
		} catch (IOException e) {
			result.setCode(402);
			result.setError("There was an error in uploading files. Please try again");
			return result;
		} catch (ParseProblemException e) {
			result.setCode(402);
			result.setError("There was an error in uploading files. Please try again");
			return result;
		}
		return result;

	}

	private void initializeAlgorithm(Node tree1, Node tree2, Map<Integer, Integer> sizeMap1,
			Map<Integer, Integer> sizeMap2) {

		// We always find the mapping between the smallest tree and the largest tree.
		// Here we might need to switch the root nodes.
		if (sizeMap1.get(System.identityHashCode(tree1)) <= sizeMap2.get(System.identityHashCode(tree2))) {
			exchanged = false;
			this.tree1 = tree1;
			this.tree2 = tree2;
			this.sizeMap1 = sizeMap1;
			this.sizeMap2 = sizeMap2;
		}

		else {
			exchanged = true;
			this.tree1 = tree2;
			this.tree2 = tree1;
			this.sizeMap1 = sizeMap2;
			this.sizeMap2 = sizeMap1;
		}

		bMap = new HashMap<Node, ArrayList<Node>>(sizeMap1.get(System.identityHashCode(tree1)));
		mMap = new HashMap<Node, Node>(sizeMap1.get(System.identityHashCode(tree1)));
		costMap = new HashMap<Node, ArrayList<Float>>(sizeMap1.get(System.identityHashCode(tree1)));
		cMap = new HashMap<Node, Float>(sizeMap1.get(System.identityHashCode(tree1)));

	}

	public float findSimilarity() {
		// If root nodes have the same label
		if (tree1.getClass().equals(tree2.getClass())) {
			// Find the size of the isomorphism
			int res = maxSubtreeIsomorphism(tree1, tree2);

			// Reconstruct isomorphism
			// tree1 can be mapped to tree2
			mMap.put(tree1, tree2);
			ArrayList<Node> list = new ArrayList<Node>(sizeMap1.get(System.identityHashCode(tree1)));

			ASTUtility.preorderTraversal(list, tree1);

			// Nodes tree1 and tree2 are already mapped
			list.remove(0);

			for (Node v : list) {
				if (bMap.containsKey(v)) {
					// For all the nodes that can be mapped to v
					for (int i = 0; i < bMap.get(v).size(); i++) {
						Node w = bMap.get(v).get(i);
						float cost = costMap.get(v).get(i);
						if (mMap.containsKey(v.getParentNode().get())) {
							if (w.getParentNode().get().equals(mMap.get(v.getParentNode().get()))) {
								mMap.put(v, w);
								if (cost == 1)
									cMap.put(v, cost);
								break;
							}
						}
					} // end for
				} // end if
			} // end for
			return similarity(sizeMap1.get(System.identityHashCode(tree1)),
					sizeMap2.get(System.identityHashCode(tree2)), res);
		} // end if
		return 0;
	}

	public int maxSubtreeIsomorphism(Node r1, Node r2) {
		// Cannot find a isomorphism when the labels differ
		if (!r1.getClass().equals(r2.getClass())) {
			return 0;
		}

		// The isomorphism has size 0 or 1 if one of the nodes are leaf nodes
		if (ASTUtility.isLeaf(r1) || ASTUtility.isLeaf(r2)) {
			return (r1.getClass().equals(r2.getClass())) ? 1 : 0;
		}

		int p = ASTUtility.numberOfChildren(r1);
		int q = ASTUtility.numberOfChildren(r2);

		// Each child of r1 has a corresponding vertex in the bipartite graph. A map
		// from node to vertex.
		HashMap<Node, Vertex> graph1 = new HashMap<Node, Vertex>(p);
		// Each child of r2 has a corresponding vertex in the bipartite graph. A map
		// from node to vertex.
		HashMap<Node, Vertex> graph2 = new HashMap<Node, Vertex>(q);
		// A map from vertex to node.
		HashMap<Vertex, Node> GT = new HashMap<Vertex, Node>(p + q);

		// There is maximum p*q edges in the bipartite graph.
		List<Edge> edges = new ArrayList<Edge>(p * q);
		// The vertices that represents the children of r1.
		List<Vertex> U = new ArrayList<Vertex>(p);
		// The vertices that represents the children of r2.
		List<Vertex> W = new ArrayList<Vertex>(q);

		initializeGraph(r1, q, r2, p, U, W, GT, graph1, graph2, edges);

		// List of matched edges
		List<Edge> list = null;

		if ((r1.getClass().equals(BlockStmt.class)) && (r2.getClass().equals(BlockStmt.class))) {
			NeedlemanWunsch nw = new NeedlemanWunsch(U, W);
			list = nw.findMatch();
		}

		// else use the max weight bipartite matching
		else {
			BipartiteMatching bm = new BipartiteMatchingBase();
			list = bm.maxWeightMatching(U, W, edges, p, q);
		}

		// Can map r1 to r2
		int res = 1;

		for (Edge e : list) {
			ArrayList<Node> nodeList;
			ArrayList<Float> costList;

			// All edges goes from the children of r1 to the children of r2
			Node v = GT.get(e.getSource());
			Node w = GT.get(e.getTarget());

			// v is already in B
			if (bMap.containsKey(v)) {
				nodeList = bMap.get(v);
				costList = costMap.get(v);
			}

			// First time we insert v. Create a list for the nodes that can be mapped to v.
			else {
				nodeList = new ArrayList<Node>(100);
				costList = new ArrayList<Float>(100);
			}

			// Insert w into the list
			nodeList.add(w);
			costList.add(e.getPercent());
			bMap.put(v, nodeList);
			costMap.put(v, costList);

			// Add the size of the max common subtree between v and w to res.
			res += e.getCost();
		}

		return res;
	}

	private void initializeGraph(Node r1, int q, Node r2, int p, List<Vertex> U, List<Vertex> W,
			HashMap<Vertex, Node> GT, HashMap<Node, Vertex> graph1, HashMap<Node, Vertex> graph2, List<Edge> edges) {
		for (Node v1 : r1.getChildNodes()) {
			// q is the number of neighbors that v can have in the bipartite graph.
			Vertex v = new Vertex(q);

			U.add(v);
			GT.put(v, v1);
			graph1.put(v1, v);

		}

		for (Node v2 : r2.getChildNodes()) {
			// p is the number of neighbors that w can have in the bipartite graph.
			Vertex w = new Vertex(p);

			W.add(w);
			GT.put(w, v2);
			graph2.put(v2, w);

		}

		for (Node v1 : r1.getChildNodes()) {
			for (Node v2 : r2.getChildNodes()) {

				// Find max common subtree between v1 and v2
				int res = maxSubtreeIsomorphism(v1, v2);

				// If max common subtree
				if (res != 0) {
					Vertex v = graph1.get(v1);
					// Insert edge between v1 and v2
					Edge e = v.insertEdge(graph2.get(v2));
					// Set cost of edge to res (size of max common subtree)
					e.setCost(res);
					// Set the similarity between the two subtrees. Used by NW
					e.setPercent(similarity(sizeMap1.get(System.identityHashCode(v1)),
							sizeMap2.get(System.identityHashCode(v2)), res));
					edges.add(e);
				}
			}
		}
	}

	// Calculates the similarity between two ASTs.
	private float similarity(int size1, int size2, int sim) {
		return ((float) (2 * sim) / (float) (size1 + size2));
	}

	// converts multipartfile to java.io.file
	public File convert(MultipartFile file) throws IOException {
		File convFile = new File(file.getOriginalFilename());
		convFile.createNewFile();
		FileOutputStream fos = new FileOutputStream(convFile);
		fos.write(file.getBytes());
		fos.close();
		return convFile;
	}

	@Override
	public void saveOutput() {
		// TODO Auto-generated method stub

	}

	private void findLineNumbers(Response<Highlighter> result, float similarity) {
		List<Highlighter> l = new ArrayList<Highlighter>();
		for (Map.Entry<Node, Float> entry : cMap.entrySet()) {
			if (exchanged) {
				Node key = entry.getKey();
				Node value = mMap.get(key);
				Highlighter h = new Highlighter(value.getRange().get().begin.line, value.getRange().get().end.line,
						key.getRange().get().begin.line, key.getRange().get().end.line, similarity);
				if (!l.contains(h)) {
					l.add(h);
				}
			} else {
				Node key = entry.getKey();
				Node value = mMap.get(key);
				Highlighter h = new Highlighter(key.getRange().get().begin.line, key.getRange().get().end.line,
						value.getRange().get().begin.line, value.getRange().get().end.line, similarity);
				if (!l.contains(h)) {
					l.add(h);
				}
			}
		}
		result.setCode(200);
		result.setMessage(String.valueOf(Math.round(similarity * 10000.0) / 100.0) + "%");
		result.setResultList(adjustRanges(l));
	}

	private List<Highlighter> adjustRanges(List<Highlighter> l) {
		List<Highlighter> result = new ArrayList<Highlighter>();
		List<Highlighter> needToRemove = new ArrayList<Highlighter>();
		result.add(l.get(0));
		for (int i = 1; i < l.size(); i++) {
			boolean added = false;
			Highlighter small = l.get(i);
			for (int j = 0; j < result.size(); j++) {
				Highlighter large = result.get(j);
				if ((small.getStart1() < large.getStart1() && small.getEnd1() > large.getEnd1())
						|| (small.getStart1() < large.getStart1() && small.getEnd1() == large.getEnd1()
								|| (small.getStart1() == large.getStart1() && small.getEnd1() > large.getEnd1()))) {
					needToRemove.add(large);
				}
				else if((small.getStart1() > large.getStart1() && small.getEnd1() < large.getEnd1())
						|| (small.getStart1() > large.getStart1() && small.getEnd1() == large.getEnd1())
						|| (small.getStart1() == large.getStart1() && small.getEnd1() < large.getEnd1())){
					added = true;
				}
			}
			result.removeAll(needToRemove);
			if(!added)
				result.add(small);
			
		}
		addColor(result);
		return result;
	}

	private void addColor(List<Highlighter> result) {
		/*for(int i = 0; i < 360; i += 360 / result.size()) {
		    HSLColor h = new HSLColor(i, )
		    c.hue = i;
		    c.saturation = 90 + randf() * 10;
		    c.lightness = 50 + randf() * 10;

		    addColor(c);
		}*/
		
        Random random = new Random();
        for(int i = 0; i < result.size(); i++) {
        	// create a big random number - maximum is ffffff (hex) = 16777215 (dez)
            int nextInt = random.nextInt(256*256*256);
            // format it as hexadecimal string (with hashtag and leading zeros)
            String colorCode = String.format("#%06x", nextInt);
            result.get(i).setColor(colorCode);
        }
	}
	
}
