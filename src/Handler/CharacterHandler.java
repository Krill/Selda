package Handler;

import java.util.ArrayList;
import java.util.HashMap;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.File;
import java.io.IOException;

import Character.CivilianCharacter;
import Character.EnemyCharacter;
import Character.ShopCharacter;
import Item.Item;
import Quest.KillingQuest;
import Quest.Quest;
import Character.Character;

public class CharacterHandler {
	private HashMap<String, Character> characters;
	private ItemHandler itemHandler;
	
	public CharacterHandler()
	{
		characters = new HashMap<String, Character>();
		itemHandler = new ItemHandler();
		loadCharacters();
		
	}
	
	public void loadCharacters()
	{
		try{
			BufferedReader reader = new BufferedReader(new FileReader(new File("src/Handler/Characters.txt")));
			
			readEnemies(reader);
			readShopNpc(reader);
			readCivilian(reader);
			
			
			reader.close();
		}
		catch(Exception e)
		{
			System.out.println("Error loading characters");
		}
		
	}
	
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
			
			
			characters.put(name, new EnemyCharacter(id, x, y, width, height, name, health, isAttackable, speed, dropRate, isHostile, senseRadius));
		}
	}

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
			
			
			//Quest ska inte läsas in såhär!
			Quest[] quests = new Quest[1];
			EnemyCharacter enemy = new EnemyCharacter(0, 600, 400, 32, 32,"BiggerMonster", 100, true, 1, 1, true, 200);
			quests[0] = new KillingQuest(0, enemy, 1, 50, "Help! \n" + "Please help us by killing 1 of me.\n");
			
			characters.put(name, new CivilianCharacter(id, x, y, width, height, name, health, isAttackable, quests, 100));
			
		}
	}

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
			
			
			ArrayList<Item> names = new ArrayList<>();
			
			for(int i = 6; i < lines.length; i++)
			{
				Item iten = itemHandler.getItem(lines[i]);
				names.add(iten);
			}
			
			Item[] items = new Item[names.size()];
			names.toArray(items);
			
			characters.put(name, new ShopCharacter(id, x, y, width, height, name, health, isAttackable, items, shopArea));
		}
	}

	public Character getCharacter(String name, int x, int y)
	{
		
		Character character = characters.get(name);
		character.setX(x);
		character.setY(y);
		
		
		
		return character;
		
	}
}






