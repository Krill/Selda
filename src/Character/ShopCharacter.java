package Character;

import Item.Item;

import java.util.ArrayList;
import java.util.List;

/**
 * A shop character can be used to purchase items from. Such items include armor, weapons and potions.
 * Holds a list of items as well as a buyback factor, determining how much money the player can sell items back
 * to the shop.
 * 
 * @author Alexander Persson & Jimmy Svensson
 * @version 2013-02-28
 */
@SuppressWarnings("serial")
public class ShopCharacter extends Character implements Cloneable
{
    private ArrayList<Item> inventory;    
    private int shopBuyBackFactor; // Percentage value!
    
    /**
     * Constructor
     * 
     * @param id the id of a character
     * @param x the x coordination of a character
     * @param y the y coordination of a character
     * @param width the width of a character
     * @param height the height of a character
     * @param name the name of a character
     * @param health the health of a character
     * @param isAttackable if a character is attackable or not
     * @param items the items that a ShopCharacter have
     * @param shopRadius from the radius a player can interact with a ShopCharacter
     * @param shopBuyBackFactor the percentage of item value that the shop will give you for your item
     */
    public ShopCharacter(int id, int x, int y, int width, int height, String name, int health,
    						boolean isAttackable, Item[] items, int shopRadius, int shopBuyBackFactor)
    {
        super(id, x, y, width, height, name, health ,isAttackable, shopRadius);
        this.shopBuyBackFactor = shopBuyBackFactor; 
        inventory = new ArrayList<Item>();        
        for(Item item : items){
            inventory.add(item);
        }
    }
    
    /**
     * Returns a clone of this shop character.
     * @return The clone of this shop character.
     */
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
    
    /**
     * Returns the shop's current inventory list.
     * @return The shop's inventory.
     */
    public List<Item> getInventory()
    {
        return inventory;
    }   
    
    /**
     * The player interacts with the shop.
     * @param player The user-controlled player.
     */
    @Override
    public void interact(PlayerCharacter player)
    {
    	setChanged();
        notifyObservers(player);
    }
    
    /**
     * Update the shop character.
     */
    public void update()
    {
    	
    	//Update something, shop inventory for example
    } 
    
    /**
     * Sets a new value for the buyback factor.
     * @param shopBuyBackFactor The new buyback factor.
     */
    public void setShopBuyBackFactor(int shopBuyBackFactor)
    {
    	this.shopBuyBackFactor = shopBuyBackFactor;
    }

    /**
     * Returns the shop character's buyback factor.
     * @return The shop's buyback factor.
     */
    public int getShopBuyBackFactor()
    {
    	return shopBuyBackFactor;
    }   
}
