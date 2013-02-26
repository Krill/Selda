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
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;


import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.TitledBorder;

import Statistics.Statistics;


/**
 * A class for providing a panel for showing statistics.
 * It will display both the players current statistics and a common highscore, which is saved on a server.
 * It takes care of both loading and saving the highscores.
 * @author Johan
 *
 */
public class StatisticsPanel extends JPanel {

	private static final String PANEL_BACKGROUND = "images/gui/empty_panel.png";	
	private Statistics statistics;
	private JLabel monstersKilled;
	private JLabel questsCompleted;
	private JLabel totalScore;
	private JLabel highscore1;
	private JLabel highscore2;
	private JLabel highscore3;
	
	
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
		createWestPanel();
		createEastPanel();
	}
	
	
	private void createWestPanel()
	{
		panelWest = new JPanel();
		panelWest.setLayout(new BoxLayout(panelWest, BoxLayout.Y_AXIS));
		
		TitledBorder westBorder = new TitledBorder("Your current stats");
		westBorder.setTitleColor(Color.gray);
		panelWest.setBorder(westBorder);
		panelWest.setOpaque(false);
		
		panelWest.add(Box.createRigidArea(new Dimension(20,15)));
		
		monstersKilled = new JLabel("Monsters killed: " + statistics.getMonstersKilled());
		monstersKilled.setForeground(Color.white);
		panelWest.add(monstersKilled);
		
		questsCompleted = new JLabel("Quests completed: " + statistics.getQuestsCompleted());
		questsCompleted.setForeground(Color.white);	
		panelWest.add(questsCompleted);
		
		panelWest.add(Box.createRigidArea(new Dimension(0, 20)));
		
		totalScore = new JLabel("Your total score is: " + (statistics.getMonstersKilled() + (statistics.getQuestsCompleted() * 5)));
		totalScore.setForeground(Color.white);
		panelWest.add(totalScore);
		
		panelWest.add(Box.createRigidArea(new Dimension(0, 40)));
		
		JButton submitButton = new JButton("Submit highscore");
		submitButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e)
			{
				submitScore();
			}
		});
		panelWest.add(submitButton);
		
		add(panelWest);
	}
	
	private void createEastPanel()
	{
		panelEast = new JPanel();
		panelEast.setLayout(new BoxLayout(panelEast, BoxLayout.Y_AXIS));
		panelEast.setOpaque(false);
		TitledBorder eastBorder = new TitledBorder("Highscore");
		eastBorder.setTitleColor(Color.gray);
		panelEast.setBorder(eastBorder);
		
		panelEast.add(Box.createRigidArea(new Dimension(20,15)));
		
		
		
		highscore1 = new JLabel();
		highscore1.setForeground(Color.white);
		panelEast.add(highscore1);
		
		highscore2 = new JLabel();
		highscore2.setForeground(Color.white);	
		panelEast.add(highscore2);
		
		highscore3 = new JLabel();
		highscore3.setForeground(Color.white);
		panelEast.add(highscore3);
		
		
		add(panelEast);
		
		updateScore();
	}
	
	private void submitScore()
	{
		try
		{
			File file = openFile();
			BufferedWriter writer = new BufferedWriter(new FileWriter(file));
			
			String[] name1 = highscore1.getText().split(" ");
			String[] name2 = highscore2.getText().split(" ");
			String[] name3 = highscore3.getText().split(" ");
			
			
			
			writer.close();
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		
		requestFocusInWindow();
	}
	
	
	
	private void updateScore()
	{
		try
		{
			File file = openFile();
			BufferedReader reader = new BufferedReader(new FileReader(file));
			
			readFile(highscore1, reader);
			readFile(highscore2, reader);
			readFile(highscore3, reader);
			
			reader.close();
		}
		catch(Exception e)
		{
			highscore1.setText("Error loading highscores, try again later");
		}
	}
	
	private void readFile(JLabel labelToUpdate, BufferedReader reader)
	{
		String line;
		
		try{
			if((line = reader.readLine()) != null)
			{
				String[] lines = line.split(" ");
				String name = lines[0];
				int score = Integer.parseInt(lines[1]);
				labelToUpdate.setText(name + ": " + score);
			}
		}
		catch(Exception e)
		{
			labelToUpdate.setText("Error loading highscores, try again later");
		}
	}
	
	private File openFile()
	{
		try
		{
			File file = new File(System.getProperty("user.dir") + "\\saves\\test.txt");
			return file;
		}
		catch(Exception e)
		{
			System.out.println("Error loading highscore");
		}
		return null;
	}
	
	
	/**
	 * Sets this panels default visuals
	 */
	private void setPanelDetails(){
		setOpaque(true);
		setDoubleBuffered(true);
		setBounds(150, 200, 500, 200);
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
		totalScore.setText("Your total score is: " + (statistics.getMonstersKilled() + (statistics.getQuestsCompleted() * 5)));
		updateScore();
		
		setVisible(true);
		requestFocusInWindow();
	}
}
