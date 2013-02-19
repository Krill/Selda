package GUI;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.Image;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;

import Character.PlayerCharacter;
import Handler.ItemImageHandler;
import Item.Item;

/**
 * Show the players inventory
 * @author kristoffer
 */
@SuppressWarnings("serial")
public class InventoryPanel extends JPanel implements Observer{
	
	// fields:
	private JPanel slotPanel;
	private JPanel equipedPanel;
	private ItemImageHandler itemImages;
	
	/**
	 * Constructor
	 */
	public InventoryPanel(){
		
		itemImages = new ItemImageHandler();
		
		setPanelDetails();
		createTopPanel();
		createBottomPanel();
	}
	
	/**
	 * Sets this panels default visuals
	 */
	private void setPanelDetails(){
		setOpaque(true);
		setDoubleBuffered(true);
		setPreferredSize(new Dimension(200, 640));
		setVisible(true);
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
	}
	
	/**
	 * Paints a background image
	 */
	public void paintComponent(Graphics g) {
		Image img = new ImageIcon("images/gui/inventory.png").getImage();	
		g.drawImage(img, 0, 0, null);
	}
	
	/**
	 * Creates the topPanel which handles the inventory slots
	 */
	private void createTopPanel(){
		JPanel topPanel = new JPanel();
		topPanel.setOpaque(false);
		topPanel.setPreferredSize(new Dimension(180, 480));
		
		JLabel placeHolder = new JLabel();
		placeHolder.setBorder(new EmptyBorder(30, 30, 10, 10));
		topPanel.add(placeHolder, BorderLayout.NORTH);
		
		slotPanel = new JPanel();
		slotPanel.setOpaque(false);
		slotPanel.setLayout(new FlowLayout(FlowLayout.LEFT, 12, 10));
		slotPanel.setPreferredSize(new Dimension(180, 480));
		
		topPanel.add(slotPanel, BorderLayout.SOUTH);
		add(topPanel);
	}
	
	/**
	 * Creates the bottomPanel which shows which items is active
	 */
	private void createBottomPanel(){
		add(Box.createVerticalGlue());

		JPanel bottomPanel = new JPanel();
		bottomPanel.setOpaque(false);
		bottomPanel.setPreferredSize(new Dimension(180, 80));
		
		equipedPanel = new JPanel();
		equipedPanel.setOpaque(false);
		equipedPanel.setLayout(new FlowLayout(FlowLayout.LEFT, 12, 10));
		equipedPanel.setPreferredSize(new Dimension(180, 80));
		
		bottomPanel.add(equipedPanel);
		
		// Just for now to see position
		JLabel label = new JLabel();
		label.setOpaque(true);
		label.setPreferredSize(new Dimension(70, 70));
		label.setIcon(itemImages.getImage("SuperSword"));
		equipedPanel.add(label);
		
		
		JLabel label2 = new JLabel();
		label2.setOpaque(true);
		label2.setPreferredSize(new Dimension(70, 70));
		label2.setIcon(itemImages.getImage("Armor"));
		equipedPanel.add(label2);
		// END
		
		add(bottomPanel);
	}

	/**
	 * Paints the items in the inventory to a slot
	 * @param player
	 * @param items
	 */
	private void updateInventory(PlayerCharacter player, ArrayList<Item> items){
		
		// clean slots
		slotPanel.removeAll();
		
		// paint slots
		for(Item item : items){
			JLabel label = new JLabel();
			label.setOpaque(true);
			label.setPreferredSize(new Dimension(70, 70));
			label.setIcon(itemImages.getImage(item.getName()));
			slotPanel.add(label);
		}
		slotPanel.revalidate();
	}
	
	/**
	 * Reset the inventory when game loads etc...
	 * @param player
	 */
	public void reset(PlayerCharacter player){
		updateInventory(player, player.getInventory());
	}
	
	/**
	 * When somthing has changed in the inventory, update is called
	 */
	@Override
	public void update(Observable o, Object arg) {
		if(o instanceof PlayerCharacter && arg instanceof ArrayList<?>){	
			updateInventory( (PlayerCharacter) o, (ArrayList<Item>) arg);
		}	
	}
}
