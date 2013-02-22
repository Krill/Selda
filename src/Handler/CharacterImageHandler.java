package Handler;

import java.awt.Image;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import javax.swing.ImageIcon;

import Character.Character;

/**
 * PlayerImageHandler loads the correct image for the player
 * @author kristoffer
 *
 */
public class CharacterImageHandler {

	// fields:
	private HashMap<String, HashMap<String, ImageIcon>> characterImageMap;
	
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
			File directory = new File("images/character/");
			File[] characterList = directory.listFiles();
			
			for(File file : characterList){
				File charFolder = new File("images/character/"+file.getName()+"/");
				File[] imageList = charFolder.listFiles();
				HashMap imageMap = new HashMap<String, ImageIcon>();
				
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
	 * @param direction, isMoving
	 * @return
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
