package GUI;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import Main.Main;

/**
 * Provides the user with a startscreen where he can choose
 * if he wants to start a new game or load an existing one.
 * @author kristoffer
 *
 */
@SuppressWarnings("serial")
public class StartScreen extends JFrame{

	// fields:
	private StartPanel startPanel;
	private JFileChooser dialog;
	
	// constants:
	private static final String GAME_TITLE = "StartScreen";	
	private static final int SCREEN_WIDTH = 800;
	private static final int SCREEN_HEIGHT = 640;

	/**
	 * Constructor
	 */
	public StartScreen(){
		
		dialog = new JFileChooser("saves/");
		
		makePanel();
		makeButtons();
		makeFrame();
	}
	
	/**
	 * Creates the StartPanel
	 */
	private void makePanel(){
		startPanel = new StartPanel();
		add(startPanel);
	}
	
	/**
	 * Creates the buttons for each label
	 */
	private void makeButtons(){	
		JPanel content = new JPanel();
		content.setPreferredSize(new Dimension(250, 500));
		content.setLayout(new BoxLayout(content, BoxLayout.PAGE_AXIS));
		content.setOpaque(false);
		startPanel.add(content);
		
		// new game "button"
		JPanel newGame = new JPanel();
		newGame.setSize(new Dimension(250, 35));
		newGame.setOpaque(false);
		newGame.addMouseListener(new MouseAdapter(){
			public void mousePressed(MouseEvent e){
				System.out.println("New Game!");
				String characterName = JOptionPane.showInputDialog(null,"Enter your characters name!");
				if(characterName != null){
					Main.newGame(characterName);
				}
			}
		});
		
		// load game "button"
		JPanel loadGame = new JPanel();
		loadGame.setSize(new Dimension(250, 40));
		loadGame.setOpaque(false);
		loadGame.addMouseListener(new MouseAdapter(){
			public void mousePressed(MouseEvent e){
				System.out.println("Load Game!");
				if(dialog.showOpenDialog(null)== JFileChooser.APPROVE_OPTION){
					Main.load(dialog.getSelectedFile().getAbsolutePath());
				}
			}
		});
		
		// exit game "button"
		JPanel exitGame = new JPanel();
		exitGame.setSize(new Dimension(250, 40));
		exitGame.setOpaque(false);
		exitGame.addMouseListener(new MouseAdapter(){
			public void mousePressed(MouseEvent e){
				System.out.println("Exit Game!");
				System.exit(0);
			}
		});
		
		// add to content
		content.add(Box.createVerticalStrut(180));
		content.add(newGame);
		content.add(Box.createVerticalStrut(50));
		content.add(loadGame);
		content.add(Box.createVerticalStrut(50));
		content.add(exitGame);
		content.add(Box.createVerticalStrut(50));
	}
	
	/**
	 * Creates the window
	 */
	private void makeFrame(){
		setTitle(GAME_TITLE);
		setSize(SCREEN_WIDTH, SCREEN_HEIGHT);
		setBackground(Color.BLACK);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLocationRelativeTo(null);
		pack();
		setVisible(true);
	}
	
	/**
	 * Handles the things that will be shown in the startscreen
	 * @author kristoffer
	 */
	private class StartPanel extends JPanel{
		
		// constants:
		private static final String PANEL_BACKGROUND = "images/gui/startscreen.png";
		
		public StartPanel(){
			setPreferredSize(new Dimension(SCREEN_WIDTH, SCREEN_HEIGHT));
			setVisible(true);
			setFocusable(true);
		}
		
		/**
		 * Paints a background image
		 */
		public void paintComponent(Graphics g) {
			Image img = new ImageIcon(PANEL_BACKGROUND).getImage();	
			g.drawImage(img, 0, 0, null);
		}
	}
}
