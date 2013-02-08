package World;

import Utility.Entity;

public class Tile extends Entity{
	private int id;
	
	/**
	 * 
	 * @param id The tiles ID number
	 * @param x The X coordinate of the tile
	 * @param y The Y coordinate of the tile
	 * @param width The width
	 * @param height The height
	 */
	public Tile(int id, int x, int y, int width, int height)
	{
		super(x, y, width, height);
		this.id = id;		
	}
	
	public int getId()
	{
		return id;		
	}
	
}
