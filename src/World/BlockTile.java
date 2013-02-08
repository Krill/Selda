package World;

/**
 * 
 * @author Johan
 * @version 2013-02-08
 */
public class BlockTile extends Tile 
{
	private boolean isHabitable;
	private boolean isPushable;
	
	/**
	 * 
	 * @param id Tile id
	 * @param x	Tile X coordinate
	 * @param y Tile Y coordinate
	 * @param width Tile width
	 * @param height Tile height
	 * @param isHabitable If the tile is habitable.
	 * @param isPushable If the tile is pushable.
	 */
	public BlockTile(int id, int x, int y, int width, int height, boolean isHabitable, boolean isPushable)
	{
		super(id, x, y, width, height);
		this.isHabitable = isHabitable;
		this.isPushable = isPushable;
	}
	
	/**
	 * Returns a boolean whether the tile is habitable.
	 * @return isHabitable
	 */
	public boolean isHabitable()
	{
		return isHabitable;
	}
	
	/**
	 * Returns a boolean whether the tile is pushable.
	 * @return isPushable
	 */
	public boolean isPushable()
	{
		return isPushable;
	}
	
}
