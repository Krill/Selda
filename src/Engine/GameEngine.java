package Engine;

import java.util.*;

import Character.PlayerCharacter;
import Character.ShopCharacter;
import Character.EnemyCharacter;

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
	
	private ArrayList<EnemyCharacter> enemies;
	private ArrayList<ShopCharacter> shops;
	
	/**
	 * Constructor
	 */
	public GameEngine(){
		world = new World(1);
		player = new PlayerCharacter(50, 50, 32, 32, "Link", false, 100, 1, true, true, 100, 5);
		
		enemies = world.getCurrentMap().getEnemies();
		shops = world.getCurrentMap().getShops();
		
		collision = new Collision(player,world.getCurrentMap().getBlockTiles(),enemies,shops);
		
		
	}
	
	/**
	 * Returns the world
	 * @return world
	 */
	public World getWorld(){
		return world;
	}
	
	/**
	 * Returns the Player object
	 * @return player
	 */
	public PlayerCharacter getPlayer(){
		return player;
	}
	
	/**
	 * Returns the Collision object
	 * @return collision
	 */
	public Collision getCollision(){
		return collision;
	}
	
	/**
	 * Returns the shops object
	 * @return shops
	 */
	public ArrayList<ShopCharacter> getShops(){
		return shops;
	}
	
	/**
	 * Returns the enemies
	 * @return enemies
	 */
	public ArrayList<EnemyCharacter> getEnemies(){
		return enemies;
	}
	
	/**
	 * Here goes all things that should constantly get updated
	 */
	@Override
	public void run() {
		while(true){
			
			// Updates the player
			player.update();
			// Checks collision
			collision.update();
			// Updates enemies
			for(EnemyCharacter enemy : enemies){ enemy.update(); };
			for(ShopCharacter shop : shops){ shop.update(); };
			
			try {Thread.sleep(10);} catch (InterruptedException e) {e.printStackTrace();}
		}
	}
}
