package GUI;

import javax.swing.JPanel;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.*;


import Controller.KeyboardController;
import Engine.GameEngine;
import World.Tile;
import Engine.Collision;
import Handler.TileHandler;
import Character.EnemyCharacter;
import Character.ShopCharacter;
import Character.CivilianCharacter;

/**
 * 
 * @author Johan @ krilleeeee
 * @version 2013-02-11
 *
 */
public class Board extends JPanel{
	
	private GameEngine engine;
	private TileHandler tileImages;
	
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
		tileImages = new TileHandler();
	}
	
	
	/**
	 *  Paints the board
	 */
	@Override
	public void paint(Graphics g)
	{
		Graphics2D g2d = (Graphics2D)g;
		// Paint Tiles
		paintBackTiles(g2d);
		// Paint Enemies
		paintEnemies(g2d);
		// Paint Shops
		paintShops(g2d);
		// Paint civilians
		paintCivilians(g2d);
		// Paint Player
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
		g2d.setColor(Color.BLUE);
		g2d.draw(engine.getPlayer().getBounds());
	}
	
	private void paintEnemies(Graphics2D g2d){
		ArrayList<EnemyCharacter> enemies = engine.getEnemies();
		g2d.setColor(Color.RED);
		for(EnemyCharacter enemy : enemies){
			g2d.draw(enemy.getBounds());
			g2d.draw(enemy.getSenseArea());
		}
	}
	
	private void paintShops(Graphics2D g2d){
		ArrayList<ShopCharacter> shops = engine.getShops();
		g2d.setColor(Color.GREEN);
		for(ShopCharacter shop : shops){
			g2d.draw(shop.getBounds());
			g2d.draw(shop.getShopArea());
		}
	}
	
	private void paintCivilians(Graphics2D g2d){
		ArrayList<CivilianCharacter> civilians = engine.getCivilians();
		g2d.setColor(Color.YELLOW);
		for(CivilianCharacter civilian : civilians){
			g2d.draw(civilian.getBounds());
			g2d.draw(civilian.getInteractArea());
		}
	}
}
