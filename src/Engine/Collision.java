package Engine;

import java.awt.Rectangle;
import java.util.ArrayList;
import java.awt.geom.Ellipse2D;
import java.io.Serializable;

import Character.PlayerCharacter;
import World.Tile;
import Character.EnemyCharacter;
import Character.Character;
import Item.Item;

/**
 * This handles all sort of collision in the game, Character->Tile, Character->Character and more
 * It contains methods that is constantly called and methods
 * that is only invoked when with certain events occur (such as attacks).
 * 
 * @author Richard Norling
 * @version 2013-02-28
 */
public class Collision implements Serializable{

	// fields:
	private static final long serialVersionUID = 8L;
	private PlayerCharacter player;
	private ArrayList<Tile> blockTiles;
	private ArrayList<Character> characters;     // CharacterList, not including player
	private ArrayList<Item> items;

	/**
	 * Constructor
	 * @param player
	 * @param blockTiles
	 * @param characters
	 */
	public Collision(PlayerCharacter player, ArrayList<Tile> blockTiles, ArrayList<Character> characters){
		this.player = player;
		this.blockTiles = blockTiles;
		this.characters = characters;
	}

	/**
	 * Updates active BlockTiles
	 * @param blockTiles
	 */
	public void setCurrentTiles(ArrayList<Tile> blockTiles){this.blockTiles = blockTiles;}

	/**
	 * Updates active characters
	 * @param characters
	 */
	public void setCurrentCharacters(ArrayList<Character> characters){this.characters = characters;}

	/**
	 * Updates active items
	 * @param items
	 */
	public void setCurrentItems(ArrayList<Item> items){this.items = items;}	

	/**
	 * Checks for new collisions, updates constantly
	 */
	public void update(){
		// Player
		checkPlayerTileCollision();		// Checks if <PlayerCharacter> collides with <BlockTile>.
		checkPlayerCharacterCollision();
		// Non-Player Characters
		checkBorderCollision();
		checkEnemyAttackCollision();
		checkCharacterTileCollision();	// Checks if all other <Character> collides with <BlockTile>.
		checkCharacterCollision();		// Checks if <Characters> collides with <Characters>
		// Misc
		checkSenseCollision();			// Checks if <Player> enters (EnemyCharacters) <Character> sense areas

		//checkItemCollision();			// Checks if <PlayerCharacter> enters <Item> bounds.
		//checkProjectileCollision();	// Checks if <Projectile> hits <Characters> or <Blocktiles>
		//checkInteractCollision();    	// Checks if <PlayerCharacter> enters a <Character> area.
	}

	/**
	 * Checks for NPC to Map-Border collisions
	 */
	public void checkBorderCollision(){
		for(Character c : characters){
			if(c.getX() > 800-c.getWidth() || c.getX() < 0+c.getWidth()){
				moveBack(c);
			}
			if(c.getY() > 640-c.getHeight() || c.getY() < 0+c.getHeight() ){
				moveBack(c);
			}
		}
	}
	
	
	/**
	 * Checks for Player to Character collision
	 */
	public void checkPlayerCharacterCollision(){
		for(Character c : characters){
			if(player.getBounds().intersects(c.getBounds())){
				moveBack(player);
			}
		}
	}

	/**
	 * Checks for Player to BlockTile collision
	 */
	public void checkPlayerTileCollision(){
		//for(Tile blockTile : blockTiles){		
			player.setY(player.getY()-player.getDy());		// move from collision
			player.setX(player.getX()-player.getDx());		// move from collision

			player.setY(player.getY()+player.getDy());
			for(Tile t1 : blockTiles){
				if(player.getBounds().intersects(t1.getBounds())){
					player.setY(player.getY()-player.getDy());
				}
			}

			player.setX(player.getX()+player.getDx());
			for(Tile t2 : blockTiles){
				if(player.getBounds().intersects(t2.getBounds())){
					player.setX(player.getX()-player.getDx());
				}
			}
		//}
	}

	/**
	 * Checks for all Character to Character collision ( NOT PLAYER )
	 * Uses a overridden equals() in Character.
	 */
	public void checkCharacterCollision(){
		for(Character c1 : characters){
			for(Character c2 : characters){
				if( !c1.equals(c2) ){
					if(c1.getBounds().intersects(c2.getBounds())){
						moveBack(c1);
					}
				}
			}
		}
	}


	/**
	 * Checks for a single Character to Tile collision
	 * shouldn't be updated constantly.
	 * @param character
	 */
	public void checkSingleCharacterTileCollision(Character character){
		for(Tile blockTile : blockTiles){
			Rectangle block = blockTile.getBounds();
			if(character.getBounds().intersects(block)){
				moveBack(character);
			}
		}
	}


	/**
	 * Checks for all character tile collision ( NON PLAYER )
	 */
	public void checkCharacterTileCollision(){
		for(Tile blockTile : blockTiles){
			Rectangle block = blockTile.getBounds();
			for(Character c : characters){
				if(c.getBounds().intersects(block)){
					moveBack(c);
				}
			}
		}
	}

	/**
	 * Checks if a EnemyCharacter is inside a Player attack area, invoked when Player attacks.
	 * @param c
	 */
	public void checkAttackCollision(Character c){
		Ellipse2D.Double attackArea = null;

		if(c.getDirection() == "up"){
			attackArea = new Ellipse2D.Double(
					c.getX(),
					c.getY() - c.getWidth()/2, 
					c.getWidth(), 
					c.getHeight());
		}else if(c.getDirection() == "down"){
			attackArea = new Ellipse2D.Double(
					c.getX(),
					c.getY() + c.getWidth()/2 ,
					c.getWidth(), 
					c.getHeight());
		}else if(c.getDirection() == "left"){
			attackArea = new Ellipse2D.Double(
					c.getX() - c.getWidth()/2,
					c.getY(),  
					c.getWidth(),
					c.getHeight());	
		}else{
			attackArea = new Ellipse2D.Double(
					c.getX() + c.getWidth()/2 , 
					c.getY(),  
					c.getWidth(), 
					c.getHeight());
		}		

		for(Character target : characters){

			if(attackArea.intersects(target.getBounds()) && target.isAttackable() ){
				target.setHealth( target.getHealth()-player.getDamage());
				pushCharacter(player,target, 5);
				System.out.println("Target health: " + target.getHealth() );
			}
		}
	}

