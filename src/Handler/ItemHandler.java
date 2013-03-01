package Handler;

import java.util.HashMap;

import Item.ArmorItem;
import Item.Item;
import Item.LifeItem;
import Item.MoneyItem;
import Item.WeaponItem;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.Serializable;


/**
 * This class will read from a text file, creating all the items supplied within the file.
 * The reading of the file is done in the class constructor.
 * After this is done, other classes may use this class to retrieve clones of these items, by supplying the name of the item.
 * @author Johan
 * @version 2013-02-17
 */
public class ItemHandler implements Serializable{
	
	// fields:
	private static final long serialVersionUID = 5L;
	private static ItemHandler itemHandler = null;
	private HashMap<String, Item> items;
	
	/**
	 * When initiating, loads all the items.
	 */
	private ItemHandler()
	{
		items = new HashMap<>();
		loadItems();
	}
	
	
	/**
	 * Returns the singleton instance of this class.
	 * @return The singleton instance
	 */
	public static ItemHandler getItemHandler()
	{
		if(itemHandler == null)
		{
			itemHandler = new ItemHandler();
		}
		
		return itemHandler;
	}
	
	/**
	 * Returns the item specified by the name, null if it doesn't exist.
	 * @param name The item to be retrieved.
	 * @return Item The item you wanted or null if it doesn't exist.
	 */
	public Item getItem(String name)
	{
		return items.get(name);
	}
	
	/**
	 * Loads all the items into the buffer
	 */
	private void loadItems()
	{
		try{
			BufferedReader reader = new BufferedReader(new FileReader(new File("src/Handler/Items.txt")));
			readArmor(reader);
			readLife(reader);
			readWeapon(reader);
			readMoney(reader);
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
			if(totLine.equals("[MONEYITEM]"))
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
			int attackDamage = Integer.parseInt(lines[6]);
			int attackSpeed = Integer.parseInt(lines[7]);
			int attackRange = Integer.parseInt(lines[8]);
			
			items.put(name, new WeaponItem(id, x, y, width, height, name, isVisible, itemValue, attackDamage, attackSpeed, attackRange));
		}
	}
	
	
	/**
	 * loads all the MoneyItems from the specified source
	 * @param reader The source to read from
	 * @throws IOException
	 */
	private void readMoney(BufferedReader reader)
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
			int moneyValue = Integer.parseInt(lines[6]);
			
			items.put(name, new MoneyItem(id, x, y, width, height, name, isVisible, itemValue, moneyValue));
		}
	}
	
}
