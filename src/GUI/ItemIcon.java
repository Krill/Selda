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
	
	// constants:
	private static final String EMPTY_ICON = "Empty";
	
	/**
	 * Constructor
	 * @param itemName
	 */
	public ItemIcon(String itemName){
		setOpaque(true);
		setPreferredSize(new Dimension(70, 70));
		
		itemImages = new ItemImageHandler();
		
		if(itemName == null){
			setIcon(itemImages.getImage(EMPTY_ICON));
		} else {
			setIcon(itemImages.getImage(itemName));
		}
	}
}
