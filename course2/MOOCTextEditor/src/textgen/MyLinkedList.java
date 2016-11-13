package textgen;

import java.util.AbstractList;


/** A class that implements a doubly linked list
 * 
 * @author UC San Diego Intermediate Programming MOOC team
 *
 * @param <E> The type of the elements stored in the list
 */
public class MyLinkedList<E> extends AbstractList<E> {
	LLNode<E> head;
	LLNode<E> tail;
	int size;

	/** Create a new empty LinkedList */
	public MyLinkedList() {
		size = 0;
		
		// initialize sentinel nodes
		head = new LLNode<E>(null);
		tail = new LLNode<E>(null);
		
		// set pointers
		head.next = tail;
		tail.prev = head;
	}

	/**
	 * Appends an element to the end of the list
	 * @param element The element to add
	 */
	public boolean add(E element ) 
	{
		// call general add method using size to refer to end of list
		add(size, element);
		return true;
	}

	/** Get the element at position index 
	 * @throws IndexOutOfBoundsException if the index is out of bounds. */
	public E get(int index) 
	{
		// check for exception
		if (index >= size || index < 0)
		{
			throw new IndexOutOfBoundsException();
		}
		
		// call helper method to return element at position index
		return getNode(index).data;
	}

	/**
	 * Add an element to the list at the specified index
	 * @param The index where the element should be added
	 * @param element The element to add
	 * @throws IndexOutOfBoundsException if the index is out of bounds
	 * @throws NullPointerException if element is null
	 */
	public void add(int index, E element ) 
	{
		// check for exceptions
		if (index > size || index < 0)
		{
			throw new IndexOutOfBoundsException();
		}
		if (element == null)
		{
			throw new NullPointerException();
		}
		
		// create nodes
		LLNode<E> previousNode = (index == 0) ? head : getNode(index - 1);
		LLNode<E> nextNode = (size == index) ? tail : getNode(index);
		LLNode<E> newNode = new LLNode<E>(element, nextNode, previousNode);
		
		// update node pointers
		previousNode.next = newNode;
		nextNode.prev = newNode;
		
		// increment linked list size
		size++;
	}

	/** Return the size of the list */
	public int size() 
	{
		return size;
	}

	/** Remove a node at the specified index and return its data element.
	 * @param index The index of the element to remove
	 * @return The data element removed
	 * @throws IndexOutOfBoundsException If index is outside the bounds of the list
	 * 
	 */
	public E remove(int index) 
	{
		// TODO: Implement this method
		return null;
	}

	/**
	 * Set an index position in the list to a new element
	 * @param index The index of the element to change
	 * @param element The new element
	 * @return The element that was replaced
	 * @throws IndexOutOfBoundsException if the index is out of bounds.
	 */
	public E set(int index, E element) 
	{
		// TODO: Implement this method
		return null;
	}
	
	/** 
	 * Get a node at index.
	 * @param index The index of an element to get
	 * @return The node to get
	 */
	private LLNode<E> getNode(int index) 
	{
		LLNode<E> node = head;
		
		// iterate over linked list counting head sentinel node
		// as index + 1
		for (int i = 0; i < index + 1; i++) 
		{
			node = node.next;
		}
		
		return node;
	}
}

class LLNode<E> 
{
	LLNode<E> prev;
	LLNode<E> next;
	E data;

	public LLNode(E e) 
	{
		this.data = e;
		this.prev = null;
		this.next = null;
	}
	
	public LLNode(E e, LLNode<E> next, LLNode<E> prev)
	{
		this.data = e;
		this.prev = prev;
		this.next = next; 
	}
}
