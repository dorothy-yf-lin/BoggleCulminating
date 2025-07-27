package boggle;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.*;
import javax.swing.border.Border;

/**
 * @author Abby Fung, Angela Yu, Ayla Bilal, Dorothy Lin, Parnia Yazdinia 
 * Start Date: 2024.05.31 
 * Due Date: 2024.06.10 
 * Description: This is the Boggle class; the main game operations occur here, 
 * 				as well as the GUI for the board.
 */

public class Boggle extends JFrame implements MouseListener {
	// ATTRIBUTES FOR BACKEND //
	private String[][] board;
	private JButton pressed;
	private int timerMax;
	private Dictionary dictionary;
	private Player currPlayer;
	private Player userOne;
	private Player userTwo;
	private String difficulty;
	private boolean verifyWord;
	private String currWord;
	private int minLength;
	private ArrayList<String> foundWords;
	private int tournamentScore;
	private boolean playedOnce;
	private boolean showBoard;
	private String prevWord;

	// ATTRIBUTES FOR USER INTERFACE/FRONT END //
	private OptionFrame optionFrame;
	private WelcomeFrame welcomeFrame;
	private Dice[][] diceArray;
	private JButton[][] buttons;
	private JButton prevButton;
	private JPanel buttonPanel;
	private Timer timer;

	// Boggle Board Frame
	private JFrame boggleFrame;
	private JLayeredPane layeredPane;
	private ImageIcon boggleIcon;
	private JLabel bgLabel;
	private JButton enterButton;
	private Border border;
	private JButton[] userButtons;
	private JPanel userButtonPanel;
	private ClosingFrame closingFrame;
	private JTextField textField;
	private JLabel playerTurnLabel;
	private JPanel wordPanel;

	private JTextArea wordBank;
	private JScrollPane wordBankScrollPane;
	private JLabel wordBankTitle;
	private int numberOfClicks;
	private JPanel timerPanel;
	private JLabel timerLabel;
	private boolean isMusicPlaying;
	private JCheckBox musicCheckBox;
	private Clip clip;
	private JLabel score;
	private JLabel score2;
	private File file;
	private AudioInputStream audioStream;
	private Boggle boggle = this;
	private JMenuBar menuBar;
	private JMenu helpMenu;
	private JMenu creditsMenu;
	private JMenu settingsMenu;
	private JMenu endGameMenu;
	private JMenu shuffle;
	private JMenuItem settingsItem;
	private JMenuItem rulesItem;
	private JMenuItem endItem;
	private JMenuItem shuffleItem;
	private JMenuItem creditsItem;
	private JMenuItem shortcutsItem;
	private ImageIcon bowIcon;
	private JLabel bowLabel;
	private boolean windowClosed;
	private ImageIcon logoIcon;
	private JPanel settingsPanel;

	// These variables are needed to validate the mouse input
	private ArrayList<JButton> buttonClicked = new ArrayList<>();
	private boolean[][] visited = new boolean[5][5];

	/**
	 * DEFAULT CONSTRUCTOR
	 * 
	 * This constructor initializes the Boggle game by displaying the welcome frame,
	 * which serves as the starting point of the game. Variables and attributes are
	 * initialized in their respective methods for simplicity, given the large
	 * number of attributes and the fact that we have multiple people working on
	 * different methods.
	 * 
	 * @throws UnsupportedAudioFileException if the audio file format is not
	 *                                       supported.
	 * @throws IOException                   if an I/O error occurs while reading
	 *                                       the audio file.
	 * @throws LineUnavailableException      if the audio line cannot be opened.
	 */
	public Boggle() throws UnsupportedAudioFileException, IOException, LineUnavailableException {
		if (!playedOnce) {
			welcomeFrame = new WelcomeFrame(this);
		}
		windowClosed = false;
	}

	// GETTERS AND SETTERS //
	public String getDifficulty() {
		return difficulty;
	}

	public void setDifficulty(String difficulty) {
		this.difficulty = difficulty;
	}

	public int getTournamentScore() {
		return tournamentScore;
	}

	public void setTournamentScore(int tournamentScore) {
		this.tournamentScore = tournamentScore;
	}

	public String getFoundWords(int index) {
		return foundWords.get(index);
	}

	public void setFoundWords(String word) {
		foundWords.add(word);
	}

	public Player getUserOne() {
		return userOne;
	}

	public void setUserOne(Player userOne) {
		this.userOne = userOne;
	}

	public Player getUserTwo() {
		return userTwo;
	}

	public void setUserTwo(Player userTwo) {
		this.userTwo = userTwo;
	}

	public int getTimerMax() {
		return timerMax;
	}

	public void setTimerMax(int timerMax) {
		this.timerMax = timerMax;
	}

	public boolean getVerifyWord() {
		return verifyWord;
	}

	public boolean getShowBoard() {
		return showBoard;
	}

	public void setShowBoard(boolean showBoard) {
		this.showBoard = showBoard;
	}

	public Player getCurrPlayer() {
		return currPlayer;
	}

	public void setCurrPlayer(Player currPlayer) {
		this.currPlayer = currPlayer;
	}

