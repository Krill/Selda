package Handler;

import java.awt.Image;
import java.io.File;
import java.util.HashMap;
import javax.swing.ImageIcon;

/**
 * This class provides the user with an image of the player character
 * given its current state.
 * All images is loaded from a given path. 
 * @author kristoffer
 */
public class PlayerImageHandler {

	// fields:
	private HashMap<String, ImageIcon> playerImageMap;
	
	// constants:
	private static final String PLAYER_IMAGE_PATH = "images/player/";
	
	/**
	 * Constructor
	 */
	public PlayerImageHandler(){
		playerImageMap = new HashMap<String, ImageIcon>();
		loadImages();
	}
	
	/**
	 * Loads all images to a hashmap
	 */
	private void loadImages(){
		try{
			File directory = new File(PLAYER_IMAGE_PATH);

			File[] fileList = directory.listFiles();
			
			for(File file : fileList){
				playerImageMap.put(file.getName().split("\\.")[0], new ImageIcon(file.getAbsolutePath()));
			}
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	/**
	 * Return an image for the given direction
	 * @param direction Direction of the player
	 * @param isMoving Is the player moving?
	 * @param isAttacking Is the player attacking?
	 * @return image The image to display
	 */
	public Image getImage(String direction, boolean isMoving, boolean isAttacking){	
		if(isMoving && !isAttacking){
			direction += "_move";
		}
		if(isAttacking){
			direction += "_attack";
		}
		return playerImageMap.get(direction).getImage();
	}
}
