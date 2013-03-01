package World;

import java.io.FileReader;
import Item.Item;
import Handler.CharacterHandler;
import java.util.HashMap;
import java.util.ArrayList;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.File;
import java.io.Serializable;

import Character.Character;

/**
 * This class contains information about all the tiles and
 * characters in a specific map and load them when the map is used
 * @author Johan
 * @version 2013-02-11
 * 
 */
public class Map implements Serializable{
	
	//String direction mapped to neighboring map
	private static final long serialVersionUID = 3L;
	private String name;
	private HashMap<String, String> neighbourMaps;
	private ArrayList<Tile> backTiles;
	private ArrayList<Tile> blockTiles;
	private ArrayList<Tile> doorTiles;
	private ArrayList<Item> items;
	private CharacterHandler charHandler;
	private ArrayList<Character> characters;
	
	
	//Skapa en statisk klass för dessa!
	private final int width = 32;
	private final int height = 32;
	
	
	
	/**
	 * Creates an empty map.
	 * Use the loadMap method to insert tiles.
	 */
	public Map()
	{
		neighbourMaps = new HashMap<>();
		backTiles = new ArrayList<>();
		blockTiles = new ArrayList<>();
		doorTiles = new ArrayList<>();
		items = new ArrayList<>();
		characters = new ArrayList<>();
		charHandler = CharacterHandler.getCharacterHandler();
	}
	
	/**
	 * Returns the maps name
	 * @return name
	 */
	public String getName()
	{
		return name;
	}
	
	/**
	 * Returns the mapID in the specified direction. If no map exists, returns null.
	 * @param direction
	 * @return MapID
	 */
	public String getMap(String direction)
	{
		return neighbourMaps.get(direction);		
	}

	
	
	/**
	 * Returns a List of all backTiles
	 * @return backTiles
	 */
	public ArrayList<Tile> getBackTiles()
	{
		return backTiles;		
	}
	
	
	
	/**
	 * Returns a List of the BlockTiles
	 * @return blockTiles
	 */
	public ArrayList<Tile> getBlockTiles()
	{
		return blockTiles;
	}
	
	
	
	/**
	 * Returns a List of all doorTiles
	 * @return doorTiles
	 */
	public ArrayList<Tile> getDoorTiles()
	{
		return doorTiles;		
	}
	
	
	
	/**
	 * Returns a List of all characters
	 * @return characters
	 */
	public ArrayList<Character> getCharacters()
	{
		return characters;		
	}


	/**
	 * Loads the map saved at the specified file
	 * @param file
	 */
	public void loadMap(File file)
	{
		try
		{
			BufferedReader reader = new BufferedReader(new FileReader(file));

			readMapName(reader);
			
			readBackTiles(reader);
			
			readBlockTiles(reader);
			
			readDoorTiles(reader);
			
			readNeighbourMaps(reader);
			
			readEnemies(reader);	
			
			readShopNpc(reader);
			
			readCivilian(reader);

			reader.close();
			
			System.out.println("Added map");
			
		}
		catch(Exception e)
		{
			System.out.println("Error loading map");
			e.printStackTrace();
		}

	}
	
	private void readEnemies(BufferedReader reader)
	throws IOException
	{
		String totLine = null;
		
		while((totLine = reader.readLine()) != null)
		{
			if(totLine.equals("[SHOPNPC]"))
			{
				break;
			}
			
			String[] lines = totLine.split(" ");
			
			int x = Integer.parseInt(lines[0]);
			int y = Integer.parseInt(lines[1]);
			String name = lines[2];
			
			characters.add(charHandler.getCharacter(name, x, y));
			
		}
	}
	
	private void readCivilian(BufferedReader reader)
	throws IOException
	{
		String totLine = null;
		
		while((totLine = reader.readLine()) != null)
		{
			if(totLine.equals("[ITEMS]"))
			{
				break;
			}
			
			
			String[] lines = totLine.split(" ");
			
			int x = Integer.parseInt(lines[0]);
			int y = Integer.parseInt(lines[1]);
			String name = lines[2];
			Character cha = charHandler.getCharacter(name, x, y);
			characters.add(cha);
			
		}
	}
	
