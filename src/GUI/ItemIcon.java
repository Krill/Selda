package GUI;

import java.awt.Dimension;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JLabel;

import Character.PlayerCharacter;
import Handler.ItemImageHandler;
import Item.ArmorItem;
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
		if(item != null){
			setIcon(item.getName());
		} else {
			setIcon(EMPTY_ICON);
		}
	}
	
	/**
	 * Sets the iconImage
	 * @param itemName
	 */
	private void setIcon(String itemName){
		setIcon(itemImages.getImage(itemName));
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		if(e.isMetaDown()){
			if(item != null && item.equals(player.getWeapon())){
				player.equipWeapon(null);
				System.out.println("Unequip weapon!");
			} else if(item != null && item.equals(player.getArmor())){
				player.equipArmor(null);
				System.out.println("Unequip armor!");
			}
		} else {
			if(item instanceof WeaponItem){
				player.equipWeapon( (WeaponItem) item);
			} else if (item instanceof ArmorItem){
				player.equipArmor( (ArmorItem) item);
			}
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
