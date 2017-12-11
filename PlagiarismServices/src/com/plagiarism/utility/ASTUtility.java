package com.plagiarism.utility;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.github.javaparser.ast.Node;

// Static methods for AST related operations
public class ASTUtility {

	// map to store the size of tree rooted at each node in AST
	// key is the system hashcode of the node and value is the size of the tree
	public static Map<Integer, Integer> sizeMap = new HashMap<Integer, Integer>();

	// updates the map with size of tree rooted at given node
	public static int subTreeSize(Node cu) {
		int size = 1;
		int key = System.identityHashCode(cu);

		if (cu.getChildNodes() != null && cu.getChildNodes().size() != 0) {
			List<Node> n = cu.getChildNodes();
			for (int i = 0; i < n.size(); i++) {
				size += subTreeSize(n.get(i));
			}
		}

		sizeMap.put(key, size);
		return size;
	}

	// determines the number of children of the given node
	public static int numberOfChildren(Node r1) {
		if (r1.getChildNodes() != null)
			return r1.getChildNodes().size();
		return 0;
	}

	// determines if the given node is leaf or not
	public static boolean isLeaf(Node r1) {
		if (r1.getChildNodes() != null) {
			return r1.getChildNodes().size() == 0 ? true : false;
		}
		return true;
	}

	// Given a List and a Node(AST)
	// Adds all the child nodes in the given node to the given list
	public static void preorderTraversal(ArrayList<Node> list, Node n) {
		list.add(n);

		if (n.getChildNodes() != null && n.getChildNodes().size() != 0) {
			List<Node> children = n.getChildNodes();
			for (int i = 0; i < children.size(); i++) {
				preorderTraversal(list, children.get(i));
			}
		}

	}
}
