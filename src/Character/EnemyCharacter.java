package Character;

import java.awt.geom.Ellipse2D;
import Character.PlayerCharacter;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import Handler.TimeHandler;
import Item.Item;

/**
 * The basic enemy in the game. Can be attacked and killed.
 * Drops items upon death which can be obtained by the player.
 * 
 * @author Alexander Persson & Jimmy Svensson
 * @version 2013-02-28
 */
@SuppressWarnings("serial")
public class EnemyCharacter extends AttributeCharacter implements Cloneable
{
	private PlayerCharacter player;
	
	private ArrayList<Item> inventory; 
	
    private float dropRate;
    private int senseRadius;
    private int deathCounter;
    
    private static final Random random = new Random();    
    
    private boolean isMoving;
    private boolean isHostile;
    private boolean detectedPlayer;    
    
    /**
     * Constructor
     * 
     * @param id the id of a character
     * @param x the x coordination of a character
     * @param y the y coordination of a character
     * @param width the width of a character
     * @param height the height of a character
     * @param name the name of a character
     * @param health the health of a character
     * @param isAttackable if a character is attackable or not
     * @param speed the speed of a AttributeCharacter
     * @param d the rate at which items will drop upon enemy is death
     * @param isHostile if a EnemyCharacter is Hostile or not
     * @param senseRadius the sense radius of a player
     * @param items the items that a EnemyCharacter have
     */
    public EnemyCharacter(int id, int x, int y, int width, int height, String name, int health,
                            boolean isAttackable, int speed,float d,
                                boolean isHostile, int senseRadius, Item[] items)
    {
        super(id, x, y, width, height, name, health, isAttackable, speed, senseRadius);       
        this.dropRate = d;
        this.isHostile = isHostile; 
        inventory = new ArrayList<>();
        
        for(Item item : items){
            inventory.add(item);
        }
        
        isMoving = true;
        detectedPlayer = false;
        deathCounter = 8;       
    }
    
    /**
     * Returns a copy of this enemy.
     * @return Clone of enemy.
     */
    @Override
    public EnemyCharacter clone()
    {
    	try{
    		EnemyCharacter copy = (EnemyCharacter)super.clone();
    		return copy;
    	}
    	catch(Exception e)
    	{
    		System.out.println("Error Cloning");
    	}
    	
		return null;
    }
    
    /**
     * Returns the rate at which items will drop upon enemy death.     *
     * @return The enemy's droprate.
     */
    public float getDropRate()
    {
        return dropRate;
    }
    
    /**
     * Returns bool showing if this enemy is currently hostile or not.
     * @return True if hostile, false otherwise.
     */
    public boolean isHostile()
    {
        return isHostile;
    }
    
    /**
     * Returns bool showing if this enemy has detected a player.
     * @return True if player has been detected, false otherwise.
     */
    public boolean hasDetectedPlayer()
    {
        return detectedPlayer;
    }
    
    /**
     * Sets new values for detected player.
     * @param detectedPlayer If player is detected or not.
     * @param player The user-controlled player.
     */
    public void setDetectedPlayer(boolean detectedPlayer, PlayerCharacter player)
    {
        this.detectedPlayer = detectedPlayer;
        this.player = player;
    }
    
    /**
     * Returns the enemy's sense radius.
     * @return The enemy's sense radius.
     */
    public int getSenseRadius()
    {
        return senseRadius;
    }
    
    /**
     * Sets a new value for this enemy's sense radius.
     * @param senseRadius The new value for senseRadius.
     */
    public void setSenseRadius(int senseRadius)
    {
        this.senseRadius = senseRadius;
    }
    
    /**
     * Returns the generated sense area for this enemy.
     * @return The enemy's sense area.
     */
    public Ellipse2D.Double getSenseArea(){
    	return new Ellipse2D.Double(getX() - (senseRadius/2) + (getWidth()/2), 
    			 getY() - (senseRadius/2) + (getHeight()/2), senseRadius, senseRadius);
    }
    
    /**
     * Interact with the player.
     * @param player The user-controlled player.
     */
    @Override
    public void interact(PlayerCharacter player)
    {
    	//Interact..
    }
    
    /**
     * Returns bool showing whether this enemy is currently moving or not.
     * @return If enemy is moving or not.
     */
    public boolean isMoving()
    {
 	   return isMoving;
    }
    
