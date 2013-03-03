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
 * @author kristoffer, johan & jimmy
 */
public class GameEngine implements Runnable, Serializable{

	// fields:
	private static final long serialVersionUID = 12L;
	private World world;
	private PlayerCharacter player;
	private Collision collision;
	private EventEngine events;
	private MapChange mapChange;	
	private ArrayList<Character> characters;
	private boolean alive;
	
	// constants:
	private static final int PLAYER_WIDTH = 22;
	private static final int PLAYER_HEIGHT = 28;
	private static final int PLAYER_LIFE = 100;
	private static final int PLAYER_MONEY = 100;
	private static final int PLAYER_INVENTORY_SIZE = 6;
	private static final int PLAYER_MAXHEALTH = 100;
	
	/**
	 * Constructor
	 * @param characterName The name of the character
	 */
	public GameEngine(String characterName){
		world = new World(1);
		player = new PlayerCharacter(0, 350, 420, PLAYER_WIDTH, PLAYER_HEIGHT, characterName, PLAYER_LIFE, true, 1, PLAYER_MONEY, PLAYER_INVENTORY_SIZE, PLAYER_MAXHEALTH);		
		characters = world.getCurrentMap().getCharacters();
		collision = new Collision(player, world.getCurrentMap().getBlockTiles(),characters);
		mapChange = new MapChange(this);
		events = new EventEngine(this);
		alive = true;
	}
	
	/**
	 * Returns the world
	 * @return world The world
	 */
	public World getWorld(){
		return world;
	}
	
	/**
	 * Sets the World when you have loaded a previous game
	 * @param world The world
	 */
	public void setWorld(World world){
		this.world = world;
	}
	
	/**
	 * Returns the Player object
	 * @return player The player character
	 */
	public PlayerCharacter getPlayer(){
		return player;
	}
	
	/**
	 * Sets the PlayerCharacter when you have loaded a previous game
	 * @param player The player character
	 */
	public void setPlayer(PlayerCharacter player){
		this.player = player;
	}
	
	/**
	 * Returns the Collision object
	 * @return collision The collision manager
	 */
	public Collision getCollision(){
		return collision;
	}
	
	/**
	 * Sets the Collision when you have loaded a previous game
	 * @param collision The collision manager
	 */
	public void setCollision(Collision collision){
		this.collision = collision;
	}

	/**
	 * Returns a List of characters, thats currently in the map
	 * @return characters List of Characters
	 */
	public ArrayList<Character> getCharacters(){
		return characters;
	}
	
	/**
	 * Sets the Character list when you have loaded a previous game
	 * @param characters List of Characters
	 */
	public void setCharacterList(ArrayList<Character> characters){
		this.characters = characters;
	}
	
	/**
	 * Returns the EventEngine
	 * @return events The event engine
	 */
	public EventEngine getEventEngine(){
		return events;
	}
	
	
	/**
	 * Returns the players alive status
	 * @return True if the player is alive, false otherwise.
	 */
	public boolean getAlive()
	{
		return alive;
	}
	
	/**
	 * Sets the EventEngine
	 * @param events The event engine
	 */
	public void setEventEngine(EventEngine events){
		this.events = events;
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
			
			if(player.getHealth() <= 0)
			{
				alive = false;
			}
						
			try {Thread.sleep(10);} catch (InterruptedException e) {e.printStackTrace();}
		}
	}

	/**
	 * Saves the current state of the game
	 * @param filePath The file path to save to
	 */
	public void save(String filePath){
		 try {
			 if(!filePath.toLowerCase().endsWith(".uno"))
			 {
				 filePath = filePath + ".uno";
			 }
			 ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(filePath));
			 out.writeObject(this);
			 out.close();
		 } catch(Exception e) {
			 e.printStackTrace();
			 System.exit(0);
		 }
	}
	
	/**
	 * Loads a current state of the game
	 * @param filePath The file path to load from
	 */
	public void load(String filePath){
		 try {
			 ObjectInputStream in = new ObjectInputStream(new FileInputStream(filePath));
			 GameEngine gE = (GameEngine)in.readObject();
			 in.close();
			 
			 // reset player direction
			 gE.getPlayer().resetDirection();
			 
			 setCharacterList(gE.getCharacters());
			 setCollision(gE.getCollision());
			 setPlayer(gE.getPlayer());
			 setWorld(gE.getWorld());
			 setEventEngine(gE.getEventEngine());
			 
			 Main.restart();
		 } catch(Exception e) {
			 e.printStackTrace();
			 System.exit(0);
		 }
	}
}
