package World;

import java.util.ArrayList;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.InputStreamReader;
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
	
	private static int numberOfMaps = 21;
	
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
		try {
			for(int i = 0; i < numberOfMaps; i++)
			{
				java.net.URL imageURL = getClass().getResource("/resources/worlds/" + id + "/maps/" + i + ".txt");
				Map map = new Map();
				map.loadMap(new BufferedReader(new InputStreamReader(imageURL.openStream())));
				maps.add(map);
				System.out.println("loading map nr " + i);
			}
		} catch (Exception e) {
			e.printStackTrace();
			
			
			File directory = new File("src/resources/worlds/" + id + "/maps/");
			
			File[] fileList = directory.listFiles();		
			Map[] mapArray = new Map[fileList.length];
					
			
			for(File file : fileList)
			{
				Map map = new Map();
				
				System.out.println(file.getName());
				
				try {
					map.loadMap(new BufferedReader(new FileReader(file)));
				} catch (FileNotFoundException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
	
				mapArray[Integer.parseInt(file.getName().split("\\.")[0])] = map;
			}
			
		
			for(Map map : mapArray)
			{
				maps.add(map);
			}
		}
		
		currentMap = maps.get(0);	
	}
}





