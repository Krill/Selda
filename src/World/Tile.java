package World;

import Utility.Entity;

/**
 * This class gives its superclass Entity information about 
 * all the Tiles position and id number
 * @author Johan
 * @version 2013-02-08
 */
public class Tile extends Entity{

	private static final long serialVersionUID = 981231;

	/**
	 * 
	 * @param id The tiles ID number
	 * @param x The X coordinate of the tile
	 * @param y The Y coordinate of the tile
	 * @param width The width of the tile
	 * @param height The height of the tile
	 */
	public Tile(int id, int x, int y, int width, int height)
	{
		super(id, x, y, width, height);
	}	
}
