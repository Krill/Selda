package Engine;

import java.awt.Rectangle;
import java.io.Serializable;
import java.util.ArrayList;

import javax.swing.JOptionPane;

import Character.CivilianCharacter;
import Character.Character;
import Handler.CharacterHandler;
import Handler.ItemHandler;
import Item.Item;
import Quest.KillingQuest;
import Quest.Quest;
import World.BlockTile;
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
		addCustomQuests();
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
	 * Creates custom quest that has no connection to a NPC
	 */
	private void addCustomQuests(){
		quests.add(new KillingQuest(100, null, 0, 0, "Push button quest"));
		quests.add(new KillingQuest(102, null, 0, 0, "Kill RedGuard to get Pickaxe"));
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
			else if(pushButtonQuest(q,i))
			{
				quests.remove(i);
			}
			else if(killEnemyButtonQuest(q,i))
			{
				quests.remove(i);
			}
			else if(killRedGuardQuest(q,i))
			{
				quests.remove(i);
			}
			else if(smashQuest(q,i))
			{
				quests.remove(i);
			}
			else if(completedQuest(q, i))
			{
				quests.remove(i);
			}
			else if(pickaxeQuest(q, i))
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
	
	
	private boolean completedQuest(Quest q, int id){
		if(q.getID() == 7 && q.isStarted()){
			
			// trigger event
			JOptionPane.showMessageDialog(null, "You've completed the game! Your highscore will be uploaded immediately!\n" +
					"Thanks for playing!\n\nAuthors: \nJohan Nilsson Hansen\nKristoffer Petersson\nRichard Norling" +
						"\nAlexander Persson\nKevin Vetter\nJimmy Svensson\n\n" , "Congratulations!", JOptionPane.INFORMATION_MESSAGE);
			engine.getPlayer().getStatistics().submitScore();
			
			System.exit(0);
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
			
			engine.getWorld().getCurrentMap().setCharacters(list);
			
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
	
	/**
	 * Handles events triggered by the first event
	 * @param q The quest bound to this event
	 * @param id The id of the quest in the EventEngines quest list
	 */
	private boolean pickaxeQuest(Quest q, int id){
		if(q.getID() == 6 && q.isStarted()){
			Character c = engine.getWorld().getCurrentMap().getCharacters().get(engine.getWorld().getCurrentMap().getCharacters().size()-1);
			c.setX(32);
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
	private boolean pushButtonQuest(Quest q, int id){
		if(q.getID() == 100 && engine.getWorld().getCurrentMap().getName().equals("dung_2")){
			if(engine.getPlayer().getBounds().intersects(new Rectangle(448,352,32,32)))
			{
				System.out.println("Button pushed!");
				
				// Add enemies
				ArrayList<Character> list = new ArrayList<>();
				for(Character c : engine.getCharacters())
				{
					list.add(c);
				}
				
				list.add(CharacterHandler.getCharacterHandler().getCharacter("BlueGuard", 388, 352));
				list.add(CharacterHandler.getCharacterHandler().getCharacter("BlueGuard", 448, 288));
				list.add(CharacterHandler.getCharacterHandler().getCharacter("BlueGuard", 448, 416));
				engine.getCollision().setCurrentCharacters(list);
				engine.setCharacterList(list);
				
				// close door
				engine.getWorld().getCurrentMap().getBackTiles().get(285).setId(200);
				engine.getWorld().getCurrentMap().getBlockTiles().add(new BlockTile(1, 320, 352, 32, 32, false));
				
				// create new custom quest
				quests.add(new KillingQuest(101, null, 0, 0, "Kill monster after button quest"));

				return true;
			}
		}
		
		// not completed
		return false;
	}
	
	/**
	 * Handles events triggered by the first event
	 * @param q The quest bound to this event
	 * @param id The id of the quest in the EventEngines quest list
	 */
	private boolean killEnemyButtonQuest(Quest q, int id){
		
		if(q.getID() == 101 && engine.getWorld().getCurrentMap().getName().equals("dung_2")){ 
			
			if(engine.getCharacters().size() == 0){
				
				// open doors
				engine.getWorld().getCurrentMap().getBackTiles().get(285).setId(196);
				engine.getWorld().getCurrentMap().getBlockTiles().remove(38);
				
				engine.getWorld().getMaps().get(17).getBackTiles().get(161).setId(197);
				engine.getWorld().getMaps().get(17).getBlockTiles().remove(24);
				
				return true;
			}
		}
		
		// not completed
		return false;
	}
	
	/**
	 * Handles events triggered by the first event
	 * @param q The quest bound to this event
	 * @param id The id of the quest in the EventEngines quest list
	 */
	private boolean killRedGuardQuest(Quest q, int id){
		
		if(q.getID() == 102 && engine.getWorld().getCurrentMap().getName().equals("dung_4")){ 
			
			if(engine.getCharacters().size() == 0){
				
				// get pickaxe
				Item item = ItemHandler.getItemHandler().getItem("Pickaxe");
				item.setId((int) System.currentTimeMillis());
				engine.getPlayer().addToInventory(item );
				
				return true;
			}
		}
		
		// not completed
		return false;
	}
}
