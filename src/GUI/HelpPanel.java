package GUI;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.TitledBorder;


/**
 * Provides a panel with basic tutorials and help for players.
 * The class itself contains all the information it displays.
 * @author Johan
 *
 */
public class HelpPanel extends JPanel{
	
	private static final long serialVersionUID = 817287;
	// constants:
	private static final String PANEL_BACKGROUND = "images/gui/empty_panel.png";	
	private static final String LABEL_ARROW = "images/gui/arrows.png";
	private static final String LABEL_SHOP = "images/character/ShopNPC/right.gif";
	private static final String LABEL_MONSTER ="images/character/BlueGuard/down_move.gif";
	private static final String LABEL_INVENTORY = "images/items/empty.png";
	private static final String LABEL_SAVE = "images/gui/save.png";
	
	/**
	 * Constructor Initiates the HelpPanel
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
	 * @param g Swing will call this method, dont use it.
	 */
	public void paintComponent(Graphics g) {
		Image img = new ImageIcon(PANEL_BACKGROUND).getImage();	
		g.drawImage(img, 0, 0, null);
	}
	
	
	
	private void createHelpPanel()
	{
		JPanel panel = new JPanel(new FlowLayout(0,0,0));
		panel.setPreferredSize(new Dimension(580, 1100));
		
		
		panel.add(new HelpInfo("<html><br/><html/>Use the arrow keys to move around in the world",new ImageIcon(LABEL_ARROW), "Movement", 100));
		
		panel.add(new HelpInfo("<html>This game uses a lot of panels to communicate with you. For example, while interacting with a character," + 
				"a panel will be displayed.<br> When you are finished with the panel, just press any key and to remove it.</html>", null, "Closing GUI panels", 150));
		
		panel.add(new HelpInfo("<html><br/>This is an NPC (Non-player-character). You may interact with them by pressing the E button, " +
				"while standing near them. There are different kinds of NPCs. Some of them will trade items with you while others may give you quests. " +
				"Make sure you talk to all of them so you don’t miss anything.</html>",new ImageIcon(LABEL_SHOP), "Interacting with NPCS", 150));
		
		panel.add(new HelpInfo("<html><br/>The inventory contains all your current items. The Inventory panel is located at the left of the screen. " +
				"The bottommost two slots indicate your equipped weapon and armor. As soon as you get your first weapon and armor, " +
				"make sure you equip them. This is done by right clicking the item you wish to equip, and by clicking it again if you want to unequip it. " +
				"If done correctly, the item should appear on one of the slots. </html>", new ImageIcon(LABEL_INVENTORY), "Inventory", 150));
		
		panel.add(new HelpInfo("<html><br/>This is a monster. If you leave the starting point, you might run into such creatures. " +
				"They will hunt you at sight, rushing towards you at all costs, and when they do, you better be ready to fight back. " +
				"You fight them by clicking spacebar. The greater the weapon you wield, the better the damage you will make, and the greater the armor you equipped, " +
				"the less they will hurt you. At the top right corner you will find your health bar. As soon as your health reaches zero, you will die and have " +
				"to restart either from a previous save or from the beginning.</html>", new ImageIcon(LABEL_MONSTER), "Fighting", 200));
		
		panel.add(new HelpInfo("<html><br/>The game autosaves every time you change map. However, you may force a save by using the save button located at File -> Save. " +
				"The autosave will always be saved as autosave.uno, while forced saves may be saved at any name. " +
				"To load a saved game, just click the Open button located at File -> Open. </html>", new ImageIcon(LABEL_SAVE), "Save/Load", 150));
	
		panel.add(new HelpInfo("<html><br/>There is a point system in the game, based on the number of enemies you’ve killed and the number of quests you’ve completed, combined into a common score. " +
				"Every kill counts as one and a completed quest counts as 5 points. You may at any time submit your score to a general highscore board. " +
				"This is done by clicking the statistics button located at Statistics -> Statistics. There you will find a submit highscore button and a refresh button. " +
				"To the right of the panel you will also see the top 3 players along with their submitted score and date.</html>", null, "Statistics/Achievements", 200));
		

		
		
		JScrollPane scrollPane = new JScrollPane(panel);
		scrollPane.setPreferredSize(new Dimension(600,350));
		add(scrollPane);
	}
	
	
	/**
	 * Sets the panel visible.
	 */
	public void showHelp()
	{
		// make visible and set focus
		setVisible(true);
		requestFocusInWindow();	
	}
	
	
	/**
	 * Class that creates a small panel with a text and a picture, surrounded by a TitledBorder.
	 * @author Johan
	 *
	 */
	private class HelpInfo extends JPanel
	{
		private static final long serialVersionUID = 234241;
		private JLabel text;
		private JLabel img;
		
		public HelpInfo(String text, ImageIcon img, String title, int height)
		{
			this.text = new JLabel(text);
			this.text.setForeground(Color.WHITE);
			
			this.img = new JLabel(img);
			
			setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
			setPreferredSize(new Dimension(580,height));
			TitledBorder border = new TitledBorder(title);
			border.setTitleColor(Color.WHITE);
			setBorder(border);
			
			add(Box.createRigidArea(new Dimension(40, 0)));
			add(this.text);
			add(Box.createHorizontalGlue());
			add(this.img);
			add(Box.createRigidArea(new Dimension(40, 0)));
		}
		
		/**
		 * Paints a background image
		 * @param g Swing will call this method, dont use it.
		 */
		public void paintComponent(Graphics g) {
			Image img = new ImageIcon(PANEL_BACKGROUND).getImage();	
			g.drawImage(img, 0, 0, null);
		}
	}
}
