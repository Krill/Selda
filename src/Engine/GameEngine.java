package Engine;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.*;

import javax.swing.JOptionPane;

import Character.PlayerCharacter;
import Character.Character;
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
	private String fileName;
	
	private ArrayList<Character> characters;
	
	/**
	 * Constructor
	 */
	public GameEngine(){
		world = new World(1);
		player = new PlayerCharacter(0, 50, 50, 22, 28, "Link", 100, false, 1, 1000, 8);		
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
	private void checkMap(){
		// The X and Y-coordinate in the middle of the character
		int playerX = player.getX() + (player.getWidth()/2);
		int playerY = player.getY() + (player.getHeight()/2);
		ArrayList<Map> maps = getWorld().getMaps();
		Map currentMap = world.getCurrentMap();
		
		if(playerX > 800){
			// Switch to east map
			world.setCurrentMap(maps.get(Integer.parseInt(currentMap.getMap("east"))));
			player.setX(800-player.getX()-player.getWidth()+3);
			changeMap();
		}
		if(playerX < 0){
			// Switch to west map
			world.setCurrentMap(maps.get(Integer.parseInt(currentMap.getMap("west"))));	
			player.setX(800+player.getX()-3);
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
	
		save(fileName);
		JOptionPane.showMessageDialog(null, "the game was autosaved");
		player.resetDirection();
		
		
	}
	
	/**
	 * Saves the current state of the game
	 * @author Jimmy
	 * @param fileName
	 */
	public void save(String fileName){
		 try {
			 
//			 if(!fileName.toLowerCase().endsWith(".uno"))
//			 {
//				 fileName = fileName + ".uno";
//			 }
			 player.resetDirection();
			 this.fileName = fileName;
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
