package Utility

import java.awt.Rectangle;
import java.util.Observable;

/**
 * Represents the base of every "thing" in the game.
 * 
 * @author kristoffer
 */
public abstract class Entity extends Observable{
	
	// fields:
	private int x;
	private int y;
	private int width;
	private int height;
	
	/**
	 * Constructor
	 * 
	 * @param x
	 * @param y
	 * @param width
	 * @param height
	 */
	public Entity(int x, int y, int width, int height){
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
	}
	
	/**
	 * Returns the x-coordinate of this entity
	 * @return x
	 */
	public int getX(){
		return x;
	}
	
	/**
	 * Returns the y-coordinate of this entity
	 * @return y
	 */
	public int getY(){
		return y;
	}
	
	/**
	 * Returns the width of this entity
	 * @return width
	 */
	public int getWidth(){
		return width;
	}
	
	/**
	 * Returns the height of this entity
	 * @return height
	 */
	public int getHeight(){
		return height;
	}
	
	/**
	 * Returns the size of this entity in a rectangle shape
	 * @return Rectangle
	 */
	public Rectangle getBounds(){
		return new Rectangle(getX(), getY(), getWidth(), getHeight());
	}	
	
	/**
	 * Sets the x-coordinate
	 * @param x-coordinate
	 */
	public void setX(int x){
		this.x = x;
	}	
	
	/**
	 * Sets the y-coordinate
	 * @param y-coordinate
	 */
	public void setY(int y){
		this.y = y;
	}
	
	/**
	 * Sets the width
	 * @param width
	 */
	public void setWidth(int width){
		this.width = width;
	}	
	
	/**
	 * Sets the height
	 * @param height
	 */
	public void setHeight(int height){
		this.height = height;
	}		
}
