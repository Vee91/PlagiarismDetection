package tests;

import static org.junit.Assert.*;

import org.junit.Test;

import com.plagiarism.datastructures.Edge;
import com.plagiarism.datastructures.Vertex;

/**
 * @author : Guiheng Zhou
 * @date : 12/3/2017
 * The following test case tests the reverseEdge method.
 */
public class EdgeTests {

	@Test
	public void testReverseEdge() {
		
		// Create two vertices
		Vertex from = new Vertex(1);
		Vertex to = new Vertex(1);
		
		// Create an edge with cost
		Edge e = new Edge(from, to, 1);
		// NOTE: remember to insertEdge
		from.insertEdge(e);
		e.reverseEdge();
		
		assertEquals(to, e.getSource());
		assertEquals(from, e.getTarget());
	}

}
