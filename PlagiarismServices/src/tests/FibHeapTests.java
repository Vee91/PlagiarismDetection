package tests;

import org.junit.Test;

import static org.junit.Assert.*;

import java.util.NoSuchElementException;

import com.plagiarism.datastructures.*;

/**
 * @author : Jheel Mehta
 * @date : 11/23/2017
 * The following test cases test the data structure of Fibonacci Heap.
 */
public class FibHeapTests {

	// a FibHeap object
    private FibHeap<String> fibHeap;

    // Constructor of FibHeapTests
    public FibHeapTests() {
        fibHeap = new FibHeap<String>(20);
    }

    // Test 1 : insert a FibNode into empty heap
    @Test
    public void insertIntoEmptyHeap() throws Exception {
        String obj1 = "3"; int key1 = 3;
        fibHeap.insert(obj1, key1);
        assertEquals(fibHeap.getNoOfNodes(), 1);
    }

    // Test 2 : insert a FibNode into a heap with one FibNode
    @Test
    public void insertIntoHeapWithOneNode() throws Exception {
        String obj1 = "3"; int key1 = 3;
        fibHeap.insert(obj1, key1);
        String obj2 = "18"; int key2 = 18;
        fibHeap.insert(obj2, key2);
        assertEquals(fibHeap.getNoOfNodes(), 2);
    }

    // Test 3 : insert a FibNode into a heap with many FibNodes
    @Test
    public void insertIntoHeapWithManyNodes() throws Exception {
        
        String obj1 = "3"; int key1 = 3;
        String obj2 = "18"; int key2 = 18;
        String obj3 = "52"; int key3 = 52;
        String obj4 = "38"; int key4 = 38;
        String obj5 = "39"; int key5 = 39;

        fibHeap.insert(obj1, key1);
        fibHeap.insert(obj2, key2);
        fibHeap.insert(obj3, key3);
        fibHeap.insert(obj4, key4);
        fibHeap.insert(obj5, key5);

        String obj6 = "41"; int key6 = 41;
        fibHeap.insert(obj6, key6);

        assertEquals(fibHeap.getNoOfNodes(), 6);
    }

    // Test 4 : insert a FibNode with key that will be the min.
    @Test
    public void insertMin() throws Exception {
        
        String obj1 = "3"; int key1 = 3;
        String obj2 = "18"; int key2 = 18;
        String obj3 = "52"; int key3 = 52;
        String obj4 = "38"; int key4 = 38;
        String obj5 = "39"; int key5 = 39;

        fibHeap.insert(obj1, key1);
        fibHeap.insert(obj2, key2);
        fibHeap.insert(obj3, key3);
        fibHeap.insert(obj4, key4);
        fibHeap.insert(obj5, key5);

        String obj6 = "1"; int key6 = 1;
        fibHeap.insert(obj6, key6);

        assertEquals(fibHeap.getMin().getKey(), 1);
    }

    // Test 5 : insert a FibNode whose key is not the min.
    @Test
    public void insertNonMin() throws Exception {
        
        String obj1 = "3"; int key1 = 3;
        String obj2 = "18"; int key2 = 18;
        String obj3 = "52"; int key3 = 52;
        String obj4 = "38"; int key4 = 38;
        String obj5 = "39"; int key5 = 39;

        fibHeap.insert(obj1, key1);
        fibHeap.insert(obj2, key2);
        fibHeap.insert(obj3, key3);
        fibHeap.insert(obj4, key4);
        fibHeap.insert(obj5, key5);

        String obj6 = "40"; int key6 = 40;
        fibHeap.insert(obj6, key6);

        assertEquals(fibHeap.getMin().getKey(), 3);
    }
    
    // Test 6 : insert a FibNode into a greater heap.
    @Test
    public void insertIntoGreaterHeap() throws Exception {
        
        String obj1 = "3"; int key1 = 3;
        String obj2 = "18"; int key2 = 18;
        String obj3 = "52"; int key3 = 52;
        String obj4 = "38"; int key4 = 38;
        String obj5 = "39"; int key5 = 39;
        String obj6 = "41"; int key6 = 41;
        String obj7 = "17"; int key7 = 17;
        String obj8 = "30"; int key8 = 30;
        String obj9 = "24"; int key9 = 24;
        String obj10 = "26"; int key10 = 26;
        String obj11 = "46"; int key11 = 46;
        String obj12 = "35"; int key12 = 35;
        String obj13 = "23"; int key13 = 23;
        String obj14 = "7"; int key14 = 7;
        String obj15 = "21"; int key15 = 21;

        fibHeap.insert(obj1, key1);
        fibHeap.insert(obj2, key2);
        fibHeap.insert(obj3, key3);
        fibHeap.insert(obj4, key4);
        fibHeap.insert(obj5, key5);
        fibHeap.insert(obj6, key6);
        fibHeap.insert(obj7, key7);
        fibHeap.insert(obj8, key8);
        fibHeap.insert(obj9, key9);
        fibHeap.insert(obj10, key10);
        fibHeap.insert(obj11, key11);
        fibHeap.insert(obj12, key12);
        fibHeap.insert(obj13, key13);
        fibHeap.insert(obj14, key14);
        fibHeap.insert(obj15, key15);

        assertEquals(fibHeap.getNoOfNodes(), 15);
    }

