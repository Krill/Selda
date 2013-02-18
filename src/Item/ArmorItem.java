package Item;

/**
 * ArmorItems extends Item and has a instance variabel for a defence rating.
 * 
 * @author norling/Kevin
 * @version 0.1
 */

public class ArmorItem extends Item{
	
	// fields:
	private int defenceRating;
	
	/**
	 * Constructor
	 * 
	 * @param id
	 * @param x
	 * @param y
	 * @param width
	 * @param height
	 * @param name
	 * @param isVisible
	 * @param itemValue
	 * @param defenceRating
	 */
	public ArmorItem(int id, int x, int y, int width, int height, String name, boolean isVisible, int itemValue, int defenceRating){	
		super(id, x, y, width, height,name, isVisible, itemValue);
		this.defenceRating = defenceRating;
	}
	
	/**
	 * Sets the ArmorItems defence rating
	 * @param defenceRating
	 */
	public void setDefenceRating(int defenceRating){
		this.defenceRating = defenceRating;
	}
		
	/**
	 * returns ArmorItems defence rating
	 * @return defenceRating
	 */
	public int getDefenceRating(){
		return defenceRating;
	}
}