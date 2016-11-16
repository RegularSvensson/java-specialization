package spelling;

import java.util.List;
import java.util.Queue;
import java.util.Set;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedList;

/** 
 * An trie data structure that implements the Dictionary and the AutoComplete ADT
 * @author You
 *
 */
public class AutoCompleteDictionaryTrie implements  Dictionary, AutoComplete {

    private TrieNode root;
    private int size;
    

    public AutoCompleteDictionaryTrie()
	{
		root = new TrieNode();
	}
	
	
	/** Insert a word into the trie.
	 * For the basic part of the assignment (part 2), you should convert the 
	 * string to all lower case before you insert it. 
	 * 
	 * @return true if the word was successfully added or false if it already exists
	 * in the dictionary.
	 */
	public boolean addWord(String word)
	{
		// start from root of trie
	    TrieNode currNode = root;
	    
	    // iterate over chars in word
	    for (char c : word.toLowerCase().toCharArray()) {
	    	// get childNode from currNode
	    	TrieNode childNode = currNode.getChild(c);
	    	
	    	// check if childNode doesn't exists
	    	if (childNode == null) {
	    		// insert char into currNode
	    		currNode = currNode.insert(c);
	    	}
	    	else {
	    		// update currNode to existing childNode
	    		currNode = childNode;
	    	}
	    }
    	// check if currNode ends a word in trie
    	// and therefore already exists
    	if (currNode.endsWord()) {
    		return false;
    	}
	    
	    // set currNode to an end of a word in trie
	    currNode.setEndsWord(true);
	    
	    return true;
	}
	
	/** 
	 * Return the number of words in the dictionary.  This is NOT necessarily the same
	 * as the number of TrieNodes in the trie.
	 */
	public int size()
	{
		// call helper method
		return sizeOfChildren(root);
	}
	
	/**
	 * Returns number of words in dictionary starting from currNode.
	 * @param currNode
	 * @return number of words in dictionary from currNode
	 */
	private int sizeOfChildren(TrieNode currNode) {
		// create nrWords counter
		int nrWords = 0;
		
		// check if currNode doesn't exist
		if (currNode == null) {
			// return nrWords which is 0
			return nrWords;
		}
		
		// iterate over chars in currNode
		for (char c : currNode.getValidNextCharacters()) {
			// create nextNode to be currNodes child
			TrieNode nextNode = currNode.getChild(c);;
			
			// check if nextNode ends word
			if (nextNode.endsWord()) {
				// increment nrWords
				nrWords++;
			}
			
			// recursively call sizeOfChildren on nextNode
			nrWords += sizeOfChildren(nextNode);
		}
		
		// return nrWords after iteration over chars and
		// recursive method call to count words in dictionary
		return nrWords;
	}
	
	/** Returns whether the string is a word in the trie, using the algorithm
	 * described in the videos for this week. */
	@Override
	public boolean isWord(String s) 
	{
		// start from root of trie
	    TrieNode currNode = root;
	    
	    // iterate over chars in word
	    for (char c : s.toLowerCase().toCharArray()) {
	    	// get childNode from currNode
	    	TrieNode childNode = currNode.getChild(c);
	    	
	    	// check if childNode doesn't exists
	    	if (childNode == null) {
	    		// s is not a word, therefore return false
	    		return false;
	    	}
	    	else {
	    		// update currNode to existing childNode
	    		currNode = childNode;
	    	}
	    }
	    
	    // return true if last node ends a word and is therefore a word
	    return (currNode.endsWord());
	}

	/** 
     * Return a list, in order of increasing (non-decreasing) word length,
     * containing the numCompletions shortest legal completions 
     * of the prefix string. All legal completions must be valid words in the 
     * dictionary. If the prefix itself is a valid word, it is included 
     * in the list of returned words. 
     * 
     * The list of completions must contain 
     * all of the shortest completions, but when there are ties, it may break 
     * them in any order. For example, if there the prefix string is "ste" and 
     * only the words "step", "stem", "stew", "steer" and "steep" are in the 
     * dictionary, when the user asks for 4 completions, the list must include 
     * "step", "stem" and "stew", but may include either the word 
     * "steer" or "steep".
     * 
     * If this string prefix is not in the trie, it returns an empty list.
     * 
     * @param prefix The text to use at the word stem
     * @param numCompletions The maximum number of predictions desired.
     * @return A list containing the up to numCompletions best predictions
     */@Override
     public List<String> predictCompletions(String prefix, int numCompletions) 
     {
		 // This method should implement the following algorithm:
		 // 1. Find the stem in the trie.  If the stem does not appear in the trie, return an
		 //    empty list
		 // 2. Once the stem is found, perform a breadth first search to generate completions
		 //    using the following algorithm:
		 //    Create a queue (LinkedList) and add the node that completes the stem to the back
		 //       of the list.
		 //    Create a list of completions to return (initially empty)
		 //    While the queue is not empty and you don't have enough completions:
		 //       remove the first Node from the queue
		 //       If it is a word, add it to the completions list
		 //       Add all of its child nodes to the back of the queue
		 // Return the list of completions
		 
    	// start from root of trie
  		TrieNode currNode = root;
  		
  		// create list of strings
 		List<String> completions = new ArrayList<String>();

	    // iterate over chars in prefix
	    for (char c : prefix.toLowerCase().toCharArray()) {
	    	// check if currNode has no child
 			if (currNode.getChild(c) == null) {
 				// return completions which is currently an empty list
 				return completions;
 			}
 			
 			// update currNode to currNode's child
 			currNode = currNode.getChild(c);
 		}
	    
	    // create queue of TrieNodes
 		Queue<TrieNode> queue = new LinkedList<TrieNode>();
 		
 		// add currNode to queue
 		queue.add(currNode);
 		
 		// loop while queue is not empty and list of completions
 		// is less than number completions supposed to complete
 		while (!queue.isEmpty() && completions.size() < numCompletions) {
 			// update currNode to the head of the queue
 			currNode = queue.remove();
 			
 			// check if currNode ends word
 			if (currNode.endsWord()) {
 				// add currNode text to list of completions
 				completions.add(currNode.getText());
 			}
 			
 			// iterate over chars in currNode
 			for (char c : currNode.getValidNextCharacters()) {
 				// get childNode from currNode
 				TrieNode childNode = currNode.getChild(c);
 				
 				// add childNode to queue
 				queue.add(childNode);
 			}
 		}
 		
 		// return list of completions
 		return completions;
     }

 	// For debugging
 	public void printTree()
 	{
 		printNode(root);
 	}
 	
 	/** Do a pre-order traversal from this node down */
 	public void printNode(TrieNode curr)
 	{
 		if (curr == null) 
 			return;
 		
 		System.out.println(curr.getText());
 		
 		TrieNode next = null;
 		for (Character c : curr.getValidNextCharacters()) {
 			next = curr.getChild(c);
 			printNode(next);
 		}
 	}
 	

	
}