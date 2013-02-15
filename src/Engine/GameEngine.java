package Engine;

import java.util.*;

import Character.CivilianCharacter;
import Character.PlayerCharacter;
import Character.ShopCharacter;
import Character.EnemyCharacter;

import World.Map;
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
	private ArrayList<CivilianCharacter> civilians;
	
	/**
	 * Constructor
	 */
	public GameEngine(){
		world = new World(1);
		player = new PlayerCharacter(0, 50, 50, 32, 32, "Link", false, 100, 1, true, true, 100, 5);
		
		enemies = world.getCurrentMap().getEnemies();
		shops = world.getCurrentMap().getShops();
		civilians = world.getCurrentMap().getCivilians();
		
		collision = new Collision(player,world.getCurrentMap().getBlockTiles(),enemies,shops,civilians);	
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
	
	public ArrayList<CivilianCharacter> getCivilians(){
		return civilians;
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
			
			// Check for mapswitch
			checkMap();
			
			// Updates enemies
			for(EnemyCharacter enemy : enemies){ enemy.update(); };
			for(ShopCharacter shop : shops){ shop.update(); };
			for(CivilianCharacter civilian : civilians){ civilian.update(); };
			
			try {Thread.sleep(10);} catch (InterruptedException e) {e.printStackTrace();}
		}
	}
	
	/**
	 * Checks if the character moves out of map
	 */
	private void checkMap(){
		// The X and Y-coordinate in the middle of the character
		int playerX = player.getX() + (player.getWidth()/2);
		int playerY = player.getY() + (player.getHeight()/2);
		ArrayList<Map> maps = getWorld().getMaps();
		Map currentMap = world.getCurrentMap();
		
		if(playerX > 800){
			// Switch to east map
			world.setCurrentMap(maps.get(Integer.parseInt(currentMap.getMap("east"))));
			player.setX(800-player.getX()-player.getWidth());
			changeMap();
		}
		if(playerX < 0){
			// Switch to west map
			world.setCurrentMap(maps.get(Integer.parseInt(currentMap.getMap("west"))));	
			player.setX(800+player.getX());
			changeMap();
			
		}
		if(playerY > 640){
			// Switch to south map
			world.setCurrentMap(maps.get(Integer.parseInt(currentMap.getMap("south"))));
			player.setY(640-player.getY()-player.getHeight());
			changeMap();
		}
		if(playerY < 0){
			// Switch to north map
			world.setCurrentMap(maps.get(Integer.parseInt(currentMap.getMap("north"))));
			player.setY(640+player.getY());
			changeMap();
			
		}		
	}
	
	private void changeMap()
	{
		collision.setCurrentTiles(world.getCurrentMap().getBlockTiles());
		collision.setCurrentEnemies(world.getCurrentMap().getEnemies());
		collision.setCurrentCivilians(world.getCurrentMap().getCivilians());
		enemies = world.getCurrentMap().getEnemies();
		shops = world.getCurrentMap().getShops();
		civilians = world.getCurrentMap().getCivilians();
	}
	
}