	private void readShopNpc(BufferedReader reader)
	throws IOException
	{
		String totLine = null;
		
		while((totLine = reader.readLine()) != null)
		{
			if(totLine.equals("[CIVILIAN]"))
			{
				break;
			}
			
			String[] lines = totLine.split(" ");
			
			int x = Integer.parseInt(lines[0]);
			int y = Integer.parseInt(lines[1]);
			String name = lines[2];
			
			characters.add(charHandler.getCharacter(name, x, y));
		}
	}
	
	/**
	 * Loads the BackTiles. Breaks when it reads "[BLOCKTILES]".
	 * @param reader
	 * @throws IOException
	 */
	public void readBackTiles(BufferedReader reader)
	throws IOException
	{
		String totLine = null;
		int x = 0;
		int y = 0;
		
		while((totLine = reader.readLine()) != null)
		{	
			if(totLine.trim().equals("[BLOCKTILES]"))
			{
				break;
			}
			
			String[] lines = totLine.split(" ");
			
			for(String line : lines)
			{
				backTiles.add(new Tile(Integer.parseInt(line), x, y , width, height));
				x += width;
			}
			
			x = 0;
			y += height;
		}
	}
		
	
	
	/**
	 * Load the maps neighbor maps
	 * @param reader
	 */
	private void readNeighbourMaps(BufferedReader reader){
		// local variables
		String line;

		try {
			while((line = reader.readLine()) != null){

				// If neighbormaps segment done, break!
				if (line.equalsIgnoreCase("[ENEMIES]"))
					break;	

				// Delete empty spaces from the line
				String[] mapId = line.split(" ");
				String[] dir = {"north", "east", "west", "south"};

				// add the surrounding maps is to the neighborMaps list
				for(int i=0; i<dir.length; i++){
					neighbourMaps.put(dir[i], mapId[i]);
				}
			}
		} catch (Exception e){
			System.out.println("An error has occured during the reading of a map.");
			e.printStackTrace();
		}
	}
	
	
	
	/**
	 * Reads the blockTiles. Ends when it reads EOF.
	 * @param reader
	 * @throws IOException
	 * @deprecated
	 */
	public void readBlockTiles(BufferedReader reader)
	throws IOException
	{
		String totLine = null;
		int x = 0;
		int y = 0;
		
		while((totLine = reader.readLine()) != null)
		{	
			if(totLine.trim().equals("[DOORTILES]"))
			{
				break;
			}
			String[] lines = totLine.split(" ");
			
			for(String line : lines)
			{
				//Hur ska man läsa in ishabitable och is pushable??
				if(Integer.parseInt(line) != 0)
				{
					blockTiles.add(new BlockTile(Integer.parseInt(line), x, y , width, height, false));
				}
				x += width;
			}
			
			x = 0;
			y += height;
		}
	}
	
	/**
	 * Reads the doorTiles. Ends when it reads EOF.
	 * @param reader
	 * @throws IOException
	 * @deprecated
	 */
	public void readDoorTiles(BufferedReader reader)
	throws IOException
	{
		String totLine = null;
		int x = 0;
		int y = 0;
		
		while((totLine = reader.readLine()) != null)
		{	
			if(totLine.equals("[NEIGHBOURS]"))
			{
				break;
			}
			String[] lines = totLine.split(" ");
			for(String line : lines)
			{
				if(line.length() == 5)
				{
					String[] info = line.split("x");

					int toMap = Integer.parseInt(info[0]);
					int toTileId = Integer.parseInt(info[1]);
					int fromDoorId = Integer.parseInt(info[2]);
					doorTiles.add(new DoorTile(0, x, y , width, height, toMap, toTileId, fromDoorId));
				}
				x += width;
			}
			
			x = 0;
			y += height;
		}
	}
	
	/**
	 * Reads the maps name.
	 * @param reader
	 * @throws IOException
	 */
	public void readMapName(BufferedReader reader)

	throws IOException
	{
		
		name = reader.readLine();
		
	
		while(!(reader.readLine().equals("[BACKTILES]")));
	}
}





