package Engine;

import java.io.Serializable;
import java.util.*;

import Character.PlayerCharacter;
import Character.Character;
import World.Map;
import World.World;
import Engine.Collision;

/**
 * GameEngine
 * @author kristoffer & johan
 */
public class GameEngine implements Runnable, Serializable{

	// fields:
	private static final long serialVersionUID = 12L;
	private World world;
	private PlayerCharacter player;
	private Collision collision;
	
	private ArrayList<Character> characters;
	
	/**
	 * Constructor
	 */
	public GameEngine(){
		world = new World(1);
		player = new PlayerCharacter(0, 50, 50, 22, 27, "Link", 100, false, 1, true, true, 100, 5);		
		characters = world.getCurrentMap().getCharacters();
		collision = new Collision(player,world.getCurrentMap().getBlockTiles(),characters);	
	}
	
	/**
	 * Returns the world
	 * @return world
	 */
	public World getWorld(){
		return world;
	}
	
	/**
	 * Sets the World when you have loaded a previous game
	 * @param world
	 */
	public void setWorld(World world){
		this.world = world;
	}
	
	/**
	 * Returns the Player object
	 * @return player
	 */
	public PlayerCharacter getPlayer(){
		return player;
	}
	
	/**
	 * Sets the PlayerCharacter when you have loaded a previous game
	 * @param player
	 */
	public void setPlayer(PlayerCharacter player){
		this.player = player;
	}
	
	/**
	 * Returns the Collision object
	 * @return collision
	 */
	public Collision getCollision(){
		return collision;
	}
	
	/**
	 * Sets the Collision when you have loaded a previous game
	 * @param collision
	 */
	public void setCollision(Collision collision){
		this.collision = collision;
	}

	/**
	 * Returns a List of characters, thats currently in the map
	 * @return List of Characters
	 */
	public ArrayList<Character> getCharacters(){
		return characters;
	}
	
	/**
	 * Sets the Character list when you have loaded a previous game
	 * @param characters
	 */
	public void setCharacterList(ArrayList<Character> characters){
		this.characters = characters;
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
			Iterator<Character> it = characters.iterator();
			while(it.hasNext()){
				Character c = it.next();
				
				// Checks if character is still alive
				if(c.getHealth() <= 0){
					it.remove();
				}
				c.update();
			}
			
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
	
	/**
	 * Changes the current map to a new one
	 */
	private void changeMap()
	{
		collision.setCurrentTiles(world.getCurrentMap().getBlockTiles());
		collision.setCurrentCharacters(world.getCurrentMap().getCharacters());
		characters = world.getCurrentMap().getCharacters();
	}
}
