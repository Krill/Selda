package Character;

import java.awt.geom.Ellipse2D;
import java.util.ArrayList;
import Quest.Quest;
import java.util.List;

public class CivilianCharacter extends Character implements Interactable, Moveable
{
    private List<Quest> quests;
    private int interactRadius;
   
    public CivilianCharacter(int id, int x, int y, int width, int height, String name, boolean isAttackable, Quest[] newQuests, int interactRadius)
    {
        super(id, x, y, width, height, name, isAttackable);
        quests = new ArrayList<Quest>();
        this.interactRadius = interactRadius;
        
        for(Quest quest : newQuests)
        {
            quests.add(quest);
        }
    }
    
    public Ellipse2D.Double getInteractArea(){
    	return new Ellipse2D.Double(getX() - (interactRadius/2) + (getWidth()/2), 
    			 getY() - (interactRadius/2) + (getHeight()/2), interactRadius, interactRadius);
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
    			return quest;
    		}
    	}
    	return null;
    }
    
    @Override
    public void interact(PlayerCharacter player)
    {
    	System.out.println("Civilian interacted with");
    	setChanged();
        notifyObservers(player);
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