    // Test 7 : extract minimum from an empty heap.
    @Test
    public void extractMinFromEmptyHeap() throws Exception {
        
        try{
        	fibHeap.extractMin();
        }catch(Exception e){
            assertEquals(new NoSuchElementException().toString(),e.toString());
        };
    }

    // Test 8 : extract minimum from a heap with only one FibNode
    @Test
    public void extractMinFromHeapWithOneNode() throws Exception {
        
        String obj1 = "3"; int key1 = 3;
        fibHeap.insert(obj1, key1);
        String obj2 = "18"; int key2 = 18;
        fibHeap.insert(obj2, key2);
        fibHeap.extractMin();
        assertEquals(fibHeap.getMin().getKey(), 18);
    }

    // Test 9 : extract minimum from a heap without children
    @Test
    public void extractMinFromHeapWithoutChildren() throws Exception {
        
        String obj1 = "3"; int key1 = 3;
        fibHeap.insert(obj1, key1);
        fibHeap.extractMin();
        assertNull(fibHeap.getMin());
    }

    // Test 10 : extract minimum from a heap with children
    @Test
    public void extractMinFromHeapWithChildren() throws Exception {
        
        String obj1 = "3"; int key1 = 3;
        String obj2 = "18"; int key2 = 18;
        String obj3 = "52"; int key3 = 52;
        String obj4 = "38"; int key4 = 38;
        String obj5 = "39"; int key5 = 39;

        fibHeap.insert(obj1, key1);
        fibHeap.insert(obj2, key2);
        fibHeap.insert(obj3, key3);
        fibHeap.insert(obj4, key4);
        fibHeap.insert(obj5, key5);

        fibHeap.extractMin();
        assertEquals(fibHeap.getMin().getKey(),18);
    }

    // Test 11 : extract minimum from a greater heap
    @Test
    public void extractMinFromGreaterHeap() throws Exception {
        
        String obj1 = "23"; int key1 = 23;
        String obj2 = "7"; int key2 = 7;
        String obj3 = "21"; int key3 = 21;
        String obj4 = "3"; int key4 = 3;
        String obj5 = "18"; int key5 = 18;
        String obj6 = "39"; int key6 = 39;
        String obj7 = "52"; int key7 = 52;
        String obj8 = "38"; int key8 = 38;
        String obj9 = "41"; int key9 = 41;
        String obj10 = "17"; int key10 = 17;
        String obj11 = "30"; int key11 = 30;
        String obj12 = "24"; int key12 = 24;
        String obj13 = "26"; int key13 = 26;
        String obj14 = "35"; int key14 = 35;
        String obj15 = "46"; int key15 = 46;

        fibHeap.insert(obj1, key1);
        fibHeap.insert(obj2, key2);
        fibHeap.insert(obj3, key3);
        fibHeap.insert(obj4, key4);
        fibHeap.insert(obj5, key5);
        fibHeap.insert(obj6, key6);
        fibHeap.insert(obj7, key7);
        fibHeap.insert(obj8, key8);
        fibHeap.insert(obj9, key9);
        fibHeap.insert(obj10, key10);
        fibHeap.insert(obj11, key11);
        fibHeap.insert(obj12, key12);
        fibHeap.insert(obj13, key13);
        fibHeap.insert(obj14, key14);
        fibHeap.insert(obj15, key15);

        fibHeap.extractMin();
        assertEquals(fibHeap.getMin().getKey(), 7);
    }

    // Test 12 : decrease key in a heap with one FibNode
    @Test
    public void decreaseKeyInHeapWithOneNode() throws Exception {
        String obj1 = "3"; int key1 = 3;
        fibHeap.insert(obj1, key1);
        fibHeap.decreaseKey(obj1,2);
        assertEquals(fibHeap.getMin().getKey(), 2);
    }

