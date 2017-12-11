package tests;

import static org.junit.Assert.*;

import org.junit.Test;

import com.plagiarism.datastructures.Edge;
import com.plagiarism.datastructures.Vertex;


/**
 * @author : Guiheng Zhou
 * @date : 12/3/2017
 * The following test case tests the situation when the adjacent list of a vertex is empty.
 */
public class VertexTests {

	@Test
	public void testEmptyAdjList() {
		// Create three vertices
		Vertex v = new Vertex(1);
		Vertex from = new Vertex(1);
		Vertex to = new Vertex(1);
		// Use two of them creating a new edge
		Edge e = new Edge(from ,to);
		
		assertFalse(v.removeEdge(e));
		assertNull(v.getFirstAdjEdge());
	}

}
