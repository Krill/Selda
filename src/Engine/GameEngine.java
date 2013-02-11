package Engine;

import World.World;

/**
 * GameEngine
 * @author kristoffer
 */
public class GameEngine implements Runnable{

	// fields:
	private World world;
	
	/**
	 * Constructor
	 */
	public GameEngine(){
		world = new World(1);
	}
	
	/**
	 * Returns the world
	 * @return world
	 */
	public World getWorld(){
		return world;
	}
	
	/**
	 * Here goes all things that should constantly get updated
	 */
	@Override
	public void run() {
		while(true){
			
			// Here goes the things that should be updated constantly...
			
			try {Thread.sleep(10);} catch (InterruptedException e) {e.printStackTrace();}
		}
	}
}
