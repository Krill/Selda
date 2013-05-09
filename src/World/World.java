package World;

import java.util.ArrayList;
import java.io.File;
import java.io.Serializable;

/**
*  This class keeps track of all the maps that are available and 
*  which one is the current map
 * @author Johan
 * @version 2013-02-11
 */
public class World implements Serializable{
	
	// fields:
	private static final long serialVersionUID = 2L;
	private int id;
	private ArrayList<Map> maps;
	private Map currentMap;
	
	/**
	 * Constructor. Initiates a world of specified ID
	 * @param id The ID of the world
	 */
	public World(int id)
	{
		this.id = id;
		maps = new ArrayList<Map>();
		currentMap = null;
		
		loadWorld(id);
	}
	
	/**
	 * Return the map id
	 * @return id The MapID
	 */
	public int getID()
	{
		return id;
	}
	
	
	/**
	 * Returns a List of all the maps in the world
	 * @return maps A list of all maps
	 */
	public ArrayList<Map> getMaps()
	{
		return maps;		
	}
	
	
	/**
	 * returns the current map
	 * @return currentMap The current map
	 */
	public Map getCurrentMap()
	{
		return currentMap;		
	}
	
	
	
	/**
	 * Sets the specified map as current map.
	 * @param map The map to set to current
	 */
	public void setCurrentMap(Map map)
	{
		currentMap = map;
	}
	
	
	/**
	 * Loads the world with the specified id
	 * @param id The ID of the world
	 */
	public void loadWorld(int id)
	{
		File directory = new File("worlds/" + id + "/maps/");
		
		File[] fileList = directory.listFiles();		
		Map[] mapArray = new Map[fileList.length];
				
		
		for(File file : fileList)
		{
			Map map = new Map();
			
			//System.out.println(file.getName());
			
			map.loadMap(file);

			
			mapArray[Integer.parseInt(file.getName().split("\\.")[0])] = map;
			
		}
		
	
		for(Map map : mapArray)
		{
			maps.add(map);
		}
		
		
		currentMap = maps.get(0);
	}
}





