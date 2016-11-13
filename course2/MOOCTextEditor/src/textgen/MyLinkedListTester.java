/**
 * 
 */
package textgen;

import static org.junit.Assert.*;

import java.util.LinkedList;

import org.junit.Before;
import org.junit.Test;

/**
 * @author UC San Diego MOOC team
 *
 */
public class MyLinkedListTester {

	private static final int LONG_LIST_LENGTH =10; 

	MyLinkedList<String> shortList;
	MyLinkedList<Integer> emptyList;
	MyLinkedList<Integer> longerList;
	MyLinkedList<Integer> list1;
	
	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
		// Feel free to use these lists, or add your own
	    shortList = new MyLinkedList<String>();
		shortList.add("A");
		shortList.add("B");
		emptyList = new MyLinkedList<Integer>();
		longerList = new MyLinkedList<Integer>();
		for (int i = 0; i < LONG_LIST_LENGTH; i++)
		{
			longerList.add(i);
		}
		list1 = new MyLinkedList<Integer>();
		list1.add(65);
		list1.add(21);
		list1.add(42);
		
	}

	
	/** Test if the get method is working correctly.
	 */
	/*You should not need to add much to this method.
	 * We provide it as an example of a thorough test. */
	@Test
	public void testGet()
	{
		//test empty list, get should throw an exception
		try {
			emptyList.get(0);
			fail("Check out of bounds");
		}
		catch (IndexOutOfBoundsException e) {
			
		}
		
		// test short list, first contents, then out of bounds
		assertEquals("Check first", "A", shortList.get(0));
		assertEquals("Check second", "B", shortList.get(1));
		
		try {
			shortList.get(-1);
			fail("Check out of bounds");
		}
		catch (IndexOutOfBoundsException e) {
		
		}
		try {
			shortList.get(2);
			fail("Check out of bounds");
		}
		catch (IndexOutOfBoundsException e) {
		
		}
		// test longer list contents
		for(int i = 0; i<LONG_LIST_LENGTH; i++ ) {
			assertEquals("Check "+i+ " element", (Integer)i, longerList.get(i));
		}
		
		// test off the end of the longer array
		try {
			longerList.get(-1);
			fail("Check out of bounds");
		}
		catch (IndexOutOfBoundsException e) {
		
		}
		try {
			longerList.get(LONG_LIST_LENGTH);
			fail("Check out of bounds");
		}
		catch (IndexOutOfBoundsException e) {
		}
		
	}
	
	
	/** Test removing an element from the list.
	 * We've included the example from the concept challenge.
	 * You will want to add more tests.  */
	@Test
	public void testRemove()
	{
		// try to remove element out of bounds
		try 
		{
			emptyList.remove(0);
			fail("Check out of bounds");
		}
		catch (IndexOutOfBoundsException e) 
		{
			
		}
		
		// assert that remove method works
		int a = list1.remove(0);
		assertEquals("Remove: check a is correct ", 65, a);
		assertEquals("Remove: check element 0 is correct ", (Integer) 21, list1.get(0));
		assertEquals("Remove: check size is correct ", 2, list1.size());
		
		// try to remove elements out of bounds
		try 
		{
			emptyList.remove(2);
			fail("Check out of bounds");
		}
		catch (IndexOutOfBoundsException e) 
		{
		
		}
		try 
		{
			emptyList.remove(-1);
			fail("Check out of bounds");
		}
		catch (IndexOutOfBoundsException e) 
		{
		
		}
	}
	
	/** Test adding an element into the end of the list, specifically
	 *  public boolean add(E element)
	 * */
	@Test
	public void testAddEnd()
	{
		// assert that add method returns true
        boolean added = list1.add(10);
		assertTrue(added);
		
		// assert that last element in linked list equals added element
		assertEquals("Add: check add is correct ", (Integer) 10, list1.get(list1.size() - 1));
	}

	
	/** Test the size of the list */
	@Test
	public void testSize()
	{
		// assert that size method works
		Integer size = list1.size;
		assertEquals("Size of list is ", (Integer) 3, size);
		assertEquals("Size of empty list is ", (Integer) 0, (Integer) emptyList.size);
	}

	
	
	/** Test adding an element into the list at a specified index,
	 * specifically:
	 * public void add(int index, E element)
	 * */
	@Test
	public void testAddAtIndex()
	{
        // try to add numbers to emptyList out of bounds
		try
		{
			emptyList.add(2, 0);
			fail("Check out of bounds.");
		}
		catch (IndexOutOfBoundsException e)
		{
			
		}
		try
		{
			emptyList.add(-1, 0);
			fail("Check out of bounds.");
		}
		catch (IndexOutOfBoundsException e)
		{
			
		}
		
		// add numbers to emptyList
		emptyList.add(0, 1);
		emptyList.add(1, 2);
		emptyList.add(2, 3);
		
		// assert correct numbers added to emptyList
		assertEquals("Item at first is ", (Integer) 1, emptyList.get(0));
		assertEquals("Item at first is ", (Integer) 2, emptyList.get(1));
		assertEquals("Item at first is ", (Integer) 3, emptyList.get(2));
	}
	
	/** Test setting an element in the list */
	@Test
	public void testSet()
	{
	    // TODO: implement this test
	    
	}
	
	
	// TODO: Optionally add more test methods.
	
}