    /**
     * Invert isMoving boolean.
     */
    public void setMoving()
    {
 	   isMoving = !isMoving;
    }
    
    /**
     * Update the enemy.
     * The enemy can walk around randomly, engage the player, or die and be removed from the game.
     */
    public void update(){
	    if(!isDead()){	
    		if(getHealth() > 0){
	    		if(isHostile && detectedPlayer){
	    			moveToPlayer();    		
	    		}else{
	    			moveRandom();
	    		}
	    	}else{
	    		die();	    		
	    	}
	    }
    }
    
    /**
     * Move the enemy towards the player that has been detected.
     */
    public void moveToPlayer()
    {
		resetDirection();

		int dx = player.getX()-getX();
		int dy = player.getY()-getY();

		// if( Math.abs(dx) < WeaponItem.range() && Math.abs(dy) < WeaponItem.range()  ){
		if( Math.abs(dx) < 30 && Math.abs(dy) < 30 ){
			// Attack with delay 1500 ms inbetween slashes
			if(getTimeStamp() == 0)
			{
				setTimeStamp(TimeHandler.getTime());
				setActionTime(1500);
				resetDirection();				
				setAttacking(true);
			}
			
			if( !TimeHandler.timePassed(getTimeStamp(), getActionTime()))
			{
				// Do nothing..
			}
			else
			{
				setTimeStamp(0);
			}
		}else{
			if(dy > 0){
				if( Math.abs(dy) > Math.abs(dx) ){
					moveY(1);
					move();
				}else{
					if(dx < 0){
						moveX(-1);
						move();
					}else{
						moveX(1);
						move();
					}
				}
			}else{
				if( Math.abs(dy) > Math.abs(dx) ){
					moveY(-1);
					move();
				}else{
					if(dx < 0){
						moveX(-1);
						move();
					}else{
						moveX(1);
						move();
					}

				}
			}
		}
	}

	/**
	 * Generate random movement for enemy.
	 */
    public void moveRandom()
    {		
		if(getTimeStamp() == 0)		// If clock is reset..
		{		
			setTimeStamp(TimeHandler.getTime());		// Set clock
			setActionTime(random.nextInt(1500) + 500);	// Set a random duration (500-2000 ms) to move/stay										
			switch(random.nextInt(4))  					// Randomize a direction			
				{
				case 0:
					moveY(-1);
					break;
				case 1:
					moveY(1);
					break;
				case 2:
					moveX(1);
					break;
				case 3:
					moveX(-1);
					break;
				}		
		}
				
		if( !TimeHandler.timePassed(getTimeStamp(), getActionTime()) )	// If time hasn't expired, move or stand still..
		{
			if(isMoving())
			{
				move();			
			}
			else resetDirection();
		}
		else											// ..else reset clock and invert isMoving state
		{						
			setMoving();
			setTimeStamp(0);
		}
	}
	
	
	/**
	 * Kill enemy after playing a short animation.
	 */
	public void die()
	{		
		if( getTimeStamp() == 0)
		{
			setTimeStamp(TimeHandler.getTime());
			setActionTime(100);
			rotate();
		}	
		
		if( !TimeHandler.timePassed(getTimeStamp(), getActionTime()) )	// If time hasn't expired..
		{
			// ..do nothing
		}
		else											
		{		
			if(deathCounter != 0)
			{
				setTimeStamp(0);
				deathCounter--;
			}
			else
			{
				giveInventory();
				setDead(true);
			}			
		}		
	}

	/**
	 * Change direction the enemy is facing depending on the last direction.
	 */
	public void rotate()
	{
		switch(getDirection())
		{
			case "up":
				setDirection("right");
				break;
			case "right":
				setDirection("down");
				break;
			case "down":
				setDirection("left");
				break;
			case "left":
				setDirection("up");
				break;
			default:
				System.out.println("rotate() is bugged");
		}
	}
	
	/**
	 * Transfer enemy's inventory to player.
	 */
	public void giveInventory()
	{
		for(Item item : inventory)
		{
			if(random.nextInt(100) <= (int)(dropRate*100))
			{
				Item give = item.clone();
				give.setId((int) TimeHandler.getTime());
				player.addToInventory(give);
				return;
			}
		}
	}
	
	/**
	 * Returns the enemy's inventory.
	 * @return List of enemy's inventory.
	 */
	public List<Item> getInventory()
	{
	    return inventory;
	} 
}



