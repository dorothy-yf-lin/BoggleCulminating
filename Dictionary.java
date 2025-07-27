package boggle;

import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * @author Dorothy Lin 
 * Due Date: 2024.06.10 
 * Description: This is the dictionary class; it manages the dictionary file and 
 * 				verifies words.
 */
public class Dictionary {
	
	// INSTANCE VARIABLES
	private ArrayList<String> dictionary;

	/**
	 * Default constructor that initializes the dictionary from a file named
	 * "dictionary.txt".
	 *
	 * @throws FileNotFoundException If the file "dictionary.txt" is not found.
	 */
	public Dictionary() throws FileNotFoundException {
		File file = new File("dictionary.txt");
		Scanner fileScan = new Scanner(file);
		this.dictionary = new ArrayList<String>();
		while (fileScan.hasNextLine()) {
			String word = fileScan.nextLine().toUpperCase();
			dictionary.add(word);
		}
	}

	// GETTERS AND SETTERS //
	public ArrayList<String> getDictionary() {
		return dictionary;
	}

	public void setDictionary(String word) {
		dictionary.add(word);
	}

	// ACTION METHODS //
	/**
	 * This method verifies if a word is in the dictionary, using binary search.
	 * 
	 * @param word the word to be looked for in the dictionary
	 * @return boolean returns whether or not the word is in the dictionary
	 * @throws FileNotFoundException if file is not found
	 */
	public boolean verifyWord(String word) throws FileNotFoundException {
		int low = 0;
		int high = dictionary.size() - 1;
		int output = binaryWordSearch(word, dictionary, low, high);

		if (output == -1) {
			return false;
		} else {
			return true;
		}
	}

	/**
	 * This method performs a recursive binary search
	 * 
	 * @param word
	 * @param dictionary
	 * @param minIndex   minimum boundary to be searched
	 * @param maxIndex   the maximum boundary
	 * @return output returns -1 if the word cannot be found
	 */
	public int binaryWordSearch(String word, ArrayList<String> dictionary, int low, int high) {
		while (low <= high) {

			int middle = low + (high - low) / 2;
			int compare = word.compareTo(dictionary.get(middle)); // get unicode comparison of word to middle word

			if (compare == 0) { // if word is same, return middle index (not -1)
				return middle;
			} else if (compare > 0) { // searches right half if word is lexicographically greater than middle word
				return binaryWordSearch(word, dictionary, middle + 1, high);
			} else { // searches left half
				return binaryWordSearch(word, dictionary, low, middle - 1);
			}
		}
		return -1; // if the low > high, then the word could not be found.
	}
}