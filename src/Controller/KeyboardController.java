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
	
	/**
	 * Constructor
	 * @param player The playercharacter
	 * @param collision The collision manager
	 */
	public KeyboardController(PlayerCharacter player){	
		this.player = player;
	}
	
	/**
	 * Gets called when a key is pressed
	 * @param key The key you have pressed
	 */
	public void keyPressed(KeyEvent key){
		
		switch (key.getKeyCode()){
		case KeyEvent.VK_UP:
			player.setDy(-1);
			break;
		case KeyEvent.VK_LEFT:
			player.setDx(-1);
			break;
		case KeyEvent.VK_RIGHT:
			player.setDx(1);
			break;
		case KeyEvent.VK_DOWN:
			player.setDy(1);
			break;		
		case KeyEvent.VK_SPACE:
			player.primaryAttack();
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
			System.out.println("[CLIENT][KEYBOARD EVENT] UP");
			player.setDy(1);
			break;
		case KeyEvent.VK_LEFT:
			System.out.println("[CLIENT][KEYBOARD EVENT] LEFT");
			player.setDx(1);
			break;
		case KeyEvent.VK_RIGHT:
			System.out.println("[CLIENT][KEYBOARD EVENT] RIGHT");
			player.setDx(-1);
			break;
		case KeyEvent.VK_DOWN:
			System.out.println("[CLIENT][KEYBOARD EVENT] DOWN");
			player.setDy(-1);
			break;
		case KeyEvent.VK_SPACE:
			System.out.println(player.getX() + ", " + player.getY());
			break;
		}
	}
}
