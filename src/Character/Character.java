package Character;

import java.util.LinkedList;

import Utility.Entity;

public abstract class Character extends Entity
{
   private String name;
   private String direction;
   private boolean isAttackable;
   private boolean up, down, right, left;
   
   public Character(int id, int x, int y, int width, int height, String name, boolean isAttackable)
   {
       super(id, x, y, width, height);
       this.name = name;
       this.isAttackable = isAttackable;
       
       direction = "right";
       up = down = right = left = false;
    }
   
   public String getName()
   {
       return name;
   }
   
   public boolean isAttackable()
   {
       return isAttackable;
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
}

