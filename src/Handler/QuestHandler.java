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

public class QuestHandler implements Serializable {

	private static final long serialVersionUID = 251623;
	
	private static QuestHandler questHandler = null;

	private HashMap<String, Quest> quests;
	private CharacterHandler charHandler;
	private ItemHandler itemHandler;
	
	private QuestHandler(CharacterHandler charHandler)
	{
		quests = new HashMap<>();
		this.charHandler = charHandler;
		itemHandler = ItemHandler.getItemHandler();
		loadQuests();
	}
	
	public static QuestHandler getQuestHandler(CharacterHandler charHandler)
	{
		if(questHandler == null)
		{
			questHandler = new QuestHandler(charHandler);
		}
		
		return questHandler;
	}
	
	private void loadQuests()
	{
		try{
			BufferedReader reader = new BufferedReader(new FileReader(new File("src/Handler/Quests.txt")));
			
			loadKillingQuest(reader);
			loadItemQuest(reader);
		}
		catch(Exception e)
		{
			System.out.println("Error loading quests");
		}
	}
	
	public Quest getQuest(String id)
	{
		return quests.get(id);
	}
	
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
			Character character = charHandler.getCharacter(lines[1], 0, 0);
			int nr = Integer.parseInt(lines[2]);
			int reward = Integer.parseInt(lines[3]);
			String message = reader.readLine();
			
			quests.put("" + id, new KillingQuest(id, character, nr, reward, message));
		}
	}
	
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
			
			quests.put("" + id, new ItemQuest(id, item, nr, reward, message));
		}
	}
	
}







