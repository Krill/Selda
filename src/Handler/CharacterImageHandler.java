package Handler;

import java.awt.Image;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.security.CodeSource;
import java.util.HashMap;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import javax.swing.ImageIcon;

/**
 * This class provides the user with an image of the NPC character
 * given its current state.
 * All images is loaded from a given path. 
 * @author kristoffer
 */
public class CharacterImageHandler {

	// fields:
	private HashMap<String, ImageIcon> characterImageMap;
	
	// constants:
	private static final String CHARACTER_IMAGE_PATH = "src/resources/images/character/";
	
	/**
	 * Constructor
	 */
	public CharacterImageHandler(){
		characterImageMap = new HashMap<>();
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
				
				for(File image : imageList){
					characterImageMap.put(charFolder.getName() + "/" +  image.getName().subSequence(0, image.getName().length() - 4),  new ImageIcon(image.getAbsolutePath()));
				}
				
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
						if(ze.getName().contains("resources/images/character/"))
						{
							String sub = ze.getName().substring("resources/images/character/".length());
							if(sub.length() > 3)
							{
								String name = sub.substring(0,sub.length()-4);
								ImageIcon image = new ImageIcon(getClass().getResource("/" + ze.getName()));
								characterImageMap.put(name, image);
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
	 * @param direction Direction of the character
	 * @param isMoving Is the character moving?
	 * @param characterName The name of the character
	 * @return image The image to display
	 */
	public Image getImage(String direction, boolean isMoving, String characterName){	
		if(isMoving){
			return characterImageMap.get(characterName + "/" + direction+"_move").getImage();
		} else {
			return characterImageMap.get(characterName + "/" + direction).getImage();
		}
	}
}
