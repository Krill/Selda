package Character;

import java.awt.geom.Ellipse2D;
import java.util.ArrayList;
import Quest.Quest;
import java.util.List;

public class CivilianCharacter extends Character implements Interactable, Moveable, Cloneable
{
    private List<Quest> quests;
   
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
    
    
    
    public List<Quest> getQuests()
    {
        return quests;
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
    			
    			quest.setStarted(true);
    			return quest;
    		}
    		
    		
    	}
    	return null;
    }
    
    @Override
    public void interact(PlayerCharacter player)
    {
    	System.out.println("Civilian interacted with");
    	
    	for(Quest quest : quests)
    	{
    		if(quest.isComplete() && !quest.isRecieved())
    		{
    			player.setMoney(player.getMoney() + quest.getReward());
    			quest.setRecieved(true);
    			System.out.println("Quest completed. Awarded " + quest.getReward() + " money!");
    		}
    	}
    	
    	
    	Quest quest = getNextQuest();
    	if(quest != null)
    	{
    		player.addQuest(quest);
    		
			System.out.println("added quest:" + quest.getMessage());
			setChanged();
			notifyObservers(player);
    	}
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
