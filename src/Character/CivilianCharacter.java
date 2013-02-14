package Character;

import java.util.ArrayList;
import Quest.Quest;
import java.util.List;

public class CivilianCharacter extends Character implements Interactable, Moveable
{
    private List<Quest> quests;
   
    public CivilianCharacter(int x, int y, int width, int height, String name, boolean isAttackable, Quest[] newQuests)
    {
        super(x, y, width, height, name, isAttackable);
        quests = new ArrayList<Quest>();
        for(Quest quest : newQuests)
        {
            quests.add(quest);
        }
    }
    
    public List<Quest> getQuests()
    {
        return quests;
    }  
    
    
    /**
     * Returns the next incompleted quest in the chain
     * @return Returns the next quest, or null if no more quests available.
     */
    public Quest getNextQuest()
    {
    	for(Quest quest : quests)
    	{
    		if(!quest.isComplete())
    		{
    			return quest;
    		}
    	}
    	return null;
    }
    
    @Override
    public void interact(PlayerCharacter player)
    {
    	setChanged();
        notifyObservers(player);
    }
    
    @Override
    public void move()
    {
        //Move character..
    }

}
