package Engine;

import java.awt.Rectangle;
import java.io.Serializable;
import java.util.ArrayList;

import Character.CivilianCharacter;
import Character.Character;
import Handler.CharacterHandler;
import Quest.Quest;
import World.Map;

/**
 * This class keeps track of the course of the game. When a quest or
 * something mandatory in the game is completed and event will be triggered.
 * @author kristoffer
 */
public class EventEngine implements Serializable{
	
	// fields:
	private GameEngine engine;
	private ArrayList<Quest> quests;
	
	// constants:
	private static final long serialVersionUID = 126L;
	
	/**
	 * Constructor
	 * @param engine The game engine
	 */
	public EventEngine(GameEngine engine){	
		this.engine = engine;
		this.quests = new ArrayList<Quest>();
		
		addQuests();
	}
	
	/**
	 * Iterates through every civilian in the world and add their quests to the list
	 */
	private void addQuests(){
		for(Map map : engine.getWorld().getMaps()){
			for(Character c : map.getCharacters()){
				if(c instanceof CivilianCharacter){
					
					// add each civilian charaters quest to the list
					for(Quest q : ((CivilianCharacter) c).getQuests()){
						quests.add(q);
					}		
				}
			}
		}
	}
	
	/**
	 * Updates the events
	 */
	public void update(){
		checkQuests();
	}
	
	/**
	 * Check if any quest is done, and should lead to an event
	 */
	private void checkQuests(){
		for(int i=0; i < quests.size(); i++){
			Quest q = quests.get(i);
			
			if(firstQuest(q, i))
			{
				quests.remove(i);
			}
			else if(chickenQuest(q,i))
			{
				quests.remove(i);
			}
			else if(raidQuest(q,i))
			{
				quests.remove(i);
			}
			else if(smashQuest(q,i))
			{
				quests.remove(i);
			}
			
			
		}
	}
	
	/**
	 * Handles events triggered by the first event
	 * @param q The quest bound to this event
	 * @param id The id of the quest in the EventEngines quest list
	 */
	private boolean firstQuest(Quest q, int id){
		if(q.getID() == 1 && q.isStarted()){
			
			// trigger event
			engine.getWorld().getMaps().get(0).getBackTiles().get(261).setId(206);
			engine.getWorld().getMaps().get(0).getBlockTiles().remove(7);
			
			// conditions is fulfilled 
			return true;
		}
		
		// not completed
		return false;
	}
	
	/**
	 * Handles events triggered by the first event
	 * @param q The quest bound to this event
	 * @param id The id of the quest in the EventEngines quest list
	 */
	private boolean chickenQuest(Quest q, int id){
		if(q.getID() == 2 && q.isStarted()){
			
			// trigger event
			Character c = engine.getWorld().getCurrentMap().getCharacters().get(0);
			c.setY(320);
			
			// conditions is fulfilled 
			return true;
		}
		
		// not completed
		return false;
	}
	
	/**
	 * Handles events triggered by the first event
	 * @param q The quest bound to this event
	 * @param id The id of the quest in the EventEngines quest list
	 */
	private boolean raidQuest(Quest q, int id){
		if(q.getID() == 3 && q.isStarted()){
			
			// trigger event
			ArrayList<Character> list = new ArrayList<>();
			for(Character c : engine.getCharacters())
			{
				list.add(c);
			}
			
			list.add(CharacterHandler.getCharacterHandler().getCharacter("BlueGuard", 450, 500));
			list.add(CharacterHandler.getCharacterHandler().getCharacter("BlueGuard", 300, 500));
			list.add(CharacterHandler.getCharacterHandler().getCharacter("BlueGuard", 550, 500));
			
			
			engine.getCollision().setCurrentCharacters(list);
			engine.setCharacterList(list);
			
			// conditions is fulfilled 
			return true;
		}
		
		// not completed
		return false;
	}
	
	
	/**
	 * Handles events triggered by the first event
	 * @param q The quest bound to this event
	 * @param id The id of the quest in the EventEngines quest list
	 */
	private boolean smashQuest(Quest q, int id){
		if(q.getID() == 5 && q.isStarted()){
			if(engine.getPlayer().getBounds().intersects(new Rectangle(320,244,96,96)) && engine.getPlayer().isAttacking())
			{
				System.out.println("hej");
				// conditions is fulfilled 
				q.setNumberDone(100);
				q.updateStatus();
				engine.getWorld().getCurrentMap().getBackTiles().get(211).setId(57);
				engine.getWorld().getCurrentMap().getBlockTiles().remove(60);
				return true;
			}
		}
		
		// not completed
		return false;
	}
	
	
}
