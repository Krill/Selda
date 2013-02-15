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
	private ArrayList<Character> characters;
	private ArrayList<Item> items;
	
	/**
	 * Contructor
	 * @param player
	 * @param blockTiles
	 */
	public Collision(PlayerCharacter player, ArrayList<Tile> blockTiles,
			ArrayList<Character> characters){
		
		this.player = player;
		this.blockTiles = blockTiles;
		this.characters = characters;
}
	
	/**
	 * Updates active BlockTiles
	 * @param blockTiles
	 */
	public void setCurrentTiles(ArrayList<Tile> blockTiles){
		this.blockTiles = blockTiles;
	}
	
	/**
	 * Updates active characters
	 * @param enemies
	 */
	public void setCurrentCharacters(ArrayList<EnemyCharacter> enemies){
		this.characters = characters;
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
		checkCharacterTileCollision();	// Checks if enemies collides with <BlockTile>.
		checkInteractCollision();    	// Checks if <PlayerCharacter> enters <EnemyCharacter> sense areas.
		checkCharacterCollision();
		//checkItemCollision();
		//checkProjectileCollision();
	}

	
	/**
	 * Vidrig metod för character collision, temporär
	 */
	
	public void checkCharacterCollision(){
		for(EnemyCharacter enemy : enemies){
			
			Rectangle eBlock = enemy.getBounds();
			
			for(CivilianCharacter c : civilians){
				Rectangle cBlock = c.getBounds();

				if(eBlock.intersects(cBlock)){
					if(enemy.isUp()){
						enemy.setY(enemy.getY()+1);
					}

					if(enemy.isLeft()){
						enemy.setX(enemy.getX()+1);
					}

					if(enemy.isRight()){
						enemy.setX(enemy.getX()-1);
					}

					if(enemy.isDown()){
						enemy.setY(enemy.getY()-1);
					}
				}
			}
			for(ShopCharacter s : shops){
				Rectangle sBlock = s.getBounds();

				if(eBlock.intersects(sBlock)){
					if(enemy.isUp()){
						enemy.setY(enemy.getY()+1);
					}

					if(enemy.isLeft()){
						enemy.setX(enemy.getX()+1);
					}

					if(enemy.isRight()){
						enemy.setX(enemy.getX()-1);
					}

					if(enemy.isDown()){
						enemy.setY(enemy.getY()-1);
					}
				}
			}
			
			Rectangle pBlock = player.getBounds();
			
			if(eBlock.intersects(pBlock)){
				if(enemy.isUp()){
					enemy.setY(enemy.getY()+1);
					player.setY(player.getY()-1);
				}

				if(enemy.isLeft()){
					enemy.setX(enemy.getX()+1);
					player.setX(player.getX()-1);
				}

				if(enemy.isRight()){
					enemy.setX(enemy.getX()-1);
					player.setX(player.getX()+1);
				}

				if(enemy.isDown()){
					enemy.setY(enemy.getY()-1);
					player.setY(player.getY()+1);
				}
			}
		}
	}
	
	/**
	 * Checks for  player tile collision
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
		for(Character character : characters){

			Ellipse2D.Double area = character.getArea(); // Get circular shop area for the shop

			if(area.intersects(player.getBounds()) ){
				character.interact(player);
			}
		}
	}
}
