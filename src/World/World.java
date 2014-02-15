package World;

import java.security.CodeSource;
import java.util.ArrayList;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Serializable;
import java.net.URL;

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
	
	private static String worldPath = "resources/worlds/1/maps/";
	
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
			
			File directory = new File("src/" + worldPath);
			
			File[] fileList = directory.listFiles();		
			Map[] mapArray = new Map[fileList.length];
					
			
			for(File file : fileList)
			{
				Map map = new Map();
				
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
			
			
		} catch (Exception e) {
			
			for(int i = 0; i < countMapFiles(); i++)
			{
				java.net.URL imageURL = getClass().getResource("/" + worldPath + i + ".txt");
				Map map = new Map();
				try {
					map.loadMap(new BufferedReader(new InputStreamReader(imageURL.openStream())));
				} catch (IOException e1) {
					e1.printStackTrace();
				}
				maps.add(map);
			}
		}
		
		currentMap = maps.get(0);	
	}
	
	private int countMapFiles()
	{
		int counter = 0;
		
		CodeSource src = this.getClass().getProtectionDomain().getCodeSource();
		if( src != null ) {
		    URL jar = src.getLocation();
		    ZipInputStream zip;
			try {
				zip = new ZipInputStream( jar.openStream());
				ZipEntry ze = null;
				while((ze = zip.getNextEntry()) != null)
				{
					if(ze.getName().contains(worldPath))
					{
						String sub = ze.getName().substring(worldPath.length());
						if(sub.length() > 3)
						{
							counter++;
						}
					}
				}
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		}
		
		return counter;
	}
}





