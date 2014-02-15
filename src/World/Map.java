package World;

import java.io.FileReader;
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
		characters = new ArrayList<>();
		charHandler = CharacterHandler.getCharacterHandler();
	}
	
	/**
	 * Returns the maps name
	 * @return name The maps name
	 */
	public String getName()
	{
		return name;
	}
	
	/**
	 * Returns the mapID in the specified direction. If no map exists, returns null.
	 * @param direction The direction to check
	 * @return MapID The ID of the map connected
	 */
	public String getMap(String direction)
	{
		return neighbourMaps.get(direction);		
	}

	
	
	/**
	 * Returns a List of all backTiles
	 * @return backTiles A list of all the backtiles
	 */
	public ArrayList<Tile> getBackTiles()
	{
		return backTiles;		
	}
	
	
	/**
	 * Returns a List of the BlockTiles
	 * @return blockTiles A list of all the blockTiles
	 */
	public ArrayList<Tile> getBlockTiles()
	{
		return blockTiles;
	}
	
	
	
	/**
	 * Returns a List of all doorTiles
	 * @return doorTiles A list of all the doortiles
	 */
	public ArrayList<Tile> getDoorTiles()
	{
		return doorTiles;		
	}
	
	
	
	/**
	 * Returns a List of all characters
	 * @return characters A list of all the charactesr
	 */
	public ArrayList<Character> getCharacters()
	{
		return characters;		
	}

	/**
	 * Sets new characters
	 * @param characters A list of all the new charactesr
	 */
	public void setCharacters(ArrayList<Character> characters)
	{
		this.characters = characters;		
	}

	/**
	 * Loads the map saved at the specified file
	 * @param file The file to read data from
	 */
	public void loadMap(BufferedReader reader)
	{
		try
		{
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
	
	/**
	 * Reads all the enemies from the stream
	 * @param reader The stream to read from
	 * @throws IOException
	 */
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
	
	/**
	 * Reads all the enemies from the stream
	 * @param reader The stream to read from
	 * @throws IOException
	 */
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
	
	/**
	 * Reads all the enemies from the stream
	 * @param reader The stream to read from
	 * @throws IOException
	 */
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
	 * @param reader The stream to read from
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
	 * @param reader The stream to read from
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
	 * @param reader The stream to read from
	 * @throws IOException
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
	 * @param reader The stream to read from
	 * @throws IOException
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
				if(line.length() == 5 || line.length() == 6)
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
	 * @param reader The stream to read from
	 * @throws IOException
	 */
	public void readMapName(BufferedReader reader)

	throws IOException
	{
		
		name = reader.readLine();
		
	
		while(!(reader.readLine().equals("[BACKTILES]")));
	}
}





