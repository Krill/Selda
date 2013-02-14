package GUI;

import java.awt.Color;
import java.awt.Dimension;
import java.util.Observable;

import GUI.Board;
import javax.swing.JFrame;
import javax.swing.JLayeredPane;
import javax.swing.border.BevelBorder;
import javax.swing.border.EtchedBorder;

import Engine.GameEngine;

/**
 * GameView
 * @author kristoffer & johan
 * @version 2013-02-11
 */
@SuppressWarnings("serial")
public class GameView extends JFrame implements Runnable{

	// fields:
	private GameEngine gameEngine;
	private JLayeredPane layers;
	private MessagePanel message;
	private Board board;
	
	// constants:
	private static final String GAME_TITLE = "GAMETITLE";
	private static final int SCREEN_WIDTH = 800;
	private static final int SCREEN_HEIGHT = 640;	
	
	/**
	 * Constructor
	 */
	public GameView(GameEngine gameEngine){
		this.gameEngine = gameEngine;
		
		layers = new JLayeredPane();
		board = new Board(gameEngine);
		message = new MessagePanel();
		
		setDetails();
		addObservers();
		makeFrame();
	}
	
	/**
	 * Set details about the GameViews architecture, right now it's 2 layers!
	 */
	private void setDetails(){
		add(layers);
		layers.setPreferredSize(new Dimension(SCREEN_WIDTH, SCREEN_HEIGHT));
		
		board.setBounds(0, 0, 800, 640);
		
		message.setBounds(300,120,200,200);
		
		layers.add(board, new Integer(0), 0);
		layers.add(message, new Integer(1), 0);
	}
	
	/**
	 * Adds all observable objects to its observer
	 */
	private void addObservers(){
		for(Observable s : gameEngine.getShops()){
			s.addObserver(message);
		}
		
		for(Observable s : gameEngine.getCivilians()){
			s.addObserver(message);
		}
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
	
	@Override
	public void run() {
		while(true){
			
			board.repaint();
			
			// Here goes the things that should be updated constantly...
			
			try {Thread.sleep(10);} catch (InterruptedException e) {e.printStackTrace();}
		}	
	}
}
