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

public class ItemHandler {
	private HashMap<String, Item> items;
	
	public ItemHandler()
	{
		items = new HashMap<>();
		loadItems();
	}
	
	public Item getItem(String name)
	{
		return items.get(name);
	}
	
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
			float itemValue = Float.parseFloat(lines[5]);
			int defenceRating = Integer.parseInt(lines[6]);
			
			items.put(name, new ArmorItem(id, x, y, width, height, name, isVisible, itemValue, defenceRating));
		}
	}
	
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
			float itemValue = Float.parseFloat(lines[5]);
			int attackDamage = Integer.parseInt(lines[6]);
			int attackSpeed = Integer.parseInt(lines[7]);
			
			items.put(name, new WeaponItem(id, x, y, width, height, name, isVisible, itemValue, attackDamage, attackSpeed));
		}
	}
	
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
			float itemValue = Float.parseFloat(lines[5]);
			int lifeValue = Integer.parseInt(lines[6]);
			
			items.put(name, new LifeItem(id, x, y, width, height, name, isVisible, itemValue, lifeValue));
			
		}
	}

}
