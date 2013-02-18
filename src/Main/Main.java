package Main;

import Engine.GameEngine;
import GUI.GameView;

/**
 * 
 * @author kristoffer & johan
 */
public class Main {

	// fields:
	private static GameView gameView;
	private static GameEngine gameEngine;
	private static Thread engineThread;
	private static Thread viewThread;	
	
	public static void main(String[] args) {
		
		gameEngine = new GameEngine();
		gameView = new GameView(gameEngine);
		
		engineThread = new Thread(gameEngine);
		viewThread = new Thread(gameView);
		
		engineThread.start();
		viewThread.start();
	}
	
	/**
	 * When the Player has loaded a current state of the game old
	 * threads stop and new ones are created. Loads the new state.
	 */
	public static void restart(){
		engineThread.stop();
		viewThread.stop();
		
		gameView.dispose();
		gameView = new GameView(gameEngine);
		
		engineThread = new Thread(gameEngine);
		viewThread = new Thread(gameView);
		
		engineThread.start();
		viewThread.start();
	}	
}
