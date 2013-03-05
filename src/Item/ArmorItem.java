package Item;

/**
 * ArmorItems extends Item and has a instance variabel for a defence rating.
 * AromorItem contain a armor rating, it’s added to the inventory and is equipped when in use and protects against attack from enemies.
 * @author norling/Kevin
 * @version 0.1
 */

@SuppressWarnings("serial")
public class ArmorItem extends Item{
	
	// fields:
	private int defenceRating;
	
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
	 * @param defenceRating The armors defencerating
	 */
	public ArmorItem(int id, int x, int y, int width, int height, String name, boolean isVisible, int itemValue, int defenceRating){	
		super(id, x, y, width, height,name, isVisible, itemValue);
		this.defenceRating = defenceRating;
	}
	
	/**
	 * Sets the ArmorItems defence rating
	 * @param defenceRating The new defencerating
	 */
	public void setDefenceRating(int defenceRating){
		this.defenceRating = defenceRating;
	}
		
	/**
	 * returns ArmorItems defence rating
	 * @return defenceRating The defencerating
	 */
	public int getDefenceRating(){
		return defenceRating;
	}
}