package GUI;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.border.EtchedBorder;
import javax.swing.border.LineBorder;

import Character.PlayerCharacter;
import Item.Item;

/**
 * Show the players inventory
 * @author kristoffer
 */
@SuppressWarnings("serial")
public class InventoryPanel extends JPanel implements Observer{
	
	// fields:
	private JPanel topPanel;
	private JPanel gridPanel;
	private final JLabel label;
	
	/**
	 * Constructor
	 */
	public InventoryPanel(){
		label = new JLabel("ASDASD");
		add(label);
		
		setPanelDetails();
		createTopPanel();
	}
	
	/**
	 * Sets this panels default visuals
	 */
	private void setPanelDetails(){
		setBorder(BorderFactory.createCompoundBorder(new LineBorder(Color.BLACK), BorderFactory.createEmptyBorder(0,0,0,0)));
		setDoubleBuffered(true);
		setPreferredSize(new Dimension(200, 640));
	}
	
	private void createTopPanel(){
		topPanel = new JPanel();
		topPanel.setPreferredSize(new Dimension(180, 400));
		
		JLabel title = new JLabel("Inventory");
		title.setBorder(new EmptyBorder(10, 10, 10, 10));
		topPanel.add(title, BorderLayout.NORTH);
		
		gridPanel = new JPanel();
		gridPanel.setLayout(new GridLayout(2,2,5,5));
		gridPanel.setPreferredSize(new Dimension(180, 180));
		
		topPanel.add(gridPanel, BorderLayout.SOUTH);

		add(topPanel, BorderLayout.NORTH);
	}

	private void updateInventory(){
		label.setText("asdasd");
		label.revalidate();
	}
	
	/**
	 * When somthing has changed in the inventory, update is called
	 */
	@Override
	public void update(Observable o, Object arg) {
		if(o instanceof PlayerCharacter && arg instanceof ArrayList<?>){
			System.out.println("update()");
			updateInventory();
		}	
	}
}
