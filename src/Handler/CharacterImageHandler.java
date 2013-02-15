package Handler;

import java.awt.Image;
import java.io.File;
import java.util.HashMap;
import javax.swing.ImageIcon;

/**
 * PlayerImageHandler loads the correct image for the player
 * @author kristoffer
 *
 */
public class CharacterImageHandler {

	// fields:
	private HashMap<String, ImageIcon> characterImageMap;
	
	/**
	 * Constructor
	 */
	public CharacterImageHandler(){
		characterImageMap = new HashMap<String, ImageIcon>();
		loadImages();
	}
	
	/**
	 * Loads all images to a hashmap
	 */
	private void loadImages(){
		try{
			File directory = new File("images/character/");

			File[] fileList = directory.listFiles();
			
			for(File file : fileList){
				characterImageMap.put(file.getName().split("\\.")[0], new ImageIcon(file.getAbsolutePath()));
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
	public Image getImage(String direction, boolean isMoving){	
		if(isMoving){
			return characterImageMap.get(direction+"_move").getImage();
		} else {
			return characterImageMap.get(direction).getImage();
		}
	}
}
