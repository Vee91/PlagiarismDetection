package tests;

import static org.junit.Assert.*;
import org.junit.Test;

import com.plagiarism.datastructures.*;
import com.plagiarism.service.BipartiteMatchingBase;

//import PlagiarismServices.*;
import java.util.*;

/**
 * @author : Guiheng Zhou
 * @date : 11/20/2017
 * The following tests test Bipartite Matching Algorithm.
 */
public class BipartiteMatchingTests {

    // An object of bipartite matching
    private BipartiteMatchingBase bm;

    // Private properties
    // Inputs of maxWeightMatching() method
    private List<Vertex> A;
    private List<Vertex> B;
    private List<Edge> edges;
    private int sizeA;
    private int sizeB;

    // Output of maxWeightMatching() method
    List<Edge> expectedBipartiteMatching;

    // Vertices of A
    private Vertex a1; private Vertex a2; private Vertex a3; private Vertex a4;

    // Vertices of B
    private Vertex b1; private Vertex b2; private Vertex b3; private Vertex b4;

    public BipartiteMatchingTests() {
    }
    
    // Test 1: input an empty bipartite graph
    @Test
    public void emptyBiGraph() throws Exception {

        bm = new BipartiteMatchingBase();

        // Initialize inputs
        A = new ArrayList<Vertex>();
        // ------------------
        B = new ArrayList<Vertex>();
        // ------------------
        edges = new ArrayList<Edge>();
        // ------------------
        sizeA = 0; sizeB = 0;

        // Fetch the matching results of maxWeightMatching
        expectedBipartiteMatching = bm.maxWeightMatching(A, B, edges, sizeA, sizeB);
        // Prepare the actual matching results
        List<Edge> actualBipartiteMatching = new ArrayList<Edge>();
        // Compare expected results with actual ones
        assertEquals(expectedBipartiteMatching, actualBipartiteMatching);
    }

    // Test 2: input a bipartite graph without edges
    @Test
    public void biGraphWithoutEdges() throws Exception {

        bm = new BipartiteMatchingBase();

        // Initialize inputs
        A = new ArrayList<Vertex>();
        A.add(a1); A.add(a2);
        // ------------------
        B = new ArrayList<Vertex>();
        B.add(b1); B.add(b2);
        // ------------------
        edges = new ArrayList<Edge>();
        // ------------------
        sizeA = 2; sizeB = 2;

        // Fetch the matching results of maxWeightMatching
        expectedBipartiteMatching = bm.maxWeightMatching(A, B, edges, sizeA, sizeB);
        // Prepare the actual matching results
        List<Edge> actualBipartiteMatching = new ArrayList<Edge>();
        // Compare expected results with actual ones
        assertEquals(expectedBipartiteMatching, actualBipartiteMatching);
    }

    // Test 3: input a bipartite graph with one match
    @Test
    public void biGraphWithOneMatch() throws Exception {

        bm = new BipartiteMatchingBase();

        // Initialize inputs
        A = new ArrayList<Vertex>();
        a1 = new Vertex(1);
        // ------------------
        B = new ArrayList<Vertex>();
        b1 = new Vertex(1); B.add(b1);
        // ------------------
        edges = new ArrayList<Edge>();
        Edge e1 = new Edge(a1, b1); e1.setCost(1);
        a1.insertEdge(e1); A.add(a1);
        edges.add(e1);
        // ------------------
        sizeA = 1; sizeB = 1;

        // Fetch the matching results of maxWeightMatching
        expectedBipartiteMatching = bm.maxWeightMatching(A, B, edges, sizeA, sizeB);
        // Prepare the actual matching results
        List<Edge> actualBipartiteMatching = new ArrayList<Edge>();
        actualBipartiteMatching.add(e1);

        // Compare expected results with actual ones
        assertEquals(expectedBipartiteMatching.size(), actualBipartiteMatching.size());
        assertEquals(expectedBipartiteMatching.get(0), actualBipartiteMatching.get(0));
        assertEquals(expectedBipartiteMatching.get(0).getCost(), 1);
    }

