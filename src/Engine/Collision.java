package Engine;

import java.awt.Rectangle;
import java.util.ArrayList;
import java.awt.geom.Ellipse2D;

import Character.PlayerCharacter;
import World.BlockTile;
import World.Tile;
import Character.EnemyCharacter;
import Character.ShopCharacter;
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
	private ArrayList<EnemyCharacter> enemies;
	private ArrayList<ShopCharacter> shops;
	private ArrayList<Item> items;
	
	/**
	 * Contructor
	 * @param player
	 * @param blockTiles
	 */
	public Collision(PlayerCharacter player, ArrayList<Tile> blockTiles,
						ArrayList<EnemyCharacter> enemies, ArrayList<ShopCharacter> shops){
		this.player = player;
		this.blockTiles = blockTiles;
		this.enemies = enemies;
		this.shops = shops;
	}
	
	/**
	 * Updates active BlockTiles
	 * @param blockTiles
	 */
	public void setCurrentTiles(ArrayList<Tile> blockTiles){
		this.blockTiles = blockTiles;
	}
	
	/**
	 * Updates active enemies
	 * @param enemies
	 */
	public void setCurrentEnemies(ArrayList<EnemyCharacter> enemies){
		this.enemies = enemies;
	}
	  	
	/**
	 * Updates active shop
	 * @param shops
	 */
	public void setCurrentShop(ArrayList<ShopCharacter> shops){
		this.shops = shops;
	}	
	
	/**
	 * Updates active items
	 * @param items
	 */
	public void setCurrentItems(ArrayList<Item> items){
		this.items = items;
	}	

	/**
	 * Checks for new collisions
	 */
	public void update(){
		checkPlayerTileCollision();		// Checks if <PlayerCharacter> collides with <BlockTile>.
		checkEnemyTileCollision();	// Checks if enemies collides with <BlockTile>.
		checkSenseCollisions();    	// Checks if <PlayerCharacter> enters <EnemyCharacter> sense areas.
		//checkItemCollision();
		//checkProjectileCollision();
	}
	
	/**
	 * Checks for  player tile collision
	 */
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
	
	
	/**
	 * Checks for enemy tile collision
	 */
	public void checkEnemyTileCollision(){
		// For every block tile on the map
		for(Tile blockTile : blockTiles){
			// And every character on the map
			for(EnemyCharacter e : enemies){
				// create bounds for the blockTile
				Rectangle block = blockTile.getBounds();
			
				if(e.getBounds().intersects(block)){

					if(e.isUp()){  // while going up
						e.setY(e.getY()+1);
					}

					if(e.isLeft()){ // while going left
						e.setX(e.getX()+1);
					}

					if(e.isRight()){ // while going right
						e.setX(e.getX()-1);
					}

					if(e.isDown()){ // while going down
						e.setY(e.getY()-1);
					}
				}
			}
		}
	}
	
	/**
	 * Checks if a player is inside a ShopCharacters shopArea, if true, interact() is invoked.
	 */
	public void checkAttackCollision(){
		System.out.println("checkAttackCollision() is called");
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
	 * Checks if a player is inside a ShopCharacters shopArea, if true, interact() is invoked.
	 */
	public void checkInteractCollision(){
		for(ShopCharacter shopCharacter : shops){
			Ellipse2D.Double shopArea = shopCharacter.getShopArea(); // Get circular shop area for the shop
			
			if(shopArea.intersects(player.getBounds()) ){
				shopCharacter.interact();
			}
		}
	}
	
	/**
	 * Checks if player has intersected with any Enemy sensor area, if true, interact() is invoked.
	 */
	public void checkSenseCollisions(){
		for(EnemyCharacter enemy : enemies){
			Ellipse2D.Double senseArea = enemy.getSenseArea(); // Get circular sense area for the enemy
			
			if(senseArea.intersects(player.getBounds()) ){
				//enemy.interact();
			}
		}
	}
}
