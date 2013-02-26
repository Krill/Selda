package Character;

import java.awt.geom.Ellipse2D;

import Utility.Entity;

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
   
   public Character(int id, int x, int y, int width, int height, String name, int health, boolean isAttackable, int radius)
   {
       super(id, x, y, width, height);
       this.name = name;
       this.isAttackable = isAttackable;
       this.radius = radius;   
       
       this.health = health;
       
       direction = "right";
       isAttacking = false;
       dx = dy = 0;
       timeStamp = 0;
    }
   
   @Override
   public Character clone()
   {
   	try{
   		Character copy = (Character)super.clone();
   		return copy;
   	}
   	catch(Exception e)
   	{
   		System.out.println("Error Cloning");
   	}
   	
		return null;
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
   
   public long getActionTime()
   {
	   return actionTime;
   }
   
   public void setActionTime(long actionTime)
   {
	   this.actionTime = actionTime;
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
   
   public boolean isDead()
   {
	   return isDead;
   }
   
   public void setDead(boolean isDead)
   {
	   this.isDead = isDead;
   }
   
   public int getHealth()
   {
       return health;
   }
   
   public void setHealth(int health)
   {
	   this.health = health;
	   setChanged();
	   notifyObservers("information");
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
   
   public void moveX(int dx){
	   if( (dx == -1 && this.dx != -1) ||
			   (dx == 1 && this.dx != 1)){
		   this.dx += dx;
	   }
   }
   
   public void moveY(int dy){
	   if( (dy == -1 && this.dy != -1) ||
			   (dy == 1 && this.dy != 1)){
		   this.dy += dy;
	   }	   
   }
   
   public int getDx(){
	   return dx;
   }
   
   public int getDy(){
	   return dy;
   }
   public Ellipse2D.Double getArea(){
   	return new Ellipse2D.Double(getX() - (radius/2) + (getWidth()/2), 
   			 getY() - (radius/2) + (getHeight()/2), radius, radius);
   }
   
   public void resetDirection(){
	   dx = 0;
	   dy = 0;
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

