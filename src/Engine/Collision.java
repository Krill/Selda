package Engine;

import java.awt.Rectangle;
import java.util.ArrayList;
import java.awt.geom.Ellipse2D;

import Character.PlayerCharacter;
import World.BlockTile;
import World.Map;
import World.Tile;
import Character.EnemyCharacter;
import Character.ShopCharacter;
import Character.CivilianCharacter;
import Character.Character;
import Item.Item;

/**
 * Handles all collision in the game
 * 
 * @author Kristoffer och Richard
 * @version 0.2
 */
public class Collision {
	
	// fields:
	private PlayerCharacter player;
	private ArrayList<Tile> blockTiles;
	private ArrayList<Character> characters;     // Not including player
	private ArrayList<Character> allCharacters;  // Including player
	private ArrayList<Item> items;			
	
	/**
	 * Contructor
	 * @param player
	 * @param blockTiles
	 */
	public Collision(PlayerCharacter player, ArrayList<Tile> blockTiles, ArrayList<Character> characters){
		this.player = player;
		this.blockTiles = blockTiles;
		this.characters = characters;
		
		//this.allCharacters = characters;
		//allCharacters.add(player);
	}
	
	/**
	 * Updates active BlockTiles
	 * @param blockTiles
	 */
	public void setCurrentTiles(ArrayList<Tile> blockTiles){this.blockTiles = blockTiles;}
	
	/**
	 * Updates active characters
	 * @param enemies
	 */
	public void setCurrentCharacters(ArrayList<Character> characters){this.characters = characters;}

	/**
	 * Updates active items
	 * @param items
	 */
	public void setCurrentItems(ArrayList<Item> items){this.items = items;}	

	/**
	 * Checks for new collisions
	 */
	public void update(){
		checkPlayerTileCollision();		// Checks if <PlayerCharacter> collides with <BlockTile>.
		checkCharacterTileCollision();	// Checks if all other <Character> collides with <BlockTile>.
		checkCharacterCollision();		// Checks if <Characters> collides with <Characters>
		checkSenseCollision();			// Checks if <Player> enters (EnemyCharacters) <Character> sense areas
		
		//checkItemCollision();			// Checks if <PlayerCharacter> enters <Item> bounds.
		//checkProjectileCollision();	// Checks if <Projectile> hits <Characters> or <Blocktiles>
		//checkInteractCollision();    	// Checks if <PlayerCharacter> enters a <Character> area.
	}

	
	public void checkCharacterCollision(){
		// Något kul
	}
	
	public void checkPlayerTileCollision(){
		// For every block tile on the map
		for(Tile blockTile : blockTiles){

			// create bounds for the blockTile
			Rectangle block = blockTile.getBounds();

			if(player.getBounds().intersects(block)){

				// while going up
				if(player.isUp()){
					player.setY(player.getY()+1);
				}

				// while going left
				if(player.isLeft()){
					player.setX(player.getX()+1);
				}

				// while going right
				if(player.isRight()){
					player.setX(player.getX()-1);
				}

				// while going down
				if(player.isDown()){
					player.setY(player.getY()-1);
				}
			}
		}
	}
	
	public void checkSingleCharacterTileCollision(Character c){
		// For every block tile on the map
		for(Tile blockTile : blockTiles){

			// create bounds for the blockTile
			Rectangle block = blockTile.getBounds();

			if(c.getBounds().intersects(block)){

				// while going up
				if(c.isUp()){
					c.setY(c.getY()+1);
				}

				// while going left
				if(c.isLeft()){
					c.setX(c.getX()+1);
				}

				// while going right
				if(c.isRight()){
					c.setX(c.getX()-1);
				}

				// while going down
				if(c.isDown()){
					c.setY(c.getY()-1);
				}
			}
		}
	}

	
	/**
	 * Checks for all character tile collision
	 */
	public void checkCharacterTileCollision(){
		// For every block tile on the map
		for(Tile blockTile : blockTiles){

			// create bounds for the blockTile
			Rectangle block = blockTile.getBounds();

			for(Character c : characters){

				if(c.getBounds().intersects(block)){

					// while going up
					if(c.isUp()){
						c.setY(c.getY()+1);
					}

					// while going left
					if(c.isLeft()){
						c.setX(c.getX()+1);
					}

					// while going right
					if(c.isRight()){
						c.setX(c.getX()-1);
					}

					// while going down
					if(c.isDown()){
						c.setY(c.getY()-1);
					}
				}
			}
		}
	}
	
