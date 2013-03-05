package Item;

import Utility.Entity;

/**
 * Abstract class for all Items in the game.
 * Item is the base class for all items. Items are only used by the player but player and other characters have inventories with items. 
 * 
 * 
 * @author kristoffer/kevin
 * @version 0.1
 */
@SuppressWarnings("serial")
public abstract class Item extends Entity implements Cloneable{

	// fields:
	private String name;
	private boolean isVisible;
	private int itemValue;
	
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
	 */
	public Item(int id, int x, int y, int width, int height, String name, boolean isVisible, int itemValue){
		super(id, x, y, width, height);
		
		this.name = name;
		this.isVisible = isVisible;
		this.itemValue = itemValue;
	}
	
	/**
	 * Returns the items name
	 * @return name The items name
	 */
	public String getName(){
		return name;
	}
	
	/**
	 * Returns true if item is visible
	 * @return isVisible True if visible, false otherwise
	 */
	
	public boolean isVisible(){
		return isVisible;
	}
	
	/**
	 * Sets the visibility of this item
	 * @param isVisible The new visible status
	 */
	public void setVisible(boolean isVisible){
		this.isVisible = isVisible;
	}
	
	/**
	 * Sets the value of the item;
	 * @param itemValue The new item value
	 */
	public void setItemValue(int itemValue){
		this.itemValue = itemValue;
	}
		
	/**
	 * returns the value of the item;
	 * @return itemValue The items value
	 */
	public int getItemValue(){
		return itemValue;
	}
	
	/**
	 * If this item has the same ID equals returns true
	 * @param other Other Item to compare against
	 * @return boolean True if same, false otherwise
	 */
	public boolean equals(Item other){
		if(other == null){
			return false;
		}
		return getId() == other.getId();
	}
	
	/**
	 * Returns a clone of this item
	 * @return Item new clone
	 */
	public Item clone(){
		try{
			Item copy = (Item) super.clone();
			return copy;
		} catch (Exception e){
			System.out.println("Error Cloning");
		}
		return null;
	}
}

