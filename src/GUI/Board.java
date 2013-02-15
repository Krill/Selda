package GUI;

import javax.swing.JPanel;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.Ellipse2D;
import java.util.*;


import Controller.KeyboardController;
import Engine.GameEngine;
import World.Tile;
import Engine.Collision;
import Handler.PlayerImageHandler;
import Handler.TileImageHandler;
import Character.Character;
import Character.EnemyCharacter;
import Character.PlayerCharacter;
import Character.ShopCharacter;
import Character.CivilianCharacter;

/**
 * 
 * @author Johan @ krilleeeee
 * @version 2013-02-11
 *
 */
public class Board extends JPanel{
	
	// fields:
	private GameEngine engine;
	private TileImageHandler tileImages;
	private PlayerImageHandler playerImages;
	
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
		tileImages = new TileImageHandler();
		playerImages = new PlayerImageHandler();
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

		// Paint Player
		paintPlayer(g2d);
		
		// Paint characters
		paintCharacter(g2d);
		
		// Paint attack bounds
		paintAttackAreas(g2d);
	}
	
	/**
	 * Paints the backtiles
	 * @param g2d
	 */
	private void paintBackTiles(Graphics2D g2d){
		for(Tile tile : engine.getWorld().getCurrentMap().getBackTiles())
		{
			g2d.setColor(new Color(61,61,61,50));
			g2d.drawImage(tileImages.getImage(tile.getId()), tile.getX(), tile.getY(), this);
			g2d.draw(tile.getBounds());
		}
	}
	
	private void paintPlayer(Graphics2D g2d){
		PlayerCharacter player = engine.getPlayer();
		g2d.setColor(Color.BLACK);
		g2d.draw(engine.getPlayer().getBounds());
		g2d.drawImage(
				playerImages.getImage(player.getDirection(), (player.isUp() || player.isDown() || player.isLeft() || player.isRight())), 
				player.getX(), player.getY(), this);
	}
	
	private void paintCharacter(Graphics2D g2d){
		ArrayList<Character> characters = engine.getCharacters();
		g2d.setColor(Color.RED);
		for(Character character : characters){
			g2d.draw(character.getArea());
			
			g2d.drawImage(
					playerImages.getImage(character.getDirection(), (character.isUp() || character.isDown() || character.isLeft() || character.isRight())), 
					character.getX(), character.getY(), this);
			
		}
	}
	
	private void paintAttackAreas(Graphics2D g2d){
		PlayerCharacter player = engine.getPlayer();
		g2d.setColor(Color.BLUE);
		
		Ellipse2D.Double attackArea = null;
		int weaponRange = 10;
		
		
		if(player.getDirection() == "up"){
			attackArea = new Ellipse2D.Double(
					player.getX() - player.getWidth()/4 , // X-cord
					player.getY() - player.getWidth() ,  // Y-cord
					player.getWidth()+weaponRange, 	// Width
					player.getHeight()+weaponRange);	// Height
		}else if(player.getDirection() == "down"){
			attackArea = new Ellipse2D.Double(
					player.getX() - player.getWidth()/4 , // X-cord
					player.getY() + player.getWidth()/2 ,  // Y-cord
					player.getWidth()+weaponRange, 	// Width
					player.getHeight()+weaponRange/2);	// Height
		}else if(player.getDirection() == "left"){
			attackArea = new Ellipse2D.Double(
					player.getX() - player.getWidth()/2 - weaponRange , // X-cord
					player.getY(),  // Y-cord
					player.getWidth()+weaponRange, 	// Width
					player.getHeight());	// Height
		}else if(player.getDirection() == "right"){
			attackArea = new Ellipse2D.Double(
					player.getX() + player.getWidth()/2 , // X-cord
					player.getY(),  // Y-cord
					player.getWidth()+weaponRange, 	// Width
					player.getHeight());	// Height
		}
		
		g2d.draw(attackArea);
		
	}
}