    // Test 13 : decrease key which represents a FibNode that is not a child of any other FibNode
    @Test
    public void decreaseKeyBeingNotChild() throws Exception {
        

        String obj1 = "23"; int key1 = 23;
        String obj2 = "7"; int key2 = 7;
        String obj3 = "21"; int key3 = 21;
        String obj4 = "3"; int key4 = 3;
        String obj5 = "18"; int key5 = 18;
        String obj6 = "39"; int key6 = 39;
        String obj7 = "52"; int key7 = 52;
        String obj8 = "38"; int key8 = 38;
        String obj9 = "41"; int key9 = 41;
        String obj10 = "17"; int key10 = 17;
        String obj11 = "30"; int key11 = 30;
        String obj12 = "24"; int key12 = 24;
        String obj13 = "26"; int key13 = 26;
        String obj14 = "35"; int key14 = 35;
        String obj15 = "46"; int key15 = 46;

        fibHeap.insert(obj1, key1);
        fibHeap.insert(obj2, key2);
        fibHeap.insert(obj3, key3);
        fibHeap.insert(obj4, key4);
        fibHeap.insert(obj5, key5);
        fibHeap.insert(obj6, key6);
        fibHeap.insert(obj7, key7);
        fibHeap.insert(obj8, key8);
        fibHeap.insert(obj9, key9);
        fibHeap.insert(obj10, key10);
        fibHeap.insert(obj11, key11);
        fibHeap.insert(obj12, key12);
        fibHeap.insert(obj13, key13);
        fibHeap.insert(obj14, key14);
        fibHeap.insert(obj15, key15);

        fibHeap.decreaseKey(obj4,2);
        assertEquals(fibHeap.getMin().getKey(), 2);
    }

    // Test 14 : decrease key which represents a FibNode that is a child of another FibNode
    @Test
    public void decreaseKeyBeingChild() throws Exception {
        

        String obj1 = "23"; int key1 = 23;
        String obj2 = "7"; int key2 = 7;
        String obj3 = "21"; int key3 = 21;
        String obj4 = "3"; int key4 = 3;
        String obj5 = "18"; int key5 = 18;
        String obj6 = "39"; int key6 = 39;
        String obj7 = "52"; int key7 = 52;
        String obj8 = "38"; int key8 = 38;
        String obj9 = "41"; int key9 = 41;
        String obj10 = "17"; int key10 = 17;
        String obj11 = "30"; int key11 = 30;
        String obj12 = "24"; int key12 = 24;
        String obj13 = "26"; int key13 = 26;
        String obj14 = "35"; int key14 = 35;
        String obj15 = "46"; int key15 = 46;

        fibHeap.insert(obj1, key1);
        fibHeap.insert(obj2, key2);
        fibHeap.insert(obj3, key3);
        fibHeap.insert(obj4, key4);
        fibHeap.insert(obj5, key5);
        fibHeap.insert(obj6, key6);
        fibHeap.insert(obj7, key7);
        fibHeap.insert(obj8, key8);
        fibHeap.insert(obj9, key9);
        fibHeap.insert(obj10, key10);
        fibHeap.insert(obj11, key11);
        fibHeap.insert(obj12, key12);
        fibHeap.insert(obj13, key13);
        fibHeap.insert(obj14, key14);
        fibHeap.insert(obj15, key15);

        fibHeap.decreaseKey(obj5,1);
        assertEquals(fibHeap.getMin().getKey(), 1);
    }

    // Test 15 : decrease key is invalid
    @Test
    public void decreaseKeyBeingInvalid() throws Exception {
        

        String obj1 = "23"; int key1 = 23;
        String obj2 = "7"; int key2 = 7;
        String obj3 = "21"; int key3 = 21;
        String obj4 = "3"; int key4 = 3;
        String obj5 = "18"; int key5 = 18;
        String obj6 = "39"; int key6 = 39;
        String obj7 = "52"; int key7 = 52;
        String obj8 = "38"; int key8 = 38;
        String obj9 = "41"; int key9 = 41;
        String obj10 = "17"; int key10 = 17;
        String obj11 = "30"; int key11 = 30;
        String obj12 = "24"; int key12 = 24;
        String obj13 = "26"; int key13 = 26;
        String obj14 = "35"; int key14 = 35;
        String obj15 = "46"; int key15 = 46;

        fibHeap.insert(obj1, key1);
        fibHeap.insert(obj2, key2);
        fibHeap.insert(obj3, key3);
        fibHeap.insert(obj4, key4);
        fibHeap.insert(obj5, key5);
        fibHeap.insert(obj6, key6);
        fibHeap.insert(obj7, key7);
        fibHeap.insert(obj8, key8);
        fibHeap.insert(obj9, key9);
        fibHeap.insert(obj10, key10);
        fibHeap.insert(obj11, key11);
        fibHeap.insert(obj12, key12);
        fibHeap.insert(obj13, key13);
        fibHeap.insert(obj14, key14);
        fibHeap.insert(obj15, key15);

        fibHeap.decreaseKey(obj3,5);
        assertEquals(fibHeap.getMin().getKey(), 3);
    }

