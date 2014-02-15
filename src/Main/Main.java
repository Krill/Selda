package Main;

import Engine.GameEngine;
import GUI.GameView;
import GUI.StartScreen;

/**
 * 
 * @author kristoffer & johan
 */
public class Main {

	// fields:
	private static GameView gameView;
	private static GameEngine gameEngine;
	private static StartScreen startScreen;
	private static Thread engineThread;
	private static Thread viewThread;
	private static boolean inGame=false;
	
	/**
	 * Main
	 * @param args
	 */
	public static void main(String[] args) {	
		startScreen = new StartScreen();
	}
	
	/**
	 * Creates a new game
	 * @param characterName
	 */
	public static void newGame(String characterName) {
		startScreen.dispose();
		inGame = true;
		
		gameEngine = new GameEngine(characterName);
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
	public static void restart(GameEngine newEngine){
		if(inGame){
			engineThread.stop();
			viewThread.stop();
			gameView.dispose();
		}
		gameEngine = newEngine;
	
		gameView = new GameView(newEngine);
		
		engineThread = new Thread(newEngine);
		viewThread = new Thread(gameView);
		
		engineThread.start();
		viewThread.start();
	}
	
	/**
	 * Loads a saved file and starts up the game engine
	 * @param filePath
	 */
	public static void load(String filePath){
		gameEngine = new GameEngine(null);
		gameEngine.load(filePath);
		inGame = true;
		startScreen.dispose();
	}
}
