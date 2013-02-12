package Character;

import java.util.ArrayList;

public class ShopCharacter extends Character implements Interactable
{
    private List<Item> inventory;

    public ShopCharacter(int x, int y, int width, int height, String name, boolean isAttackable, Item[] items)
    {
        super(x, y, width, height, name, isAttackable);
        inventory = new ArrayList<Item>();        
        for(Item item : items)
        {
            inventory.add(item);
        }
    }
    
    public List<Item> getInventory()
    {
        return inventory;
    }   
    
    @Override
    public void interact()
    {
        //Do something..
    }

}
