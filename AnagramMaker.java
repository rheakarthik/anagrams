
/**
 *  AnagramMaker
 *  This program takes in a str given by the user to make as many anagrams
 *  as was specified by the user as well. While making anagrams, the program
 *  considers the amount of desired words per anagram that the user has asked
 *  for. The program calculates the anagrams recursively.
 *
 *  Requires the WordUtilities, SortMethods, Prompt, and FileUtils classes
 *
 *      @author Rhea Karthik
 *      @since  January 18th, 2022
 */
 
import java.util.ArrayList;
public class AnagramMaker {
								
	private final String FILE_NAME = "randomWords.txt";	// file containing all words
	
	private ArrayList<String> anagrams;
	private String origPhrase;
	
	private WordUtilities wu;	// the word utilities for building the word
								// database, sorting the database,
								// and finding all words that match
								// a string of characters
	
	// variables for constraining the print output of AnagramMaker
	private int numWords;		// the number of words in a phrase to print
	private int maxPhrases;		// the maximum number of phrases to print
	private int numPhrases;		// the number of phrases that have been printed
		
	/*	Initialize the database inside WordUtilities
	 *	The database of words does NOT have to be sorted for AnagramMaker to work,
	 *	but the output will appear in order if you DO sort.
	 */
	public AnagramMaker() {
		anagrams = new ArrayList<String>();
		origPhrase = "";
		
		wu = new WordUtilities();
		wu.readWordsFromFile(FILE_NAME);
		wu.sortWords();
	}
	
	public static void main(String[] args) {
		AnagramMaker am = new AnagramMaker();
		am.run();
	}
	
	/**	The top routine that prints the introduction and runs the anagram-maker.
	 */
	public void run() {
		printIntroduction();
		runAnagramMaker();
		System.out.println("\nThanks for using AnagramMaker!\n");
	}
	
	/**
	 *	Print the introduction to AnagramMaker
	 */
	public void printIntroduction() {
		System.out.println("\nWelcome to ANAGRAM MAKER");
		System.out.println("\nProvide a word, name, or phrase and out comes their anagrams.");
		System.out.println("You can choose the number of words in the anagram.");
		System.out.println("You can choose the number of anagrams shown.");
		System.out.println("\nLet's get started!");
	}
	
	/**
	 *	Prompt the user for a phrase of characters, then create anagrams from those
	 *	characters.
	 */
	public void runAnagramMaker()
	{
		String phrase = "";
		do
		{
			//prompts
			phrase = Prompt.getString("Word(s), name or phrase (q to quit) -> ");
			origPhrase = phrase;
			
			if(!phrase.equals("q"))
			{
				numWords = Prompt.getInt("Number of words in anagram -> ");
				numPhrases = Prompt.getInt("Maximum number of anagrams to print -> ");
				maxPhrases = numPhrases;
				anagrams = new ArrayList<String>();
				System.out.println();
				
				//remove bad characters from the phrase
				phrase = removeBadCharacters(phrase);
				
				//call recursive method
				makeAnagrams(phrase);
				System.out.println("\nStopped at "+(maxPhrases-numPhrases)+" anagrams\n");
			}
		}
		while(!phrase.equals("q"));
	}
	 
	 /**
	 *	Makes the anagrams recursively while checking that the conditions for the 
	 *  number of words per anagram and the total number of phrases are met.
	 *  Prints out the anagrams as it makes it.
	 */            
	public void makeAnagrams(String phrase) //passing in phrase
	{
		ArrayList<String> possibleWords = wu.allWords(phrase); //find all words
		
		for(int i = 0; i < possibleWords.size(); i++)
		{
			if(numPhrases == 0) //if no more phrases are to be made, then return
				return;
			if(phrase.length() == 0) //if the phrase length is 0, then no words can be made with it
				return;				 //so return
				
			String word = possibleWords.get(i); //get next possible word from the array of words
			
			int length = 0;
			for(String words: anagrams)
				length += words.length();
			
			if(length >= origPhrase.length() && anagrams.size() <= numWords)
				return;
			else if(anagrams.size()+1 < numWords) 
			{
				anagrams.add(word); //add the word to anagrams
				makeAnagrams(updatePhrase(phrase, word)); //send updated phrase to next call
			}
			else if(anagrams.size()+1 == numWords)
			{
				if(word.length() == phrase.length())
				{
					anagrams.add(word);
					makeAnagrams(updatePhrase(phrase, word)); //send updated phrase to next call
				}
			}
			
			if(anagrams.size() == numWords)
			{
				for(String theWord: anagrams)
					System.out.print(theWord+" ");
				System.out.println(); //print the phrase
				numPhrases--; //the number of phrases left to make goes down
				if(anagrams.size() != 0)
					anagrams.remove(anagrams.size()-1); //remove most recent element
			}
			
			if(i+1 == possibleWords.size())
			{
				if(anagrams.size() != 0)
					anagrams.remove(anagrams.size()-1); //remove again
				return;
			}
		}
		
		//System.out.pritnkln
		if(possibleWords.size() == 0)
		{
			if(anagrams.size() == numWords)
			{
				for(String theWord: anagrams)
					System.out.print(theWord+" ");
				System.out.println(); //print the phrase
				numPhrases--;
				
			}
			if(anagrams.size() != 0)
				anagrams.remove(anagrams.size()-1);
		}
	}
	
	/**
     *  Updates the phrase passed in by removing all the letters used in
     *  the other parameter (the word) from that phrase.
     *  Returns the updated phrase.
     */
	public String updatePhrase(String phrase, String word)
	{
		int index = 0;
		int numRemove = word.length();
		int counter = 0;
		
		if(word == phrase)
			return "";
		
		while(numRemove != 0)
		{
			if(counter >= phrase.length())
				counter = 0;
			
			if(numRemove != 0 && index < word.length() && phrase.charAt(counter) == word.charAt(index))
			{
				phrase = phrase.substring(0, counter) + phrase.substring(counter+1);
				index++;
				counter--;
				numRemove--;
			}
			
			counter++;
		}
		
		return phrase;
	}
	
	/**
     *  removes all characters that are not letters from the string.
     *  this method is called before the call to the recursive method
     *  to remove any characters that anagrams can't be made with.
     */
	public String removeBadCharacters(String phrase)
	{
		int counter = 0;
		phrase = phrase.toUpperCase();
		String theNewPhrase = "";
		
		do
		{
			char theChar = phrase.charAt(counter);
			if(theChar >= 'A' && theChar <= 'Z')
				theNewPhrase += theChar;
			counter++;
		}
		while(counter < phrase.length());
		
		theNewPhrase = theNewPhrase.toLowerCase();
		return theNewPhrase;
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	

	
}
