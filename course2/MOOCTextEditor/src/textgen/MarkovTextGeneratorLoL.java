package textgen;

import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;
import java.util.Random;

/** 
 * An implementation of the MTG interface that uses a list of lists.
 * @author UC San Diego Intermediate Programming MOOC team 
 */
public class MarkovTextGeneratorLoL implements MarkovTextGenerator {

	// The list of words with their next words
	private List<ListNode> wordList; 
	
	// The starting "word"
	private String starter;
	
	// The random number generator
	private Random rnGenerator;
	
	public MarkovTextGeneratorLoL(Random generator)
	{
		wordList = new LinkedList<ListNode>();
		starter = "";
		rnGenerator = generator;
	}
	
	
	/** Train the generator by adding the sourceText */
	@Override
	public void train(String sourceText)
	{	
		// create array of words from sourceText
		String[] words = sourceText.split("[\\s]+");
		
		// set "starter" to be the first word in the text
		starter = words[0];
		
		// set "prevWord" to be starter
		String prevWord = starter;
		
		// for each word "w" in the source text starting at the second word
		for (int i = 1; i < words.length; i++) {
			String w = words[i];
			
			// check to see if "prevWord" is already a node in the list
			if (wordList.contains(findNode(prevWord))) {
				// if "prevWord" is a node in the list
				// add "w" as a nextWord to the "prevWord" node
				findNode(prevWord).addNextWord(w);
			}
			else {
				// else add a node to the list with "prevWord" as the node's word
				// add "w" as a nextWord to the "prevWord" node
                ListNode node = new ListNode(prevWord);
                node.addNextWord(w);
                wordList.add(node);
			}
			// set "prevWord" = "w"
			prevWord = w;
		}
		// add starter to be a next word for the last word in the source text.
		if (wordList.contains(findNode(prevWord))) {
			findNode(prevWord).addNextWord(starter);
		} 
		else {
            ListNode node = new ListNode(prevWord);
            node.addNextWord(starter);
            wordList.add(node);
		}
	}
	
	/** 
	 * Generate the number of words requested.
	 */
	@Override
	public String generateText(int numWords) {		
		// check if generator has not been trained or
		// numWords is zero
		if (wordList.isEmpty() || numWords == 0) {
			return "";
		}
		
		// set "currWord" to be the starter word
		String currWord = starter;
		
		// add "currWord" to output
		String output = currWord;
		
		// increment numWords left
		int wordsAdded = 1;
		
		// while you need more words
		while (wordsAdded < numWords) {
			// find the "node" corresponding to "currWord" in the list
			ListNode node = findNode(currWord);
			
			// select a random word "w" from the "wordList" for "node"
			String w = node.getRandomNextWord(rnGenerator);
			
			// add "w" to the "output"
			output += " " + w;
			
			// set "currWord" to be "w"
			currWord = w;
			
			// increment number of words added to the list
			wordsAdded++;
		}
		return output;
	}
	
	// Can be helpful for debugging
	@Override
	public String toString()
	{
		String toReturn = "";
		for (ListNode n : wordList)
		{
			toReturn += n.toString();
		}
		return toReturn;
	}
	
	/** Retrain the generator from scratch on the source text */
	@Override
	public void retrain(String sourceText)
	{
		// reset starter
		starter = "";
		
		// clear wordList
		wordList.clear();
		
		// call train method on sourceText
		train(sourceText);
	}
	
	/**
	 * Returns a ListNode if found in wordList that matches word,
	 * else returns null.
	 * @param prevWord
	 * @return node if found or null if not found
	 */
    private ListNode findNode (String word) {
        for (ListNode node : wordList){
            if (node.getWord().equals(word)) {
                return node;
            }
        }
        return null;
    }
	
	
	/**
	 * This is a minimal set of tests.  Note that it can be difficult
	 * to test methods/classes with randomized behavior.   
	 * @param args
	 */
	public static void main(String[] args)
	{
		// feed the generator a fixed random value for repeatable behavior
		MarkovTextGeneratorLoL gen = new MarkovTextGeneratorLoL(new Random(42));
		// String textString = "Hello.  Hello there.  This is a test.  Hello there.  Hello Bob.  Test again.";
		String textString = "hi there hi Leo";
		System.out.println(textString);
		gen.train(textString);
		System.out.println(gen);
		System.out.println(gen.generateText(20));
		String textString2 = "You say yes, I say no, "+
				"You say stop, and I say go, go, go, "+
				"Oh no. You say goodbye and I say hello, hello, hello, "+
				"I don't know why you say goodbye, I say hello, hello, hello, "+
				"I don't know why you say goodbye, I say hello. "+
				"I say high, you say low, "+
				"You say why, and I say I don't know. "+
				"Oh no. "+
				"You say goodbye and I say hello, hello, hello. "+
				"I don't know why you say goodbye, I say hello, hello, hello, "+
				"I don't know why you say goodbye, I say hello. "+
				"Why, why, why, why, why, why, "+
				"Do you say goodbye. "+
				"Oh no. "+
				"You say goodbye and I say hello, hello, hello. "+
				"I don't know why you say goodbye, I say hello, hello, hello, "+
				"I don't know why you say goodbye, I say hello. "+
				"You say yes, I say no, "+
				"You say stop and I say go, go, go. "+
				"Oh, oh no. "+
				"You say goodbye and I say hello, hello, hello. "+
				"I don't know why you say goodbye, I say hello, hello, hello, "+
				"I don't know why you say goodbye, I say hello, hello, hello, "+
				"I don't know why you say goodbye, I say hello, hello, hello,";
		
		System.out.println(textString2);
		gen.retrain(textString2);
		System.out.println(gen);
		System.out.println(gen.generateText(20));
	}

}

/** Links a word to the next words in the list 
 * You should use this class in your implementation. */
class ListNode
{
    // The word that is linking to the next words
	private String word;
	
	// The next words that could follow it
	private List<String> nextWords;
	
	ListNode(String word)
	{
		this.word = word;
		nextWords = new LinkedList<String>();
	}
	
	public String getWord()
	{
		return word;
	}

	public void addNextWord(String nextWord)
	{
		nextWords.add(nextWord);
	}
	
	public String getRandomNextWord(Random generator)
	{
	    // The random number generator should be passed from 
	    // the MarkovTextGeneratorLoL class
	    return nextWords.get(generator.nextInt(nextWords.size()));
	}

	public String toString()
	{
		String toReturn = word + ": ";
		for (String s : nextWords) {
			toReturn += s + "->";
		}
		toReturn += "\n";
		return toReturn;
	}
	
}


