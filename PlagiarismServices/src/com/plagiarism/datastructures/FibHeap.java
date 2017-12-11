package com.plagiarism.datastructures;

import java.util.HashMap;
import java.util.NoSuchElementException;


// This class represents Fibonacci Heap data structure and is implemented using
// pseudo code provided in the book Introduction to Algorithms by Cormen, Thomas H.
// which is available in NU library or online
public class FibHeap<T> {

	public static class FibNode<T> {

		private T obj;
		private int key;
		private FibNode<T> parent;
		private FibNode<T> child;
		private FibNode<T> left;
		private FibNode<T> right;

		// indicates whether this node has lost a child since the last time
		// this node was made the child of another node.
		// A node can lose a child when we use the decrease key operation.
		private boolean mark;
		private int degree; // degree of this node. Represents how many child nodes
		// are connected to this node

		private int insertNum;

		public FibNode(T t, int key, int insertNum) {
			this.obj = t;
			this.key = key;
			this.insertNum = insertNum;

			parent = child = null;
			left = right = this;

			mark = false;
			degree = 0;

		}

		public FibNode getRight() {
			return right;
		}

		public FibNode getLeft() {
			return left;
		}

		public FibNode getChild() {
			return child;
		}

		public FibNode getParent() {
			return parent;
		}

		public int getKey() {
			return key;
		}

		public int getDegree() {
			return degree;
		}

		public String toString() {
			return String.valueOf(key) + " with degree " + String.valueOf(degree);
		}
	}

	// min node of this heap. H.min from the book
	private FibNode<T> min;

	// represents mapping of object to a Node(in turn key in the node) contains
	// every node.
	// Not just the root list as stated in the book.
	// Please note that Node itself has object and key but this is also maintained
	// explicitly
	private HashMap<T, FibNode<T>> objectToNode;

	// number of nodes in this heap. H.n from the book
	private int noOfNodes;

	private int insertNumCounter;

	public FibHeap(int maxSize) {
		min = null;
		noOfNodes = 0;
		insertNumCounter = 0;
		objectToNode = new HashMap<T, FibNode<T>>(maxSize);

	}

	// removes the given fibnode from root list. There is no root list but all
	// root nodes have parent null and we need to remove their right and left
	// siblings
	private void removeNodeFromRootList(FibNode<T> temp) {
		temp.left.right = temp.right;
		temp.right.left = temp.left;
	}

	// given two nodes
	// where both nodes have parent null or have same parent
	// makes the given nodes as neighbors
	private void makeNeighbor(FibNode<T> x, FibNode<T> y) {
			x.left.right = y;
			y.left.right = x;
			FibNode<T> temp = y.left;
			y.left = x.left;
			x.left = temp;
	}

	// following method extracts the minimum node and consolidates the trees
	public T extractMin() {
		FibNode<T> minFibNode = this.min;

		if (minFibNode != null) {
			// for each child of minFibNode
			FibNode<T> curChild = minFibNode.child;
			if (curChild != null) {
				
				while (curChild.parent != null) {
					curChild.parent = null;
					curChild = curChild.right;
				}

				makeNeighbor(curChild, min);
			}

			removeNodeFromRootList(minFibNode);

			if (minFibNode == minFibNode.right) {
				if (noOfNodes - 1 != 0) {
					System.out.println("Error");
				}
				this.min = null;

			} else {
				this.min = minFibNode.right;
				consolidate();
			}
			minFibNode.right = minFibNode.left = null;
			// remove minFibNode from the HashMap
			objectToNode.remove(minFibNode.obj);
			this.noOfNodes--;
			return minFibNode.obj;
		}

		throw new NoSuchElementException();
	}

	// following method makes a node using given object and key and inserts into
	// this heap
	public void insert(T obj, int key) {

		if (noOfNodes == 0)
			insertNumCounter = 0;
		FibNode<T> node = new FibNode<T>(obj, key, insertNumCounter);
		insertNumCounter++;
		objectToNode.put(obj, node);

		if (min != null) {
			// insert node into the root list
			makeNeighbor(node, min);

			// test if new added node is min
			if (min.key > node.key)
				min = node;
		}

		else {
			min = node;
		}

		noOfNodes++;

	}

	// following method removes a node with given object and key mapping from this
	// heap
	public void decreaseKey(T obj, int key) {
		FibNode<T> decreasedNode = objectToNode.get(obj);
		// If current node has a value lesser than new key value
		if (decreasedNode.key < key) {
			return;
		}
		decreasedNode.key = key;
		FibNode<T> decreasedNodeParent = decreasedNode.parent;
		// If current node's new key is lesser than parent
		if ((decreasedNodeParent != null)
				&& ((decreasedNode.key < decreasedNodeParent.key) || ((decreasedNode.key == decreasedNodeParent.key)
						&& (decreasedNode.insertNum < decreasedNodeParent.insertNum)))) {
			cut(decreasedNode, decreasedNodeParent);
			cascadingCut(decreasedNodeParent);
		}
		// If current node's key is lesser than FibHeap's min.key
		if (min == null || (decreasedNode.key < min.key)
				|| ((decreasedNode.key == min.key) && (decreasedNode.insertNum < min.insertNum))) {
			min = decreasedNode;
		}

	}

