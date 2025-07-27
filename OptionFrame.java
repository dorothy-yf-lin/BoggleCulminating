package boggle;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

/**
 * @author Angela Yu, Abby Fung, Dorothy Lin 
 * Date: 2024.06.10 
 * Description: This is the frame where a user can choose who they want 
 * 				to play against (another human or AI) and difficulty 
 * 				level. Represents the frame where users can choose game 
 * 				mode and settings.
 */

public class OptionFrame extends JFrame implements ActionListener {

	/* ATTRIBUTES FOR OPTION FRAME */
	private JFrame optionFrame;
	private String userOne;
	private String userTwo;
	private String difficulty;
	private int minLength;
	private int tournamentScore;
	private int timer;
	private Boggle boggle;
	private Player player1;
	private Player player2;

	private JButton pvpButton;
	private JButton aiButton;
	private JLayeredPane layeredPane;
	private ImageIcon optionIcon;
	private JLabel bgLabel;
	private JLabel textLabel;
	private ImageIcon inputIcon1;
	private ImageIcon inputIcon2;
	private Border border;
	private Color darkPink;

	// ATTRIBUTES FOR PLAY FRAME //
	private JFrame playFrame;
	private JLayeredPane gameSettingsPane;
	private JPanel gameSettingsPanel;
	private JLabel roundTimerSliderLabel;
	private JPanel roundTimerSliderPanel;
	private JSlider roundTimerSlider;
	private JLabel tournamentSliderLabel;
	private JPanel tournamentSliderPanel;
	private JSlider tournamentSlider;
	private JLabel minWordLengthLabel;
	private JPanel minWordLengthPanel;
	private JSlider minWordLengthSlider;
	private JButton confirmButton;
	private JButton returnButton;;
	private JPanel topPanel;
	private JLabel names;
	private JLabel instructions;

	// ATTRIBUTES FOR MENU BAR //
	private JMenuBar menuBar;
	private JMenu helpMenu;
	private JMenuItem rulesItem;
	private JMenu aboutMenu;
	private JMenuItem aboutItem;
	private JMenuItem creditsItem;
	private JMenu scoreMenu;
	private JMenuItem highScoreItem;

	private String modePlayed;
	private File file;
	private AudioInputStream audioStream;
	private Clip clip;
	private ImageIcon logoIcon;
	private ImageIcon coinIcon;

