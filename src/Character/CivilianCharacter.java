package Character;


import java.util.ArrayList;
import Quest.Quest;
import java.util.List;


/**
 * A CivilianCharacter is a NPC with carries a certain amount of quests.
 * It is responsible for giving out quests to players and also to give the rewards when completed.
 * @author Johan
 *
 */
public class CivilianCharacter extends Character implements Moveable, Cloneable
{
    
	private static final long serialVersionUID = 115281;
	private List<Quest> quests;
   
	/**
	 * Creates a new Civilian Character.
	 * @param id
	 * @param x
	 * @param y
	 * @param width
	 * @param height
	 * @param name
	 * @param health
	 * @param isAttackable
	 * @param newQuests
	 * @param interactRadius
	 */
    public CivilianCharacter(int id, int x, int y, int width, int height, String name, int health, boolean isAttackable, Quest[] newQuests, int interactRadius)
    {
        super(id, x, y, width, height, name, health, isAttackable, interactRadius);
        quests = new ArrayList<Quest>();
        
        for(Quest quest : newQuests)
        {
            quests.add(quest);
        }
    }
    
    @Override
    public CivilianCharacter clone()
    {
    	try{
    		CivilianCharacter copy = (CivilianCharacter)super.clone();
    		return copy;
    	}
    	catch(Exception e)
    	{
    		System.out.println("Error Cloning");
    	}
    	
		return null;
    }
    
    
    /**
     * Returns a list of all the quests held.
     * @return Quests
     */
    public List<Quest> getQuests()
    {
        return quests;
    } 
    
    /**
     * Returns this civilians active quest
     * @return quest
     */
    public Quest getActiveQuest(){
    	for(Quest quest : quests){
    		if(quest.isStarted() && (!quest.isRecieved())){
    			return quest;
    		}
    	}
    	
    	// No active quest
    	return null;
    }
    
    
    /**
     * Returns the next incomplete quest in the chain
     * @return Returns the next quest, or null if no more quests available.
     */
    public Quest getNextQuest()
    {
    	for(Quest quest : quests)
    	{
    		if(!quest.isComplete())
    		{
    			
    			if(quest.getStarted())
    			{
    				return null;
    			}
    			
//    			startQuest(quest);
    			return quest;
    		}	
    	}
    	return null;
    }
    
    /**
     * Starts a quest
     * @param quest
     */
    public void startQuest(Quest quest){
    	quest.setStarted(true);
    }
    
    
    /**
     * Method for interacting with the Civilian character.
     * The method will check if the supplied player has any rewards to receive, if so, give out rewards.
     * It will also update the statistics for the player and give new quests.
     */
    @Override
    public void interact(PlayerCharacter player)
    {
    	System.out.println("Civilian interacted with");
    	setChanged();
        notifyObservers(player);
    	
    	
//    	for(Quest quest : quests)
//    	{
//    		
//    		if(quest.isComplete() && !quest.isRecieved())
//    		{
//    			player.setMoney(player.getMoney() + quest.getReward());
//    			player.updateStatistics("Quest");
//    			quest.setRecieved(true);
//    			System.out.println("Quest completed. Awarded " + quest.getReward() + " money!");
//    		}
//    	}
//    	
//    	Quest quest = getNextQuest();
//    	if(quest != null)
//    	{
//    		player.addQuest(quest);
//    		
//			System.out.println("added quest:" + quest.getMessage());
//			setChanged();
//			notifyObservers(player);
//    	}
    }
    
    @Override
    public void move()
    {
        //Move character..
    }
    
    public void update(){
    	// Update character
    }
}
