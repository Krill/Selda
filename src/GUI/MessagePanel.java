package GUI;

import java.awt.Color;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.Observable;
import java.util.Observer;
import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.border.LineBorder;


import Character.ShopCharacter;

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
		msgArea.setSize(170, 120);
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
			msgArea.setText( (String) arg);
			msgArea.requestFocusInWindow();
			setVisible(true);
		}
	}
}