	/**
	 * Checks if a Player is inside a EnemyCharacter attack area, invoked when EnemyCharacter attacks.
	 */
	
	public void checkEnemyAttackCollision(){
		for(Character c1 : characters){
			if(c1 instanceof EnemyCharacter){
				if(c1.isAttacking()){
					Ellipse2D.Double attackArea = null;

					if(c1.getDirection() == "up"){
						attackArea = new Ellipse2D.Double(
								c1.getX(),
								c1.getY() - c1.getWidth()/2, 
								c1.getWidth(), 
								c1.getHeight());
					}else if(c1.getDirection() == "down"){
						attackArea = new Ellipse2D.Double(
								c1.getX(),
								c1.getY() + c1.getWidth()/2 ,
								c1.getWidth(), 
								c1.getHeight());
					}else if(c1.getDirection() == "left"){
						attackArea = new Ellipse2D.Double(
								c1.getX() - c1.getWidth()/2,
								c1.getY(),  
								c1.getWidth(),
								c1.getHeight());	
					}else
					{
						attackArea = new Ellipse2D.Double(
								c1.getX() + c1.getWidth()/2 , 
								c1.getY(),  
								c1.getWidth(), 
								c1.getHeight());
					}
					

					if(attackArea.intersects(player.getBounds())){
						// Hit!! play a enemy/player hurt sound
						player.setHealth(player.getHealth()-5+player.getArmorRating());
						pushCharacter(c1,player, 5);
						System.out.println("Target health: " + player.getHealth() );

						c1.setAttacking(false);
					}else{
						// Missed! play only sword swing or whatever
					}
				}
			}
		}
	}

	/**
	 * Checks if a Player is colliding with a Item
	 */
	public void checkItemCollision(){
		for(Item item : items){
			if(item.getBounds().intersects(player.getBounds()) ){
				// Play a pickup sound
				player.pickUpItem(item);
				System.out.println("Item picked up.");
			}
		}
	}

	/**
	 * Checks if a player is inside a interact Area, if true, interact() is called.
	 */
	public void checkInteractCollision(){
		for(Character character : characters){

			Ellipse2D.Double area = character.getArea(); // Get circular interact area

			if(area.intersects(player.getBounds()) ){
				character.interact(player);
				// Play a interact sound
			}
		}
	}

	/**
	 * Checks if a Player is inside a EnemyCharacter Sense Area, if true, EnemyPlayer detects Player
	 */
	public void checkSenseCollision(){
		for(Character character : characters){
			if(character instanceof EnemyCharacter){

				EnemyCharacter enemy = (EnemyCharacter) character;

				Ellipse2D.Double area = enemy.getArea();

				if(area.intersects(player.getBounds()) ){
					if(!enemy.hasDetectedPlayer()){           // If the enemy has not seen the player before
						enemy.setDetectedPlayer(true,player); // Then detect player
						                                      // And play a detected sound
					}
				}else{
					enemy.setDetectedPlayer(false,player);	  // If player is not inside sense area, Enemy don't detect Player
				}
			}
		}
	}

	/**
	 * Pushes a character a certain amount of pixels in a certain direction
	 * depending on the attackers direction.
	 * @param pusher
	 * @param target
	 * @param pixels
	 */
	public void pushCharacter(Character pusher, Character target, int pixels){
		
		String pushDirection = pusher.getDirection();
		
		for(int i = 0; i < pixels ; i++){
			
			String beforeDirection = target.getDirection();
			
			if(pushDirection == "up"){
				target.setY(target.getY()-1);
				target.setDirection("up");
				
				for(Tile blockTile : blockTiles){
					Rectangle block = blockTile.getBounds();
					if(target.getBounds().intersects(block)){
						target.setY(target.getY()+1);
					}
				}
			}
			if(pushDirection == "down"){ 
				target.setY(target.getY()+1);
				target.setDirection("down");

				for(Tile blockTile : blockTiles){
					Rectangle block = blockTile.getBounds();
					if(target.getBounds().intersects(block)){
						target.setY(target.getY()-1);
					}
				}
			}
			if(pushDirection == "left"){
				target.setX(target.getX()-1);
				target.setDirection("left");
				
				for(Tile blockTile : blockTiles){
					Rectangle block = blockTile.getBounds();
					if(target.getBounds().intersects(block)){
						target.setX(target.getX()+1);
					}
				}
			}
			if(pushDirection == "right"){
				target.setX(target.getX()+1);
				target.setDirection("right");

				for(Tile blockTile : blockTiles){
					Rectangle block = blockTile.getBounds();
					if(target.getBounds().intersects(block)){
						target.setX(target.getX()-1);
					}
				}
			}
			target.setDirection(beforeDirection);
		}
	}

	/**
	 * Moves the character one pixel back from the way he is moving
	 * @param character
	 */
	public void moveBack(Character character){
		if(character.getDy() < 0){
			character.setY(character.getY()+1);
		}
		if(character.getDx() < 0){
			character.setX(character.getX()+1);
		}
		if(character.getDx() > 0){
			character.setX(character.getX()-1);
		}
		if(character.getDy() > 0){
			character.setY(character.getY()-1);
		}
	}
}
