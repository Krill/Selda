package Engine;

import java.io.Serializable;
import java.util.ArrayList;

import World.DoorTile;
import World.Map;
import World.Tile;

public class MapChange implements Serializable{

	// fields:
	private GameEngine engine;
	
	// constants:
	private static final long serialVersionUID = 96L;
	private static final int PLAYER_WIDTH = 22;
	private static final int PLAYER_HEIGHT = 28;
	
	/**
	 * Constructor
	 * @param engine
	 */
	public MapChange(GameEngine engine){
		this.engine = engine;
	}
	
	/**
	 * Checks for mapchange
	 */
	public void update(){
		checkMapBounds();
		checkDoorTiles();
	}
	
	/**
	 * Checks if the character moves out of map
	 */
	private void checkMapBounds(){
		// The X and Y-coordinate in the middle of the character
		int playerX = engine.getPlayer().getX() + (engine.getPlayer().getWidth()/2);
		int playerY = engine.getPlayer().getY() + (engine.getPlayer().getHeight()/2);
		ArrayList<Map> maps = engine.getWorld().getMaps();
		Map currentMap = engine.getWorld().getCurrentMap();
		
		if(playerX > 800){
			// Switch to east map
			engine.getWorld().setCurrentMap(maps.get(Integer.parseInt(currentMap.getMap("east"))));
			engine.getPlayer().setX(800-engine.getPlayer().getX()-engine.getPlayer().getWidth()+10);
			changeMap();
		}
		if(playerX < 0){
			// Switch to west map
			engine.getWorld().setCurrentMap(maps.get(Integer.parseInt(currentMap.getMap("west"))));	
			engine.getPlayer().setX(800+engine.getPlayer().getX()-10);
			changeMap();
			
		}
		if(playerY > 640){
			// Switch to south map
			engine.getWorld().setCurrentMap(maps.get(Integer.parseInt(currentMap.getMap("south"))));
			engine.getPlayer().setY(640-engine.getPlayer().getY()-engine.getPlayer().getHeight()+10);
			changeMap();
		}
		if(playerY < 0){
			// Switch to north map
			engine.getWorld().setCurrentMap(maps.get(Integer.parseInt(currentMap.getMap("north"))));
			engine.getPlayer().setY(640+engine.getPlayer().getY()-10);
			changeMap();
			
		}
	}
	
	/**
	 * Checks for mapswitch with a doortile
	 */
	private void checkDoorTiles(){
		ArrayList<Map> maps = engine.getWorld().getMaps();
		Map currentMap = engine.getWorld().getCurrentMap();
		
		for(Tile tile : currentMap.getDoorTiles()){
			
			// Cast tile to doorTile
			DoorTile d1 = (DoorTile)tile;
			
			if(engine.getPlayer().getBounds().intersects(d1.getBounds()) && d1.isActive()){
				// get intersectarea
				int width = (int)engine.getPlayer().getBounds().intersection(d1.getBounds()).getWidth();
				int height = (int)engine.getPlayer().getBounds().intersection(d1.getBounds()).getHeight();
				
				if(width * height >= (PLAYER_WIDTH * PLAYER_HEIGHT)){
					System.out.println("Switch map!");
					engine.getWorld().setCurrentMap(maps.get(d1.getToMap()));
						
					// Search for the door that is connected to this
					for(Tile door : maps.get(d1.getToMap()).getDoorTiles()){
						
						// Cast to DoorTile
						DoorTile d2 = (DoorTile)door;
						
						if(d1.getToTileId() == d2.getFromDoorId()){
							int newX = door.getX();
							int newY = door.getY();
							
							// Set the players new coordinates according to the doortile its moving to	
							engine.getPlayer().setX(newX);
							engine.getPlayer().setY(newY);
							
							// Set the door you are traveling to to not active
							d2.setInactive(2000);
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
		engine.getCollision().setCurrentTiles(engine.getWorld().getCurrentMap().getBlockTiles());
		engine.getCollision().setCurrentCharacters(engine.getWorld().getCurrentMap().getCharacters());
		engine.setCharacterList(engine.getWorld().getCurrentMap().getCharacters());
		
		System.out.println("Autosaved to file: autosave.uno");
		engine.save(System.getProperty("user.dir") + "\\saves\\autosave");
	}
}
