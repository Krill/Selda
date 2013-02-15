package Character;

import Item.Item;

import java.util.ArrayList;
import java.util.List;
import java.awt.geom.Ellipse2D; // For the shopArea

public class ShopCharacter extends Character implements Interactable
{
    private List<Item> inventory;
    

    public ShopCharacter(int id, int x, int y, int width, int height, String name, boolean isAttackable, Item[] items, int shopRadius)
    {
        super(id, x, y, width, height, name, isAttackable, shopRadius);
        inventory = new ArrayList<Item>();        
        for(Item item : items){
            inventory.add(item);
        }
    }
    
    public List<Item> getInventory()
    {
        return inventory;
    }   
    
    @Override
    public void interact(PlayerCharacter player)
    {
    	setChanged();
        notifyObservers("TestarTestarTestarTestarTestarTestarTestarTestarTestarTestarTestarTestarTestarTestarTestarTestarTestarTestarTestarTestarTestarTestarTestarTestarTestarTestarTestarTestar");
    }
    
    public void update(){
    	
    	//Update something, shop inventory for example
    }
    
    

    
    
}