    // Test 4: input a bipartite graph with two matches
    @Test
    public void biGraphWithTwoMatches() throws Exception {

        bm = new BipartiteMatchingBase();

        // Initialize inputs
        A = new ArrayList<Vertex>();
        a1 = new Vertex(2); a2 = new Vertex(2);
        // ------------------
        B = new ArrayList<Vertex>();
        b1 = new Vertex(2); b2 = new Vertex(2);
        // ------------------
        edges = new ArrayList<Edge>();
        Edge e1 = new Edge(a1, b1); e1.setCost(1);
        Edge e2 = new Edge(a2, b1); e2.setCost(1);
        Edge e3 = new Edge(a2, b2); e3.setCost(2);
        a1.insertEdge(e1); a2.insertEdge(e2); a2.insertEdge(e3);
        edges.add(e1); edges.add(e2); edges.add(e3);
        // ------------------
        A.add(a1); A.add(a2); B.add(b1); B.add(b2);
        // ------------------
        sizeA = 3; sizeB = 3;

        // Fetch the matching results of maxWeightMatching
        expectedBipartiteMatching = bm.maxWeightMatching(A, B, edges, sizeA, sizeB);
        // Prepare the actual matching results
        List<Edge> actualBipartiteMatching = new ArrayList<Edge>();
        actualBipartiteMatching.add(e1); actualBipartiteMatching.add(e3);

        // Compare expected results with actual ones
        assertEquals(expectedBipartiteMatching.size(), actualBipartiteMatching.size());
        assertEquals(expectedBipartiteMatching.get(0), actualBipartiteMatching.get(0));
        assertEquals(expectedBipartiteMatching.get(1), actualBipartiteMatching.get(1));
        assertEquals(expectedBipartiteMatching.get(0).getCost(), 1);
        assertEquals(expectedBipartiteMatching.get(1).getCost(), 2);
    }

    // Test 5: input a bipartite graph with three matches
    @Test
    public void biGraphWithThreeMatches() throws Exception {

        bm = new BipartiteMatchingBase();

        // Initialize inputs
        A = new ArrayList<Vertex>();
        a1 = new Vertex(4); a2 = new Vertex(4);
        a3 = new Vertex(4); a4 = new Vertex(4);
        // ------------------
        B = new ArrayList<Vertex>();
        b1 = new Vertex(4); b2 = new Vertex(4);
        b3 = new Vertex(4); b4 = new Vertex(4);
        // ------------------
        edges = new ArrayList<Edge>();
        Edge e1 = new Edge(a1, b1); e1.setCost(6);
        Edge e2 = new Edge(a1, b2); e2.setCost(5);
        Edge e3 = new Edge(a1, b3); e3.setCost(8);
        Edge e4 = new Edge(a2, b1); e4.setCost(3);
        Edge e5 = new Edge(a2, b2); e5.setCost(9);
        Edge e6 = new Edge(a3, b1); e6.setCost(10);
        Edge e7 = new Edge(a3, b3); e7.setCost(100);
        a1.insertEdge(e1); a1.insertEdge(e2); a1.insertEdge(e3);
        a2.insertEdge(e4); a2.insertEdge(e5); a3.insertEdge(e6); a3.insertEdge(e7);
        edges.add(e1); edges.add(e2); edges.add(e3);
        edges.add(e4); edges.add(e5); edges.add(e6); edges.add(e7);
        // ------------------
        A.add(a1); A.add(a2); A.add(a3); A.add(a4);
        B.add(b1); B.add(b2); B.add(b3); B.add(b4);
        // ------------------
        sizeA = 4; sizeB = 4;

        // Fetch the matching results of maxWeightMatching
        expectedBipartiteMatching = bm.maxWeightMatching(A, B, edges, sizeA, sizeB);
        // Prepare the actual matching results
        List<Edge> actualBipartiteMatching = new ArrayList<Edge>();
        actualBipartiteMatching.add(e1); actualBipartiteMatching.add(e5);
        actualBipartiteMatching.add(e7);

        // Compare expected results with actual ones
        assertEquals(expectedBipartiteMatching.size(), actualBipartiteMatching.size());
        assertEquals(expectedBipartiteMatching.get(0), actualBipartiteMatching.get(0));
        assertEquals(expectedBipartiteMatching.get(1), actualBipartiteMatching.get(1));
        assertEquals(expectedBipartiteMatching.get(2), actualBipartiteMatching.get(2));
        assertEquals(expectedBipartiteMatching.get(0).getCost(), 6);
        assertEquals(expectedBipartiteMatching.get(1).getCost(), 9);
        assertEquals(expectedBipartiteMatching.get(2).getCost(), 100);
    }
}