    // Test 16 : decrease key is invalid
    @Test
    public void invalidDecreaseKey() throws Exception {
        

        String obj1 = "23"; int key1 = 23;
        String obj2 = "7"; int key2 = 7;

        fibHeap.insert(obj1, key1);
        fibHeap.insert(obj2, key2);

        fibHeap.decreaseKey(obj1, 62);
        assertEquals(fibHeap.getMin().getKey(), 7);
    }

    // Test 17 : extract min and decrease key
    @Test
    public void decreaseKeyFibHeap() throws Exception {
        

        String obj1 = "23"; int key1 = 23;
        String obj2 = "7"; int key2 = 7;
        String obj3 = "21"; int key3 = 21;
        String obj4 = "3"; int key4 = 3;
        String obj5 = "18"; int key5 = 18;
        String obj6 = "39"; int key6 = 39;
        String obj7 = "52"; int key7 = 52;
        String obj8 = "38"; int key8 = 38;
        String obj9 = "41"; int key9 = 41;
        String obj10 = "17"; int key10 = 17;
        String obj11 = "30"; int key11 = 30;
        String obj12 = "24"; int key12 = 24;
        String obj13 = "26"; int key13 = 26;
        String obj14 = "35"; int key14 = 35;
        String obj15 = "46"; int key15 = 46;

        fibHeap.insert(obj1, key1);
        fibHeap.insert(obj2, key2);
        fibHeap.insert(obj3, key3);
        fibHeap.insert(obj4, key4);
        fibHeap.insert(obj5, key5);
        fibHeap.insert(obj6, key6);
        fibHeap.insert(obj7, key7);
        fibHeap.insert(obj8, key8);
        fibHeap.insert(obj9, key9);
        fibHeap.insert(obj10, key10);
        fibHeap.insert(obj11, key11);
        fibHeap.insert(obj12, key12);
        fibHeap.insert(obj13, key13);
        fibHeap.insert(obj14, key14);
        fibHeap.insert(obj15, key15);
        
        fibHeap.extractMin();
        fibHeap.decreaseKey(obj6, key4);
        assertEquals(fibHeap.extractMin(), obj6);
    }  
    
    // Test 18 : check left and right of FibNode
    @Test
    public void rightLeftFibNode() throws Exception {
        String obj1 = "23"; int key1 = 23;
        fibHeap.insert(obj1, key1);

        assertEquals(fibHeap.getMin().getRight(), fibHeap.getMin());
        assertEquals(fibHeap.getMin().getLeft(), fibHeap.getMin());
    }    
    
    // Test 19 : check child and parent of FibNode
    @Test
    public void childParentFibNode() throws Exception {
        String obj1 = "23"; int key1 = 23;
        fibHeap.insert(obj1, key1);

        assertEquals(fibHeap.getMin().getChild(), null);
        assertEquals(fibHeap.getMin().getParent(), null);
    }
    
    // Test 20 : check member of FibNode
    @Test
    public void memberFibNode() throws Exception {
        String obj1 = "23"; int key1 = 23;
        fibHeap.insert(obj1, key1);

        assertEquals(fibHeap.member(obj1), true);
    }
    
    // Test 21 : delete a FibNode
    @Test
    public void deleteFibNode() throws Exception {
        String obj1 = "23"; int key1 = 23;
        String obj2 = "43"; int key2 = 43;
        fibHeap.insert(obj1, key1);
        fibHeap.insert(obj2, key2);

        assertEquals(fibHeap.delete(obj1), obj1);
    }

    // Test 22 : delete a FibNode from an empty heap
    @Test
    public void emptyDeleteFibNode() throws Exception {
        String obj1 = "23"; 
        try{
        		fibHeap.delete(obj1);
        }catch(Exception e){
        		assertEquals(e.toString(), new NullPointerException().toString());
        }
    }
    
    // Test 23 : check empty heap
    @Test
    public void emptyMemberFibNode() throws Exception {
        assertEquals(fibHeap.empty(), true);
    }
    
    // Test 24 : check nonEmpty heap
    @Test
    public void nonEmptyCheckFibHeap() throws Exception {
        String obj1 = "23"; int key1 = 23;
        fibHeap.insert(obj1, key1);
    		assertEquals(fibHeap.empty(), false);
    }
    
    // Test 25 : get FibNode in rootList from an empty heap
    @Test
    public void getNodesInRootListFromEmptyFibHeap() throws Exception {
    		assertEquals(fibHeap.getNoOfNodesInRootList(), 0);
    }
   
    // Test 26 : check toString() method
    @Test
    public void toStringCheck() throws Exception {
        String obj1 = "23"; int key1 = 23;
        fibHeap.insert(obj1, key1);
        final String s = "23 with degree 0";
        assertEquals(fibHeap.getMin().toString(), s);
    }
    
    
    
  

}

