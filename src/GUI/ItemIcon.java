package GUI;

import java.awt.Dimension;
import javax.swing.JLabel;

import Handler.ItemImageHandler;
import Item.Item;


/**
 * This is a label that represents an item by displaying its picture.
 * @author kristoffer
 *
 */
@SuppressWarnings("serial")
public class ItemIcon extends JLabel{

	// fields:
	private ItemImageHandler itemImages;
	private Item item;
	
	// constants:
	private static final String EMPTY_ICON = "Empty";
	
	/**
	 * Constructor to create an empty ImageIcon
	 */
	public ItemIcon(){	
		this.item = null;
		setOpaque(true);
		setPreferredSize(new Dimension(70, 70));
		
		itemImages = new ItemImageHandler();
		setIcon(EMPTY_ICON);
	}
	
	/**
	 * Constructor to create an ItemIcon that represents an Item
	 * @param item The item that this object should represemt
	 */
	public ItemIcon(Item item){
		this();
		setItem(item);
	}
	
	
	/**
	 * Sets the iconImage
	 * @param itemName The name of the item to set this objects picture to
	 */
	public void setIcon(String itemName){
		setIcon(itemImages.getImage(itemName));
	}
	
	/**
	 * Updates the item this label represents
	 * @param item The item you want this object to represents
	 */
	public void setItem(Item item){
		this.item = item;
		
		if(item == null){
			setIcon(EMPTY_ICON);
		} else {
			setIcon(item.getName());
		}
	}
	
	/**
	 * Return the item this label represents
	 * @return item The item this object represents
	 */
	public Item getItem(){
		return item;
	}
}
