package Character;

/**
 * Abstract class that determines what makes a character possess attributes, such as speed. Holds information
 * about the speed of this character.
 * 
 * @author Alexander Persson & Jimmy Svensson
 * @version 2013-02-28
 */
@SuppressWarnings("serial")
public abstract class AttributeCharacter extends Character implements Moveable
{
    private int speed;
    
    /**
     * Constructor
     * 
     * @param id
     * @param x
     * @param y
     * @param width
     * @param height
     * @param name
     * @param health
     * @param isAttackable
     * @param speed
     * @param radius
     */
    public AttributeCharacter(int id, int x, int y, int width, int height, String name, int health, boolean isAttackable, int speed, int radius)
    {
        super(id, x, y, width, height, name, health, isAttackable, radius);
        this.speed = speed;
    }
    
    /**
     * Returns the speed of this character.
     * @return speed
     */
    public int getSpeed()
    {
        return speed;
    }
    
	/**
	 * Moves this character in the specified direction.
	 */	
	public void move()
	{
    	// Move character
    	setY(getY()+getDy());
        setX(getX()+getDx());

        
        // Set the current direction
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
}
