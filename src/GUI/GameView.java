package GUI;

import java.awt.Color;
import java.awt.Dimension;
import java.util.Observable;
import java.util.Observer;
import javax.swing.JFrame;
import javax.swing.JLayeredPane;

import Character.CivilianCharacter;
import Character.PlayerCharacter;
import Character.ShopCharacter;
import Engine.GameEngine;

/**
 * The main window that handles what should be displayed
 * @author kristoffer
 *
 */
@SuppressWarnings("serial")
public class GameView extends JFrame implements Observer, Runnable{

	// fields:
	private GameEngine engine;
	private JLayeredPane layers;
	private GamePanel gamePanel;
	private ShopPanel shopPanel;
	private InventoryPanel inventoryPanel;
	
	// constants:
	private static final String GAME_TITLE = "GAMETITLE";
	private static final int SCREEN_WIDTH = 800;
	private static final int SCREEN_HEIGHT = 640;	
	
	/**
	 * Constructor
	 * @param engine
	 */
	public GameView(GameEngine engine){
		this.engine = engine;
		
		// Create the layered panel and add each panel
		layers = new JLayeredPane();
		layers.setPreferredSize(new Dimension(SCREEN_WIDTH, SCREEN_HEIGHT));
		add(layers);
		createPanels();
		
		addObservers();
		makeFrame();
	}
	
	/**
	 * Create all panels and adds them to each layer
	 */
	private void createPanels(){
		gamePanel = new GamePanel(engine);
		layers.add(gamePanel, JLayeredPane.DEFAULT_LAYER);
		
		shopPanel = new ShopPanel();
		layers.add(shopPanel, JLayeredPane.MODAL_LAYER);
		
		inventoryPanel = new InventoryPanel();
		layers.add(inventoryPanel, JLayeredPane.POPUP_LAYER);
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
	 * Updates the window constanlty
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
			// To-do
		}else if( o instanceof PlayerCharacter && arg instanceof String){
			inventoryPanel.update( (PlayerCharacter) o);
			System.out.println("Inventory!");
		}
	}
}
