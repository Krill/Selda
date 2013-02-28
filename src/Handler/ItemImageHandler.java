package Handler;

import java.io.File;
import java.util.HashMap;
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
	private static final String ITEM_IMAGE_PATH = "images/items/";
	
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
			e.printStackTrace();
		}
	}
	
	/**
	 * Return an image for the given direction
	 * @param direction, isMoving
	 * @return
	 */
	public ImageIcon getImage(String itemName){	
		return itemImageMap.get(itemName);
	}
}
