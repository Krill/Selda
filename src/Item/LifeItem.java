package Item;

/**
 * LifeItem extends Item and gives life (potion or a heart).
 * LifeItem is an item which contains a lifeValue that represents how much the item heals the player. 
 * The item is added to the inventory and when checked on it heals the player.
 * @author Kevin
 * @version 0.1
 */

public class LifeItem extends Item{

	private static final long serialVersionUID = 722225;
	private int lifeValue;

	
	/**
	 * Constructor
	 * 
	 * @param id The id of the Armor
	 * @param x The x coord 
	 * @param y The y coord
	 * @param width The width of armor
	 * @param height The height of the armor
	 * @param name The armors name
	 * @param isVisible True if the armor is visible
	 * @param itemValue The armors value
	 * @param lifeValue the life value
	 */
	public LifeItem(int id, int x, int y, int width, int height, String name, boolean isVisible, int itemValue, int lifeValue){	
		
		super(id,x,y,width,height,name,isVisible, itemValue);
		this.lifeValue = lifeValue;
	}
		
	/**
	 * Sets attack damage for the item
	 * @param lifeValue New life value
	 */
	public void setLifeValue(int lifeValue){
		this.lifeValue = lifeValue;
	}
	
	/**
	 * Returns the attack damage
	 * @return attackDamage The attack damage
	 */
	public int getLifeValue(){
		return lifeValue;
	}
}