	/**
	 * PARAMETIZED CONSTRUCTOR
	 *
	 * @param boggle The Boggle instance to link with this frame.
	 * @throws UnsupportedAudioFileException If the audio file format is not
	 *                                       supported.
	 * @throws IOException                   If there is an IO error.
	 * @throws LineUnavailableException      If a line is unavailable.
	 */
	public OptionFrame(Boggle boggle) throws UnsupportedAudioFileException, IOException, LineUnavailableException {

		this.boggle = boggle;
		border = BorderFactory.createMatteBorder(5, 5, 5, 5, Color.white); // create a border
		darkPink = new Color(0xFA5FBF);

		// make a frame to add components onto
		optionFrame = new JFrame("Choose your mode! °❀⋆.ೃ࿔*:･");
		optionFrame.setSize(600, 600);
		optionFrame.setLayout(null);
		optionFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		optionFrame.setLocationRelativeTo(null);
		optionFrame.setResizable(false);
		logoIcon = new ImageIcon("logo.png");
		optionFrame.setIconImage(logoIcon.getImage());

		optionFrame.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				clip.close();
			}
		});

		// background icon
		optionIcon = new ImageIcon("option_bg.png");

		// icon for dialog boxes
		inputIcon1 = new ImageIcon("input1.png");
		inputIcon2 = new ImageIcon("input2.png");
		coinIcon = new ImageIcon("coin.gif");

		// label for background image icon
		bgLabel = new JLabel();
		bgLabel.setSize(600, 600);
		bgLabel.setIcon(optionIcon);
		bgLabel.setOpaque(true);

		// text
		textLabel = new JLabel();
		textLabel.setText("Choose the mode:");
		textLabel.setFont(new Font("Times New Roman", Font.PLAIN, 45));
		textLabel.setForeground(Color.white);
		textLabel.setHorizontalAlignment(JLabel.CENTER);
		textLabel.setBounds(0, 70, 600, 100);

		// make a player vs player button which user can take action on
		pvpButton = new JButton("Player VS Player");
		pvpButton.setFont(new Font("Comic Sans MS", Font.PLAIN, 24));
		pvpButton.setForeground(Color.white);
		pvpButton.setBackground(darkPink);
		pvpButton.setBounds(75, 375, 200, 90);
		pvpButton.setBorder(border);
		pvpButton.setFocusable(false);
		pvpButton.addActionListener(this);

		// make a player vs ai button which user can take action on
		aiButton = new JButton("Player VS AI");
		aiButton.setFont(new Font("Comic Sans MS", Font.PLAIN, 25));
		aiButton.setForeground(Color.white);
		aiButton.setBackground(darkPink);
		aiButton.setBounds(320, 375, 200, 90);
		aiButton.setBorder(border);
		aiButton.setFocusable(false);
		aiButton.addActionListener(this);

		// make layered pane and set order of components
		layeredPane = new JLayeredPane();
		layeredPane.setBounds(0, 0, 600, 600);
		layeredPane.add(bgLabel, Integer.valueOf(0));
		layeredPane.add(textLabel, Integer.valueOf(1));
		layeredPane.add(pvpButton, Integer.valueOf(2));
		layeredPane.add(aiButton, Integer.valueOf(2));

		// add layered pane to frame and make frame visible
		optionFrame.add(layeredPane);
		optionFrame.setVisible(true);

		// background music
		file = new File("bg_music.wav");
		audioStream = AudioSystem.getAudioInputStream(file);
		clip = AudioSystem.getClip();
		clip.open(audioStream);
		clip.start();

	}

	/* GETTERS AND SETTERS */
	public String getUserOne() {
		return userOne;
	}

	public String getUserTwo() {
		return userTwo;
	}

	public String getDifficulty() {
		return difficulty;
	}

	public int getMinLength() {
		return minLength;
	}

	public int getTournamentScore() {
		return tournamentScore;
	}

	public int getTimer() {
		return timer;
	}

	public void setTimer(int timer) {
		this.timer = timer;
	}

	public Player getPlayer1() {
		return player1;
	}

	public void setPlayer1(Player player1) {
		this.player1 = player1;
	}

	public Player getPlayer2() {
		return player2;
	}

	public void setPlayer2(Player player2) {
		this.player2 = player2;
	}

	public String getModePlayed() {
		return modePlayed;
	}

	/**
	 * Action performed method on components of WelcomeFrame.
	 */
	@Override
	public void actionPerformed(ActionEvent e) {

		if (e.getSource() == pvpButton) {
			modePlayed = "pvp";
			userOne = JOptionPane.showInputDialog(optionFrame, "User 1: Enter your name", "⋅˚₊‧ ୨୧ ‧₊˚ ⋅",
					JOptionPane.OK_CANCEL_OPTION, inputIcon1, null, "").toString();
			if (userOne.equals(null) || userOne.equals("") || userOne.equals(" ")) {
				userOne = "Player 1";
			}
			while (userOne.equals("AI")) {
				JOptionPane.showMessageDialog(optionFrame, "Cannot call yourself AI. You're not a robot :(", "Warning",
						JOptionPane.WARNING_MESSAGE);
				userOne = JOptionPane.showInputDialog(optionFrame, "User 1: Enter your name", "⋅˚₊‧ ୨୧ ‧₊˚ ⋅",
						JOptionPane.PLAIN_MESSAGE, inputIcon1, null, "").toString();
			}
			while (userOne.length() > 10) {
				JOptionPane.showMessageDialog(optionFrame, "Maximum 10 characters in username", "Warning",
						JOptionPane.WARNING_MESSAGE);
				userOne = JOptionPane.showInputDialog(optionFrame, "User 1: Enter your name", "⋅˚₊‧ ୨୧ ‧₊˚ ⋅",
						JOptionPane.PLAIN_MESSAGE, inputIcon1, null, "").toString();
			}
			player1 = new HumanPlayer(userOne, 0);
			userTwo = JOptionPane.showInputDialog(optionFrame, "User 2: Enter your name", "⋅˚₊‧ ୨୧ ‧₊˚ ⋅",
					JOptionPane.PLAIN_MESSAGE, inputIcon2, null, "").toString();
			// while loop so user cannot input same name as user one
			while (userOne.equals(userTwo)) {
				JOptionPane.showMessageDialog(optionFrame, "Cannot enter same username as Player 1", "Warning",
						JOptionPane.WARNING_MESSAGE);
				userTwo = JOptionPane.showInputDialog(optionFrame, "User 2: Enter your name", "⋅˚₊‧ ୨୧ ‧₊˚ ⋅",
						JOptionPane.PLAIN_MESSAGE, inputIcon2, null, "").toString();
			}
			while (userTwo.length() > 10) {
				JOptionPane.showMessageDialog(optionFrame, "Maximum 10 characters in username", "Warning",
						JOptionPane.WARNING_MESSAGE);
				userTwo = JOptionPane.showInputDialog(optionFrame, "User 2: Enter your name", "⋅˚₊‧ ୨୧ ‧₊˚ ⋅",
						JOptionPane.PLAIN_MESSAGE, inputIcon1, null, "").toString();
			}
			while (userTwo.equals("AI")) {
				JOptionPane.showMessageDialog(optionFrame, "Cannot call yourself AI. You're not a robot :(", "Warning",
						JOptionPane.WARNING_MESSAGE);
				userTwo = JOptionPane.showInputDialog(optionFrame, "User 2: Enter your name", "⋅˚₊‧ ୨୧ ‧₊˚ ⋅",
						JOptionPane.PLAIN_MESSAGE, inputIcon2, null, "").toString();
			}
			if (userTwo.equals(null) || userTwo.equals("") || userTwo.equals(" ")) {
				userTwo = "Player 2";
			}
			player2 = new HumanPlayer(userTwo, 0);
			optionFrame.dispose();
			showPlayFrame();
		} else if (e.getSource() == aiButton) {
			modePlayed = "ai";
			userOne = JOptionPane.showInputDialog(optionFrame, "User: Enter your name", "⋅˚₊‧ ୨୧ ‧₊˚ ⋅",
					JOptionPane.OK_OPTION, inputIcon1, null, "").toString();
			if (userOne.equals(null) || userOne.equals("") || userOne.equals(" ")) {
				userOne = "Player 1";
			}
			while (userOne.equals("AI")) {
				JOptionPane.showMessageDialog(optionFrame, "Cannot call yourself AI. You're not a robot :(", "Warning",
						JOptionPane.WARNING_MESSAGE);
				userOne = JOptionPane.showInputDialog(optionFrame, "User 1: Enter your name", "⋅˚₊‧ ୨୧ ‧₊˚ ⋅",
						JOptionPane.PLAIN_MESSAGE, inputIcon1, null, "").toString();
			}
			while (userOne.length() > 10) {
				JOptionPane.showMessageDialog(optionFrame, "Maximum 10 characters in username", "Warning",
						JOptionPane.WARNING_MESSAGE);
				userOne = JOptionPane.showInputDialog(optionFrame, "User 1: Enter your name", "⋅˚₊‧ ୨୧ ‧₊˚ ⋅",
						JOptionPane.PLAIN_MESSAGE, inputIcon1, null, "").toString();
			}
			player1 = new HumanPlayer(userOne, 0);
			userTwo = "AI";
			String[] options = { "Easy", "Medium", "Hard"};
			difficulty = options[JOptionPane.showOptionDialog(null, "Choose your difficulty level", "₊‧.°.⋆✮⋆.°.‧₊",
					JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE, null, options, null)];
			try {
				player2 = new AIPlayer(difficulty, minLength, 0);
			} catch (FileNotFoundException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			optionFrame.dispose();
			showPlayFrame();
		}
		// if user presses menu item, display the corresponding message
		if (e.getSource() == rulesItem) { // Rules from: Hasbro.com
											// https://www.hasbro.com/common/instruct/boggle.pdf
			String rulesText = ("Objective:\n"
					+ "The objective of the game is to find as many words as possible within the given time limit and to reach the tournament score before the opponent.\n\n"
					+ "Gameplay:\n"
					+ "1. The game begins with one player starting the timer and examining the letters on the Boggle board.\n"
					+ "2. Players search for words by connecting adjacent letters horizontally, vertically, or diagonally to form words.\n"
					+ "3. Words must be the minimum specified length and can only use each letter on the board once per word.\n"
					+ "4. Players continue to find words until their time runs out or they pass.\n"
					+ "5. If a player finds a word, they score points based on the word's length (e.g., 3-letter words score 1 point, 4-letter words score 2 points, etc.).\n"
					+ "6. After one player's turn ends (either by finding words or passing), it is the other player's turn to search for words within their allotted time.\n"
					+ "7. If both players pass without finding any words, the board automatically shuffles.\n"
					+ "8. The game continues with players taking turns until one player reaches the tournament score.\n\n"
					+ "Winner:\n"
					+ "The game ends when one player reaches the tournament score (e.g., 25 points) before the opponent.\n"
					+ "That player is declared the winner of the game.");
			JOptionPane.showMessageDialog(null, rulesText, "Rules", JOptionPane.DEFAULT_OPTION);
		}
		if (e.getSource() == aboutItem) { // About page by Wikipedia, https://en.wikipedia.org/wiki/Boggle
			String aboutText = "Boggle is a word game in which players try to find as many words as they can from a grid of lettered dice, within a set time limit. \n"
					+ "It was invented by Allan Turoff and originally distributed by Parker Brothers.";
			JOptionPane.showMessageDialog(null, aboutText, "About", JOptionPane.DEFAULT_OPTION);
		}
		if (e.getSource() == creditsItem) {
			String creditsText = "Made by Angela Yu, Dorothy Lin, Ayla Bilal, Abby Fung and Parnia Yazdi Nia \n"
					+ "About Page credits to Wikipedia\n" + "Rules Page credits to Hasbro";
			JOptionPane.showMessageDialog(null, creditsText, "Credits", JOptionPane.DEFAULT_OPTION);
		}
		if (e.getSource() == highScoreItem) {
			try {
				JOptionPane.showMessageDialog(null, displayHighScore(true), "Current High Score ★彡",
						JOptionPane.DEFAULT_OPTION);
			} catch (FileNotFoundException e1) {
				e1.printStackTrace();
			}
		}
	}

	/**
	 * Displays the high scores from a text file.
	 *
	 * @param highScore Flag indicating if only the top high score should be
	 *                  displayed. If true, only the top high score is returned;
	 *                  otherwise, all scores are returned.
	 * @return A string representation of the high scores read from the
	 *         "highScore.txt" file. If the file is not found or an error occurs
	 *         during reading, an empty string is returned.
	 * @throws FileNotFoundException If the "highScore.txt" file is not found in the
	 *                               source folder.
	 */
	public String displayHighScore(boolean highScore) throws FileNotFoundException {
		String board = "";
		File file = new File("highScore.txt");
		if (!file.exists()) {
			JOptionPane.showMessageDialog(null,
					"Please ensure that the highScore.txt file is downloaded in your source folder!",
					"File Not Found :/", JOptionPane.ERROR_MESSAGE);
		} else {
			Scanner fileScan;
			try {
				fileScan = new Scanner(file);
				while (fileScan.hasNextLine()) {
					if (highScore == true) {
						board = fileScan.nextLine();
					} else {
						board += fileScan.nextLine() + "\n";
					}
				}
				fileScan.close();
			} catch (FileNotFoundException e1) {
				e1.printStackTrace();
			}
		}
		return board;
	}

	/**
	 * Sets up and displays the game settings frame for players to configure game
	 * parameters. This method initializes UI components such as sliders, buttons,
	 * and menu items, and handles user interactions for setting round timer,
	 * tournament score, and minimum word length.
	 */
	private void showPlayFrame() {
		playFrame = new JFrame("Game Settings °❀⋆.ೃ࿔*:･");
		gameSettingsPane = new JLayeredPane();
		gameSettingsPanel = new JPanel();
		roundTimerSliderLabel = new JLabel();
		roundTimerSliderPanel = new JPanel();
		roundTimerSlider = new JSlider(15, 60, 15);
		tournamentSliderLabel = new JLabel();
		tournamentSliderPanel = new JPanel();
		tournamentSlider = new JSlider(10, 100, 10);
		minWordLengthLabel = new JLabel();
		minWordLengthPanel = new JPanel();
		minWordLengthSlider = new JSlider(3, 8, 3);
		confirmButton = new JButton();
		returnButton = new JButton();

		// Menu Bar
		menuBar = new JMenuBar();
		menuBar.setBackground(darkPink);
		helpMenu = new JMenu("Help");
		helpMenu.setForeground(Color.white);
		rulesItem = new JMenuItem("Rules");
		rulesItem.addActionListener(this);
		aboutMenu = new JMenu("About");
		aboutMenu.setForeground(Color.white);
		aboutItem = new JMenuItem("About");
		aboutItem.addActionListener(this);
		creditsItem = new JMenuItem("Credits");
		creditsItem.addActionListener(this);
		scoreMenu = new JMenu("Scores");
		scoreMenu.setForeground(Color.white);
		highScoreItem = new JMenuItem("High Score");
		highScoreItem.addActionListener(this);
		scoreMenu.add(highScoreItem);
		aboutMenu.add(aboutItem);
		aboutMenu.add(creditsItem);
		helpMenu.add(rulesItem);
		menuBar.add(helpMenu);
		menuBar.add(aboutMenu);
		menuBar.add(scoreMenu);
		playFrame.setJMenuBar(menuBar);

		// label
		topPanel = new JPanel();
		names = new JLabel("Hello " + userOne + " and " + userTwo + "!");
		instructions = new JLabel("Choose your game settings here (˶ᵔ ᵕ ᵔ˶)");
		names.setFont(new Font("Times New Roman", Font.BOLD, 20));
		instructions.setFont(new Font("Times New Roman", Font.BOLD, 20));
		topPanel.setBounds(100, 90, 400, 70);
		topPanel.add(names);
		topPanel.add(instructions);
		topPanel.setOpaque(false);
		gameSettingsPane.add(topPanel, Integer.valueOf(2));

		// Round timer slider selector
		roundTimerSliderLabel.setText("Set Round Timer: " + roundTimerSlider.getValue() + "s");
		roundTimerSliderLabel.setFont(new Font("Times New Roman", Font.BOLD, 16));
		roundTimerSliderLabel.setForeground(Color.black);
		roundTimerSliderPanel.setBounds(140, 190, 300, 85);

		roundTimerSlider.setPreferredSize(new Dimension(300, 50));
		roundTimerSlider.setOpaque(false);
		roundTimerSlider.setPaintTicks(true);
		roundTimerSlider.setMinorTickSpacing(1);
		roundTimerSlider.setPaintTrack(true);
		roundTimerSlider.setMajorTickSpacing(5);
		roundTimerSlider.setPaintLabels(true);
		roundTimerSlider.setFocusable(false);
		roundTimerSlider.setForeground(Color.BLACK);
		roundTimerSlider.setFont(new Font("Times New Roman", Font.BOLD, 16));
		roundTimerSlider.addChangeListener(new ChangeListener() {
			@Override
			public void stateChanged(ChangeEvent e) {
				roundTimerSliderLabel.setText("Set Round Timer: " + roundTimerSlider.getValue() + "s");
			}
		});

		roundTimerSliderPanel.add(roundTimerSlider);
		roundTimerSliderPanel.setOpaque(false);
		roundTimerSliderPanel.add(roundTimerSliderLabel);

		// Tournament score selector
		tournamentSliderLabel.setText("Set Tournament Score: " + tournamentSlider.getValue() + " Points");
		tournamentSliderLabel.setFont(new Font("Times New Roman", Font.BOLD, 16));
		tournamentSliderLabel.setForeground(Color.black);

		tournamentSlider.setPreferredSize(new Dimension(300, 50));
		tournamentSlider.setPaintTicks(true);
		tournamentSlider.setOpaque(false);
		tournamentSlider.setMinorTickSpacing(1);
		tournamentSlider.setPaintTrack(true);
		tournamentSlider.setMajorTickSpacing(10);
		tournamentSlider.setPaintLabels(true);
		tournamentSlider.setFocusable(false);
		tournamentSlider.setForeground(Color.BLACK);
		tournamentSlider.setFont(new Font("Times New Roman", Font.BOLD, 12));
		tournamentSlider.addChangeListener(new ChangeListener() {
			@Override
			public void stateChanged(ChangeEvent e) {
				tournamentSliderLabel.setText("Set Tournament Score: " + tournamentSlider.getValue() + " Points");
			}
		});
		tournamentSliderPanel.setBounds(140, 275, 300, 85);
		tournamentSliderPanel.add(tournamentSlider);
		tournamentSliderPanel.add(tournamentSliderLabel);
		tournamentSliderPanel.setOpaque(false);

		// Minimum word length selector
		minWordLengthLabel.setText("Set Min Word Length: " + minWordLengthSlider.getValue());
		minWordLengthLabel.setFont(new Font("Times New Roman", Font.BOLD, 16));
		minWordLengthLabel.setForeground(Color.black);

		minWordLengthSlider.setPreferredSize(new Dimension(300, 50));
		minWordLengthSlider.setPaintTicks(true);
		minWordLengthSlider.setMinorTickSpacing(1);
		minWordLengthSlider.setOpaque(false);
		minWordLengthSlider.setPaintTrack(true);
		minWordLengthSlider.setMajorTickSpacing(1);
		minWordLengthSlider.setPaintLabels(true);
		minWordLengthSlider.setFocusable(false);
		minWordLengthSlider.setForeground(Color.BLACK);
		minWordLengthSlider.setFont(new Font("Times New Roman", Font.BOLD, 12));
		minWordLengthSlider.addChangeListener(new ChangeListener() {
			@Override
			public void stateChanged(ChangeEvent e) {
				minWordLengthLabel.setText("Set Min Word Length: " + minWordLengthSlider.getValue());
			}
		});

		minWordLengthPanel.setBounds(140, 375, 300, 85);
		minWordLengthPanel.add(minWordLengthSlider);
		minWordLengthPanel.add(minWordLengthLabel);
		minWordLengthPanel.setOpaque(false);

		// Confirm button
		confirmButton.setBounds(350, 475, 100, 40);
		confirmButton.setFont(new Font("Comic Sans MS", Font.CENTER_BASELINE, 15));
		confirmButton.setForeground(Color.black);
		confirmButton.setHorizontalTextPosition(JLabel.CENTER);
		confirmButton.setVerticalTextPosition(JLabel.CENTER);
		confirmButton.setContentAreaFilled(true);
		confirmButton.setFocusable(false);
		confirmButton.setBackground(darkPink);
		confirmButton.setText("Confirm");

		confirmButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// Logic for confirm action
				clip.stop();
				// Retrieves the selected game settings
				timer = roundTimerSlider.getValue();
				tournamentScore = tournamentSlider.getValue();
				minLength = minWordLengthSlider.getValue();
				if (userTwo.equals("AI")) {
					try {
						player2 = new AIPlayer(difficulty, minLength, 0);
					} catch (FileNotFoundException e1) {
						e1.printStackTrace();
					}
				}
				try {
					String[] options = { "Toss" };
					String name = options[JOptionPane.showOptionDialog(null,
							"A coin will be tossed to determine who goes first.", "₊‧.°.⋆✮⋆.°.‧₊",
							JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE, coinIcon, options, null)];
					int random = (int) (Math.random() * 2) + 1;
					Player first;
					if (random == 1) {
						first = player1;
					} else {
						first = player2;
					}
					try {
						JOptionPane.showMessageDialog(null, first.getUserName() + " is going first!", "₊‧.°.⋆✮⋆.°.‧₊",
								JOptionPane.PLAIN_MESSAGE);
						playFrame.dispose();
						boggle.startGame(first);
					} catch (InterruptedException e1) {
						e1.printStackTrace();
					}
				} catch (IOException | UnsupportedAudioFileException | LineUnavailableException e1) {
					e1.printStackTrace();
				}
			}
		});

		// Return to Option Page button
		returnButton.setBounds(130, 475, 100, 40);
		returnButton.setFont(new Font("Comic Sans MS", Font.CENTER_BASELINE, 15));
		returnButton.setForeground(Color.black);
		returnButton.setOpaque(true);
		returnButton.setContentAreaFilled(true);
		returnButton.setFocusable(false);
		returnButton.setText("Back");
		returnButton.setBackground(darkPink);
		returnButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				playFrame.dispose();
				clip.stop();
				try {
					new OptionFrame(boggle);
				} catch (UnsupportedAudioFileException | IOException | LineUnavailableException e1) {
					e1.printStackTrace();
				}
			}
		});

		// Layered pane for Game Settings frame
		gameSettingsPane.setBounds(0, 0, 500, 600);
		gameSettingsPane.add(gameSettingsPanel);
		gameSettingsPane.add(roundTimerSliderPanel, Integer.valueOf(2));
		gameSettingsPane.add(tournamentSliderPanel, Integer.valueOf(2));
		gameSettingsPane.add(minWordLengthPanel, Integer.valueOf(2));
		gameSettingsPane.add(confirmButton, Integer.valueOf(2));
		gameSettingsPane.add(returnButton, Integer.valueOf(2));

		// Game Settings Frame
		playFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		playFrame.setSize(610, 640);
		playFrame.setVisible(true);
		playFrame.add(gameSettingsPane);
		playFrame.setLocationRelativeTo(null);
		playFrame.setIconImage(logoIcon.getImage());

		// set background
		optionIcon = new ImageIcon("bg.png");
		bgLabel = new JLabel();
		bgLabel.setSize(600, 600);
		bgLabel.setIcon(optionIcon);
		bgLabel.setOpaque(true);
		playFrame.add(bgLabel);
	}
}