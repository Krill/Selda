package Character;

import Item.Item;

import java.util.ArrayList;
import java.util.List;
import java.awt.geom.Ellipse2D; // For the shopArea

public class ShopCharacter extends Character implements Interactable
{
    private List<Item> inventory;
    private int shopRadius;

    public ShopCharacter(int x, int y, int width, int height, String name, boolean isAttackable, Item[] items, int shopRadius)
    {
        super(x, y, width, height, name, isAttackable);
        inventory = new ArrayList<Item>();        
        for(Item item : items){
            inventory.add(item);
        }
        this.shopRadius = shopRadius;
    }
    
    public List<Item> getInventory()
    {
        return inventory;
    }   
    
    @Override
    public void interact()
    {
        System.out.println("Welcome to the shop!");
    }
    
    public void update(){
    	
    	//Update something, shop inventory for example
    }
    
    public void setShopRadius(int shopRadius){
		this.shopRadius = shopRadius;
	}
    
    public int getShopRadius(){
		return shopRadius;
	}
    
    public Ellipse2D.Double getShopArea(){
    	return new Ellipse2D.Double(getX() - (shopRadius/2) + (getWidth()/2), 
    			 getY() - (shopRadius/2) + (getHeight()/2), shopRadius, shopRadius);
    }

    
    
}
