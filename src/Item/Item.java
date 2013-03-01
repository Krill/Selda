package Item;

import Utility.Entity;

/**
 * Abstract class for all Items in the game.
 * 
 * @author kristoffer/kevin
 * @version 0.1
 */
public abstract class Item extends Entity implements Cloneable{

	// fields:
	private String name;
	private boolean isVisible;
	private int itemValue;
	
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
	 */
	public Item(int id, int x, int y, int width, int height, String name, boolean isVisible, int itemValue){
		super(id, x, y, width, height);
		
		this.name = name;
		this.isVisible = isVisible;
		this.itemValue = itemValue;
	}
	
	/**
	 * Returns the items name
	 * @return name
	 */
	public String getName(){
		return name;
	}
	
	/**
	 * Returns true if item is visible
	 * @return isVisible
	 */
	
	public boolean isVisible(){
		return isVisible;
	}
	
	/**
	 * Sets the visibility of this item
	 * @param isVisible
	 */
	public void setVisible(boolean isVisible){
		this.isVisible = isVisible;
	}
	
	/**
	 * Sets the value of the item;
	 * @param itemValue
	 */
	public void setItemValue(int itemValue){
		this.itemValue = itemValue;
	}
		
	/**
	 * returns the value of the item;
	 * @param itemValue
	 */
	public int getItemValue(){
		return itemValue;
	}
	
	/**
	 * If this item has the same ID equals returns true
	 * @param other
	 * @return boolean
	 */
	public boolean equals(Item other){
		if(other == null){
			return false;
		}
		return getId() == other.getId();
	}
	
	/**
	 * Returns a clone of this item
	 * @return Item
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

