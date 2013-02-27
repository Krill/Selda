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

import Character.Character;
import Character.CivilianCharacter;
import Character.PlayerCharacter;
import Character.ShopCharacter;
import Engine.GameEngine;

import Handler.AudioHandler;

/**
 * The main window that handles what should be displayed
 * @author kristoffer
 *
 */
public class GameView extends JFrame implements Observer, Runnable, Serializable{

	// fields:
	private static final long serialVersionUID = 11L;
	private GameEngine engine;
	private JLayeredPane layers;
	private GamePanel gamePanel;
	private ShopPanel shopPanel;
	private QuestPanel questPanel;
	private HelpPanel helpPanel;
	private StatisticsPanel statsPanel;
	private InformationPanel informationPanel;
	private InventoryPanel inventoryPanel;
	private JFileChooser dialog;	
	private AudioHandler audio;
	
	// constants:
	private static final String BACKGROUND_MUSIC = "audio/music/zeldatheme.mp3";
	private static final String GAME_TITLE = "GAMETITLE";
	private static final int SCREEN_WIDTH = 1000;
	private static final int SCREEN_HEIGHT = 640;
	private static final int GAME_WIDTH = 800;
	private static final int GAME_HEIGHT = 640;
	
	/**
	 * Constructor
	 * @param engine
	 */
	public GameView(GameEngine engine){
		this.engine = engine;		
		audio = new AudioHandler();
		
		audio.setPlaying(true);
		audio.startPlaying(BACKGROUND_MUSIC);
		
		// Create the inventorypanel
		createInventoryPanel();
		
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
	private void createInventoryPanel(){
		inventoryPanel = new InventoryPanel(engine.getPlayer());
		inventoryPanel.reset(engine.getPlayer());
		add(inventoryPanel, BorderLayout.WEST);
	}
	
	/**
	 * Create the inventorypanel, displays the players items
	 */
	private void createInformationPanel(){
		informationPanel = new InformationPanel();
		informationPanel.reset(engine.getPlayer());
		layers.add(informationPanel, JLayeredPane.MODAL_LAYER);
	}
	
	/**
	 * Create all panels and adds them to each layer
	 */
	private void createGamePanels(){
		layers = new JLayeredPane();
		layers.setPreferredSize(new Dimension(GAME_WIDTH, GAME_HEIGHT));
		add(layers, BorderLayout.CENTER);
		
		gamePanel = new GamePanel(engine);
		layers.add(gamePanel, JLayeredPane.DEFAULT_LAYER);
		
		shopPanel = new ShopPanel();
		layers.add(shopPanel, JLayeredPane.MODAL_LAYER);
		
		questPanel = new QuestPanel();
		layers.add(questPanel, JLayeredPane.MODAL_LAYER);
		
		helpPanel = new HelpPanel();
		layers.add(helpPanel, JLayeredPane.POPUP_LAYER);
		
		statsPanel = new StatisticsPanel(engine.getPlayer());
		layers.add(statsPanel, JLayeredPane.PALETTE_LAYER);
	}
	
	/**
	 * Creates a menubar with 3 options: Load, save and quit
	 * @author Jimmy
	 */
	private void makeMenu(){
		dialog = new JFileChooser("saves/");

		//create menu and menubars 
		JMenuBar bar = new JMenuBar();
		setJMenuBar(bar);

		JMenu fileMenu = new JMenu("File");
		bar.add(fileMenu);
		
		JMenu statsMenu = new JMenu("Statistics");
		bar.add(statsMenu);
		
		JMenu aboutMenu = new JMenu("About");
		bar.add(aboutMenu);
		
		
		
		JMenuItem statistics = new JMenuItem("Statistics");
		statistics.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				statsPanel.Show();
			}
		});
		statsMenu.add(statistics);
		
		JMenuItem help = new JMenuItem("Help");
		help.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				helpPanel.showHelp();
			}
		});
		aboutMenu.add(help);
		

		JMenuItem open = new JMenuItem("Open");
		open.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				if(dialog.showOpenDialog(null)== JFileChooser.APPROVE_OPTION){
					engine.load(dialog.getSelectedFile().getAbsolutePath());
					audio.stop();
				}
			}
		});
		fileMenu.add(open);

		JMenuItem save = new JMenuItem("Save");
		save.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				if(dialog.showSaveDialog(null)== JFileChooser.APPROVE_OPTION)
					engine.save(dialog.getSelectedFile().getAbsolutePath());
			}
		});
		fileMenu.add(save);

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
		for(Observable c : engine.getCharacters()){
			c.addObserver(this);
		}	
			
		// add observer to player
		engine.getPlayer().addObserver(this);
		engine.getPlayer().addObserver(inventoryPanel);
		engine.getPlayer().addObserver(informationPanel);
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
		while(true){
			// Here goes the things that should be updated constantly...
			gamePanel.repaint();
			
			try {Thread.sleep(10);} catch (InterruptedException e) {e.printStackTrace();}
		}	
	}
	
	
	/**
	 * If something changed by an observable object, update is called
	 */
	@Override
	public void update(Observable o, Object arg) {
		if(o instanceof ShopCharacter && arg instanceof PlayerCharacter){
			shopPanel.update( (ShopCharacter) o, (PlayerCharacter) arg);
		}else if( o instanceof CivilianCharacter && arg instanceof PlayerCharacter){
			questPanel.update( (CivilianCharacter) o, (PlayerCharacter) arg);
		}else if( o instanceof Character && arg instanceof String){
//			audio.startPlaying((String) arg);
		}
	}
}
