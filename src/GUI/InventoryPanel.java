package GUI;

import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.LineBorder;

import Character.PlayerCharacter;
import Item.Item;

/**
 * Show the players inventory
 * @author kristoffer
 */
@SuppressWarnings("serial")
public class InventoryPanel extends JPanel{

	/**
	 * Constructor
	 */
	public InventoryPanel(){
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
		setBorder(BorderFactory.createCompoundBorder(new LineBorder(Color.BLACK), BorderFactory.createEmptyBorder(10, 10, 10, 10)));
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
	public void update(PlayerCharacter player) {
		
		// cancel players movement
		player.setUp(false);
		player.setDown(false);
		player.setLeft(false);
		player.setRight(false);
		
		// remove old shops and set new content
		removeAll();
		setContent(player);
		
		// make visible and set focus
		setLocation(player.getX(),player.getY());
		setVisible(true);
		requestFocusInWindow();	
	}
	
	/**
	 * Sets the new content
	 * @param shop
	 * @param player
	 */
	private void setContent(final PlayerCharacter player){
		setLayout(new GridLayout(player.getInventory().size() + 1, 1));
		
		// Set titles
		JPanel titleRow = new JPanel();
		titleRow.setLayout(new GridLayout(1, 2));
		JLabel titleName = new JLabel("Name:");
		JLabel titleValye = new JLabel("Value:");
		titleRow.add(titleName);
		titleRow.add(titleValye);
		add(titleRow);
		
		for(final Item item : player.getInventory()){
			JPanel row = new JPanel();
			row.setLayout(new GridLayout(1, 2));
			
			final JLabel itemName = new JLabel(item.getName());
			row.add(itemName);
			
			JLabel itemValue = new JLabel(""+item.getItemValue());
			row.add(itemValue);			
			
			add(row);
		}
	}	
}
