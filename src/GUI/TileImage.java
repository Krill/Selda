package GUI;

import java.io.File;
import java.util.HashMap;
import javax.swing.ImageIcon;
import java.awt.Image;

import World.Map;

/**
 * 
 * @author Johan
 * @version 2013-02-11
 */
public class TileImage {

	private HashMap<Integer, ImageIcon> tileImageMap;
	
	/*
	 * Creates a TileImage and initiates its images
	 */
	public TileImage()
	{
		tileImageMap = new HashMap<>();
		loadImages();
	}
	
	
	/**
	 * Initiates all the Tile images
	 */
	private void loadImages()
	{
		try
		{
			int id = 0;
			File directory = new File("src/GUI/");

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
