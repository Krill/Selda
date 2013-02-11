package World;

import java.io.FileReader;
import java.util.HashMap;
import java.util.ArrayList;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

/**
 * 
 * @author Johan
 * @version 2013-02-08
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
	 * 
	 * @param name
	 */
	public Map(String name)
	{
		this.name = name;
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
	 * @deprecated
	 */
	public void loadMap(String filePath)
	{
		
		
		
		try
		{
			BufferedReader reader = new BufferedReader(new FileReader(filePath));
			
			readBackTiles(reader);
			
			reader.close();
			
		}
		catch(Exception e)
		{
			System.out.println("Error loading map");
		}
			
	}
	
	
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
			
			y += height;
		}
		
	}
}





