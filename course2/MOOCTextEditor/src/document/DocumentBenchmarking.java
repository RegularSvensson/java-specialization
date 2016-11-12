package document;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;

/** A class for timing the EfficientDocument and BasicDocument classes
 * 
 * @author UC San Diego Intermediate Programming MOOC team
 *
 */

public class DocumentBenchmarking {

	
	public static void main(String [] args) {

	    // Run each test more than once to get bigger numbers and less noise.
	    // You can try playing around with this number.
	    int trials = 100;

	    // The text to test on
	    String textfile = "data/warAndPeace.txt";
		
	    // The amount of characters to increment each step
	    // You can play around with this
		int increment = 20000;

		// The number of steps to run.  
		// You can play around with this.
		int numSteps = 20;
		
		// The number of characters to start with. 
		// You can play around with this.
		int start = 50000;
		
		// print headers
		System.out.println("NumberOfChars\tBasicTime\tEfficientTime");
		
		// run and print out timing results as described in the assignment instructions
		for (int numToCheck = start; numToCheck < numSteps*increment + start; 
				numToCheck += increment)
		{ 	 
			// Print out numToCheck followed by a tab (\t) (NOT a newline)
			 System.out.print(numToCheck + "\t\t");
			 
			 // Read numToCheck characters from the file into a String
			 String text = getStringFromFile(textfile, numToCheck);
			 
			 // Time a loop that runs trials times (trials is the variable above) that:
			 // a. Creates a BasicDocument 
			 // b. Calls fleshScore on this document
			 long startTime = System.nanoTime();
			 for (int i = 0; i < trials; i++) {
				 BasicDocument basicDoc = new BasicDocument(text);
				 basicDoc.getFleschScore();
			 }
			 long endTime = System.nanoTime();
			 double basicTime = endTime - startTime;
			 
			 //  Print out the time it took to complete the loop
			 System.out.print(basicTime / 1000000000 + "\t");
			 
			 // Time a loop that runs trials times (trials is the variable above) that:
			 // a. Creates a EfficientDocument 
			 // b. Calls fleshScore on this document
			 startTime = System.nanoTime();
			 for (int i = 0; i < trials; i++) {
				 EfficientDocument efficientDoc = new EfficientDocument(text);
				 efficientDoc.getFleschScore();
			 }
			 endTime = System.nanoTime();
			 double efficientTime = endTime - startTime;
			 
			 // Print out the time it took to complete the loop
			 System.out.println(efficientTime / 1000000000);
		}
	
	}
	
	/** Get a specified number of characters from a text file
	 * 
	 * @param filename The file to read from
	 * @param numChars The number of characters to read
	 * @return The text string from the file with the appropriate number of characters
	 */
	public static String getStringFromFile(String filename, int numChars) {
		
		StringBuffer s = new StringBuffer();
		try {
			FileInputStream inputFile= new FileInputStream(filename);
			InputStreamReader inputStream = new InputStreamReader(inputFile);
			BufferedReader bis = new BufferedReader(inputStream);
			int val;
			int count = 0;
			while ((val = bis.read()) != -1 && count < numChars) {
				s.append((char)val);
				count++;
			}
			if (count < numChars) {
				System.out.println("Warning: End of file reached at " + count + " characters.");
			}
			bis.close();
		}
		catch(Exception e)
		{
		  System.out.println(e);
		  System.exit(0);
		}
		
		
		return s.toString();
	}
	
}
