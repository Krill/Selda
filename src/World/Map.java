package World;

import java.util.HashMap;
import java.util.ArrayList;

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
		//Hur ser filformatet ut, 2 olika filer för block och back tiles? mm mm
	}
}





