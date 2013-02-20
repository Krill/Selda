package GUI;

import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.LineBorder;

import Character.PlayerCharacter;
import Character.ShopCharacter;
import Item.Item;

/**
 * Handles the ShopPanel which displays items to buy from a specific ShopNPC
 * @author kristoffer
 *
 */
@SuppressWarnings("serial")
public class ShopPanel extends JPanel{
	
	// fields:
	
	/**
	 * Constructor
	 */
	public ShopPanel(){
		setPanelDetails();

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
		setBorder(BorderFactory.createCompoundBorder(new LineBorder(Color.BLACK), BorderFactory.createEmptyBorder(5, 5, 5, 5)));
		setDoubleBuffered(true);
		setBounds(200, 220, 400, 200);
		setVisible(false);
		setFocusable(true);
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
		removeAll();
		setContent(shop, player);
		
		// make visible and set focus
		setLocation(shop.getX(),shop.getY());
		setVisible(true);
		requestFocusInWindow();	
	}
	
	/**
	 * Sets the new content
	 * @param shop
	 * @param player
	 */
	private void setContent(ShopCharacter shop, final PlayerCharacter player){
		setLayout(new GridLayout(shop.getInventory().size() + 1, 1));
		
		// Set titles
		JPanel titleRow = new JPanel();
		titleRow.setLayout(new GridLayout(1, 3));
		JLabel titleName = new JLabel("Name:");
		JLabel titleValye = new JLabel("Value:");
		JLabel titleBuy = new JLabel("Buy:");
		titleRow.add(titleName);
		titleRow.add(titleValye);
		titleRow.add(titleBuy);
		add(titleRow);
		
		for(final Item item : shop.getInventory()){
			JPanel row = new JPanel();
			row.setLayout(new GridLayout(1, 3));
			
			final JLabel itemName = new JLabel(item.getName());
			row.add(itemName);
			
			JLabel itemValue = new JLabel(""+item.getItemValue());
			row.add(itemValue);			
			
			JButton b = new JButton("Buy!");
			b.addActionListener(new ActionListener(){
				public void actionPerformed(ActionEvent e){
					buyItem(item, player);
					requestFocusInWindow();
				}
			});
			
			row.add(b);
			add(row);
		}
	}
	
	/**
	 * Buys an Item from the shop
	 * @param item
	 */
	private void buyItem(Item item, PlayerCharacter player){
		
		int response = JOptionPane.showConfirmDialog(this, "Are you sure you want to buy:\n" + item.getName() + "\nfor " + item.getItemValue() + " money", "Confirm!", JOptionPane.YES_NO_OPTION);
		
		if(response == JOptionPane.YES_OPTION){
			// If player has enough money
			if(player.getMoney() >= item.getItemValue()){
				
				// If player has space left in his inventory
				if(player.getCurrentInventorySize() < player.getMaxInventorySize()){
					System.out.println("Du har nu köpt: " + item.getName());
					
					Item newItem = item.clone();
					player.addToInventory(newItem);
									
					// ugly solutuiob
					int invID = player.getCurrentInventorySize() - 1;
					Item inItem = player.getInventory().get(invID);					
					inItem.setId(invID);
					
		
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
