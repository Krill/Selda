package GUI;

import java.awt.Color;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.List;
import java.util.Observable;
import java.util.Observer;
import javax.swing.BorderFactory;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.border.LineBorder;


import Character.CivilianCharacter;
import Character.ShopCharacter;
import Character.PlayerCharacter;
import Item.Item;

/**
 * Handles all messages that should be displayed on the GUI
 * @author kristoffer
 */
@SuppressWarnings("serial")
public class MessagePanel extends JPanel implements Observer{

	// fields:
	private JTextArea msgArea;
	
	/**
	 * Constructor
	 */
	public MessagePanel(){
		makePanel();
	}
	
	/**
	 * Create the messagepanel
	 */
	private void makePanel(){
	
		msgArea = new JTextArea();		
		msgArea.setEditable(false);
		msgArea.setLineWrap(true);
		msgArea.setBorder(BorderFactory.createCompoundBorder(new LineBorder(Color.BLACK), BorderFactory.createEmptyBorder(10, 10, 10, 10)));
		msgArea.addKeyListener(new KeyAdapter(){
			@Override
			public void keyPressed(KeyEvent e) {
				setVisible(false);
			}
		});
		add(msgArea);
		setOpaque(false);
		setVisible(false);
	}
	
	/**
	 * Updates the Textarea with something from arg
	 * OBS! This panel only gets focus if u press a key twice when it shows....wierd
	 */
	@Override
	public void update(Observable o, Object arg) {
		if(o instanceof ShopCharacter && arg instanceof String){
			shopCharacterUpdate(o,arg);
		}else if( o instanceof CivilianCharacter && arg instanceof PlayerCharacter){
			civilianCharacterUpdate(o,arg);
		}
	}
	
	
	
	public void shopCharacterUpdate(Observable o, Object arg){
		ShopCharacter shop = (ShopCharacter)o;
		setLocation(shop.getX(),shop.getY());  // Sets the location of the JPanel close the the Shop
		
		String text = "";
		List<Item> shopItems = shop.getInventory();
		for(Item item : shopItems)
		{
			text += item.getName() + "\n";
		}
		
		msgArea.setText(text);
		msgArea.requestFocusInWindow();
		
		
		setVisible(true);
	}
	
	public void civilianCharacterUpdate(Observable o, Object arg){
		CivilianCharacter civilian = (CivilianCharacter)o;
		setLocation(civilian.getX(),civilian.getY());	// Sets the location of the JPanel close the the Civilian
		
		boolean started = civilian.getNextQuest().getStarted();
		if(started)
		{	
			JOptionPane.showMessageDialog(this, "You've already started my quest!");
		}
		else
		{
			int response = JOptionPane.showConfirmDialog(this, civilian.getNextQuest().getMessage(), "Quest", JOptionPane.YES_NO_OPTION);
			
			if(response == JOptionPane.YES_OPTION)
			{
				PlayerCharacter player = (PlayerCharacter)arg;
				player.addQuest(civilian.getNextQuest());
				civilian.getNextQuest().setStarted(true);
			}
		}
	}
}
