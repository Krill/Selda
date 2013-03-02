package Handler;

import java.awt.Image;
import java.io.File;
import java.util.HashMap;
import javax.swing.ImageIcon;

/**
 * This class provides the user with an image of the NPC character
 * given its current state.
 * All images is loaded from a given path. 
 * @author kristoffer
 */
public class CharacterImageHandler {

	// fields:
	private HashMap<String, HashMap<String, ImageIcon>> characterImageMap;
	
	// constants:
	private static final String CHARACTER_IMAGE_PATH = "images/character/";
	
	/**
	 * Constructor
	 */
	public CharacterImageHandler(){
		characterImageMap = new HashMap<String, HashMap<String, ImageIcon>>();
		loadImages();
	}
	
	/**
	 * Loads all images to a hashmap
	 */
	private void loadImages(){
		try{
			File directory = new File(CHARACTER_IMAGE_PATH);
			File[] characterList = directory.listFiles();
			
			for(File file : characterList){
				File charFolder = new File(CHARACTER_IMAGE_PATH+file.getName()+"/");
				File[] imageList = charFolder.listFiles();
				HashMap<String, ImageIcon> imageMap = new HashMap<String, ImageIcon>();
				
				for(File image : imageList){
					imageMap.put(image.getName().split("\\.")[0], new ImageIcon(image.getAbsolutePath()));
				}
				characterImageMap.put(file.getName(), imageMap);
			}
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	/**
	 * Return an image for the given direction
	 * @param direction Direction of the character
	 * @param isMoving Is the character moving?
	 * @param characterName The name of the character
	 * @return image The image to display
	 */
	public Image getImage(String direction, boolean isMoving, String characterName){	
		
		HashMap<String, ImageIcon> imageMap = characterImageMap.get(characterName);
		
		if(isMoving){
			return imageMap.get(direction+"_move").getImage();
		} else {
			return imageMap.get(direction).getImage();
		}
	}
}
