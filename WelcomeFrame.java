package boggle;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.*;
import javax.swing.border.Border;

/**
 * Author: Angela Yu, Abby Fung 
 * Date: May 27, 2024 
 * Description: This creates a welcome frame to start off the boggle game.
 */
public class WelcomeFrame extends JFrame implements ActionListener {

	/* INSTANCE VARIABLES */
	private JFrame welcomeFrame;
	private JButton playButton;
	private JLayeredPane layeredPane;
	private ImageIcon welcomeIcon;
	private JLabel bgLabel;
	private Boggle boggle;
	private File file;
	private AudioInputStream audioStream;
	private Clip clip;
	private Border border;
	private ImageIcon logoIcon;

	/**
	 * Parameterized constructor for WelcomeFrame class that initializes the frame
	 * and its components.
	 *
	 * @param boggle The Boggle object associated with this WelcomeFrame.
	 * @throws IOException                   If an I/O error occurs while reading
	 *                                       files.
	 * @throws UnsupportedAudioFileException If the audio file format is not
	 *                                       supported.
	 * @throws LineUnavailableException      If a line cannot be opened because it
	 *                                       is unavailable.
	 */
	public WelcomeFrame(Boggle boggle) throws UnsupportedAudioFileException, IOException, LineUnavailableException {

		this.boggle = boggle;

		border = BorderFactory.createMatteBorder(5, 5, 5, 5, Color.white); // create a border
		logoIcon = new ImageIcon("logo.png");

		// make a fame to add components onto
		welcomeFrame = new JFrame("Welcome to Boggle °❀⋆.ೃ࿔*:･");
		welcomeFrame.setSize(600, 600);
		welcomeFrame.setLayout(null);
		welcomeFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		welcomeFrame.setLocationRelativeTo(null);
		welcomeFrame.setIconImage(logoIcon.getImage());

		// background icon
		welcomeIcon = new ImageIcon("welcome_bg.png");

		// label for background image icon
		bgLabel = new JLabel();
		bgLabel.setSize(600, 600);
		bgLabel.setIcon(welcomeIcon);
		bgLabel.setOpaque(true);

		// make a play button which user can take action on
		playButton = new JButton("PLAY");
		playButton.setFont(new Font("Comic Sans", Font.PLAIN, 30));
		playButton.setForeground(Color.white);
		playButton.setBackground(new Color(0xFA5FBF));
		playButton.setBounds(200, 440, 200, 60);
		playButton.setBorder(border);
		playButton.setFocusable(false);
		playButton.addActionListener(this);

		// make layered pane and set order of components
		layeredPane = new JLayeredPane();
		layeredPane.setSize(600, 600);
		layeredPane.add(bgLabel, Integer.valueOf(0));
		layeredPane.add(playButton, Integer.valueOf(1));

		// add layered pane to frame and make frame visible
		welcomeFrame.add(layeredPane);
		welcomeFrame.setVisible(true);

		// background music
		file = new File("bg_music.wav");
		audioStream = AudioSystem.getAudioInputStream(file);
		clip = AudioSystem.getClip();
		clip.open(audioStream);
		clip.start();
	}

	/**
	 * Action performed method on components of WelcomeFrame.
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		// if play is pressed welcome screen disappears and prompts for user name inputs
		if (e.getSource() == playButton) {
			welcomeFrame.dispose();
			clip.stop();
			try {
				boggle.setOptionFrame(new OptionFrame(boggle));
			} catch (UnsupportedAudioFileException | IOException | LineUnavailableException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
	}
}