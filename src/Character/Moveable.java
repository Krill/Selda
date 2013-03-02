package Character;

/**
 * Interface containing the move method.
 * Objects of classes implementing this interface can be moved around in the game world.
 * 
 * @author Alexander Persson & Jimmy Svensson
 * @version 2013-02-28
 */
public interface Moveable
{
	
	/**
	 * An abstract method for moving an object in the game world.
	 */
    public abstract void move();
}
