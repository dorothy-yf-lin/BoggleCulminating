package boggle;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import javax.swing.*;

/**
 * @author Dorothy Lin, Abby Fung Due Date: 2024.06.10 Description: This is the
 *         AI player class. It represents the computer playing against the human
 *         player
 */

public class AIPlayer extends Player {
	/* INSTANCE VARIABLES */
	private Dictionary dictionary;
	private int minLength;
	private String difficulty;
	private JOptionPane notification;

	/**
	 * Parameterized constructor for AI.
	 * 
	 * @param difficulty the difficulty level of the AI; determines how smart it
	 *                   will be.
	 * @param minLength  the minimum length of words found.
	 */
	public AIPlayer(String diff, int length, int score) throws FileNotFoundException {
		super(diff, score);
		dictionary = new Dictionary();
		difficulty = diff;
		minLength = length;
		super.foundWords = new ArrayList<String>();
		setUserName("AI");
	}

	/**
	 * This is the method for the AI's turn. It randomly selects a starting location
	 * to search for a word, then searches the surrounding area around it. It
	 * chooses a random square on a 3x3 by section to begin recursive word search.
	 * 
	 * @param board this 2d array represents the current board.
	 * @throws InterruptedException
	 * @throws FileNotFoundException
	 */
	@Override
	public String takeTurn(String[][] board) throws FileNotFoundException, InterruptedException {
		// announce to player that it is the AI's turn
		notification = new JOptionPane();
		JOptionPane.showMessageDialog(notification, "It is now the AI's turn");

		// find words; make sure no duplicates.
		String foundWord = findWords(board);
		alreadyFoundWord(foundWord);

		// if there are no found words or all found words are duplicates, return a blank
		// string.
		if (foundWord.equals(null)) {
			return "";
		} else {
			return foundWords.get(foundWords.size() - 1); // get the previously added found word
		}
	}

	/**
	 * Checks if a word has already been found. If the word has not been found
	 * before, adds it to the list of found words.
	 *
	 * @param foundWord The word to check if it has already been found.
	 * @return true if the word has already been found before; false otherwise.
	 */
	public boolean alreadyFoundWord(String foundWord) {
		if (!(foundWords.contains(foundWord))) {
			foundWords.add(foundWord);
			return false;
		}
		return true;
	}

	/**
	 * Finds all valid words on the given board using recursive word search.
	 * 
	 * @param board The 2D array representing the game board.
	 * @return A string containing all valid words found on the board.
	 * @throws FileNotFoundException If a file required for processing is not found.
	 * @throws InterruptedException  If the thread is interrupted during word
	 *                               search.
	 */
	public String findWords(String[][] board) throws FileNotFoundException, InterruptedException {
		// start recursive word search by generating random square to check
		boolean[][] pressed = new boolean[3][3];
		String fullWord = "";
		int r = (int) (Math.random() * 3);
		int c = (int) (Math.random() * 3);
		return recursiveWordSearch(pressed, r, c, board, fullWord);
	}

	/**
	 * This method recursively checks every adjacent square of a randomly chosen
	 * box; it returns when a valid word is found.
	 * 
	 * @param pressed the 3x3 area that surrounds the current box being checked
	 * @param r       the row of pressed
	 * @param c       the column of pressed
	 * @param board   the letter board.
	 * @throws FileNotFoundException
	 * @throws InterruptedException
	 */
	private String recursiveWordSearch(boolean[][] pressed, int r, int c, String[][] board, String fullWord)
			throws FileNotFoundException, InterruptedException {
		// Append letter to string
		fullWord += board[r][c];
		// set current button as visited
		pressed[r][c] = true;
		// counter to see if full dictionary has been checked
		int count = 0;

		if (dictionary.verifyWord(fullWord) == true && fullWord.length() >= minLength
				&& !alreadyFoundWord(fullWord)) {
				count++;
			if (difficulty.equals("Easy")) {
				// This ensures the easy AI can only guess words at the minimum length
				if (fullWord.length() == minLength) {
					return fullWord;
				}
			} else if (difficulty.equals("Medium")) {
				// This ensures the medium AI can guess words up to 2 more letters after
				// minLength.
				if (fullWord.length() >= minLength && fullWord.length() <= minLength + 2) {
					return fullWord;
				}
			} else if (difficulty.equals("Hard")) {
				// This ensures the hard AI can guess words up to 3 more letters after
				// minLength.
				if (fullWord.length() >= minLength && fullWord.length() <= minLength + 3) {
					return fullWord;
				}
			}
		} else if (count == 109583) { // max number words
			return null;
		}

		// recursively search board
		for (int row = r - 1; row <= r + 1 && row < 3; row++) {
			for (int col = c - 1; col <= c + 1 && col < 3; col++) {
				if (row >= 0 && col >= 0 && !pressed[row][col]) {
					recursiveWordSearch(pressed, row, col, board, fullWord);
				}
			}
		}
		// reset current button to not visited
		pressed[r][c] = false;
		return "";
	}

	// SETTER //
	public void setMinLength(int minLength) {
		this.minLength = minLength;
	}
}