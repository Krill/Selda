package Handler;

import java.awt.Image;
import java.io.File;
import java.util.HashMap;
import javax.swing.ImageIcon;

/**
 * ItemImageHandler loads the correct image for the itemslots
 * @author kristoffer
 *
 */
public class ItemImageHandler {

	// fields:
	private HashMap<String, ImageIcon> itemImageMap;
	
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
			File directory = new File("images/items/");

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
