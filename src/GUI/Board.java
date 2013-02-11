package GUI;

import Engine.GameEngine;
import javax.swing.JPanel;
import java.awt.Graphics;
import java.awt.Graphics2D;
import World.Tile;

/**
 * 
 * @author Johan @ krilleeeee
 * @version 2013-02-11
 *
 */
public class Board extends JPanel{
	
	private GameEngine engine;
	private TileImage tileImages;
	
	/**
	 * Creates a Board component.
	 * @param engine
	 */
	public Board(GameEngine engine)
	{
		this.engine = engine;
		//Loads all the tile images to a buffer of images. (use tileImages.getImage(id) to use it)
				tileImages = new TileImage();
	}
	
	
	/**
	 *  Paints the board
	 */
	@Override
	public void paint(Graphics g)
	{
		Graphics2D g2d = (Graphics2D)g;
		
		for(Tile tile : engine.getWorld().getCurrentMap().getBackTiles())
		{
			g2d.drawImage(tileImages.getImage(tile.getId()), tile.getX(), tile.getY(), this);
		}
	}
}
