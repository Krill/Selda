package World;

import java.io.FileReader;
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
	public void loadMap(File file)
	{
		try
		{
			BufferedReader reader = new BufferedReader(new FileReader(file));

			readMapName(reader);
			
			readBackTiles(reader);
			
			readBlockTiles(reader);
			
			readDoorTiles(reader);

			reader.close();
			
			//System.out.println("Loaded map");
		}
		catch(Exception e)
		{
			System.out.println("Error loading map");
			e.printStackTrace();
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





