package Handler;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.Serializable;
import java.util.HashMap;

import Character.Character;
import Item.Item;
import Quest.ItemQuest;
import Quest.KillingQuest;
import Quest.Quest;


/**
 * This class will read from a text file, creating all the quests supplied within the file.
 * The reading of the file is done in the class constructor.
 * After this is done, other classes may use this class to retrieve clones of these quests, by supplying the name of the quest.
 * @author Johan
 *
 */
public class QuestHandler implements Serializable {

	private static final long serialVersionUID = 251623;
	
	private static QuestHandler questHandler = null;

	private HashMap<String, Quest> quests;
	private HashMap<String, Character> characters;
	private ItemHandler itemHandler;
	
	/**
	 * Initiates the QuestHandler. 
	 * @param characters A list of all the characters that could be included in a quest.
	 */
	private QuestHandler(HashMap<String, Character> characters)
	{
		quests = new HashMap<>();
		itemHandler = ItemHandler.getItemHandler();
		this.characters = characters;
		loadQuests();
	}
	
	/**
	 * Returns the singleton instance of this class.
	 * @param  characters A list of all the characters that could be included in a quest.
	 * @return singleton The singleton 
	 */
	public static QuestHandler getQuestHandler(HashMap<String, Character> characters)
	{
		if(questHandler == null)
		{
			questHandler = new QuestHandler(characters);
		}
		
		return questHandler;
	}
	
	/**
	 * Loads all the quests from a txt file.
	 */
	private void loadQuests()
	{
		try{
			BufferedReader reader = new BufferedReader(new FileReader(new File("src/Handler/Quests.txt")));
			
			loadKillingQuest(reader);
			loadItemQuest(reader);
			
			reader.close();
		}
		catch(Exception e)
		{
			System.out.println("Error loading quests");
		}
	}
	
	
	/**
	 * Returns the quest with specified id.
	 * @param id The ID of the quest
	 * @return quest The quest you wanted or null if it doesnt exist.
	 */
	public Quest getQuest(String id)
	{
		return quests.get(id);
	}
	
	/**
	 * Loads all killing quests.
	 * @param reader the source to read from
	 * @throws IOException
	 */
	private void loadKillingQuest(BufferedReader reader)
	throws IOException
	{
		String totLine = null;
		
		//Reads past [KILLINQUEST]
		reader.readLine();
		
		while((totLine = reader.readLine()) != null)
		{
			
			if(totLine.equals("[ITEMQUEST]"))
			{
				break;
			}
			String[] lines = totLine.split(" ");
			
			int id = Integer.parseInt(lines[0]);
			
			System.out.println("Now adding character: " + lines[1]);
			Character character = characters.get(lines[1]);
			System.out.println("Now added:" + character.getName());
			int nr = Integer.parseInt(lines[2]);
			int reward = Integer.parseInt(lines[3]);
			String message = reader.readLine();
			
			
			quests.put("" + id, new KillingQuest(id, character, nr, reward, message));
		}
	}
	
	
	/**
	 * Loads all item quests.
	 * @param reader The source to be read from
	 * @throws IOException
	 */
	private void loadItemQuest(BufferedReader reader)
	throws IOException
	{
		String totLine = null;
		
		while((totLine = reader.readLine()) != null)
		{
			String[] lines = totLine.split(" ");
			
			int id = Integer.parseInt(lines[0]);
			Item item = itemHandler.getItem(lines[1]);
			int nr = Integer.parseInt(lines[2]);
			int reward = Integer.parseInt(lines[3]);
			String message = reader.readLine();
			
			System.out.println("adding quest nr: " + id);
			
			quests.put("" + id, new ItemQuest(id, item, nr, reward, message));
		}
	}
	
}







