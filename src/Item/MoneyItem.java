package Item;

/**
 * MoneyItem extends Item and has a instance variabel for money value.
 * 
 * @author Kevin
 * @version 0.1
 */

public class MoneyItem extends Item{
	
	// fields:
	private int moneyValue;
	
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
	 * @param moneyValue
	 */
	public MoneyItem(int id, int x, int y, int width, int height, String name, boolean isVisible, int itemValue, int moneyValue){	
		super(id,x,y,width,height,name,isVisible, itemValue);
		this.moneyValue = moneyValue;
	}
	
	
	/**
	 * Sets the MoneyItems value
	 * @param moneyValue
	 */
	public void setMoneyValue(int moneyValue){
		this.moneyValue = moneyValue;
	}
		
	/**
	 * returns MoneyItems value
	 * @return moneyValue
	 */
	public int getMoney(){
		return moneyValue;
	}
}

