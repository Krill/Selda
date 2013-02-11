package GUI;

import java.awt.Color;
import javax.swing.JFrame;

import Engine.GameEngine;

/**
 * GameView
 * @author kristoffer & johan
 */
@SuppressWarnings("serial")
public class GameView extends JFrame implements Runnable{

	// fields:
	private GameEngine gameEngine;
	
	// constants:
	private static final String GAME_TITLE = "GAMETITLE";
	private static final int SCREEN_WIDTH = 806;
	private static final int SCREEN_HEIGHT = 668;	
	
	/**
	 * Constructor
	 */
	public GameView(GameEngine gameEngine){
		this.gameEngine = gameEngine;
		
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

		setVisible(true);
	}
	
	@Override
	public void run() {
		while(true){
			
			// Here goes the things that should be updated constantly...
			
			try {Thread.sleep(10);} catch (InterruptedException e) {e.printStackTrace();}
		}	
	}
}
