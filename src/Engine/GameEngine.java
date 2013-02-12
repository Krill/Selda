package Engine;

import Character.PlayerCharacter;
import World.World;

/**
 * GameEngine
 * @author kristoffer & johan
 */
public class GameEngine implements Runnable{

	// fields:
	private World world;
	private PlayerCharacter player;
	
	/**
	 * Constructor
	 */
	public GameEngine(){
		world = new World(1);
		player = new PlayerCharacter(0, 0, 32, 32, "Link", false, 100, 1, true, true, 100, 5);
	}
	
	/**
	 * Returns the world
	 * @return world
	 */
	public World getWorld(){
		return world;
	}
	
	/**
	 * Returns the payerobject
	 * @return player
	 */
	public PlayerCharacter getPlayer(){
		return player;
	}
	
	/**
	 * Here goes all things that should constantly get updated
	 */
	@Override
	public void run() {
		while(true){
			
			// Updates the player
			player.update();
			
			try {Thread.sleep(10);} catch (InterruptedException e) {e.printStackTrace();}
		}
	}
}