	// the following method �cuts� the link between decreasedNode and its
	// parent decreasedNodeParent, making decreasedNode a root.
	public void cut(FibNode<T> decreasedNode, FibNode<T> decreasedNodeParent) {
		// remove decreasedNode from the child list of its parent and parent's degree
		removeNodeFromRootList(decreasedNode);

		if (decreasedNodeParent.child == decreasedNode) {
			if (decreasedNode.right != decreasedNode) {
				decreasedNodeParent.child = decreasedNode.right;
			} else {
				decreasedNodeParent.child = null;
			}
		}
		
		decreasedNodeParent.degree--;
		decreasedNode.parent = null;
		decreasedNode.mark = false;
		
		// added by vikas to make sure decreasedNode is made the neighbor of min
		decreasedNode.left = decreasedNode.right = decreasedNode;
		makeNeighbor(decreasedNode, min);

	}

	// the following method performs cascading cut needed by the decrease key
	// operation
	public void cascadingCut(FibNode<T> decreasedNodeParent) {
		FibNode<T> DNPparent = decreasedNodeParent.parent;
		if (DNPparent != null) {
			if (decreasedNodeParent.mark == false) {
				decreasedNodeParent.mark = true;
			} else {
				cut(decreasedNodeParent, DNPparent);
				cascadingCut(DNPparent);
			}
		}
	}

	private void consolidate() {
		// upper bound on degree of any node is given as page 524 in book
		double pot = (1.0 + Math.sqrt(5.0)) / 2.0;
		int size = (int) (Math.floor(Math.log((double) noOfNodes) / Math.log(pot)));

		@SuppressWarnings("unchecked")
		FibNode<T> arr[] = new FibNode[size + 1];
		for (int i = 0; i < arr.length; i++) {
			arr[i] = null;
		}

		// total number of root nodes in root list
		int noOfRootNode = getNoOfNodesInRootList();

		// min is a root node so we can start loop from min
		FibNode<T> x = min;
		// for each node in root list
		while (noOfRootNode > 0 && x != null) {
			int d = x.degree;
			FibNode<T> next = x.right; // save next node to loop on

			while (arr[d] != null) {
				FibNode<T> y = arr[d];

				// if key of x is bigger than key of y make x child of y to
				// satisfy heap property
				if (x.key > y.key && (x.insertNum > y.insertNum)) {
					FibNode<T> temp = x;
					x = y;
					y = temp;
				}
				fibHeapLink(y, x);

				// make arr[d] null to indicate node with degree d does not exist
				arr[d] = null;

				// check if you can build node with degree d + 1
				d++;
			}

			// now the tree with root x is in arr[d]
			arr[d] = x;

			// loop on next root node
			x = next;
			noOfRootNode--;
		}

		// we need to find new min
		min = null;

		// findmin
		for (int i = 0; i <= size; i++) {
			if (arr[i] != null) {
				// arr[i] is the new min node if min = null or if it has a smaller key than min
				if ((min == null) || (arr[i].key < min.key)
						|| ((arr[i].key == min.key) && (arr[i].insertNum < min.insertNum))) {
					min = arr[i];
				}
			}
		}
		
		
	}

	// link two root nodes y and x making y as child of x
	private void fibHeapLink(FibNode<T> y, FibNode<T> x) {
		removeNodeFromRootList(y);
		y.right = y.left = y;
		y.parent = x;
		if (x.child != null) {
			makeNeighbor(y, x.child);
		}

		// y is the new child of x
		x.child = y;
		x.degree++;
		y.mark = false;
	}

	public int getNoOfNodesInRootList() {
		FibNode<T> x = min;
		int noOfRootNode = 0;
		if (x != null) {
			noOfRootNode++;
			x = x.right;
			while (x != min) {
				noOfRootNode++;
				x = x.right;
			}
		}
		return noOfRootNode;
	}

	// following method removes the given object from this heap
	public T delete(T object) {
		if (objectToNode != null) {
			decreaseKey(object, Integer.MIN_VALUE);
			return extractMin();
		}

		return null;
	}

	// check if a node with object is in this heap
	public boolean member(T object) {
		if (objectToNode != null)
			return objectToNode.containsKey(object);

		return false;
	}

	// check if this heap is empty
	public boolean empty() {
		if (min == null)
			return true;

		return false;
	}

	public int getNoOfNodes() {
		return noOfNodes;
	}

	public FibNode getMin() {
		return min;
	}

}