package World;

/**
 * This class checks if the tile is a blocktile, if it's a blocktile 
 * the characters can't walk on the specific tile.
 * @author Johan
 * @version 2013-02-11
 */
@SuppressWarnings("serial")
public class BlockTile extends Tile 
{
	private boolean isPushable;
	
	/**
	 * 
	 * @param id Tile id
	 * @param x	Tile X coordinate
	 * @param y Tile Y coordinate
	 * @param width Tile width
	 * @param height Tile height
	 * @param isPushable If the tile is pushable.
	 */
	public BlockTile(int id, int x, int y, int width, int height, boolean isPushable)
	{
		super(id, x, y, width, height);
		this.isPushable = isPushable;
	}
	
	
	/**
	 * Returns a boolean whether the tile is pushable.
	 * @return isPushable True if pushable, false otherwise.
	 */
	public boolean isPushable()
	{
		return isPushable;
	}
	
}
