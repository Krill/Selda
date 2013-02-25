package Quest;

import Character.PlayerCharacter;
import Item.Item;


/**
 * A quest to retrieve specified item, x times, where x is supplied to the constructor.
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
		if(name.equals(itemToObtain.getName()))
		{
			int amount = 0;
			for(Item item : p.getInventory())
			{
				if(item.getName().equals(name))
				{
					amount++;
				}
			}
			setNumberDone(amount);
			updateStatus();
		}
	}
}
