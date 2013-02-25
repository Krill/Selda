package Character;

import Item.Item;

import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("serial")
public class ShopCharacter extends Character implements Cloneable
{
    private ArrayList<Item> inventory;    

    public ShopCharacter(int id, int x, int y, int width, int height, String name, int health, boolean isAttackable, Item[] items, int shopRadius)
    {
        super(id, x, y, width, height, name, health ,isAttackable, shopRadius);
        inventory = new ArrayList<Item>();        
        for(Item item : items){
            inventory.add(item);
        }
    }
    
    @Override
    public ShopCharacter clone()
    {
    	try{
    		ShopCharacter copy = (ShopCharacter)super.clone();
    		return copy;
    	}
    	catch(Exception e)
    	{
    		System.out.println("Error Cloning");
    	}
    	
		return null;
    }
    
    public List<Item> getInventory()
    {
        return inventory;
    }   
    
    @Override
    public void interact(PlayerCharacter player)
    {
    	setChanged();
        notifyObservers(player);
    }
    
    public void update(){
    	
    	//Update something, shop inventory for example
    } 
}
