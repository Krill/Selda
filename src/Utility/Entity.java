package Utility;

import java.awt.Rectangle;
import java.io.Serializable;
import java.util.Observable;

/**
 * This abstract class is responsible for giving other objects a position, an ID along with a width and height. 
 * 
 * @author kristoffer petersson
 * @version 2013-02-08
 */
public abstract class Entity extends Observable implements Serializable{
	
	// fields:
	private static final long serialVersionUID = 10L;
	private int id;
	private int x;
	private int y;
	private int width;
	private int height;
	
	/**
	 * Constructor
	 * 
	 * @param id The id of the entity
	 * @param x The x-coordinate of the entity
	 * @param y The y-coordinate of the entity
	 * @param width The width of the entity
	 * @param height The height of the entity
	 */
	public Entity(int id, int x, int y, int width, int height){
		this.id = id;
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
	}
	
	/**
	 * Returns the id of this entity
	 * @return id The id of the entity
	 */
	public int getId(){
		return id;
	}	
		
	/**
	 * Returns the x-coordinate of this entity
	 * @return x The x-coordinate of the entity
	 */
	public int getX(){
		return x;
	}
	
	/**
	 * Returns the y-coordinate of this entity
	 * @return y The y-coordinate of the entity
	 */
	public int getY(){
		return y;
	}
	
	/**
	 * Returns the width of this entity
	 * @return width The width of the entity
	 */
	public int getWidth(){
		return width;
	}
	
	/**
	 * Returns the height of this entity
	 * @return height The height of the entity
	 */
	public int getHeight(){
		return height;
	}
	
	/**
	 * Returns the size of this entity in a rectangle shape
	 * @return Rectangle The area of this entity
	 */
	public Rectangle getBounds(){
		return new Rectangle(getX(), getY(), getWidth(), getHeight());
	}	
	
	/**
	 * Sets the id
	 * @param id The id of the entity
	 */
	public void setId(int id){
		this.id = id;
	}
	
	/**
	 * Sets the x-coordinate
	 * @param x The x-coordinate of the entity
	 */
	public void setX(int x){
		this.x = x;
	}	
	
	/**
	 * Sets the y-coordinate
	 * @param y The y-coordinate of the entity
	 */
	public void setY(int y){
		this.y = y;
	}
	
	/**
	 * Sets the width
	 * @param width The width of the entity
	 */
	public void setWidth(int width){
		this.width = width;
	}	
	
	/**
	 * Sets the height
	 * @param height The height of the entity
	 */
	public void setHeight(int height){
		this.height = height;
	}		
}
