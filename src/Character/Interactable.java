package Character;

/**
 * Interface containing the interact method.
 * Objects of classes implementing this interface intend to interact with the player in some way.
 * 
 * @author Alexander Persson & Jimmy Svensson
 * @version 2013-02-28
 */
public interface Interactable
{
	/**
	 * A abstract method for interacting with objects.
	 * @param player The player interacting with the object.
	 */
    public abstract void interact(PlayerCharacter player);
}
