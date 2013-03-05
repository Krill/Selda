package Item;

/**
 * MoneyItem extends Item and has a instance variabel for money value.
 * MoneyItem is a somewhat unique item as it contains a value that is used to update the player’s money and is not added to the player’s inventory array list when added.
 * @author Kevin
 * @version 0.1
 */

@SuppressWarnings("serial")
public class MoneyItem extends Item{
	
	// fields:
	private int moneyValue;
	
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
	 * @param moneyValue The moneys value
	 */
	public MoneyItem(int id, int x, int y, int width, int height, String name, boolean isVisible, int itemValue, int moneyValue){	
		super(id,x,y,width,height,name,isVisible, itemValue);
		this.moneyValue = moneyValue;
	}
	
	
	/**
	 * Sets the MoneyItems value
	 * @param moneyValue The new moneyvalue
	 */
	public void setMoneyValue(int moneyValue){
		this.moneyValue = moneyValue;
	}
		
	/**
	 * returns MoneyItems value
	 * @return moneyValue The moneyvalue
	 */
	public int getMoney(){
		return moneyValue;
	}
}

