package GUI;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.Image;
import java.util.Observable;
import java.util.Observer;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

import Character.PlayerCharacter;

/**
 * One of the panels that is displayed constantly by the GameView class. It provides
 * the player with information about the character, such as current life, and money.
 * @author kristoffer
 */
@SuppressWarnings("serial")
public class InformationPanel extends JPanel implements Observer{

	// consants:
	private static final String PANEL_BACKGROUND = "images/gui/information.png";
	private static final int LIFEBAR_MAX = 129;
	
	/**
	 * Constructor
	 */
	public InformationPanel(){
		setPanelDetails();
	}
	
	/**
	 * Sets this panels default visuals
	 */
	private void setPanelDetails(){
		setDoubleBuffered(true);
		setBounds(590, 10, 200, 80);
		setOpaque(false);
		setVisible(true);
		setLayout(new FlowLayout(FlowLayout.LEFT, 0 , 0));
	}
	
	/**
	 * Paints a background image
	 * @param g Swing will call this method, dont use it.
	 */
	public void paintComponent(Graphics g) {
		Image img = new ImageIcon(PANEL_BACKGROUND).getImage();	
		g.drawImage(img, 0, 0, null);
	}

	/**
	 * Handles the updating process
	 * @param player The player character
	 */
	private void updateInformation(PlayerCharacter player){
		removeAll();
		
		// create the lifeContainer
		JPanel life = new JPanel();
		life.setBounds(0, 0, 129, 12);
		life.setLayout(new FlowLayout(FlowLayout.LEFT, 65 , 5));
		life.setOpaque(false);
		add(life);
		
		// calculate lifebar
		int lifeBar = Math.round( LIFEBAR_MAX * ((float)(player.getHealth())/ 100) );		// float lifeBar = LIFEBAR_MAX * (float)(player.getHealth()/ player.getMaxHealth());
	
		// create the red lifebar
		JPanel lifeContainer = new JPanel();
		lifeContainer.setPreferredSize(new Dimension(lifeBar, 12));
		lifeContainer.setOpaque(true);
		lifeContainer.setBackground(Color.RED);
		life.add(lifeContainer);
		
		JPanel money = new JPanel();
		money.setBounds(0, 0, 129, 12);
		money.setLayout(new FlowLayout(FlowLayout.LEFT, 65 , 5));
		money.setOpaque(false);
		add(money);
		
		JLabel moneyLabel = new JLabel(player.getMoney()+"");
		moneyLabel.setForeground(Color.WHITE);
		money.add(moneyLabel);
		
		
		revalidate();
	}
	
	/**
	 * Reset the information when game loads etc...
	 * @param player The player character
	 */
	public void reset(PlayerCharacter player){
		updateInformation(player);
	}
	
	/**
	 * When something has changed in with the players life/money update is called
	 */
	@Override
	public void update(Observable o, Object arg) {
		if(o instanceof PlayerCharacter && arg instanceof String){	
			if(arg.equals("information")){
				updateInformation( (PlayerCharacter) o);
			}
		}	
	}
}
