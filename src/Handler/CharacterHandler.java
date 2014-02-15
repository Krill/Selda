package Handler;

import java.util.ArrayList;
import java.util.HashMap;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Serializable;

import Character.CivilianCharacter;
import Character.EnemyCharacter;
import Character.ShopCharacter;
import Item.Item;
import Quest.Quest;
import Character.Character;

/**
 * This class will read from a text file, creating all the characters supplied within the file.
 * The reading of the file is done in the class constructor.
 * After this is done, other classes may use this class to retrieve clones of these characters, by supplying the name of the character.
 * @author Johan
 * @version 2013-02-17
 * 
 */
public class CharacterHandler implements Serializable{

	private static CharacterHandler charHandler = null;

	// fields:
	private static final long serialVersionUID = 4L;
	private HashMap<String, Character> characters;
	private ItemHandler itemHandler;
	private QuestHandler questHandler;


	/**
	 * When initiating, it loads all the characters.
	 */
	private CharacterHandler()
	{
		characters = new HashMap<String, Character>();
		itemHandler = ItemHandler.getItemHandler();
		loadCharacters();

	}

	/**
	 * Returns the singleton instance of this class.
	 * @return CharHandler The singleton of this class.
	 */
	public static CharacterHandler getCharacterHandler()
	{
		if(charHandler == null)
		{
			charHandler = new CharacterHandler();
		}

		return charHandler;
	}

	/**
	 * Loads the Character into a buffer
	 */
	private void loadCharacters()
	{
		BufferedReader reader;

		try{
			java.net.URL imageURL = getClass().getResource("/Handler/Characters.txt");
			reader = new BufferedReader(new InputStreamReader(imageURL.openStream()));
			readEnemies(reader);
			readShopNpc(reader);
			readCivilian(reader);
			reader.close();
		}
		catch(Exception e)
		{
			try {
				reader = new BufferedReader(new FileReader(new File("src/Handler/Characters.txt")));
				readEnemies(reader);
				readShopNpc(reader);
				readCivilian(reader);
				reader.close();
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		}
	}


	/**
	 * loads all the enemies from the file
	 * @param reader the source to be read from
	 * @throws IOException
	 */
	private void readEnemies(BufferedReader reader)
			throws IOException
			{
		String totLine = null;

		reader.readLine(); //Reads past [ENEMIES]
		while((totLine = reader.readLine()) != null)
		{	
			if(totLine.equals("[SHOPNPC]"))
			{
				break;
			}

			String[] lines = totLine.split(" ");

			int id = Integer.parseInt(lines[0]);
			int x = 0;
			int y = 0;
			int width = Integer.parseInt(lines[1]);
			int height = Integer.parseInt(lines[2]);
			String name = lines[3];
			int health = Integer.parseInt(lines[4]);
			boolean isAttackable = Boolean.parseBoolean(lines[5]);
			int speed = Integer.parseInt(lines[6]);
			float dropRate = Float.parseFloat(lines[7]);
			boolean isHostile = Boolean.parseBoolean(lines[8]);
			int senseRadius = Integer.parseInt(lines[9]);

			ArrayList<Item> names = new ArrayList<>();

			for(int i = 10; i < lines.length; i++)
			{

				Item iten = itemHandler.getItem(lines[i]);

				names.add(iten);
			}

			Item[] items = new Item[names.size()];

			names.toArray(items);

			characters.put(name, new EnemyCharacter(id, x, y, width, height, name, health, isAttackable, speed, dropRate, isHostile, senseRadius, items));
		}
			}


	/**
	 * loads all the civilians from the file
	 * @param reader the source to be read from
	 * @throws IOException
	 */
	private void readCivilian(BufferedReader reader)
			throws IOException
			{
		String totLine = null;

		while((totLine = reader.readLine()) != null)
		{	
			if(totLine.equals("[ITEMS]"))
			{
				break;
			}

			String[] lines = totLine.split(" ");

			int id = Integer.parseInt(lines[0]);
			int x = 0;
			int y = 0;
			int width = Integer.parseInt(lines[1]);
			int height = Integer.parseInt(lines[2]);
			String name = lines[3];
			int health = Integer.parseInt(lines[4]);
			boolean isAttackable = Boolean.parseBoolean(lines[5]);
			int interactRadius = Integer.parseInt(lines[6]);

			questHandler = QuestHandler.getQuestHandler(characters);

			ArrayList<Quest> q = new ArrayList<>();
			for(int i = 7; i < lines.length; i++)
			{
				q.add(questHandler.getQuest(lines[i]));
				System.out.println("Adding quest: " + lines[i] + "to" + name);
			}
			Quest[] quests = new Quest[q.size()];
			q.toArray(quests);


			characters.put(name, new CivilianCharacter(id, x, y, width, height, name, health, isAttackable, quests, interactRadius));

		}
			}


	/**
	 * loads all the shop NPCs from the file
	 * @param reader the source to be read from
	 * @throws IOException
	 */
	private void readShopNpc(BufferedReader reader)
			throws IOException
			{
		String totLine = null;

		while((totLine = reader.readLine()) != null)
		{	
			if(totLine.equals("[CIVILIAN]"))
			{
				break;
			}

			String[] lines = totLine.split(" ");

			int id = Integer.parseInt(lines[0]);
			int x = 0;
			int y = 0;
			int width = Integer.parseInt(lines[1]);
			int height = Integer.parseInt(lines[2]);
			String name = lines[3];
			int health = Integer.parseInt(lines[4]);
			boolean isAttackable = Boolean.parseBoolean(lines[5]);
			int shopArea = Integer.parseInt(lines[6]);
			int shopBuyFactor = Integer.parseInt(lines[7]);

			ArrayList<Item> names = new ArrayList<>();

			for(int i = 8; i < lines.length; i++)
			{
				Item iten = itemHandler.getItem(lines[i]);
				names.add(iten);
			}

			Item[] items = new Item[names.size()];
			names.toArray(items);

			characters.put(name, new ShopCharacter(id, x, y, width, height, name, health, isAttackable, items, shopArea, shopBuyFactor));
		}
			}


	/**
	 * Returns a character from the buffer, null if the character doesn't exist.
	 * @param name The name of the character to be returned
	 * @param x The x coordinate that the character should have
	 * @param y The y coordinate that the character should have
	 * @return character The character you wanted to retrieve.
	 */
	public Character getCharacter(String name, int x, int y)
	{

		Character character = characters.get(name);
		character.setX(x);
		character.setY(y);

		return character.clone();
	}
}






