package GUI;

import java.io.File;
import java.util.HashMap;
import javax.swing.ImageIcon;
import java.awt.Image;

import World.Map;

public class TileImage {

	private HashMap<Integer, ImageIcon> tileImageMap;
	
	public TileImage()
	{
		tileImageMap = new HashMap<>();
		loadImages();
	}
	
	
	/**
	 *  @deprecated
	 */
	private void loadImages()
	{
		try
		{
			int id = 0;
			File directory = new File("Where to save?");

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
	
	public Image getImage(int id)
	{
		return tileImageMap.get(id).getImage();
	}
}