	/**
	 * Checks if a player is inside a ShopCharacters shopArea, if true, interact() is invoked.
	 */
	public void checkAttackCollision(Character c){
		Ellipse2D.Double attackArea = null;
		int weaponRange = 10;
		int weaponPower = 10;
		
		if(c.getDirection() == "up"){
			attackArea = new Ellipse2D.Double(
					player.getX() - player.getWidth()/4 , // X-cord
					player.getY() - player.getWidth() ,  // Y-cord
					player.getWidth()+weaponRange, 	// Width
					player.getHeight()+weaponRange);	// Height
			
		}else if(c.getDirection() == "down"){
			attackArea = new Ellipse2D.Double(
					player.getX() - player.getWidth()/4 , // X-cord
					player.getY() + player.getWidth()/2 ,  // Y-cord
					player.getWidth()+weaponRange, 	// Width
					player.getHeight()+weaponRange/2);	// Height
		}else if(c.getDirection() == "left"){
			attackArea = new Ellipse2D.Double(
					player.getX() - player.getWidth()/2 - weaponRange , // X-cord
					player.getY(),  // Y-cord
					player.getWidth()+weaponRange, 	// Width
					player.getHeight());	// Height
		}else if(c.getDirection() == "right"){
			attackArea = new Ellipse2D.Double(
					player.getX() + player.getWidth()/2 , // X-cord
					player.getY(),  // Y-cord
					player.getWidth()+weaponRange, 	// Width
					player.getHeight());	// Height
		}else{ System.out.println("Error: No direction when attacking"); }
		
		for(Character target : characters){
			
			if(attackArea.intersects(target.getBounds()) && target.isAttackable() ){
				target.setHealth( target.getHealth()-weaponPower );
				pushCharacter(target,c.getDirection(), weaponPower);
				System.out.println("Enemy health: " + target.getHealth() );
			}
		}
	}
	
	/**
	 * Checks if a player is inside a ShopCharacters shopArea, if true, interact() is invoked.
	 */
	public void checkItemCollision(){
		for(Item item : items){
			if(item.getBounds().intersects(player.getBounds()) ){
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

			Ellipse2D.Double area = character.getArea(); // Get circular shop area for the shop

			if(area.intersects(player.getBounds()) ){
				character.interact(player);
			}
		}
	}
	
	/**
	 * Checks if a player is inside a interact Area, if true, interact() is called.
	 */
	public void checkSenseCollision(){
		for(Character character : characters){
			if(character instanceof EnemyCharacter){
				
				EnemyCharacter enemy = (EnemyCharacter) character;
				
				Ellipse2D.Double area = enemy.getArea();
				
				if(area.intersects(player.getBounds()) ){
					enemy.moveToPlayer(player);
				}
			}
		}
	}
	
	public void pushCharacter(Character c, String direction, int pixels){
		
		c.resetDirection();
		
		for(int i = 0; i < pixels ; i++){
			
			
			if(direction == "up"){
				c.setY(c.getY()-1);
				c.setUp(true);
			}
			if(direction == "down"){ 
				c.setY(c.getY()+1);
				c.setDown(true);
			}
			if(direction == "left"){
				c.setX(c.getX()-1);
				c.setLeft(true);
			}
			if(direction == "right"){
				c.setX(c.getX()+1);
				c.setRight(true);
			}
			checkSingleCharacterTileCollision(c);
			
		}
		
		c.resetDirection();
	}
}
