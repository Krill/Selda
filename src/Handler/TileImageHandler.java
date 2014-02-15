package Handler;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.security.CodeSource;
import java.util.HashMap;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import javax.swing.ImageIcon;
import java.awt.Image;

/**
 * This class will read all the tileimages in a specific directory.
 * The reading of the file is done in the class constructor.
 * After this is done, other classes may use this class to retrieve images of tiles, by supplying the tile id.
 * @author Johan
 * @version 2013-02-11
 */
public class TileImageHandler {

	private HashMap<Integer, ImageIcon> tileImageMap;
	
	/**
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
			File directory = new File("src/resources/images/tiles/backtiles/");

			File[] fileList = directory.listFiles();

			for(File file : fileList)
			{
				String[] name = file.getName().split("\\.");
				tileImageMap.put(Integer.parseInt(name[0]), new ImageIcon(file.getAbsolutePath()));
				
			}
		}
		catch(Exception e)
		{
			System.out.println("HERE");
			CodeSource src = this.getClass().getProtectionDomain().getCodeSource();
			if( src != null ) {
			    URL jar = src.getLocation();
			    ZipInputStream zip;
				try {
					zip = new ZipInputStream( jar.openStream());
					ZipEntry ze = null;
					while((ze = zip.getNextEntry()) != null)
					{
						if(ze.getName().contains("resources/images/tiles/backtiles/"))
						{
							
							String sub = ze.getName().substring("resources/images/tiles/backtiles/".length());
							if(sub.length() > 3)
							{
								String name = sub.substring(0,sub.length()-4);
								ImageIcon image = new ImageIcon(getClass().getResource("/" + ze.getName()));
								tileImageMap.put(Integer.parseInt(name), image);
								
							}
						}
					}
				} catch (Exception ex) {
					ex.printStackTrace();
				}
			}
		}
	}
	
	
	/**
	 * Returns an image of a tile
	 * @param id The id of the tile to get an image of
	 * @return Returns an image of the tile with the specified id
	 */
	public Image getImage(int id)
	{
		return tileImageMap.get(id).getImage();
	}
}
