package GUI;

import java.awt.Dimension;
import javax.swing.JLabel;

import Handler.ItemImageHandler;


/**
 * Creates an ItemIcon which represents a slot in the inventory
 * @author kristoffer
 *
 */
@SuppressWarnings("serial")
public class ItemIcon extends JLabel{

	// fields:
	private ItemImageHandler itemImages;
	
	/**
	 * Constructor to create an ImageIcon without a listener
	 * @param itemName
	 */
	public ItemIcon(String itemName){	
		setOpaque(true);
		setPreferredSize(new Dimension(70, 70));
		
		itemImages = new ItemImageHandler();
		setIcon(itemName);
	}
	/**
	 * Sets the iconImage
	 * @param itemName
	 */
	public void setIcon(String itemName){
		setIcon(itemImages.getImage(itemName));
	}
}
