package boggle;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.*;
import javax.swing.border.Border;

/**
 * ClosingFrame for Boggle Game. Handles displaying the final game results,
 * including the winner, scores, and options for playing again or viewing high
 * scores. Background music and high score achievements are also managed.
 * 
 * @author Angela Yu, Ayla Bilal
 * 2024.05.31
 */
public class ClosingFrame extends JFrame implements ActionListener {
	
	// ATTRIBUTES FOR CLOSING FRAME//
	private JFrame closingFrame;
	private JLabel bgLabel;
	private ImageIcon winIcon;
	private ImageIcon loseIcon;
	private ImageIcon tieIcon;
	private JLabel announceLabel;
	private JLabel scoreLabel;
	private JLabel winnerLabel;
	private JLayeredPane layeredPane;
	private JButton playAgainButton;
	private JButton highScoreButton;
	private Border border;
	private String modePlayed;
	private File file;
	private AudioInputStream audioStream;
	private Clip clip;
	private ImageIcon logoIcon;
	private File highScoreFile;
	private int highScore;
	private Scanner fileScan;
	private FileWriter writer;
	private ImageIcon starIcon;

	/**
	 * Constructor method for ClosingFrame.
	 * 
	 * @param boggle  the instance of Boggle game
	 * @param winner  the winner of the game
	 * @param userOne player one object
	 * @param userTwo player two object
	 * @throws IOException                   if an I/O error occurs
	 * @throws UnsupportedAudioFileException if the audio file is unsupported
	 * @throws LineUnavailableException      if a line is unavailable
	 */
	ClosingFrame(Boggle boggle, String winner, Player userOne, Player userTwo)
			throws UnsupportedAudioFileException, IOException, LineUnavailableException {

		// check if user was playing pvp or against ai
		if (userTwo.getUserName().equals("AI")) {
			modePlayed = "ai";
		} else {
			modePlayed = "pvp";
		}

		// set icons and border
		winIcon = new ImageIcon("win_bg.gif");
		loseIcon = new ImageIcon("lose_bg.gif");
		tieIcon = new ImageIcon("tie_bg.gif");
		logoIcon = new ImageIcon("logo.png");
		border = BorderFactory.createMatteBorder(5, 5, 5, 5, Color.white);
		winner = boggle.getWinner();

		// closing frame
		closingFrame = new JFrame("Bye bye! (づ๑•ᴗ•๑)づ♡");
		closingFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		closingFrame.setSize(600, 600);
		closingFrame.setLayout(null);
		closingFrame.setLocationRelativeTo(null);
		closingFrame.setIconImage(logoIcon.getImage());

		// label for background image icon
		bgLabel = new JLabel();
		bgLabel.setSize(600, 600);
		bgLabel.setOpaque(true);

		// label for announcement text
		announceLabel = new JLabel();
		announceLabel.setText("Final Score:");
		announceLabel.setFont(new Font("Times New Roman", Font.BOLD, 40));
		announceLabel.setForeground(new Color(0xFA5FBF));
		announceLabel.setBounds(50, 30, 500, 100);
		announceLabel.setHorizontalAlignment(JLabel.CENTER);

		// label for score text
		scoreLabel = new JLabel();
		scoreLabel.setText(userOne.getUserName() + ": " + userOne.getPlayerScore() + " pts               "
				+ userTwo.getUserName() + ": " + userTwo.getPlayerScore() + " pts");
		scoreLabel.setFont(new Font("Times New Roman", Font.PLAIN, 30));
		scoreLabel.setForeground(new Color(0xFA5FBF));
		scoreLabel.setBounds(50, 80, 500, 100);
		scoreLabel.setHorizontalAlignment(JLabel.CENTER);

		// Checking for a new high score achieved

		// play again button
		playAgainButton = new JButton("PLAY AGAIN");
		playAgainButton.setFont(new Font("Comic Sans MS", Font.BOLD, 20));
		playAgainButton.setForeground(Color.white);
		playAgainButton.setBackground(new Color(0xFA5FBF));
		playAgainButton.setBounds(305, 430, 175, 60);
		playAgainButton.setBorder(border);
		playAgainButton.setFocusable(false);
		playAgainButton.addActionListener(this);

		// high score button
		highScoreButton = new JButton("HIGH SCORE");
		highScoreButton.setFont(new Font("Comic Sans MS", Font.BOLD, 20));
		highScoreButton.setForeground(Color.white);
		highScoreButton.setBackground(new Color(0xFA5FBF));
		highScoreButton.setBounds(95, 430, 190, 60);
		highScoreButton.setBorder(border);
		highScoreButton.setFocusable(false);
		highScoreButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				File file = new File("highScore.txt");
				if (!file.exists()) {
					JOptionPane.showMessageDialog(null,
							"Please ensure that the highScore.txt file is downloaded in your source folder!",
							"File Not Found :/", JOptionPane.ERROR_MESSAGE);
				} else {
					Scanner fileScan;
					String board = "";
					try {
						fileScan = new Scanner(file);
						while (fileScan.hasNextLine()) {
							board = fileScan.nextLine();
						}
						fileScan.close();
					} catch (FileNotFoundException e1) {
						e1.printStackTrace();
					}
					JOptionPane.showMessageDialog(null, board, "High Score ★彡", JOptionPane.DEFAULT_OPTION);

				}
			}

		});

		// label for winner text
		winnerLabel = new JLabel();
		winnerLabel.setHorizontalAlignment(JLabel.CENTER);
		winnerLabel.setFont(new Font("Times New Roman", Font.BOLD, 50));
		winnerLabel.setForeground(Color.white);
		winnerLabel.setBounds(0, 345, 600, 100);

		if (winner.equals("tie")) {
			winnerLabel.setText("DRAW!!!");
			bgLabel.setIcon(tieIcon);
			achievedHighScore(userOne.getPlayerScore());
		} else if (modePlayed.equals("pvp")) {
			bgLabel.setIcon(winIcon);
			winnerLabel.setText(winner + " WINS!!!");
			if (winner.equals(userOne.getUserName())) {
				achievedHighScore(userOne.getPlayerScore());
			} else if (winner.equals(userTwo.getUserName())) {
				achievedHighScore(userTwo.getPlayerScore());
			}
		} else {
			if (winner.equals("AI")) {
				bgLabel.setIcon(loseIcon);
				winnerLabel.setText("You lose :(");
			} else {
				bgLabel.setIcon(winIcon);
				winnerLabel.setText("You win :)");
				achievedHighScore(userOne.getPlayerScore());
			}
		}

		// make a layered pane and set order of components
		layeredPane = new JLayeredPane();
		layeredPane.setBounds(0, 0, 600, 600);
		layeredPane.add(bgLabel, Integer.valueOf(0));
		layeredPane.add(playAgainButton, Integer.valueOf(1));
		layeredPane.add(announceLabel, Integer.valueOf(1));
		layeredPane.add(scoreLabel, Integer.valueOf(1));
		layeredPane.add(winnerLabel, Integer.valueOf(1));
		layeredPane.add(highScoreButton, Integer.valueOf(2));

		// add layered pane to frame and make frame visible
		closingFrame.add(layeredPane);
		closingFrame.setVisible(true);

		// background music
		file = new File("bg_music.wav");
		audioStream = AudioSystem.getAudioInputStream(file);
		clip = AudioSystem.getClip();
		clip.open(audioStream);
		clip.start();

	}

	/**
	 * Checks if the winner's score qualifies as a high score and updates the high
	 * score file if it does.
	 * 
	 * @param winnerScore the score of the winner to be checked
	 * @throws FileNotFoundException if the high score file is not found
	 */
	public void achievedHighScore(int winnerScore) throws FileNotFoundException {

		starIcon = new ImageIcon("star.gif");
		highScoreFile = new File("highScore.txt");
		highScore = 0;
		fileScan = new Scanner(highScoreFile);
		while (fileScan.hasNextLine()) {
			highScore = Integer.parseInt(fileScan.nextLine());
		}
		fileScan.close();

		if (winnerScore > highScore) {
			JOptionPane.showMessageDialog(null, "New high score achieved!", "Congrats! ヾ( ˃ᴗ˂ )◞ • *✰",
					JOptionPane.INFORMATION_MESSAGE, starIcon);
			try {
				writer = new FileWriter("highScore.txt", true);
				writer.write("\n" + winnerScore);
				writer.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == playAgainButton) {
			clip.close();
			closingFrame.dispose();
			try {
				new Boggle();
			} catch (UnsupportedAudioFileException | IOException | LineUnavailableException e1) {
				e1.printStackTrace();
			}
		}
	}

}