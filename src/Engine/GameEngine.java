package Engine;

import Character.PlayerCharacter;
import World.World;
import Engine.Collision;

/**
 * GameEngine
 * @author kristoffer & johan
 */
public class GameEngine implements Runnable{

	// fields:
	private World world;
	private PlayerCharacter player;
	private Collision collision;
	
	/**
	 * Constructor
	 */
	public GameEngine(){
		world = new World(1);
		player = new PlayerCharacter(50, 50, 32, 32, "Link", false, 100, 1, true, true, 100, 5);
		collision = new Collision(player,world.getCurrentMap().getBlockTiles(),null);
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
	
	public Collision getCollision(){
		return collision;
	}
	
	/**
	 * Here goes all things that should constantly get updated
	 */
	@Override
	public void run() {
		while(true){
			
			// Updates the player
			player.update();
			collision.update();
			
			try {Thread.sleep(10);} catch (InterruptedException e) {e.printStackTrace();}
		}
	}
}
