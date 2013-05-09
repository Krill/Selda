package GUI;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.Serializable;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLayeredPane;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;

import World.Map;

import Character.Character;
import Character.PlayerCharacter;
import Character.ShopCharacter;
import Engine.GameClient;
import Engine.ServerEngine;

import Handler.AudioHandler;
import Main.Main;

/**
 * The main window of the game, it handles what panels in the GUI that should be displayed
 * at any given moment. 
 * @author kristoffer
 *
 */
public class GameView extends JFrame implements Observer, Runnable, Serializable{

	// fields:
	private static final long serialVersionUID = 11L;
	private GameClient gameClient;
	private JLayeredPane layers;
	private GamePanel gamePanel;
	private HelpPanel helpPanel;
	private InformationPanel informationPanel;	
	private AudioHandler audio;
	private boolean running;
	
	// constants:
	private static final String BACKGROUND_MUSIC = "audio/music/zeldatheme.mp3";
	private static final String GAME_TITLE = "SELDA";
	private static final int SCREEN_WIDTH = 1000;
	private static final int SCREEN_HEIGHT = 640;
	private static final int GAME_WIDTH = 800;
	private static final int GAME_HEIGHT = 640;
	
	/**
	 * Constructor
	 * @param engine The game engine
	 */
	public GameView(GameClient gameClient){
		this.gameClient = gameClient;		
		audio = new AudioHandler();		
		running = true;
		
		// Create the layered panel and add each panel
		createGamePanels();
		
		// Create the informationPanel
		createInformationPanel();
		
		// Create menubar
		makeMenu();
		
		addObservers();
		makeFrame();
	}
	
	/**
	 * Create the inventorypanel, displays the players items
	 */
	private void createInformationPanel(){
		informationPanel = new InformationPanel();
		informationPanel.reset(gameClient.getClientPlayer());
		layers.add(informationPanel, JLayeredPane.MODAL_LAYER);
	}
	
	/**
	 * Create all panels and adds them to each layer
	 */
	private void createGamePanels(){
		layers = new JLayeredPane();
		layers.setPreferredSize(new Dimension(GAME_WIDTH, GAME_HEIGHT));
		add(layers, BorderLayout.CENTER);
		
		gamePanel = new GamePanel(gameClient);
		layers.add(gamePanel, JLayeredPane.DEFAULT_LAYER);
		
		helpPanel = new HelpPanel();
		layers.add(helpPanel, JLayeredPane.POPUP_LAYER);
	}
	
	/**
	 * Creates a menubar with 3 options: Load, save and quit
	 */
	private void makeMenu(){

		//create menu and menubars 
		JMenuBar bar = new JMenuBar();
		setJMenuBar(bar);

		JMenu fileMenu = new JMenu("File");
		bar.add(fileMenu);
		
		JMenu aboutMenu = new JMenu("About");
		bar.add(aboutMenu);
		
		JMenuItem help = new JMenuItem("Help");
		help.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				helpPanel.showHelp();
			}
		});
		aboutMenu.add(help);
		
		JMenuItem about = new JMenuItem("About Selda");
		about.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e)
			{
				JOptionPane.showMessageDialog(null, "Authors: \nJohan Nilsson Hansen\nKristoffer Petersson\nRichard Norling" +
						"\nAlexander Persson\nKevin Vetter\nJimmy Svensson\n\n" +
						"Version 1.0", "About Selda", JOptionPane.INFORMATION_MESSAGE);
			}
		});
		aboutMenu.add(about);

		JMenuItem quit = new JMenuItem("Quit");
		quit.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				System.exit(0);
			}
		});
		fileMenu.add(quit);
	}
	
	/**
	 * Adds all observable objects to its observer
	 */
	private void addObservers(){
		for(Map map : gameClient.getWorld().getMaps())
		{
			for(Observable c : map.getCharacters()){
				c.addObserver(this);
			}	
		}
		
		// add observer to player
		gameClient.getClientPlayer().addObserver(this);
		gameClient.getClientPlayer().addObserver(informationPanel);
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
	 * Updates the window constantly
	 */
	@Override
	public void run() {
		while(running){
			// Here goes the things that should be updated constantly...
			gamePanel.repaint();
			
			if(!audio.isPlaying()){
				audio.setPlaying(true);
				audio.startPlaying(BACKGROUND_MUSIC);
			}
			
			try {Thread.sleep(10);} catch (InterruptedException e) {e.printStackTrace();}
		}	
	}
	
	
	/**
	 * If something changed by an observable object, update is called
	 */
	@Override
	public void update(Observable o, Object arg) {
		if( ((String) arg).contains("audio/") ){
			audio.startPlaying((String) arg);
		}
	}
}
