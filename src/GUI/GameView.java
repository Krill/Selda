package GUI;

import Engine.GameEngine;

/**
 * GameView
 * @author kristoffer & johan
 */
public class GameView implements Runnable{

	// fields:
	private GameEngine gameEngine;
	
	/**
	 * Constructor
	 */
	public GameView(GameEngine gameEngine){
		this.gameEngine = gameEngine;
	}
	
	@Override
	public void run() {
		while(true){
			
			// Here goes the things that should be updated constantly...
			
			try {Thread.sleep(10);} catch (InterruptedException e) {e.printStackTrace();}
		}	
	}
}
