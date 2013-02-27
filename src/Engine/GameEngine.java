package Engine;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.*;

import Character.PlayerCharacter;
import Character.Character;
import World.World;
import Engine.Collision;
import Main.Main;

/**
 * GameEngine
 * @author kristoffer & johan
 */
public class GameEngine implements Runnable, Serializable{

	// fields:
	private static final long serialVersionUID = 12L;
	private EventEngine events;
	private World world;
	private PlayerCharacter player;
	private Collision collision;	
	private MapChange mapChange;	
	private ArrayList<Character> characters;
	
	// constants:
	private static final int PLAYER_WIDTH = 22;
	private static final int PLAYER_HEIGHT = 28;
	private static final int PLAYER_LIFE = 100;
	private static final int PLAYER_MONEY = 100;
	private static final int PLAYER_INVENTORY_SIZE = 6;
	
	/**
	 * Constructor
	 */
	public GameEngine(String characterName){
		world = new World(1);
		player = new PlayerCharacter(0, 370, 370, PLAYER_WIDTH, PLAYER_HEIGHT, characterName, PLAYER_LIFE, true, 1, PLAYER_MONEY, PLAYER_INVENTORY_SIZE);		
		characters = world.getCurrentMap().getCharacters();
		collision = new Collision(player, world.getCurrentMap().getBlockTiles(),characters);
		mapChange = new MapChange(this);
		events = new EventEngine(this);
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
			
			// Check for event triggers
			events.update();
			
			// Updates the player
			player.update();
			
			// Checks collision
			collision.update();
			
			// Check for mapswitch
			mapChange.update();
			
			// Updates enemies
			Iterator<Character> it = characters.iterator();
			while(it.hasNext()){
				Character c = it.next();
				
				// Checks if character is still alive
				if( c.isDead() ){
					player.updateQuests(c.getName(), player);
					player.updateStatistics("Monster");
					it.remove();
				}
				c.update();
			}
						
			try {Thread.sleep(10);} catch (InterruptedException e) {e.printStackTrace();}
		}
	}

	/**
	 * Saves the current state of the game
	 * @author Jimmy
	 * @param fileName
	 */
	public void save(String fileName){
		 try {
			 if(!fileName.toLowerCase().endsWith(".uno"))
			 {
				 fileName = fileName + ".uno";
			 }
			 ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(fileName));
			 out.writeObject(this);
			 out.close();
		 } catch(Exception e) {
			 e.printStackTrace();
			 System.exit(0);
		 }
	}
	
	/**
	 * Loads a current state of the game
	 * @author Jimmy
	 * @param fileName
	 */
	public void load(String fileName){
		 try {
			 ObjectInputStream in = new ObjectInputStream(new FileInputStream(fileName));
			 GameEngine gE = (GameEngine)in.readObject();
			 in.close();
			 
			 // reset player direction
			 gE.getPlayer().resetDirection();
			 
			 setCharacterList(gE.getCharacters());
			 setCollision(gE.getCollision());
			 setPlayer(gE.getPlayer());
			 setWorld(gE.getWorld());
			 
			 Main.restart();
		 } catch(Exception e) {
			 e.printStackTrace();
			 System.exit(0);
		 }
	}
}
