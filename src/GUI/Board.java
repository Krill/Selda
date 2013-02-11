package GUI;

import Engine.GameEngine;
import javax.swing.JPanel;
import java.awt.Graphics;
import java.awt.Graphics2D;
import World.Tile;

public class Board extends JPanel{
	
	private GameEngine engine;
	private TileImage tileImages;
	
	
	public Board(GameEngine engine)
	{
		this.engine = engine;
		//Loads all the tile images to a buffer of images. (use tileImages.getImage(id) to use it)
				tileImages = new TileImage();
	}
	
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
