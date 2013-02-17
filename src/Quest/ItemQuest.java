package Quest;

import Item.Item;

/**
 * A quest to retreive specified item.
 * @author Johan
 *
 */
public class ItemQuest extends Quest{

	private Item itemToObtain;
	
	
	/**
	 * Inits the quest.
	 * @param id Quest ID
	 * @param item The item to retreve
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
	 * Update the quest stats i.e if it's done
	 */
	public void update()
	{
		
	}
}
