package Character;

import java.awt.geom.Ellipse2D;
import java.awt.geom.RectangularShape;
import java.util.LinkedList;
import Handler.TimeHandler;

import Utility.Entity;

public abstract class Character extends Entity implements Interactable
{
   private String name;
   private String direction;
   private int health;
   
   private boolean isAttackable; 
   private boolean isAttacking;
   private boolean isDead;
   
   private boolean up, down, right, left;
   private int radius;
   private long timeStamp;
   
   public Character(int id, int x, int y, int width, int height, String name, int health, boolean isAttackable, int radius)
   {
       super(id, x, y, width, height);
       this.name = name;
       this.isAttackable = isAttackable;
       this.radius = radius;   
       
       this.health = health;
       
       direction = "right";
       isAttacking = false;
       up = down = right = left = false;
    }
   
   public String getName()
   {
       return name;
   }
   
   public long getTimeStamp()
   {
	   return timeStamp;
   }
   
   public void setTimeStamp(long timeStamp)
   {
	   this.timeStamp = timeStamp;
   }
   
   public boolean isAttackable()
   {
       return isAttackable;
   }
   
   public boolean isAttacking(){
	   return isAttacking;
   }
   
   public void setAttacking(boolean isAttacking){
	   this.isAttacking = isAttacking;
   }
   
   public boolean isDead(){
	   return(isDead);
   }
   
   public void setDead(boolean isDead){
	   this.isDead = isDead;
   }
   
   public int getHealth()
   {
       return health;
   }
   
   public void setHealth(int health)
   {
       this.health = health;
   }
  
   
   /**
    * Returns the latest direction of this character
    * @return direction
    */
   public String getDirection(){
       return direction;
   }  
   
   /**
    * Sets the current direction of this character
    * @param arg
    */
   public void setDirection(String arg){
       direction = arg;
   }  
   
   public void setUp(boolean newUp)
   {
       up = newUp;
   }
   
   public void setDown(boolean newDown)
   {
	   down = newDown;
   }
   
   public void setRight(boolean newRight)
   {
	   right = newRight;
     
   }
   
   public void setLeft(boolean newLeft)
   {
	   left = newLeft;
   }
   
   public boolean isUp(){
	   return up;
   }
   
   public boolean isLeft(){
	   return left;
   }
   
   public boolean isRight(){
	   return right;
   }
   
   public boolean isDown(){
	   return down;
   }
   
   public void setRadius(int shopRadius){
		this.radius = shopRadius;
	}
   
   public int getRadius(){
		return radius;
	}
   
   public Ellipse2D.Double getArea(){
   	return new Ellipse2D.Double(getX() - (radius/2) + (getWidth()/2), 
   			 getY() - (radius/2) + (getHeight()/2), radius, radius);
   }
   
   public void resetDirection()
	{
		setUp(false);
		setRight(false);
		setDown(false);
		setLeft(false);
	}
   
   @Override
   public boolean equals(Object obj){
	   if( this.getY() == ((Character) obj).getY() && this.getX() == ((Character) obj).getX() ){
		   return(true);
	   }
	   return(false);
   }
   
   public abstract void update();
}

