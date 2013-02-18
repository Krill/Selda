package Handler;

import java.util.HashMap;

import Item.ArmorItem;
import Item.Item;
import Item.LifeItem;
import Item.WeaponItem;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.Serializable;


/**
 * Class that loads all the items to a buffer.
 * @author Johan
 * @version 2013-02-17
 */
public class ItemHandler implements Serializable{
	
	// fields:
	private static final long serialVersionUID = 5L;
	private HashMap<String, Item> items;
	
	/**
	 * When initiating, loads all the items.
	 */
	public ItemHandler()
	{
		items = new HashMap<>();
		loadItems();
	}
	
	/**
	 * Returns the item specified by the name, null if it doesn't exist.
	 * @param name The item to be retrieved.
	 * @return The item
	 */
	public Item getItem(String name)
	{
		return items.get(name);
	}
	
	/**
	 * Loads all the items
	 */
	private void loadItems()
	{
		try{
			BufferedReader reader = new BufferedReader(new FileReader(new File("src/Handler/Items.txt")));
			readArmor(reader);
			readLife(reader);
			readWeapon(reader);
			reader.close();
			
		}
		catch(Exception e)
		{
			System.out.println("Error loading items");
		}
	}
	
	/**
	 * loads all the armors from the specified source
	 * @param reader The source to read from
	 * @throws IOException
	 */
	private void readArmor(BufferedReader reader)
	throws IOException
	{
		reader.readLine(); //Reads past [ARMORITEM]
		
		String totLine = null;
		while((totLine = reader.readLine()) != null)
		{
			if(totLine.equals("[LIFEITEM]"))
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
			boolean isVisible = Boolean.parseBoolean(lines[4]);
			int itemValue = Integer.parseInt(lines[5]);
			int defenceRating = Integer.parseInt(lines[6]);
			
			items.put(name, new ArmorItem(id, x, y, width, height, name, isVisible, itemValue, defenceRating));
		}
	}
	
	
	/**
	 * loads all the weapons from the specified source
	 * @param reader The source to read from
	 * @throws IOException
	 */
	private void readWeapon(BufferedReader reader)
	throws IOException
	{
		String totLine = null;
		while((totLine = reader.readLine()) != null)
		{
			
			String[] lines = totLine.split(" ");
			
			int id = Integer.parseInt(lines[0]);
			int x = 0;
			int y = 0;
			int width = Integer.parseInt(lines[1]);
			int height = Integer.parseInt(lines[2]);
			String name = lines[3];
			boolean isVisible = Boolean.parseBoolean(lines[4]);
			int itemValue = Integer.parseInt(lines[5]);
			int attackDamage = Integer.parseInt(lines[6]);
			int attackSpeed = Integer.parseInt(lines[7]);
			
			items.put(name, new WeaponItem(id, x, y, width, height, name, isVisible, itemValue, attackDamage, attackSpeed));
		}
	}
	
	
	/**
	 * loads all the lifepotions from the specified source
	 * @param reader The source to read from
	 * @throws IOException
	 */
	private void readLife(BufferedReader reader)
	throws IOException
	{
		String totLine = null;
		while((totLine = reader.readLine()) != null)
		{
			if(totLine.equals("[WEAPONITEM]"))
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
			boolean isVisible = Boolean.parseBoolean(lines[4]);
			int itemValue = Integer.parseInt(lines[5]);
			int lifeValue = Integer.parseInt(lines[6]);
			
			items.put(name, new LifeItem(id, x, y, width, height, name, isVisible, itemValue, lifeValue));
		}
	}

}
