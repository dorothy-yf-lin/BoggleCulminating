package boggle;

import java.io.IOException;

import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

/**
 * @author Abby Fung, Angela Yu, Ayla Bilal, Dorothy Lin, Parnia Yazdinia Start
 * Date: 2024.05.31 
 * Due Date: 2024.06.10 
 * Description: This is the main class where the Boggle game will be instantiated. 
 * 		   		It creates a fully-functioning GUI-based game of Boggle Deluxe. A 
 * 				5x5 square board containing 25 random letters will be provided, 
 * 				with players selecting adjacent letters to build words. Players 
 * 				will be give the option to compete against each other or against 
 * 				a computer algorithm.
 */

public class Main {

	/**
	 * Main method to start the Boggle game by creating an instance of an object
	 * Boggle.
	 *
	 * @param args Command-line arguments (not used in this application).
	 * @throws Exception                     If an exception occurs during the
	 *                                       execution of the game.
	 * @throws UnsupportedAudioFileException If an unsupported audio file format is
	 *                                       encountered.
	 * @throws IOException                   If an I/O error occurs.
	 * @throws LineUnavailableException      If a line cannot be opened because it
	 *                                       is unavailable.
	 */
	public static void main(String[] args)
			throws Exception, UnsupportedAudioFileException, IOException, LineUnavailableException {
		Boggle game = new Boggle();
	}

}