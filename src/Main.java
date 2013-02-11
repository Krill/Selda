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
	
	public static void main(String[] args) {
		
		gameEngine = new GameEngine();
		gameView = new GameView(gameEngine);
		
		new Thread(gameEngine).start();
		new Thread(gameView).start();
	}

}
