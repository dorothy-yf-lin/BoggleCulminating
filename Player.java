package boggle;

import java.io.FileNotFoundException;
import java.util.ArrayList;

/**
 * @author Abby Fung 
 * 2024.06.04 
 * Description: This is the class that is used to create a player.
 */
public abstract class Player {

	/* INSTANCE VARIABLES */
	private String userName;
	private Boolean passed;
	private int playerScore;
	protected ArrayList<String> foundWords;
	protected boolean[][] visited;

	/**
	 * Parameterized constructor
	 * 
	 * @param playerName name of player
	 * @param score      creates score for player
	 */
	public Player(String playerName, int score) {
		setUserName(playerName);
		setPlayerScore(score);
		setPassed(false);
		this.foundWords = new ArrayList<>();
	}

	/* Getters and Setters */
	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public Boolean getPassed() {
		return passed;
	}

	public void setPassed(Boolean passed) {
		this.passed = passed;
	}

	public int getPlayerScore() {
		return playerScore;
	}

	public void setPlayerScore(int playerScore) {
		this.playerScore = playerScore;
	}

	public void setFoundWords(String foundWord) {
		foundWords.add(foundWord);
	}

	public String getFoundWords(int index) {
		return foundWords.get(index);
	}

	public ArrayList<String> getFoundWords() {
		return foundWords;
	}

	/**
	 * Abstract method for the player to take their turn
	 * 
	 * @param board --> A 2D array containing the letters on the board
	 * @return returns the word that is found.
	 * @throws InterruptedException
	 * @throws FileNotFoundException
	 */
	public abstract String takeTurn(String[][] board) throws FileNotFoundException, InterruptedException;

}