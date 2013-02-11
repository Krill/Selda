package GUI;

import java.awt.Color;
import GUI.Board;
import javax.swing.JFrame;

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
	private Board board;
	
	// constants:
	private static final String GAME_TITLE = "GAMETITLE";
	private static final int SCREEN_WIDTH = 806;
	private static final int SCREEN_HEIGHT = 668;	
	
	/**
	 * Constructor
	 */
	public GameView(GameEngine gameEngine){
		this.gameEngine = gameEngine;
		board = new Board(gameEngine);
		
		
		
		makeFrame();
	}
	
	/**
	 * Creates the window
	 */
	private void makeFrame(){
		pack();
		
		setTitle(GAME_TITLE);
		setSize(SCREEN_WIDTH, SCREEN_HEIGHT);
		setBackground(Color.BLACK);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLocationRelativeTo(null);
		
		add(board);

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
