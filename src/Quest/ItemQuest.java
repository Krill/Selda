package Quest;

import Item.Item;

public class ItemQuest extends Quest{

	private Item itemToObtain;
	
	
	public ItemQuest(int id, Item item, int nr, int reward)
	{
		super(id, false, nr, reward);
		itemToObtain = item;
	}
	
	
	
	public void update()
	{
		
	}
}
