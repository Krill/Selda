package GUI;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;

import Handler.ItemImageHandler;

public class HelpPanel extends JPanel{

	
	
	
	// constants:
	private static final String PANEL_BACKGROUND = "images/gui/empty_panel.png";	
	private static final String LABEL_ARROW = "images/gui/arrows.png";
	private static final String LABEL_SHOP = "images/character/ShopNPC/right.gif";
	private static final String LABEL_MONSTER ="images/character/BlueGuard/down_move.gif";
	private static final String LABEL_INVENTORY = "images/items/empty.png";
	
	/**
	 * Constructor
	 */
	public HelpPanel()
	{
		
		
		setPanelDetails();
		createHelpPanel();
		
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
		setBounds(100, 200, 600, 400);
		
		//setLayout(new GridLayout(3,1));
		setVisible(false);
		setFocusable(true);
	}
	
	

	
	/**
	 * Paints a background image
	 */
	public void paintComponent(Graphics g) {
		/*Image img = new ImageIcon(PANEL_BACKGROUND).getImage();	
		g.drawImage(img, 0, 0, null);*/
	}
	
	
	
	public void createHelpPanel()
	{
		
		
		JPanel panel = new JPanel(new GridLayout(4,0));
		
		
		panel.add(new HelpInfo("Use the arrow keys to move around in the world",new ImageIcon(LABEL_ARROW), "Movement"));
		panel.add(new HelpInfo("<html>This is a NPC. Interact with them by pressing E while standing near.<br/>" +
				"Some NPCs will trade items with you and some other may give you quests.<br/>" +
				" Make sure you talk to all of them so you don't miss anything important.</html>",new ImageIcon(LABEL_SHOP), "Interacting with NPCS"));
		panel.add(new HelpInfo("<html>The inventory contains all your items. Don't forget to equip an armor and a weapon as soon as you get one,<br/> " +
								"else you won't gain their benefits. You equip/unequip them by clicking them. </html>", new ImageIcon(LABEL_INVENTORY), "Inventory"));
		panel.add(new HelpInfo("<html>This is a monster. If you leave the city you might run into such creatures. <br/>" +
				"If they start chasing you, you better be ready to fight or run.<br/>" +
				"You attack them by using spacebar. The better weapon you wield, the greater the damage!</html>", new ImageIcon(LABEL_MONSTER), "Fighting"));
	
		
		JScrollPane scrollPane = new JScrollPane(panel);
		scrollPane.setPreferredSize(new Dimension(550,350));
		add(scrollPane);
		
		
	}
	
	
	
	public void showHelp()
	{
		// make visible and set focus
		setVisible(true);
		requestFocusInWindow();	
	}
	
	
	
	private class HelpInfo extends JPanel
	{
		private JLabel text;
		private JLabel img;
		
		public HelpInfo(String text, ImageIcon img, String title)
		{
			this.text = new JLabel(text);
			this.text.setForeground(Color.WHITE);
			
			this.img = new JLabel(img);
			
			setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
			setPreferredSize(new Dimension(250,100));
			TitledBorder border = new TitledBorder(title);
			border.setTitleColor(Color.gray);
			setBorder(border);
			
			add(Box.createRigidArea(new Dimension(40, 1)));
			add(this.text);
			add(Box.createHorizontalGlue());
			add(this.img);
			add(Box.createRigidArea(new Dimension(40, 1)));
		}
		
		/**
		 * Paints a background image
		 */
		public void paintComponent(Graphics g) {
			Image img = new ImageIcon(PANEL_BACKGROUND).getImage();	
			g.drawImage(img, 0, 0, null);
		}
	}
}
