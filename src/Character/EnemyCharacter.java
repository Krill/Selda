package Character;

import java.awt.geom.Ellipse2D;
import Character.PlayerCharacter;
import java.util.Random;
import Handler.TimeHandler;

public class EnemyCharacter extends AttributeCharacter implements Moveable, Interactable
{
	private PlayerCharacter player;
	
    private float dropRate;
    private int senseRadius;
    
    private static final Random random = new Random();    
    
    private boolean isHostile;
    private boolean detectedPlayer;

    public EnemyCharacter(int id, int x, int y, int width, int height, String name, int health,
                            boolean isAttackable, int speed,float d,
                                boolean isHostile, int senseRadius)
    {
        super(id, x, y, width, height, name, health, isAttackable, speed, senseRadius);       
        this.dropRate = d;
        this.isHostile = isHostile;     
        
        detectedPlayer = false;        
        
        setTimeStamp(0);
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
					setDown(true);
					move();
				}else{
					if(dx < 0){
						setLeft(true);
						move();
					}else{
						setRight(true);
						move();
					}
				}
			}else{
				if( Math.abs(dy) > Math.abs(dx) ){
					setUp(true);
					move();
				}else{
					if(dx < 0){
						setLeft(true);
						move();
					}else{
						setRight(true);
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
			setTimeStamp(System.currentTimeMillis());	// Set clock
			if(isMoving())								// If ready to move, randomize a direction
			{
				switch(random.nextInt(4))
				{
				case 0:
					setUp(true);
					break;
				case 1:
					setDown(true);
					break;
				case 2:
					setRight(true);
					break;
				case 3:
					setLeft(true);
					break;
				}				
			}
		}
				
		if( !TimeHandler.timePassed(getTimeStamp(), 1500) )	// If time hasn't expired, move or stand still..
		{
			if(isMoving())
			{
				move();			
			}
		}
		else											// ..else reset clock, directions and invert isMoving state
		{
			resetDirection();			
			setMoving();
			setTimeStamp(0);
		}
	}
	
	/**
	 * Moves this 1 unit in the specified direction.
	 */	
	public void move()
	{
    	// reference to set direction
    	int dx = getX();
    	int dy = getY();
    	
    	// move character
        if(isUp()){
        	setY(getY()-1);
        }
        if(isLeft()){
        	setX(getX()-1);
        }
        if(isRight()){
        	setX(getX()+1);
        }
        if(isDown()){
        	setY(getY()+1);
        }  
        
        // set the current direction
        if(dx-getX() < 0){
        	setDirection("right");
        } else if(dx-getX() > 0) {
        	setDirection("left");
        }
        
        if(dy-getY() < 0){
        	setDirection("down");
        } else if(dy-getY() > 0){
        	setDirection("up");
        }
	}

	/**
	 * Resets all directions for the enemy.
	 */
	public void resetDirection()
	{
		setUp(false);
		setRight(false);
		setDown(false);
		setLeft(false);
	}
	
	public void die(){
		/*
		if( getTimeStamp() == 0){
			setTimeStamp(TimeHandler.getTime());
			
			
			
		}else{
			setTimeStamp(0);
		}
		
		if()
		*/
		
	}

	public void rotate(){
		switch(getDirection()){
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