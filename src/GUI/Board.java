package GUI;

import Controller.KeyboardController;
import Engine.GameEngine;
import javax.swing.JPanel;
import java.awt.Graphics;
import java.awt.Graphics2D;
import World.Tile;
import Engine.Collision;

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
		
		addKeyListener(new KeyboardController(engine.getPlayer(),engine.getCollision()));
		setFocusable(true);
		tileImages = new TileImage();
	}
	
	
	/**
	 *  Paints the board
	 */
	@Override
	public void paint(Graphics g)
	{
		Graphics2D g2d = (Graphics2D)g;
		
		paintBackTiles(g2d);
		paintPlayer(g2d);
	}
	
	/**
	 * Paints the backtiles
	 * @param g2d
	 */
	private void paintBackTiles(Graphics2D g2d){
		for(Tile tile : engine.getWorld().getCurrentMap().getBackTiles())
		{
			g2d.drawImage(tileImages.getImage(tile.getId()), tile.getX(), tile.getY(), this);
		}
	}
	
	private void paintPlayer(Graphics2D g2d){
		g2d.draw(engine.getPlayer().getBounds());
	}
}
