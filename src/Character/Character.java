package Character;

import java.awt.geom.Ellipse2D;
import Utility.Entity;

/**
 * Abstract class that determines what makes an entity a character in the game. Holds information
 * such as character name, what direction it is facing, as well as time stamps and flags used by Collision.
 * 
 * @author Alexander Persson & Jimmy Svensson
 * @version 2013-02-28
 */
@SuppressWarnings("serial")
public abstract class Character extends Entity implements Interactable, Cloneable
{
   private String name;
   private String direction;
   private int health;
   private int dx, dy;
   
   private boolean isAttackable; 
   private boolean isAttacking;
   private boolean isDead;
   
   private int radius;
   private long timeStamp;
   private long actionTime;
   
   /**
    * Constructor
    * 
    * @param id the id of a character
    * @param x the x coordination of a character
    * @param y the y coordination of a character
    * @param width the width of a character
    * @param height height the height of a character
    * @param name the name of a character
    * @param health the health of a character
    * @param isAttackable if a character is attackable or not
    * @param radius the radius of a character (is used to get the sense area of a character)
    */
   public Character(int id, int x, int y, int width, int height, String name, int health, boolean isAttackable, int radius)
   {
       super(id, x, y, width, height);
       this.name = name;
       this.isAttackable = isAttackable;
       this.radius = radius;   
       
       this.health = health;
       
       direction = "right";
       isAttacking = false;
       isDead = false;
       dx = dy = 0;
       timeStamp = 0;
   }
   
   /**
    * Returns a clone of this Character.
    * @return The clone of this Character.
    */
   @Override
   public Character clone()
   {
   	try
   	{
   		Character copy = (Character)super.clone();
   		return copy;
   	}
   	catch(Exception e)
   	{
   		System.out.println("Error Cloning");
   	}
   	
		return null;
   }
   
   /**
    * Returns the name of this Character.
    * @return The name of this Character.
    */
   public String getName()
   {
       return name;
   }
   
   /**
    * Returns the current time stamp for this Character. A time stamp is the time an action was initiated.
    * @return The time stamp for this Character.
    */
   public long getTimeStamp()
   {
	   return timeStamp;
   }
   
   /**
    * Sets a new value for timeStamp.
    * @param timeStamp The new value for timeStamp
    */
   public void setTimeStamp(long timeStamp)
   {
	   this.timeStamp = timeStamp;
   }
   
   /**
    * Returns the current action time for this Character.
    * Action time is used to determine how long an action should last.
    * @return The current action time for this Character.
    */
   public long getActionTime()
   {
	   return actionTime;
   }
   
   /**
    * Sets a new value for actionTime.
    * @param actionTime The new value for actionTime.
    */
   public void setActionTime(long actionTime)
   {
	   this.actionTime = actionTime;
   }
   
   /**
    * Determines whether this Character can be attacked or not.
    * @return Boolean showing if this Character is attackable.
    */
   public boolean isAttackable()
   {
       return isAttackable;
   }
   
   /**
    * Determines whether this Character is currently attacking or not.
    * @return Boolean showing if this Character is currently attacking.
    */
   public boolean isAttacking(){
	   return isAttacking;
   }
   
   /**
    * Sets a new value for isAttacking.
    * @param isAttacking The new value for isAttacking.
    */
   public void setAttacking(boolean isAttacking){
	   this.isAttacking = isAttacking;
   }
   
   /**
    * Determines whether this Character is alive or not.    
    * @return Boolean showing if this character is alive or dead.
    */
   public boolean isDead()
   {
	   return isDead;
   }
   
   /**
    * Sets a new value for isDead.
    * @param isDead The new value for isDead.
    */
   public void setDead(boolean isDead)
   {
	   this.isDead = isDead;
   }
   
   /**
    * Returns the health of this Character.
    * @return The current health of this Character.
    */
   public int getHealth()
   {
       return health;
   }
   
   /**
    * Sets a new value for health.
    * @param health The new value for health.
    */
   public void setHealth(int health)
   {
	   this.health = health;
	   setChanged();
	   notifyObservers("information");
   }  
   
   /**
    * Returns the latest direction of this character.
    * @return direction The current direction this Character is facing.
    */
   public String getDirection()
   {
       return direction;
   }  
   
   /**
    * Sets the current direction of this character
    * @param arg The new value for direction.
    */
   public void setDirection(String arg)
   {
       direction = arg;
   }  
   
   /**
    * Sets a new direction for dx.
    * @param dx The new direction argument for dx.
    */
   public void moveX(int dx)
   {
	   if( (dx == -1 && this.dx != -1) ||
			   (dx == 1 && this.dx != 1)){
		   this.dx += dx;
	   }
   }
   
   /**
    * Sets a new direction for dy.
    * @param dy The new direction argument for dy.
    */
   public void moveY(int dy)
   {
	   if( (dy == -1 && this.dy != -1) ||
			   (dy == 1 && this.dy != 1)){
		   this.dy += dy;
	   }	   
   }
   
   public void setDx(int dx)
   {
	   this.dx = dx;
   }
   public void setDy(int dy)
   {
	   this.dy = dy;
   }
   
   /**
    * Returns the current argument for dx.
    * @return Argument for dx.
    */
   public int getDx()
   {
	   return dx;
   }
   
   /**
    * Returns the current argument for dy.
    * @return Argument for dy.
    */
   public int getDy()
   {
	   return dy;
   }
   
   /**
    * Returns the sense area of this Character.
    * @return The generated sense area of this Character.
    */
   public Ellipse2D.Double getArea()
   {
   		return new Ellipse2D.Double(getX() - (radius/2) + (getWidth()/2), 
   			 getY() - (radius/2) + (getHeight()/2), radius, radius);
   }
   
   /**
    * Reset direction of this Character.
    */
   public void resetDirection()
   {
	   dx = 0;
	   dy = 0;
   }
   
   /**
    * Equals-method used to compare two characters.
    * @return Boolean showing if the two characters were the same or not.
    */
   @Override
   public boolean equals(Object obj)
   {
	   /*if( this.getY() == ((Character) obj).getY() && this.getX() == ((Character) obj).getX() ){
		   return(true);
	   }*/
	   
	   if( this.getName() == ((Character)obj).getName()){
		   return true;
	   }
	   
	   return(false);
   }
   
   /**
    * Updates this Character.
    */
   public abstract void update();
}

