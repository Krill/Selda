package World;

import java.util.ArrayList;


/**
 * 
 * @author Johan
 * @version 2013-02-08
 */
public class World {
	private int id;
	private ArrayList<Map> maps;
	private Map currentMap;
	
	public World(int id)
	{
		this.id = id;
		maps = new ArrayList<Map>();
		currentMap = null;
	}
	
	/**
	 * Return the map id
	 * @return id
	 */
	public int getID()
	{
		return id;
	}
	
	
	/**
	 * Returns a List of all the maps in the world
	 * @return maps
	 */
	public ArrayList<Map> getMaps()
	{
		return maps;		
	}
	
	
	/**
	 * returns the current map
	 * @return currentMap
	 */
	public Map getCurrentMap()
	{
		return currentMap;		
	}
	
	
	/**
	 * Adds the specified map to the world
	 * @param map
	 */
	public void addMap(Map map)
	{
		maps.add(map);
	}
	
	
	/**
	 * Sets the specified map as current map.
	 * @param map
	 */
	public void setCurrentMap(Map map)
	{
		currentMap = map;
	}
	
	
	/**
	 * Loads the world with the specified id
	 * @param id
	 * @deprecated
	 */
	public void loadWorld(int id)
	{
		//filformat mm.
	}
	

}





