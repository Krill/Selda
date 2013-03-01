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
import java.util.ArrayList;
import java.util.Iterator;


import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.TitledBorder;


import Character.PlayerCharacter;
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
	private PlayerCharacter player;
	private JLabel monstersKilled;
	private JLabel questsCompleted;
	private JLabel totalScore;
	private JLabel highscore1;
	private JLabel highscore2;
	private JLabel highscore3;


	private JPanel panelWest;
	private JPanel panelEast;


	public StatisticsPanel(PlayerCharacter player)
	{
		this.player = player;

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

		panelWest.add(Box.createRigidArea(new Dimension(20,35)));

		monstersKilled = new JLabel("Monsters killed: " + player.getStatistics().getMonstersKilled());
		monstersKilled.setForeground(Color.white);
		panelWest.add(monstersKilled);

		questsCompleted = new JLabel("Quests completed: " + player.getStatistics().getQuestsCompleted());
		questsCompleted.setForeground(Color.white);	
		panelWest.add(questsCompleted);

		panelWest.add(Box.createRigidArea(new Dimension(0, 20)));

		totalScore = new JLabel("Your total score is: " + (player.getStatistics().getMonstersKilled() + (player.getStatistics().getQuestsCompleted() * 5)));
		totalScore.setForeground(Color.white);
		panelWest.add(totalScore);


		panelWest.add(Box.createGlue());

		JButton submitButton = new JButton("Submit highscore");
		submitButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e)
			{
				submitScore();
			}
		});
		panelWest.add(submitButton);
		panelWest.add(Box.createRigidArea(new Dimension(0, 40)));

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


		panelEast.add(Box.createRigidArea(new Dimension(0,15)));

		highscore1 = new JLabel();
		highscore1.setForeground(Color.white);
		TitledBorder highscoreBorder1 = new TitledBorder("1");
		highscoreBorder1.setTitleColor(Color.gray);
		highscore1.setBorder(highscoreBorder1);

		panelEast.add(highscore1);

		highscore2 = new JLabel();
		highscore2.setForeground(Color.white);	
		TitledBorder highscoreBorder2 = new TitledBorder("2");
		highscoreBorder2.setTitleColor(Color.gray);
		highscore2.setBorder(highscoreBorder2);
		panelEast.add(highscore2);

		highscore3 = new JLabel();
		highscore3.setForeground(Color.white);
		TitledBorder highscoreBorder3 = new TitledBorder("3");
		highscoreBorder3.setTitleColor(Color.gray);
		highscore3.setBorder(highscoreBorder3);
		panelEast.add(highscore3);

		panelEast.add(Box.createGlue());

		JButton refreshButton = new JButton("Refresh");
		refreshButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e)
			{
				refreshScore();
			}
		});

		panelEast.add(refreshButton);

		panelEast.add(Box.createRigidArea(new Dimension(0, 40)));

		add(panelEast);
		refreshScore();
	}

	private void refreshScore()
	{	
		String[] topScores = player.getStatistics().refreshScore();
		highscore1.setText("" + topScores[0]);
		highscore2.setText("" + topScores[1]);
		highscore3.setText("" + topScores[2]);
		
		
		requestFocusInWindow();
	}

	private void submitScore()
	{
		player.getStatistics().submitScore();
		refreshScore();
		requestFocusInWindow();
	}



	private void updateScore(URLConnection connection)
	{
		
			
	}

	


	


	/**
	 * Sets this panels default visuals
	 */
	private void setPanelDetails(){
		setOpaque(true);
		setDoubleBuffered(true);
		setBounds(150, 200, 500, 300);
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
		monstersKilled.setText("Monsters killed: " + player.getStatistics().getMonstersKilled());
		questsCompleted.setText("Quests completed: " + player.getStatistics().getQuestsCompleted());
		totalScore.setText("Your total score is: " + (player.getStatistics().getTotalScore()));

		setVisible(true);
		requestFocusInWindow();
	}

	
}
