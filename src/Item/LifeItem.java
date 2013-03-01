package Item;

/**
 * LifeItem extends Item and gives life (potion or a heart).
 * LifeItem is an item which contains a lifeValue that represents how much the item heals the player. 
 * The item is added to the inventory and when checked on it heals the player.
 * @author Kevin
 * @version 0.1
 */

public class LifeItem extends Item{
	
	private int lifeValue;

	
	/**
	 * Constructor
	 * 
	 * @param id
	 * @param x
	 * @param y
	 * @param width
	 * @param height
	 * @param name
	 * @param itemValue
	 * @param isVisible
	 * @param lifeValue
	 */
	public LifeItem(int id, int x, int y, int width, int height, String name, boolean isVisible, int itemValue, int lifeValue){	
		
		super(id,x,y,width,height,name,isVisible, itemValue);
		this.lifeValue = lifeValue;
	}
		
	/**
	 * Sets attack damage for the item
	 * @param lifeValue
	 */
	public void setLifeValue(int lifeValue){
		this.lifeValue = lifeValue;
	}
	
	/**
	 * Returns the attack damage
	 * @return attackDamage
	 */
	public int getLifeValue(){
		return lifeValue;
	}
}