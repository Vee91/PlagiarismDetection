package com.plagiarism.nodeformatter;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.Node;


public class SimpleNode{

	private static Map<Integer, Integer> sizeMap = new HashMap<Integer, Integer>();
	
	public int subTreeSize(Node cu) {
		int size = 1;
		int key = System.identityHashCode(cu);
		
		if(cu.getChildNodes() != null && cu.getChildNodes().size() != 0) {
			List<Node> n = cu.getChildNodes();
			for(int i = 0; i < n.size(); i++) {
				size += subTreeSize(n.get(i));
			}
		}
		
		sizeMap.put(key, size);
		return size;
	}
	
	public Map<Integer, Integer> getMap(){
		return sizeMap;
	}
}
