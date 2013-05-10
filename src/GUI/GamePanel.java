package GUI;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.util.ArrayList;
import java.util.Iterator;

import javax.swing.JPanel;

import World.Tile;

import Character.PlayerCharacter;
import Controller.KeyboardController;
import Engine.GameClient;
import Engine.ServerEngine;
import Handler.CharacterImageHandler;
import Handler.PlayerImageHandler;
import Handler.TileImageHandler;

/**
 * This class handles all graphics that are related to the "game" so to speak. It paints
 * the player, characters and tiles constantly
 * @author kristoffer
 */
@SuppressWarnings("serial")
public class GamePanel extends JPanel{
	
	// fields:
	private GameClient gameClient;
	private TileImageHandler tileImages;
	private PlayerImageHandler playerImages;
	private CharacterImageHandler characterImages;
	
	/**
	 * Creates a GamePanel component.
	 * @param engine The game engine
	 */
	public GamePanel(GameClient gameClient){
		this.gameClient = gameClient;
		addKeyListener(new KeyboardController(gameClient.getClientPlayer()));
		
		//Loads all the tile images to a buffer of images. (use tileImages.getImage(id) to use it)	
		
		setDoubleBuffered(true);
		setBounds(0, 0, 800, 640);
		setFocusable(true);
		
		tileImages = new TileImageHandler();
		playerImages = new PlayerImageHandler();
		characterImages = new CharacterImageHandler();		
	}
	
	/**
	 *  Paints the board
	 */
	@Override
	public void paint(Graphics g){
		addKeyListener(new KeyboardController(gameClient.getClientPlayer()));
		
		super.paint(g);
		Graphics2D g2d = (Graphics2D)g;
		// Paint Tiles
		paintBackTiles(g2d);

		// Paint Player
		paintPlayers(g2d);
		paintClientPlayer(g2d);
		
		// Paint characters
		//paintCharacter(g2d);
	}
	
	/**
	 * Paints the backtiles
	 * @param g2d The graphics
	 */
	private void paintBackTiles(Graphics2D g2d){
		for(Tile tile : gameClient.getWorld().getCurrentMap().getBackTiles()){
			g2d.drawImage(tileImages.getImage(tile.getId()), tile.getX(), tile.getY(), this);
			g2d.draw(tile.getBounds());
		}
	}
	
	private void paintPlayers(Graphics2D g2d){
		g2d.setColor(Color.BLACK);
		ArrayList<PlayerCharacter> players = gameClient.getPlayers();
		
		Iterator<PlayerCharacter> it = players.iterator();
		while(it.hasNext()){
			PlayerCharacter player = it.next();
			// get current image
			Image img = playerImages.getImage(player.getDirection(), (player.getDx() != 0 || player.getDy() != 0), player.isAttacking());
			
			// calc where to draw image
			int x = player.getX() - (img.getWidth(this)/4) - 2;
			int y = player.getY() - (img.getHeight(this)/4) + 2;
			
			g2d.drawImage(img, x, y, this);
		}
	}
	
	/**
	 * Paints the player
	 * @param g2d The graphics
	 */
	private void paintClientPlayer(Graphics2D g2d){
		PlayerCharacter player = gameClient.getClientPlayer();
		g2d.setColor(Color.BLACK);
		
		// get current image
		Image img = playerImages.getImage(player.getDirection(), (player.getDx() != 0 || player.getDy() != 0), player.isAttacking());
		
		// calc where to draw image
		int x = player.getX() - (img.getWidth(this)/4) - 2;
		int y = player.getY() - (img.getHeight(this)/4) + 2;
		
		g2d.drawImage(img, x, y, this);
	}
	
	/**
	 * Paints all NPC characters
	 * @param g2d The graphics
	 */
	/*private void paintCharacter(Graphics2D g2d){
		ArrayList<PlayerCharacter> characters = engine.getPlayers();
		g2d.setColor(Color.RED);
		for(Character character : characters){
			
			// get current image
			Image img = characterImages.getImage(character.getDirection(), (character.getDx() != 0 || character.getDy() != 0), character.getName());
			
			// calc where to draw image
			int x = character.getX() - (img.getWidth(this)/4) - 2;
			int y = character.getY() - (img.getHeight(this)/4) + 2;
			
			g2d.drawImage(img, x, y, this);		
		}
	}*/
}
