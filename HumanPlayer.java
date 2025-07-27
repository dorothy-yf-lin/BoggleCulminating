package boggle;

/**
 * @author Dorothy Lin 
 * Due Date: 2024.06.10 
 * Description: This is the Human player class. It is a subclass of the player class.
 */

public class HumanPlayer extends Player {
	/**
	 * Parameterized constructor Constructs a HumanPlayer object with a specified
	 * username and initial score.
	 *
	 * @param userName    The username of the human player.
	 * @param playerScore The initial score of the human player.
	 */
	public HumanPlayer(String userName, int playerScore) {
		super(userName, playerScore);
	}

	@Override
	/**
	 * @param board a 2d array representing the board
	 * @return returns null, as restrictions to human player will be done in game
	 *         class.
	 */
	public String takeTurn(String[][] board) {
		return null;
	}
}