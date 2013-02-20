package GUI;

import java.awt.Dimension;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JLabel;

import Character.PlayerCharacter;
import Handler.ItemImageHandler;
import Item.Item;
import Item.WeaponItem;

/**
 * Creates an ItemIcon which represents a slot in the inventory
 * @author kristoffer
 *
 */
@SuppressWarnings("serial")
public class ItemIcon extends JLabel implements MouseListener{

	// fields:
	private ItemImageHandler itemImages;
	private Item item;
	private PlayerCharacter player;
	
	// constants:
	private static final String EMPTY_ICON = "Empty";
	
	/**
	 * Constructor to create an ImageIcon without a listener
	 * @param itemName
	 */
	public ItemIcon(Item item, PlayerCharacter player){	
		this.item = item;
		this.player = player;
		
		setOpaque(true);
		setPreferredSize(new Dimension(70, 70));
		itemImages = new ItemImageHandler();		
		setIcon(itemImages.getImage(EMPTY_ICON));
		addMouseListener(this);
	}
	
	/**
	 * Sets which item this label represents
	 * @param item
	 */
	public void setItem(Item item){
		this.item = item;
		setIcon(item.getName());
	}
	
	/**
	 * Sets the iconImage
	 * @param itemName
	 */
	private void setIcon(String itemName){
		setIcon(itemImages.getImage(itemName));
	}

	@Override
	public void mouseClicked(MouseEvent arg0) {
		System.out.println("Clicked!");
		if(item instanceof WeaponItem){
			player.equipWeapon( (WeaponItem) item);
		}
	}

	@Override
	public void mouseEntered(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mousePressed(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseReleased(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}
}
