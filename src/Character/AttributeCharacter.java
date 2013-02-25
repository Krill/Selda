package Character;

@SuppressWarnings("serial")
public abstract class AttributeCharacter extends Character implements Moveable
{
    private int speed;
    
    public AttributeCharacter(int id, int x, int y, int width, int height, String name, int health, boolean isAttackable, int speed, int radius)
    {
        super(id, x, y, width, height, name, health, isAttackable, radius);
        this.speed = speed;
    }
    
    public int getSpeed()
    {
        return speed;
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
}
