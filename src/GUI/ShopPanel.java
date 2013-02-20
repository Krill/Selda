package GUI;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
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
	
	// constants:
	private static final String PANEL_BACKGROUND = "images/gui/shop.png";	
	
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
		setOpaque(true);
		setDoubleBuffered(true);
		setBounds(100, 300, 600, 300);
		setVisible(false);
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		setFocusable(true);
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
		removeAll();
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
	private void updateShop(ShopCharacter shop, PlayerCharacter player){
		
		// fill out shop label
		add(Box.createVerticalStrut(50));
		
		JPanel shopPanel = new JPanel();
		shopPanel.setPreferredSize(new Dimension(600, 200));
//		shopPanel.setBorder(new LineBorder(Color.BLACK));
		shopPanel.setOpaque(false);
		add(shopPanel);
	}
}
