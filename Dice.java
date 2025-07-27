package boggle;

import java.util.Random;

/**
 * @author Dorothy Lin, Abby Fung 
 * 2024.06.10 
 * Description: This is the class for the dice objects; 25 will be instantiated for a game of Boggle.
 */
public class Dice {
	/* INSTANCE VARIABLES */
	private String face;
	private String letters;
	private Random randomGen;
	private boolean pressed;

	/**
	 * Default Constructor Assigns letters and a face to a dice according to its
	 * initial position on the board.
	 * 
	 * @param r the row position of the dice instantiated
	 * @param c the column position of the dice instantiated
	 */
	public Dice(int r, int c) {
		String[][] diceSet = { { "AAAFRS", "AEEGMU", "CEIILT", "DHHNOT", "FIPRSY" },
				{ "AAEEEE", "AEGMNN", "CEILPT", "DHLNOR", "GORRVW" },
				{ "AAFIRS", "AFIRSY", "CEIPST", "EIIITT", "HIPRRY" },
				{ "ADENNN", "BJKQXZ", "DDLNOR", "EMOTTT", "NOOTUW" },
				{ "AEEEEM", "CCNSTW", "DHHLOR", "ENSSSU", "OOOTTU" } };
		this.letters = diceSet[r][c];
		this.randomGen = new Random();
		int firstFace = randomGen.nextInt(6);
		this.face = String.valueOf(letters.charAt(firstFace));
		this.pressed = false;
	}

	/* GETTERS AND SETTERS */
	public String getFace() {
		return face;
	}

	public void setFace(String face) {
		this.face = face;
	}

	public String getLetters() {
		return letters;
	}

	public void setLetters(String letters) {
		this.letters = letters;
	}

	public boolean getPressed() {
		return pressed;
	}

	public void setPressed(boolean pressed) {
		this.pressed = pressed;
	}

	/* ACTION METHODS */

	/**
	 * This method rolls a dice, generating one random face using random numbers.
	 */
	public void roll() {
		int newFace = randomGen.nextInt(6);
		face = String.valueOf(letters.charAt(newFace));
	}
}