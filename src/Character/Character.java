package Character;

import Utility.Entity;

public abstract class Character extends Entity
{
   private String name;
   private boolean isAttackable;
   private boolean up, down, right, left;
   
   public Character(int id, int x, int y, int width, int height, String name, boolean isAttackable)
   {
       super(id, x, y, width, height);
       this.name = name;
       this.isAttackable = isAttackable;
       
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

