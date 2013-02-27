package GUI;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.util.ArrayList;

import javax.swing.JPanel;

import World.Tile;

import Character.PlayerCharacter;
import Character.Character;
import Controller.KeyboardController;
import Engine.GameEngine;
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
	private GameEngine engine;
	private TileImageHandler tileImages;
	private PlayerImageHandler playerImages;
	private CharacterImageHandler characterImages;
	
	/**
	 * Creates a GamePanel component.
	 * @param engine
	 */
	public GamePanel(GameEngine engine){
		this.engine = engine;
		
		//Loads all the tile images to a buffer of images. (use tileImages.getImage(id) to use it)	
		addKeyListener(new KeyboardController(engine.getPlayer(),engine.getCollision()));
		
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
		super.paint(g);
		Graphics2D g2d = (Graphics2D)g;
		// Paint Tiles
		paintBackTiles(g2d);

		// Paint Player
		paintPlayer(g2d);
		
		// Paint characters
		paintCharacter(g2d);
	}
	
	/**
	 * Paints the backtiles
	 * @param g2d
	 */
	private void paintBackTiles(Graphics2D g2d){
		for(Tile tile : engine.getWorld().getCurrentMap().getBackTiles()){
			g2d.drawImage(tileImages.getImage(tile.getId()), tile.getX(), tile.getY(), this);
//			g2d.draw(tile.getBounds());
		}
	}
	
	/**
	 * Paints the player
	 * @param g2d
	 */
	private void paintPlayer(Graphics2D g2d){
		PlayerCharacter player = engine.getPlayer();
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
	 * @param g2d
	 */
	private void paintCharacter(Graphics2D g2d){
		ArrayList<Character> characters = engine.getCharacters();
		g2d.setColor(Color.RED);
		for(Character character : characters){
			
			// get current image
			Image img = characterImages.getImage(character.getDirection(), (character.getDx() != 0 || character.getDy() != 0), character.getName());
			
			// calc where to draw image
			int x = character.getX() - (img.getWidth(this)/4) - 2;
			int y = character.getY() - (img.getHeight(this)/4) + 2;
			
			g2d.drawImage(img, x, y, this);		
		}
	}
}