	public int getMinLength() {
		return minLength;
	}

	public void setMinLength(int minLength) {
		this.minLength = minLength;
	}

	// ACTION METHODS //

	/**
	 * Sets the option frame and updates the tournament score based on the given
	 * option frame.
	 * 
	 * @param optionFrame OptionFrame object containing configuration and score
	 *                    details
	 */
	public void setOptionFrame(OptionFrame optionFrame) {
		this.optionFrame = optionFrame;
		tournamentScore = optionFrame.getTournamentScore();
	}

	/**
	 * Initializes all attributes and sets up the GUI component of the board. This
	 * method initializes the main JFrame, its components, and various game
	 * elements, and also starts background music.
	 *
	 * @throws UnsupportedAudioFileException if the audio file format is not
	 *                                       supported.
	 * @throws IOException                   if an I/O error occurs while reading
	 *                                       the audio file.
	 * @throws LineUnavailableException      if the audio line cannot be
	 *                                       opened.hrows LineUnavailableException
	 */
	public void setBoard() throws UnsupportedAudioFileException, IOException, LineUnavailableException {
		dictionary = new Dictionary();
		logoIcon = new ImageIcon("logo.png");

		// Initialize JFrame
		boggleFrame = new JFrame("Now playing: Boggle °❀⋆.ೃ࿔*:･");
		boggleFrame.setSize(1000, 710); // Adjusted for smaller screen
		boggleFrame.setLayout(null);
		boggleFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		boggleFrame.setLocationRelativeTo(null);
		boggleFrame.setResizable(false);
		boggleFrame.setIconImage(logoIcon.getImage());

		boggleFrame.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				timer.stop();
				clip.close();
				windowClosed = true;
			}
		});

		// Load background icon
		boggleIcon = new ImageIcon("boggle_bg.png");

		playerTurnLabel = new JLabel();
		playerTurnLabel.setText(currPlayer.getUserName() + "'s turn:");
		playerTurnLabel.setBounds(55, 100, 200, 100);
		playerTurnLabel.setFont(new Font("Times New Roman", Font.BOLD, 20)); // Smaller font
		playerTurnLabel.setForeground(new Color(0xFA5FBF));
		playerTurnLabel.setHorizontalAlignment(JLabel.CENTER);
		playerTurnLabel.setBackground(Color.white);

		// border
		border = BorderFactory.createMatteBorder(3, 3, 3, 3, Color.white); // Smaller border

		// text field
		textField = new JTextField();
		textField.setBounds(300, 580, 300, 50);
		textField.setFont(new Font("Comic Sans MS", Font.PLAIN, 15)); // Smaller font
		textField.setEnabled(false); // the purpose of the text field is to display the mouse inputs
		textField.setDisabledTextColor(new Color(0xFA5FBF));

		// enter button
		enterButton = new JButton();
		enterButton.setText("Enter");
		enterButton.setFont(new Font("Comic Sans MS", Font.PLAIN, 15)); // Smaller font
		enterButton.setForeground(Color.white);
		enterButton.setBackground(new Color(0xFA5FBF));
		enterButton.setBounds(640, 580, 100, 50); // Adjusted position and size
		enterButton.setBorder(border);
		enterButton.setFocusable(false);
		enterButton.addMouseListener(this);

		// Create label for background image
		bgLabel = new JLabel();
		bgLabel.setIcon(boggleIcon);
		bgLabel.setBounds(-5, -36, 1000, 690); // Adjusted size
		bgLabel.setOpaque(true);

		// Create and configure layered pane
		layeredPane = new JLayeredPane();
		layeredPane.setBounds(0, 0, 1000, 690);

		// make timer
		makeTimer();

		// Add background label to layered pane
		layeredPane.add(bgLabel, Integer.valueOf(0));
		layeredPane.add(textField, Integer.valueOf(2));
		layeredPane.add(playerTurnLabel, Integer.valueOf(2));
		layeredPane.add(enterButton, Integer.valueOf(2));
		// Add layered pane to frame content pane
		boggleFrame.add(layeredPane);

		// Make the frame visible
		boggleFrame.setVisible(true);

		// Makes button panel
		makeButtons();

		// Makes control panel
		makeUserPanel();

		// Makes word bank
		makeWordBank();

		// make scoreboard
		makeScoreBoard();

		// add music (Abby)
		file = new File("boggle_music.wav");
		audioStream = AudioSystem.getAudioInputStream(file);
		isMusicPlaying = true;
		clip = AudioSystem.getClip();
		clip.open(audioStream);
		clip.loop(Clip.LOOP_CONTINUOUSLY);
		clip.start();

		// make menu bar
		makeMenu();
	}

	/**
	 * Creates and initializes the game timer.
	 * 
	 * This method sets up a new timer for the game in the user interface, ensuring
	 * any existing timer is stopped and removed. When the timer reaches zero, it
	 * switches the player and displays a warning message.
	 */
	public void makeTimer() {

		if (timer != null) {
			timer.stop(); // Stop the existing timer
		}
		if (timerPanel != null) {
			layeredPane.remove(timerPanel); // Remove the existing timer panel
		}

		// Timer Panel with Bow Image
		timerPanel = new JPanel();
		timerPanel.setLayout(new BorderLayout());
		timerPanel.setOpaque(false);
		timerPanel.setBounds(450, 0, 100, 120);

		bowIcon = new ImageIcon("bow.png");
		bowLabel = new JLabel(bowIcon);
		bowLabel.setBounds(-7, -13, 1000, 690);

		timerLabel = new JLabel("00:" + optionFrame.getTimer(), SwingConstants.CENTER);
		timerLabel.setFont(new Font("Serif", Font.BOLD, 24));
		timerLabel.setForeground(Color.white); // Set timer text color to pink
		timerLabel.setBounds(570, 35, 140, 40); // Position and size the timer label
		timerPanel.add(timerLabel);

		timer = new Timer(1000, new ActionListener() {
			int timeRemaining = optionFrame.getTimer();

			@Override
			public void actionPerformed(ActionEvent e) {
				timeRemaining--;
				int minutes = timeRemaining / 60;
				int seconds = timeRemaining % 60;
				timerLabel.setText(String.format("%02d : %02d", minutes, seconds));

				if (timeRemaining <= 0 && !windowClosed) {
					((Timer) e.getSource()).stop();
					timerLabel.setFont(new Font("Serif", Font.BOLD, 17));
					timerLabel.setText("Time's up!");
					JOptionPane.showMessageDialog(null, "Your time's up!", "Missed your turn",
							JOptionPane.WARNING_MESSAGE);
					try {
						switchPlayer();
					} catch (FileNotFoundException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					} catch (InterruptedException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					} catch (UnsupportedAudioFileException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					} catch (LineUnavailableException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}
			}
		});

		timer.start();
		layeredPane.add(bowLabel, Integer.valueOf(1));
		layeredPane.add(timerPanel, Integer.valueOf(2));
	}

	/**
	 * Formats the given time in seconds into a string representation in the format
	 * MM:SS.
	 * 
	 * @param seconds the total time in seconds to be formatted.
	 * @return a string representing the formatted time in the format MM:SS.
	 */
	public String formatTime(int seconds) {
		int minutes = seconds / 60;
		int remainingSeconds = seconds % 60;
		String timeRemaining = String.format("%d:%02d", minutes, remainingSeconds);
		return timeRemaining;
	}

	/**
	 * Ends the game by stopping the timer and background music, closing the main
	 * game window, and displaying the closing frame with the game results.
	 * 
	 * @throws UnsupportedAudioFileException if the audio file format is not
	 *                                       supported.
	 * @throws IOException                   if an I/O error occurs while reading or
	 *                                       stopping the audio file.
	 * @throws LineUnavailableException      if the audio line cannot be stopped.
	 */
	public void endGame() throws UnsupportedAudioFileException, IOException, LineUnavailableException {
		clip.stop();
		timer.stop();
		boggleFrame.dispose();
		windowClosed = true;
		closingFrame = new ClosingFrame(boggle, this.getWinner(), userOne, userTwo);
	}

	/**
	 * @author Abby This method shows an JOptionPane with a list of available
	 *         keyboard shortcuts for the menu
	 */
	private void showKeyboardShortcuts() {
		JOptionPane.showMessageDialog(boggleFrame,
				"Note: When using shortcuts you must open menu first then select dropdown. \nKeyboard Shortcuts:\n"
						+ "Alt + H: Open Help menu\n" + "Alt + C: Open Credits menu\n" + "Alt + S: Open Settings menu\n"
						+ "Alt + E: Open End Game menu\n" + "Alt + B: Open Shuffle Menu\n" + "M: Open Settings\n"
						+ "R: Show Rules\n" + "K: Show Keyboard Shortcuts\n" + "E: End the current game\n"
						+ "D: Display Credits\n" + "S: Shake up the board",
				"Keyboard Shortcuts", JOptionPane.INFORMATION_MESSAGE);
	}

	/**
	 * @author Abby Displays the settings panel in the menu for the Boggle game.
	 *         This method creates and shows a settings dialog with options for game
	 *         settings, such as toggling music.
	 * 
	 *         The settings panel includes: - A checkbox to toggle background music.
	 *         When the checkbox is selected or deselected, the music state is
	 *         toggled accordingly.
	 */
	private void showSettings() {
		// Create a new JPanel to hold the settings components
		settingsPanel = new JPanel();
		settingsPanel.setLayout(new GridLayout(2, 1));

		// Create a checkbox for toggling music
		musicCheckBox = new JCheckBox("Music");
		musicCheckBox.setSelected(isMusicPlaying);

		// Add an action listener to the checkbox to toggle music when clicked
		musicCheckBox.addActionListener(e -> toggleMusic());

		settingsPanel.add(musicCheckBox);
		JOptionPane.showMessageDialog(boggleFrame, settingsPanel, "Settings", JOptionPane.INFORMATION_MESSAGE);
	}

	/**
	 * @author Abby This method starts or stops the background music based on the
	 *         state of the music checkbox.
	 */
	private void toggleMusic() {
		if (musicCheckBox.isSelected()) {
			clip.loop(Clip.LOOP_CONTINUOUSLY);
			isMusicPlaying = true;
		} else {
			clip.stop();
			isMusicPlaying = false;
		}
	}

	/**
	 * @author Abby This method shows a JOptionPane with information about the
	 *         developers and background music
	 */
	private void showCredits() {
		JOptionPane.showMessageDialog(boggleFrame,
				"Game developed by Abby, Ayla, Dorothy, Angela & Parnia.\nJune 1 2024 \nBGM: boba date by Stream Cafe on Youtube",
				"Credits", JOptionPane.INFORMATION_MESSAGE);
	}

	/**
	 * @author Abby This method shows a JOptionPane with a link to an online guide
	 *         for playing boggle
	 */
	private void showRules() {
		// Rules from: Hasbro.com
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
		JOptionPane.showMessageDialog(boggleFrame, rulesText, "Rules", JOptionPane.INFORMATION_MESSAGE);
	}

	/**
	 * Initializes and sets up the board's buttons with letters on them.
	 * 
	 * This method creates a 5x5 grid of buttons, each representing a dice with a
	 * letter face. It initializes the board, buttons, and dice arrays, sets the GUI
	 * settings for each button, and adds them to the button panel. The panel is
	 * then added to the layered pane.
	 */
	public void makeButtons() {
		board = new String[5][5];
		buttons = new JButton[5][5];
		buttonPanel = new JPanel();
		buttonPanel.setLayout(new GridLayout(5, 5));
		buttonPanel.setOpaque(false);
		buttonPanel.setBounds(300, 100, 400, 400);
		boggleFrame.add(buttonPanel);
		diceArray = new Dice[5][5];
		// Create an array of dice and buttons
		for (int r = 0; r < 5; r++) {
			for (int c = 0; c < 5; c++) {
				Dice dice = new Dice(r, c);
				dice.roll();
				board[r][c] = dice.getFace();
				diceArray[r][c] = dice;
				buttons[r][c] = new JButton(board[r][c]);

				// Set GUI settings for each button
				buttons[r][c].setFont(new Font("Comic Sans", Font.BOLD, 20));
				buttons[r][c].setForeground(Color.white);
				buttons[r][c].setOpaque(false);
				buttons[r][c].setContentAreaFilled(false);
				buttons[r][c].setBorderPainted(false);
				buttons[r][c].setFocusable(false);
				buttons[r][c].addMouseListener(this);
				buttons[r][c].setActionCommand("BoardButton");
				buttonPanel.add(buttons[r][c]);
			}
		}
		layeredPane.add(buttonPanel, Integer.valueOf(1));
	}

	/**
	 * Creates and initializes the menu bar for the Boggle game.
	 * 
	 * @author Abby This method sets up a menu bar with various tabs including Help,
	 *         Credits, Settings, End Game, and Shuffle Board. Each tab contains
	 *         menu items with associated action listeners to handle user
	 *         interactions such as displaying settings, rules, ending the game, and
	 *         shuffling the board. Mnemonics are set for quick keyboard access to
	 *         menu items.
	 */
	public void makeMenu() {
		menuBar = new JMenuBar();
		menuBar.setBackground(new Color(0xFA5FBF));

		// Create Settings tab
		helpMenu = new JMenu("Help");
		helpMenu.setForeground(Color.white);
		menuBar.add(helpMenu);

		// Create Credits tab
		creditsMenu = new JMenu("Credits");
		creditsMenu.setForeground(Color.white);
		menuBar.add(creditsMenu);

		// Create settings tab
		settingsMenu = new JMenu("Settings");
		settingsMenu.setForeground(Color.white);
		menuBar.add(settingsMenu);

		// Create end game tab
		endGameMenu = new JMenu("End Game");
		endGameMenu.setForeground(Color.white);
		menuBar.add(endGameMenu);

		// create shuffle board tab
		shuffle = new JMenu("Shuffle Board");
		shuffle.setForeground(Color.white);
		menuBar.add(shuffle);

		// Add JMenuBar to the frame
		boggleFrame.setJMenuBar(menuBar);

		// Add action listeners for menu items under help tab
		settingsItem = new JMenuItem("Music");
		settingsMenu.add(settingsItem);
		rulesItem = new JMenuItem("Rules");
		helpMenu.add(rulesItem);
		endItem = new JMenuItem("End Game Now");
		endGameMenu.add(endItem);
		shuffleItem = new JMenuItem("Shuffle Board");
		shuffle.add(shuffleItem);
		// learnt this from brocode
		settingsItem.addActionListener(e -> showSettings());
		rulesItem.addActionListener(e -> showRules());
		endItem.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				int response = JOptionPane.showConfirmDialog(null, "Are you sure you want to end the game?", "End Game",
						JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);

				if (response == JOptionPane.YES_OPTION) {
					try {
						endGame();
					} catch (UnsupportedAudioFileException | IOException | LineUnavailableException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}
			}
		});
		shuffleItem.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				int response = JOptionPane.showConfirmDialog(null,
						"Are you sure you want to shuffle the board? This will shake the board and shuffle ALL the dice.",
						"Shuffle?", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
				if (response == JOptionPane.YES_OPTION) {
					for (int r = 0; r < 5; r++) {
						for (int c = 0; c < 5; c++) {
							diceArray[r][c].roll();
							buttons[r][c].setText(diceArray[r][c].getFace());
						}
					}
				}
			}
		});

		// add item under credits tab
		creditsItem = new JMenuItem("Display Credits");
		creditsMenu.add(creditsItem);
		creditsItem.addActionListener(e -> showCredits());

		// add item under help tab
		shortcutsItem = new JMenuItem("Keyboard Shortcuts");
		helpMenu.add(shortcutsItem);
		shortcutsItem.addActionListener(e -> showKeyboardShortcuts());

		// set shortcuts for menu
		shortcutsItem.setMnemonic(KeyEvent.VK_K); // k for keyboard shortcuts
		helpMenu.setMnemonic(KeyEvent.VK_H); // Alt + H for help
		creditsMenu.setMnemonic(KeyEvent.VK_C); // Alt + C for credits
		settingsMenu.setMnemonic(KeyEvent.VK_S); // Alt + S for settings
		endGameMenu.setMnemonic(KeyEvent.VK_E); // Alt + E for end game
		shuffle.setMnemonic(KeyEvent.VK_B); // alt b for shuffle board
		creditsItem.setMnemonic(KeyEvent.VK_D); // d for display credits
		settingsItem.setMnemonic(KeyEvent.VK_M); // m for music
		rulesItem.setMnemonic(KeyEvent.VK_R); // r for rules
		endItem.setMnemonic(KeyEvent.VK_E); // e for end game
		shuffleItem.setMnemonic(KeyEvent.VK_S); // s for shuffle
	}

	/**
	 * This method creates the panel for user buttons.
	 */
	public void makeUserPanel() {
		userButtonPanel = new JPanel();
		userButtonPanel.setLayout(new GridLayout(1, 3, 10, 0));
		userButtonPanel.setOpaque(false);
		userButtonPanel.setBounds(150, 520, 700, 50); // Adjusted position and size

		userButtons = new JButton[3];
		String[] buttonTexts = { "Restart", "Pause", "Pass" };

		for (int i = 0; i < 3; i++) {
			userButtons[i] = new JButton(buttonTexts[i]);
			userButtons[i].setFont(new Font("Comic Sans MS", Font.PLAIN, 15)); // Smaller font
			userButtons[i].setForeground(Color.white);
			userButtons[i].setBackground(new Color(0xFA5FBF));
			userButtons[i].setBorder(border);
			userButtons[i].setFocusable(false);
			userButtonPanel.add(userButtons[i]);
		}

		// add actionlisteners for each button

		userButtons[2].addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				currPlayer.setPassed(true);
				if (userOne.getPassed() && userTwo.getPassed()) {
					userOne.setPassed(false);
					userTwo.setPassed(false);
					int response = JOptionPane.showConfirmDialog(null, "Both players pass. Shake up board?",
							"°❀⋆.ೃ࿔*:･", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
					if (response == JOptionPane.YES_OPTION) {
						for (int r = 0; r < 5; r++) {
							for (int c = 0; c < 5; c++) {
								diceArray[r][c].roll();
								buttons[r][c].setText(diceArray[r][c].getFace());
							}
						}
					}
				}

				try {
					switchPlayer();
				} catch (FileNotFoundException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (InterruptedException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (UnsupportedAudioFileException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (LineUnavailableException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});

		numberOfClicks = 0;
		userButtons[1].addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				if (numberOfClicks % 2 == 0) {
					for (int r = 0; r < 5; r++) {
						for (int c = 0; c < 5; c++) {
							// disable buttons
							buttons[r][c].setEnabled(false);
						}
					}
					// disable everything else
					userButtons[0].setEnabled(false);
					userButtons[2].setEnabled(false);
					enterButton.setEnabled(false);
					textField.setEnabled(false);
					// pause timer
					timer.stop();

					numberOfClicks++;

					userButtons[1].setText("Continue");
					userButtons[1].setBackground(Color.white);
					userButtons[1].setForeground(Color.magenta);

					// make JOptionPane
					JOptionPane.showMessageDialog(boggleFrame, "You have paused the game.", "Paused",
							JOptionPane.WARNING_MESSAGE);
				} else {
					// change back to pause
					userButtons[1].setText("Pause");
					userButtons[1].setBackground(new Color(0xFA5FBF));
					userButtons[1].setForeground(Color.white);

					for (int r = 0; r < 5; r++) {
						for (int c = 0; c < 5; c++) {
							// enable buttons
							buttons[r][c].setEnabled(true);
						}
						// enable everything else
						userButtons[0].setEnabled(true);
						userButtons[2].setEnabled(true);
						enterButton.setEnabled(true);
						textField.setEnabled(true);
						// resume timer
						timer.start();

						numberOfClicks++;
					}
				}
			}
		});

		userButtons[0].addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					boggleFrame.dispose();
					timer.stop();
					clip.close();
					userOne.setPlayerScore(0);
					userTwo.setPlayerScore(0);
					startGame(currPlayer);
				} catch (UnsupportedAudioFileException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (LineUnavailableException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (InterruptedException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}

		});

		layeredPane.add(userButtonPanel, Integer.valueOf(1));
	}

	/**
	 * Creates and initializes the panel for displaying the word bank.
	 * 
	 * This method sets up a panel to display a list of words found during the game.
	 * It includes a JTextArea within a JScrollPane to handle scrolling if the word
	 * list exceeds the visible area.
	 */
	public void makeWordBank() {
		wordPanel = new JPanel();
		wordPanel.setLayout(new BorderLayout());
		wordPanel.setOpaque(false);
		wordPanel.setBounds(750, 100, 190, 395);
		layeredPane.add(wordPanel, Integer.valueOf(3));

		wordBank = new JTextArea();
		wordBank.setFont(new Font("Comic Sans MS", Font.PLAIN, 15));
		wordBank.setForeground(Color.black);
		wordBank.setBackground(Color.white);
		wordBank.setOpaque(false);
		wordBank.setEditable(false);

		// Create the JScrollPane and pass in the JTextArea
		wordBankScrollPane = new JScrollPane(wordBank);
		wordBankScrollPane.getViewport().setOpaque(false);
		// Set the preferred size for the scroll pane
		wordBankScrollPane.setPreferredSize(new Dimension(180, 370));
		wordBankScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		wordBankScrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);

		// Add the scroll pane to the center of wordPanel
		wordPanel.add(wordBankScrollPane, BorderLayout.CENTER);

		wordBankTitle = new JLabel("Word List:");
		wordBankTitle.setFont(new Font("Times New Roman", Font.BOLD, 20));
		wordBankTitle.setForeground(Color.black);
		wordBankTitle.setOpaque(false);
		wordPanel.add(wordBankTitle, BorderLayout.NORTH);
	}

	/**
	 * Starts the Boggle game with the specified first player.
	 * 
	 * This method initializes the game with player attributes retrieved from the
	 * option frame.
	 * 
	 * @param first the first player to start the game.
	 * @throws UnsupportedAudioFileException if the audio file format is not
	 *                                       supported.
	 * @throws IOException                   if an I/O error occurs.
	 * @throws LineUnavailableException      if a line is not available for
	 *                                       playback.
	 * @throws InterruptedException          if the thread is interrupted during
	 *                                       gameplay setup.
	 */
	public void startGame(Player first)
			throws UnsupportedAudioFileException, IOException, LineUnavailableException, InterruptedException {
		// GAME PLAYS AND PLAYERS GET ATTRIBUTES
		userOne = optionFrame.getPlayer1();
		userTwo = optionFrame.getPlayer2();
		if (first == userOne) {
			currPlayer = userOne;
		} else if (first == userTwo) {
			if (first.getUserName().equals("AI")) {
				currPlayer = userOne;
			} else {
				currPlayer = userTwo;
			}
		}
		prepareGame();
		setBoard();
		// quick tip option dialog
		JOptionPane.showMessageDialog(null, "Quick Tip! \nClick on the letter again to undo!", "~( ˘▾˘~)",
				JOptionPane.INFORMATION_MESSAGE);
		if (first.getUserName().equals("AI")) {
			switchPlayer();
		}
	}

	/**
	 * Prepares the game environment by initializing game settings based on option
	 * frame selections.
	 * 
	 * @throws FileNotFoundException if a required file or resource is not found.
	 */
	public void prepareGame() throws FileNotFoundException {
		foundWords = new ArrayList<String>();
		// when the optionframe is not disposed, get each element
		if (optionFrame != null) {
			setTimerMax(optionFrame.getTimer());
			setDifficulty(optionFrame.getDifficulty());
			setTournamentScore(optionFrame.getTournamentScore());
			setMinLength(optionFrame.getMinLength());
		}
	}

	/**
	 * Creates and displays the scoreboard for tracking players' scores during the
	 * game.
	 *
	 * @throws UnsupportedAudioFileException if the audio file format is not
	 *                                       supported.
	 * @throws IOException                   if an I/O error occurs.
	 * @throws LineUnavailableException      if a line is not available for
	 *                                       playback.
	 */
	public void makeScoreBoard() throws UnsupportedAudioFileException, IOException, LineUnavailableException {
		if (score != null) {
			score.setText("");
			score2.setText("");
		}
		score = new JLabel(
				userOne.getUserName() + "'s score: " + userOne.getPlayerScore() + "/" + getTournamentScore());
		score2 = new JLabel(
				userTwo.getUserName() + "'s score: " + userTwo.getPlayerScore() + "/" + getTournamentScore());
		score.setBounds(55, 200, 200, 100);
		score.setFont(new Font("Times New Roman", Font.BOLD, 15));
		score.setForeground(new Color(0xFA5FBF));
		score.setBackground(Color.white);
		score.setHorizontalAlignment(JLabel.CENTER);
		layeredPane.add(score, Integer.valueOf(2));

		score2.setBounds(55, 230, 200, 100);
		score2.setFont(new Font("Times New Roman", Font.BOLD, 15));
		score2.setForeground(new Color(0xFA5FBF));
		score2.setBackground(Color.white);
		score2.setHorizontalAlignment(JLabel.CENTER);
		layeredPane.add(score2, Integer.valueOf(2));

		// Checks if any players has won yet
		if (userOne.getPlayerScore() >= getTournamentScore() || userTwo.getPlayerScore() >= getTournamentScore()) {
			try {
				endGame();
			} catch (UnsupportedAudioFileException | IOException | LineUnavailableException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
	}

	// MouseListener implementation
	@Override
	public void mousePressed(MouseEvent e) {
		pressed = (JButton) e.getSource();
		if (pressed == enterButton) {
			String word = "";

			for (int i = 0; i < buttonClicked.size(); i++) {
				word = word + buttonClicked.get(i).getText();
			}
			currWord = word;

			try {
				playerMove();
			} catch (InterruptedException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (UnsupportedAudioFileException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (LineUnavailableException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}

			buttonClicked.clear();
			textField.setText("");
			resetButtons();
		} else {
			// If the button is disabled the user cannot click it
			if (!pressed.isEnabled()) {
				return;
			}
			// Disabling all the buttons to only enable the valid options
			for (int i = 0; i < 5; i++) {
				for (int j = 0; j < 5; j++) {
					buttons[i][j].setEnabled(false);
					if (buttons[i][j].getForeground().equals(new Color(144, 238, 144))) {
						buttons[i][j].setEnabled(true);
					}
				}
			}
			// Enabling the surrounding buttons
			int[] pressedIndex = getButtonRowColumn(pressed);
			for (int i = Math.max(0, pressedIndex[0] - 1); i <= Math.min(4, pressedIndex[0] + 1); i++) {
				for (int j = Math.max(0, pressedIndex[1] - 1); j <= Math.min(4, pressedIndex[1] + 1); j++) {
					buttons[i][j].setEnabled(true);
				}
			}

			buttonClicked.add(pressed);
			textField.setText(textField.getText() + pressed.getText());

			pressed.setForeground(new Color(144, 238, 144));

			// Checking for a valid move
			if (buttonClicked.size() > 1) {
				JButton prev = buttonClicked.get(buttonClicked.size() - 2);
				JButton next = buttonClicked.get(buttonClicked.size() - 1);

				int[] prevIndex = getButtonRowColumn(prev);
				int[] nextIndex = getButtonRowColumn(next);
				visited[prevIndex[0]][prevIndex[1]] = true;

				if (visited[nextIndex[0]][nextIndex[1]] == true) {
					// Invalid move, reseting the textfield and buttonClicked list
					buttonClicked.clear();
					textField.setText("");
					resetButtons();
				} else {
					// Valid move, change color of next to green
					buttons[nextIndex[0]][nextIndex[1]].setForeground(new Color(144, 238, 144));
				}
			}
		}
	}

	/**
	 * Method to add a word to the word bank.
	 * 
	 * @param word The word to be added.
	 */
	public void addWordToBank(String word) {
		wordBank.append(word + "\n"); // Append the word followed by a newline character
	}

	public int[] getButtonRowColumn(JButton clickedButton) {
		int[] rowAndCol = new int[2];

		for (int row = 0; row < 5; row++) {
			for (int col = 0; col < 5; col++) {
				if (buttons[row][col] == clickedButton) {
					rowAndCol[0] = row;
					rowAndCol[1] = col;
				}
			}
		}
		return rowAndCol;
	}

	/**
	 * Resets the state of all buttons on the game board.
	 */
	public void resetButtons() {
		for (int i = 0; i < 5; i++) {
			for (int j = 0; j < 5; j++) {
				visited[i][j] = false;
				buttons[i][j].setForeground(Color.WHITE);
				buttons[i][j].setEnabled(true);
			}
		}
	}

	/**
	 * Switches the turn between players during the game.
	 * 
	 * @throws InterruptedException          if the thread is interrupted during
	 *                                       game play.
	 * @throws UnsupportedAudioFileException if the audio file format is not
	 *                                       supported.
	 * @throws IOException                   if an I/O error occurs.
	 * @throws LineUnavailableException      if a line is not available for play
	 *                                       back.
	 */
	public void switchPlayer()
			throws InterruptedException, UnsupportedAudioFileException, IOException, LineUnavailableException {
		// Swap current player
		if (getCurrPlayer().equals(getUserOne())) {
			currPlayer = userTwo;
		} else if (getCurrPlayer().equals(getUserTwo())) {
			currPlayer = userOne;
		}
		// Reset board and timer each time.
		timer.stop();
		playerTurnLabel.setText(currPlayer.getUserName() + "'s turn:");
		makeTimer();
		resetBoard();

		// make AI take turn if applicable
		// Checks if any words can be played; if not, AI will pass.
		if (currPlayer.getUserName().equals("AI") && !windowClosed) {
			currWord = userTwo.takeTurn(board);
			boolean playPossible = playerMove();

			// if a word cannot be played, the AI will pass
			if (!playPossible) {
				currPlayer.setPassed(true);
				JOptionPane.showMessageDialog(null, "AI passes.", "Pass", JOptionPane.INFORMATION_MESSAGE);
				switchPlayer();
			} else {
				JOptionPane.showMessageDialog(null, "AI chooses " + currWord + ".", "AI Turn",
						JOptionPane.INFORMATION_MESSAGE);
				setFoundWords(currWord);
			}
		}
	}

	/**
	 * This method resets the board after each turn.
	 */
	public void resetBoard() {
		for (int r = 0; r < 5; r++) {
			for (int c = 0; c < 5; c++) {
				buttons[r][c].setEnabled(true);
				diceArray[r][c].setPressed(false);
			}
		}
		prevButton = null;
		enterButton.setEnabled(true);
		prevWord = "";

		textField.setText("");
		resetButtons();
	}

	/**
	 * This method handles player moves during their turn.
	 * 
	 * @return true if the player successfully plays a valid word, false otherwise.
	 * 
	 * @throws InterruptedException          if the thread is interrupted during
	 *                                       game play.
	 * @throws UnsupportedAudioFileException if the audio file format is not
	 *                                       supported.
	 * @throws IOException                   if an I/O error occurs.
	 * @throws LineUnavailableException      if a line is not available for play
	 *                                       back.
	 */
	public boolean playerMove()
			throws InterruptedException, UnsupportedAudioFileException, IOException, LineUnavailableException {
		boolean playPossible = false;

		// verifies if word and is not a duplicate or too short
		boolean isWord = dictionary.verifyWord(currWord);
		boolean isDupe = checkDupes(currWord);
		boolean tooShort = false;
		if (currWord.length() < optionFrame.getMinLength()) {
			tooShort = true;
		}

		// check conditions; add to found words if so and handle points
		if (isWord && !isDupe && !tooShort) {
			playPossible = true;
			setFoundWords(currWord);

			// add points to score
			calculatePoints();

			addWordToBank(currWord);

			resetBoard();
			switchPlayer();
		} else if (!isWord && !currPlayer.getUserName().equals("AI")) {
			JOptionPane.showMessageDialog(null, "The word '" + currWord + "' is not found in the dictionary.",
					"Invalid", JOptionPane.WARNING_MESSAGE);
		} else if (tooShort && !currPlayer.getUserName().equals("AI")) {
			JOptionPane.showMessageDialog(null, "Your word is too short!", "Invalid", JOptionPane.WARNING_MESSAGE);
		} else if (isDupe && !currPlayer.getUserName().equals("AI")) {
			JOptionPane.showMessageDialog(null, "Duplicate word!", "Invalid", JOptionPane.WARNING_MESSAGE);
		}
		return playPossible;

	}

	/**
	 * This method calculates, adds points to scores, and updates the board.
	 * 
	 * @param allWords accepts a String of all of the player's found words to
	 *                 calculate.
	 * @throws UnsupportedAudioFileException if the audio file format is not
	 *                                       supported.
	 * @throws IOException                   if an I/O error occurs.
	 * @throws LineUnavailableException      if a line is not available for play
	 *                                       back.
	 */
	public void calculatePoints() throws UnsupportedAudioFileException, IOException, LineUnavailableException {
		int points = 0;

		if (currWord.length() == 3 || currWord.length() == 4) {
			points += 1;
		} else if (currWord.length() == 5) {
			points += 2;
		} else if (currWord.length() == 6) {
			points += 3;
		} else if (currWord.length() == 7) {
			points += 5;
		} else { // use else; 3 is the minimum length that is checked.
			points += 11;
		}

		int playerScore = currPlayer.getPlayerScore();
		playerScore += points;
		if (currPlayer.getUserName().equals(userOne.getUserName())) {
			userOne.setPlayerScore(playerScore);
		} else {
			userTwo.setPlayerScore(playerScore);
		}

		makeScoreBoard();

	}

	/**
	 * Checks if a word is already found in the list of found words.
	 * 
	 * @param word the word to check for duplicates
	 * @return true if the word is found in the list of found words, false otherwise
	 */
	public boolean checkDupes(String word) {
		for (int i = 0; i < foundWords.size(); i++) {
			if (word.equals(foundWords.get(i))) {
				return true;
			}
		}
		return false;
	}

	/**
	 * Determines the winner of the game based on player scores.
	 * 
	 * @return the name of the player with the higher score, or "tie" if scores are
	 *         equal
	 */
	public String getWinner() {
		if (userOne.getPlayerScore() > userTwo.getPlayerScore()) {
			return userOne.getUserName();
		} else if (userTwo.getPlayerScore() > userOne.getPlayerScore()) {
			return userTwo.getUserName();
		} else {
			return "tie";
		}
	}

	// The type Boggle must implement the inherited abstract method
	// MouseListener.mouseClicked(MouseEvent).
	// These methods must be implemented, but we are not using it
	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub

	}
}