package GUI;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;

import Statistics.Statistics;

public class StatisticsPanel extends JPanel {

	private static final String PANEL_BACKGROUND = "images/gui/empty_panel.png";	
	private Statistics statistics;
	private JLabel monstersKilled;
	private JLabel questsCompleted;
	private JPanel panelWest;
	private JPanel panelEast;
	
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
		panelWest = new JPanel(new GridLayout(2, 0));
		
		TitledBorder westBorder = new TitledBorder("Your current stats");
		westBorder.setTitleColor(Color.gray);
		panelWest.setBorder(westBorder);
		panelWest.setOpaque(false);
		
		
		monstersKilled = new JLabel("Monsters killed: " + statistics.getMonstersKilled());
		monstersKilled.setForeground(Color.white);
		panelWest.add(monstersKilled);
		
		questsCompleted = new JLabel("Quests completed: " + statistics.getQuestsCompleted());
		questsCompleted.setForeground(Color.white);	
		panelWest.add(questsCompleted);
		
		add(panelWest);
		
		
		
		panelEast = new JPanel();
		panelEast.setOpaque(false);
		TitledBorder eastBorder = new TitledBorder("Highscore");
		eastBorder.setTitleColor(Color.gray);
		panelEast.setBorder(eastBorder);
		add(panelEast);
	}
	
	/**
	 * Sets this panels default visuals
	 */
	private void setPanelDetails(){
		setOpaque(true);
		setDoubleBuffered(true);
		setBounds(100, 300, 600, 300);
		setVisible(false);
		setLayout(new GridLayout(1,1));
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
