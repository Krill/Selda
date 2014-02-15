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
 * This class provides the user with an image of the player character
 * given its current state.
 * All images is loaded from a given path. 
 * @author kristoffer
 */
public class PlayerImageHandler {

	// fields:
	private HashMap<String, ImageIcon> playerImageMap;
	
	// constants:
	private static final String PLAYER_IMAGE_PATH = "src/resources/images/player/";
	
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
			CodeSource src = this.getClass().getProtectionDomain().getCodeSource();
			if( src != null ) {
			    URL jar = src.getLocation();
			    ZipInputStream zip;
				try {
					zip = new ZipInputStream( jar.openStream());
					ZipEntry ze = null;
					while((ze = zip.getNextEntry()) != null)
					{
						if(ze.getName().contains("resources/images/player/"))
						{
							String sub = ze.getName().substring("resources/images/player/".length());
							if(sub.length() > 3)
							{
								String name = sub.substring(0,sub.length()-4);
								ImageIcon image = new ImageIcon(getClass().getResource("/" + ze.getName()));
								playerImageMap.put(name, image);
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
