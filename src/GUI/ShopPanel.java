package GUI;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import Character.PlayerCharacter;
import Character.ShopCharacter;
import Item.ArmorItem;
import Item.Item;
import Item.LifeItem;
import Item.WeaponItem;

/**
 * Handles the ShopPanel which displays items to buy from a specific ShopNPC
 * @author kristoffer
 *
 */
@SuppressWarnings("serial")
public class ShopPanel extends JPanel{
	
	// fields:
	private JPanel shopPanel;
	
	// constants:
	private static final String PANEL_BACKGROUND = "images/gui/shop.png";	
	
	/**
	 * Constructor
	 */
	public ShopPanel(){
		setPanelDetails();
		createShopPanel();
		
		// Add a KeyListener to this window when active
		addKeyListener(new KeyAdapter(){
			@Override
			public void keyPressed(KeyEvent e) {
				setVisible(false);
			}
		});
	}
	
	/**
	 * Sets this panels default visuals
	 */
	private void setPanelDetails(){
		setOpaque(true);
		setDoubleBuffered(true);
		setBounds(100, 300, 600, 300);
		setVisible(false);
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		setFocusable(true);
	}
	
	/**
	 * Create the shopPanel, stores all items to sell
	 */
	private void createShopPanel(){
		// fill out shop label
		add(Box.createVerticalStrut(50));
		
		shopPanel = new JPanel();
		shopPanel.setPreferredSize(new Dimension(500, 200));
		shopPanel.setLayout(new FlowLayout(FlowLayout.LEFT, 30, 10));
		shopPanel.setOpaque(false);
		add(shopPanel);
	}
	
	/**
	 * Paints a background image
	 */
	public void paintComponent(Graphics g) {
		Image img = new ImageIcon(PANEL_BACKGROUND).getImage();	
		g.drawImage(img, 0, 0, null);
	}

	/**
	 * Updates the shop panel to display the shop which the player interacted with
	 * @param o
	 * @param arg
	 */
	public void update(ShopCharacter shop, PlayerCharacter player) {

		// cancel players movement
		player.setUp(false);
		player.setDown(false);
		player.setLeft(false);
		player.setRight(false);
		
		// remove old shops and set new content
		shopPanel.removeAll();
		updateShop(shop, player);
		
		// make visible and set focus
		setVisible(true);
		requestFocusInWindow();	
	}
	
	/**
	 * Updates the shop with items
	 * @param shop
	 * @param player
	 */
	private void updateShop(ShopCharacter shop, final PlayerCharacter player){
		for(final Item item : shop.getInventory()){
			
			// create item panel
			JPanel shopItem = new JPanel();
			shopItem.setPreferredSize(new Dimension(250, 70));
			shopItem.setLayout(new FlowLayout(FlowLayout.LEFT, 0, 0));
			shopItem.setOpaque(false);
			
			// create item image
			ItemIcon itemIcon = new ItemIcon(item);
			shopItem.add(itemIcon);
			
			// create item info panel
			JPanel itemInfo = new JPanel();
			itemInfo.setPreferredSize(new Dimension(120, 70));
			itemInfo.setBorder(new EmptyBorder(2, 10, 0, 0));
			itemInfo.setOpaque(false);
			itemInfo.setLayout(new BoxLayout(itemInfo, BoxLayout.Y_AXIS));
			shopItem.add(itemInfo);
			
			// create item name label
			JLabel itemName = new JLabel(item.getName());
			itemName.setFont(new Font("Verdana", Font.BOLD, 14));
			itemName.setBorder(new EmptyBorder(0,0,10,0));
			itemName.setForeground(Color.WHITE);
			itemInfo.add(itemName);
			
			// create item value label
			JLabel itemValue = new JLabel("Value:     " + item.getItemValue()+"");
			itemValue.setFont(new Font("Verdana", Font.PLAIN, 11));
			itemValue.setForeground(Color.WHITE);
			itemInfo.add(itemValue);
			
			// create item effect label
			JLabel itemEffect = new JLabel();
			if(item instanceof WeaponItem){
				itemEffect.setText("Damage: " + ((WeaponItem) item).getAttackDamage());
			} else if (item instanceof ArmorItem){
				itemEffect.setText("Armor:    " + ((ArmorItem) item).getDefenceRating());
			} else if(item instanceof LifeItem){
				itemEffect.setText("Life:        " + ((LifeItem) item).getLifeValue());
			}
			itemEffect.setFont(new Font("Verdana", Font.PLAIN, 11));
			itemEffect.setForeground(Color.WHITE);
			itemInfo.add(itemEffect);		
			
			// create button are
			JPanel buttonArea = new JPanel();
			buttonArea.setPreferredSize(new Dimension(60, 50));
			buttonArea.setOpaque(false);
			buttonArea.setLayout(new BoxLayout(buttonArea, BoxLayout.Y_AXIS));			
			shopItem.add(buttonArea);
			
			// create buy button
			JButton buy = new JButton("Buy!");
			buy.addActionListener(new ActionListener(){
				public void actionPerformed(ActionEvent e){
					buyItem(item, player);
					requestFocusInWindow();
				}
			});			
			
			buttonArea.add(Box.createVerticalGlue());
			buttonArea.add(buy);
			
			shopPanel.add(shopItem);
		}
	}
	
	/**
	 * Buys an Item from the shop
	 * @param shop
	 * @param player
	 */
	private void buyItem(Item item, PlayerCharacter player){
		int response = JOptionPane.showConfirmDialog(this, "Are you sure you want to buy:\n" + item.getName() + "\nfor " + item.getItemValue() + " money", "Confirm!", JOptionPane.YES_NO_OPTION);

		if(response == JOptionPane.YES_OPTION){
			// If player has enough money
			if(player.getMoney() >= item.getItemValue()){

				// If player has space left in his inventory
				if(player.getCurrentInventorySize() < player.getMaxInventorySize()){
					System.out.println("You've bought: " + item.getName());

					// clone item from shop and give it an unique ID
					Item newItem = item.clone();
					newItem.setId((int) System.currentTimeMillis());
					player.addToInventory(newItem);

					player.setMoney(player.getMoney() - newItem.getItemValue());
				} else {
					System.out.println("Your inventory is full!");
					JOptionPane.showMessageDialog(this, "Your inventory is full!");
				}
			} else {
				System.out.println("You cannot afford this item!");
				JOptionPane.showMessageDialog(this, "You cannot afford this item!");
			}
		}
	}
}
