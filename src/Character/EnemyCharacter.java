package Character;

import java.awt.geom.Ellipse2D;
import Character.PlayerCharacter;
import java.util.Random;
import Handler.TimeHandler;

public class EnemyCharacter extends AttributeCharacter implements Moveable, Interactable, Cloneable
{
	private PlayerCharacter player;
	
    private float dropRate;
    private int senseRadius;
    private int deathCounter;
    
    private static final Random random = new Random();    
    
    private boolean isMoving;
    private boolean isHostile;
    private boolean detectedPlayer;    
    
    public EnemyCharacter(int id, int x, int y, int width, int height, String name, int health,
                            boolean isAttackable, int speed,float d,
                                boolean isHostile, int senseRadius)
    {
        super(id, x, y, width, height, name, health, isAttackable, speed, senseRadius);       
        this.dropRate = d;
        this.isHostile = isHostile;     
        
        isMoving = true;
        detectedPlayer = false;
        deathCounter = 8;       
    }
    
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
    
    public float getDropRate()
    {
        return dropRate;
    }
    
    public boolean isHostile()
    {
        return isHostile;
    }
    
    public boolean hasDetectedPlayer()
    {
        return detectedPlayer;
    }
    
    public void setDetectedPlayer(boolean detectedPlayer, PlayerCharacter player)
    {
        this.detectedPlayer = detectedPlayer;
        this.player = player;
    }
    
    public int getSenseRadius()
    {
        return senseRadius;
    }
    
    public void setSenseRadius(int senseRadius)
    {
        this.senseRadius = senseRadius;
    }
    
    public Ellipse2D.Double getSenseArea(){
    	return new Ellipse2D.Double(getX() - (senseRadius/2) + (getWidth()/2), 
    			 getY() - (senseRadius/2) + (getHeight()/2), senseRadius, senseRadius);
    }
    
    @Override
    public void interact(PlayerCharacter player)
    {
    	//Interact..
    }
    
    public boolean isMoving()
    {
 	   return isMoving;
    }
    
    public void setMoving()
    {
 	   isMoving = !isMoving;
    }
    
    public void update(){
    	if(getHealth()>0){
    		if( detectedPlayer){
    			moveToPlayer();
    		
    		}else{
    			moveRandom();
    		}
    	}else{
    		die();
    	}
    }
    
    public void moveToPlayer()
    {
		resetDirection();

		int dx = player.getX()-getX();
		int dy = player.getY()-getY();

		// if( Math.abs(dx) < WeaponItem.range() && Math.abs(dy) < WeaponItem.range()  ){
		if( Math.abs(dx) < 30 && Math.abs(dy) < 30 ){
			resetDirection();
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
	 * Moves this 1 unit in the specified direction.
	 */	
	public void move()
	{
    	// move character
    	setY(getY()+getDy());
        setX(getX()+getDx());

        
        // set the current direction
        if(getDx() > 0){
        	setDirection("right");
        } else if(getDx() < 0) {
        	setDirection("left");
        }
        
        if(getDy() > 0){
        	setDirection("down");
        } else if(getDy() < 0){
        	setDirection("up");
        }
	}
	
	/**
	 * Kill enemy after playing a short animation
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
				setDead(true);				
			}
		}		
	}

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
}
