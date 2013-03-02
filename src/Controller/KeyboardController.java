package Controller;

import java.awt.event.KeyEvent;
import java.awt.event.KeyAdapter;
import java.io.Serializable;
import Character.PlayerCharacter;
import Engine.Collision;

/**
 * Keeps track of which keys that the user is pressing and takes
 * action according to which keys that are pressed.
 * @author kristoffer
 */
public class KeyboardController extends KeyAdapter  implements Serializable{

	// fields:
	private static final long serialVersionUID = 32L;
	private PlayerCharacter player;
	private Collision collision;
	
	/**
	 * Constructor
	 * @param player The playercharacter
	 * @param collision The collision manager
	 */
	public KeyboardController(PlayerCharacter player, Collision collision){	
		this.player = player;
		this.collision = collision;
		System.out.println("KeyBoardController initialized!");
	}
	
	/**
	 * Gets called when a key is pressed
	 * @param key The key you have pressed
	 */
	public void keyPressed(KeyEvent key){
		
		switch (key.getKeyCode()){
		case KeyEvent.VK_UP:
			player.moveY(-1);
			break;
		case KeyEvent.VK_LEFT:
			player.moveX(-1);
			break;
		case KeyEvent.VK_RIGHT:
			player.moveX(1);
			break;
		case KeyEvent.VK_DOWN:
			player.moveY(1);
			break;
		case KeyEvent.VK_E:
			collision.checkInteractCollision();
			break;		
		case KeyEvent.VK_SPACE:
			player.primaryAttack();
			collision.checkAttackCollision(player);
			break;
		}
	}
	
	/**
	 * Gets called when a key is released
	 * @param key The key you have released
	 */
	public void keyReleased(KeyEvent key){
		
		switch (key.getKeyCode()){
		case KeyEvent.VK_UP:
			player.moveY(1);
			break;
		case KeyEvent.VK_LEFT:
			player.moveX(1);
			break;
		case KeyEvent.VK_RIGHT:
			player.moveX(-1);
			break;
		case KeyEvent.VK_DOWN:
			player.moveY(-1);
			break;
		case KeyEvent.VK_SPACE:
			System.out.println(player.getX() + ", " + player.getY());
			break;
		}
	}
}
