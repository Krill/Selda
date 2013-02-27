package Quest;

import Character.PlayerCharacter;
import Item.Item;


/**
 *  This is a class that provides a quest to find a certain item, specified in the constructor, x times.
 *  This class will only know what item to find, leaving the rest up to its superclass, Quest.
 * @author Johan
 *
 */
public class ItemQuest extends Quest{

	private static final long serialVersionUID = 1412;
	private Item itemToObtain;
	
	
	/**
	 * Initiates the quest.
	 * @param id Quest ID
	 * @param item The item to retrieve
	 * @param nr How many to get
	 * @param reward The cash reward
	 * @param message The message to be displayed when accepting quest
	 */
	public ItemQuest(int id, Item item, int nr, int reward, String message)
	{
		super(id, false, nr, reward, message);
		itemToObtain = item;
	}
	
	
	/**
	 * Update the quest, updates it's completed status and counts how many parts the player completed.
	 */
	public void update(String name, PlayerCharacter p)
	{
		//Tests if the name of the supplied is the same as the items name.
	
			int amount = 0;
			for(Item item : p.getInventory())
			{
				if(item.getName().equals(itemToObtain.getName()))
				{
					amount++;
				}
			}
			
			if(p.getWeapon() != null)
			{
				if(p.getWeapon().getName().equals(itemToObtain.getName()))
				{
					amount++;
				}
			}
			
			if(p.getArmor() != null)
			{
				if(p.getArmor().getName().equals(itemToObtain.getName()))
				{
					amount++;
				}
			}
			
			
			setNumberDone(amount);
			updateStatus();
		}
	
}
