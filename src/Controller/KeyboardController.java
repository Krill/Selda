package Controller;

import java.awt.event.KeyEvent;
import java.awt.event.KeyAdapter;
import java.io.Serializable;
import Character.PlayerCharacter;
import Engine.Collision;

/**
 * Handles the players keyboard events
 * @author kristoffer
 */
public class KeyboardController extends KeyAdapter  implements Serializable{

	// fields:
	private static final long serialVersionUID = 32L;
	private PlayerCharacter player;
	private Collision collision;
	
	/**
	 * Constructor
	 * @param player
	 * @param collision
	 */
	public KeyboardController(PlayerCharacter player, Collision collision){	
		this.player = player;
		this.collision = collision;
		System.out.println("KeyBoardController initialized!");
	}
	
	/**
	 * Gets called when a key is pressed
	 */
	public void keyPressed(KeyEvent key){
		
		switch (key.getKeyCode()){
		case KeyEvent.VK_UP:
			player.setUp(true);
			break;
		case KeyEvent.VK_LEFT:
			player.setLeft(true);
			break;
		case KeyEvent.VK_RIGHT:
			player.setRight(true);
			break;
		case KeyEvent.VK_DOWN:
			player.setDown(true);
			break;
		case KeyEvent.VK_E:
			collision.checkInteractCollision();
			break;
		case KeyEvent.VK_I:
			player.showInventory();
			break;			
		case KeyEvent.VK_SPACE:
			player.primaryAttack();
			collision.checkAttackCollision(player);
			break;
		}
	}
	
	/**
	 * Gets called when a key is released
	 */
	public void keyReleased(KeyEvent key){
		
		switch (key.getKeyCode()){
		case KeyEvent.VK_UP:
			player.setUp(false);
			break;
		case KeyEvent.VK_LEFT:
			player.setLeft(false);
			break;
		case KeyEvent.VK_RIGHT:
			player.setRight(false);
			break;
		case KeyEvent.VK_DOWN:
			player.setDown(false);
			break;
		case KeyEvent.VK_SPACE:
			System.out.println(player.getX() + ", " + player.getY());
			break;
		}
	}
}
