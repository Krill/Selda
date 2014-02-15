package Handler;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.security.CodeSource;
import java.util.HashMap;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import javax.swing.ImageIcon;

/**
 * This class provides the user with an image of a given item.
 * All images is loaded from a given path. 
 * @author kristoffer
 */
public class ItemImageHandler {

	// fields:
	private HashMap<String, ImageIcon> itemImageMap;
	
	// constants:
	private static final String ITEM_IMAGE_PATH = "src/resources/images/items/";
	
	/**
	 * Constructor
	 */
	public ItemImageHandler(){
		itemImageMap = new HashMap<String, ImageIcon>();
		loadImages();
	}
	
	/**
	 * Loads all images to a hashmap
	 */
	private void loadImages(){
		try{
			File directory = new File(ITEM_IMAGE_PATH);

			File[] fileList = directory.listFiles();
			
			for(File file : fileList){
				itemImageMap.put(file.getName().split("\\.")[0], new ImageIcon(file.getAbsolutePath()));
			}
		}catch(Exception e){
			CodeSource src = this.getClass().getProtectionDomain().getCodeSource();
			if( src != null ) {
			    URL jar = src.getLocation();
			    ZipInputStream zip;
				try {
					zip = new ZipInputStream( jar.openStream());
					ZipEntry ze = null;
					while((ze = zip.getNextEntry()) != null)
					{
						if(ze.getName().contains("resources/images/items/"))
						{
							String sub = ze.getName().substring("resources/images/items/".length());
							if(sub.length() > 3)
							{
								String name = sub.substring(0,sub.length()-4);
								ImageIcon image = new ImageIcon(getClass().getResource("/" + ze.getName()));
								itemImageMap.put(name, image);
							}
						}
					}
				} catch (IOException ex) {
					ex.printStackTrace();
				}
			}
		}
	}
	
	/**
	 * Return an image for the given direction
	 * @param itemName The name of the item
	 * @return image The image of the item
	 */
	public ImageIcon getImage(String itemName){	
		return itemImageMap.get(itemName);
	}
}
