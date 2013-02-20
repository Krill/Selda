package Quest;

import Character.Character;
import Character.PlayerCharacter;
import Item.Item;
import Utility.Entity;

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
	 * Update the quest stats i.e if it's done
	 */
	public void update(String name, PlayerCharacter p)
	{
		//Tests if the name of the character is the same as the one you should kill in the quest
		if(name.equals(itemToObtain.getName()))
		{
			setNumberDone(getNumberDone() + 1);
			
			updateStatus();
		}
	}
}
