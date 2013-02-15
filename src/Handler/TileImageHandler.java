package Handler;

import java.io.File;
import java.util.HashMap;
import javax.swing.ImageIcon;
import java.awt.Image;

/**
 * 
 * @author Johan
 * @version 2013-02-11
 */
public class TileImageHandler {

	private HashMap<Integer, ImageIcon> tileImageMap;
	
	/*
	 * Creates a TileImage and initiates its images
	 */
	public TileImageHandler(){
		tileImageMap = new HashMap<>();
		loadImages();
	}
	
	
	/**
	 * Initiates all the Tile images
	 */
	private void loadImages(){
		try
		{
			int id = 0;
			File directory = new File("images/tiles/backtiles/");

			File[] fileList = directory.listFiles();

			for(File file : fileList)
			{
				tileImageMap.put(id, new ImageIcon(file.getAbsolutePath()));
				id++;
			}
		}
		catch(Exception e)
		{
			System.out.println("Error loading tile images");
		}
	}
	
	
	/**
	 * 
	 * @param id
	 * @return Returns an image of the tile with the specified id
	 */
	public Image getImage(int id)
	{
		return tileImageMap.get(id).getImage();
	}
}
