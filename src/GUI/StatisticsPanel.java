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
import javax.swing.JLabel;
import javax.swing.JPanel;

import Statistics.Statistics;

public class StatisticsPanel extends JPanel {

	private static final String PANEL_BACKGROUND = "images/gui/statistics.png";	
	private Statistics statistics;
	private JLabel monstersKilled;
	private JLabel questsCompleted;
	
	public StatisticsPanel(Statistics statistics)
	{
		this.statistics = statistics;
		
		setPanelDetails();
		createStatisticsPanel();
		
		// Add a KeyListener to this window when active
		addKeyListener(new KeyAdapter(){
			@Override
			public void keyPressed(KeyEvent e) {
				setVisible(false);
			}
		});
	}
	
	public void createStatisticsPanel()
	{
		monstersKilled = new JLabel("Monsters killed: " + statistics.getMonstersKilled());
		monstersKilled.setForeground(Color.white);
		add(monstersKilled);
		
		questsCompleted = new JLabel("Quests completed: " + statistics.getQuestsCompleted());
		questsCompleted.setForeground(Color.white);	
		add(questsCompleted);
	}
	
	/**
	 * Sets this panels default visuals
	 */
	private void setPanelDetails(){
		setOpaque(true);
		setDoubleBuffered(true);
		setBounds(100, 300, 600, 300);
		setVisible(false);
		setLayout(new GridLayout(2,0));
		setFocusable(true);
		
		
	}
	
	
	/**
	 * Paints a background image
	 */
	public void paintComponent(Graphics g) {
		Image img = new ImageIcon(PANEL_BACKGROUND).getImage();	
		g.drawImage(img, 0, 0, null);
	}
	
	public void Show()
	{
		monstersKilled.setText("Monsters killed: " + statistics.getMonstersKilled());
		
		
		questsCompleted.setText("Quests completed: " + statistics.getQuestsCompleted());
		
		
		
		setVisible(true);
		requestFocusInWindow();
	}
	
	
	
}
