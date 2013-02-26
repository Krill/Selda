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
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;


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
			String data = URLEncoder.encode("player_name", "UTF-8") + "=" + URLEncoder.encode("johan", "UTF-8");
	        data += "&" + URLEncoder.encode("player_score", "UTF-8") + "=" + URLEncoder.encode("512312310", "UTF-8");
	        data += "&" + URLEncoder.encode("identifier", "UTF-8") + "=" + URLEncoder.encode("gamecontroler", "UTF-8");
			data += "&" + URLEncoder.encode("submit", "UTF-8") + "=" + URLEncoder.encode("true", "UTF-8");
			
			URLConnection connection = openUrl();
			connection.setDoOutput(true);
			OutputStreamWriter out = new OutputStreamWriter(connection.getOutputStream());
			
			System.out.println(data);
			
			out.write(data);
			out.flush();
			
			
			BufferedReader rd = new BufferedReader(new
					InputStreamReader(connection.getInputStream()));
	        String line;
	        while ((line = rd.readLine()) != null) {
	            // Process line...
				System.out.println("LINE  : #" + line );
	        }
	        out.close();
	        rd.close();
			
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
			URLConnection connection = openUrl();
			
			
		}
		catch(Exception e)
		{
			highscore1.setText("Error loading highscores, try again later");
		}
	}
	
	/*private void readUrl(JLabel labelToUpdate, BufferedReader reader)
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
	}*/
	
	private URLConnection openUrl()
	{
		try
		{
			URL url = new URL("http://jpv-net.dyndns.org:1337/red_elephant/submitt_score.php");
	        return url.openConnection();
			
		}
		catch(Exception e)
		{
			System.out.println("Error loading url");
			e.printStackTrace();
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
