package World;

import java.io.FileReader;
import java.util.HashMap;
import java.util.ArrayList;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.File;

/**
 * 
 * @author Johan
 * @version 2013-02-11
 */
public class Map 
{
	private String name;
	
	//String direction mapped to neighboring map
	private HashMap<String, Map> neighbourMaps;
	private ArrayList<Tile> backTiles;
	private ArrayList<Tile> blockTiles;
	
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
	 * Returns the map in the specified direction. If no map exists, returns null.
	 * @param direction
	 * @return map
	 */
	public Map getMap(String direction)
	{
		return neighbourMaps.get(direction);		
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
	 * Returns a List of all backTiles
	 * @return backTiles
	 */
	public ArrayList<Tile> getBackTiles()
	{
		return backTiles;		
	}


	/**
	 * Loads the map saved at the specified filepath
	 * @param filePath
	 */
	public void loadMap(File file)
	{
		try
		{
			BufferedReader reader = new BufferedReader(new FileReader(file));

			readMapName(reader);
			
			readBackTiles(reader);
			
			readBlockTiles(reader);

			reader.close();
		}
		catch(Exception e)
		{
			System.out.println("Error loading map");
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
			if(totLine.equals("[BLOCKTILES]"))
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
	 * @param reader
	 * @throws IOException
	 * @Deprecated
	 */
	public void readBlockTiles(BufferedReader reader)
	throws IOException
	{
		String totLine = null;
		int x = 0;
		int y = 0;
		
		while((totLine = reader.readLine()) != null)
		{	
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





