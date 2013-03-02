package GUI;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.List;

import javax.swing.Box;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;

import Character.CivilianCharacter;
import Character.PlayerCharacter;
import Quest.Quest;

/**
 * When the player interacts with a civilian in the game, this panel
 * becomes active. It will display information about the quests that
 * this civilian has.
 * @author kristoffer
 *
 */
@SuppressWarnings("serial")
public class QuestPanel extends JPanel{
	
	// fields:
	private JPanel activeQuestPanel;
	private JPanel completedQuestPanel;
	
	// constants:
	private static final String PANEL_BACKGROUND = "images/gui/quest.png";	
	
	/**
	 * Constructor
	 */
	public QuestPanel(){
		setPanelDetails();
		createQuestPanels();
		
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
		setBounds(100, 300, 600, 300);
		setVisible(false);
		setLayout(new FlowLayout(FlowLayout.LEFT, 30, 80));
		setFocusable(true);
	}
	
	/**
	 * Create the shopPanel, stores all items to sell
	 */
	private void createQuestPanels(){
		// create the panel for the active quest
		activeQuestPanel = new JPanel();
		activeQuestPanel.setPreferredSize(new Dimension(235, 200));
		activeQuestPanel.setLayout(new FlowLayout(FlowLayout.LEFT, 10, 10));
		activeQuestPanel.setOpaque(false);
		
		// create the panel for the completed quests, show the two latest & completed quests
		completedQuestPanel = new JPanel();
		completedQuestPanel.setPreferredSize(new Dimension(235, 200));
		completedQuestPanel.setLayout(new FlowLayout(FlowLayout.LEFT, 10, 10));
		completedQuestPanel.setOpaque(false);
		
		add(activeQuestPanel);
		add(Box.createHorizontalStrut(1));
		add(completedQuestPanel);
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
	 * Updates the quest panel to display information about quests
	 * @param civilian The civilian character you are interacting with
	 * @param player The player character
	 */
	public void update(CivilianCharacter civilian, PlayerCharacter player) {

		// cancel players movement
		player.resetDirection();
		
		// remove old shops and set new content
		activeQuestPanel.removeAll();
		completedQuestPanel.removeAll();
		updateActiveQuest(civilian, player);
		updateCompletedQuest(civilian, player);
		
		// make visible and set focus
		setVisible(true);
		requestFocusInWindow();	
	}
	
	/**
	 * Updates the activeQuestPanel
	 * @param civilian The civilian character you are interacting with
	 * @param player The player character
	 */
	private void updateActiveQuest(CivilianCharacter civilian, PlayerCharacter player){
	
		// If the character does not have an active quest with this Civilian get one, else, add it to the panel
		if(civilian.getActiveQuest() == null){
			
			// Get next quest if there is one
			Quest quest = civilian.getNextQuest();
			if(quest != null){
				int response = JOptionPane.showConfirmDialog(this, quest.getMessage(), "New Quest!", JOptionPane.YES_NO_OPTION);
				
				if(response == JOptionPane.YES_OPTION){
					civilian.startQuest(quest);
					player.addQuest(quest);
					activeQuestPanel.add(new QuestInfo(civilian.getActiveQuest()));
				}
			} else {
				JOptionPane.showMessageDialog(this, "Sorry, but i don't have any more quests for you!");
			}
			
		} else {
			
			// Get next quest if there is one
			Quest quest = civilian.getActiveQuest();
			
			// Check if active quest is completed
			if(quest.isComplete() && !quest.isRecieved()){
    			player.setMoney(player.getMoney() + quest.getReward());
    			player.updateStatistics("Quest");
    			quest.setRecieved(true);
    			JOptionPane.showMessageDialog(this, "You have completed my quest, thanks! Here is your reward: " + quest.getReward());
			} else {
				activeQuestPanel.add(new QuestInfo(civilian.getActiveQuest()));
			}
		}
	}
	
	/**
	 * Updates the completedQuestPanel
	 * @param civilian The civilian character you are interacting with
	 * @param player The player character
	 */
	private void updateCompletedQuest(CivilianCharacter civilian, PlayerCharacter player){	
		
		List<Quest> quests = civilian.getQuests();
		int j = 0;
		for(int i = quests.size(); i>0 ; i--){
			
			Quest q = quests.get(i-1);
			if(q.isComplete()){
				completedQuestPanel.add(new QuestInfo(q));
				
				// Only display two latest
				if(j++ > 1){
					break;
				}
			}
		}
		
	}
	
	
	/**
	 * Private class QuestInfo displays information about one specific quest
	 * @author kristoffer
	 */
	private class QuestInfo extends JPanel{
		
		public QuestInfo(Quest quest){
			setPreferredSize(new Dimension(220, 90));
			setLayout(new FlowLayout(FlowLayout.LEFT, 0, 0));
			setOpaque(false);
			
			JTextArea msg = new JTextArea(quest.getMessage());
			msg.setPreferredSize(new Dimension(220, 90));
			msg.setEditable(false);
			msg.setForeground(Color.WHITE);
			msg.setWrapStyleWord(true);
			msg.setLineWrap(true);
			msg.setOpaque(false);
			add(msg);
		}
	}
}
