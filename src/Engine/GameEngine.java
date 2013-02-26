package Engine;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.*;

import Character.PlayerCharacter;
import Character.Character;
import World.DoorTile;
import World.Map;
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
	private World world;
	private PlayerCharacter player;
	private Collision collision;	
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
		player = new PlayerCharacter(0, 50, 50, PLAYER_WIDTH, PLAYER_HEIGHT, characterName, PLAYER_LIFE, true, 1, PLAYER_MONEY, PLAYER_INVENTORY_SIZE);		
		characters = world.getCurrentMap().getCharacters();
		collision = new Collision(player, world.getCurrentMap().getBlockTiles(),characters);
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
			checkMapBounds();
			checkDoorTiles();
			
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
	 * Checks if the character moves out of map
	 */
	private void checkMapBounds(){
		// The X and Y-coordinate in the middle of the character
		int playerX = player.getX() + (player.getWidth()/2);
		int playerY = player.getY() + (player.getHeight()/2);
		ArrayList<Map> maps = getWorld().getMaps();
		Map currentMap = world.getCurrentMap();
		
		if(playerX > 800){
			// Switch to east map
			world.setCurrentMap(maps.get(Integer.parseInt(currentMap.getMap("east"))));
			player.setX(800-player.getX()-player.getWidth()+10);
			changeMap();
		}
		if(playerX < 0){
			// Switch to west map
			world.setCurrentMap(maps.get(Integer.parseInt(currentMap.getMap("west"))));	
			player.setX(800+player.getX()-10);
			changeMap();
			
		}
		if(playerY > 640){
			// Switch to south map
			world.setCurrentMap(maps.get(Integer.parseInt(currentMap.getMap("south"))));
			player.setY(640-player.getY()-player.getHeight()+10);
			changeMap();
		}
		if(playerY < 0){
			// Switch to north map
			world.setCurrentMap(maps.get(Integer.parseInt(currentMap.getMap("north"))));
			player.setY(640+player.getY()-10);
			changeMap();
			
		}
	}
	
	/**
	 * Checks for mapswitch with a doortile
	 */
	private void checkDoorTiles(){
		ArrayList<Map> maps = getWorld().getMaps();
		Map currentMap = world.getCurrentMap();
		
		for(DoorTile tile : currentMap.getDoorTiles()){
			
			if(player.getBounds().intersects(tile.getBounds()) && tile.isActive()){
				// get intersectarea
				int width = (int)player.getBounds().intersection(tile.getBounds()).getWidth();
				int height = (int)player.getBounds().intersection(tile.getBounds()).getHeight();
				
				if(width * height >= (PLAYER_WIDTH * PLAYER_HEIGHT)){
					System.out.println("Switch map!");
					world.setCurrentMap(maps.get(tile.getToMap()));
						
					// Search for the door that is connected to this
					for(DoorTile door : maps.get(tile.getToMap()).getDoorTiles()){
						if(tile.getToTileId() == door.getFromDoorId()){
							int newX = door.getX();
							int newY = door.getY();
							
							// Set the players new coordinates according to the doortile its moving to	
							player.setX(newX);
							player.setY(newY);
							
							// Set the door you are traveling to to not active
							door.setInactive(2000);
						}
					}
					
					changeMap();
				}
			}
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
		
		System.out.println("Autosaved to file: autosave.uno");
		save(System.getProperty("user.dir") + "\\saves\\autosave");
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
