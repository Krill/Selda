package Engine;

import java.awt.Rectangle;
import java.util.ArrayList;
import java.awt.geom.Ellipse2D;

import Character.PlayerCharacter;
import World.BlockTile;
import World.Tile;
import Character.EnemyCharacter;

/**
 * Handles all collision in the game
 * 
 * @author Kristoffer och Richard
 * @version 0.2
 */
public class Collision {
	
	// fields:
	private PlayerCharacter player;
	private ArrayList<Tile> tiles;
	private ArrayList<EnemyCharacter> enemies;
	
	/**
	 * Contructor
	 * @param player
	 * @param tiles
	 */
	public Collision(PlayerCharacter player, ArrayList<Tile> tiles, ArrayList<EnemyCharacter> enemies){
		this.player = player;
		this.tiles = tiles;
		this.enemies = enemies;
	}
	
	/**
	 * Updates active BlockTiles
	 * @param tiles
	 */
	public void setCurrentTiles(ArrayList<Tile> tiles){
		this.tiles = tiles;
	}
	
	/**
	 * Updates active enemies
	 * @param enemies
	 */
	  	public void setCurrentEnemies(ArrayList<EnemyCharacter> enemies){
		this.enemies = enemies;
	}
	
	/**
	 * Checks for new collisions
	 */
	public void update(){
		checkPlayerTileCollision();		// Checks if <PlayerCharacter> collides with <BlockTile>.
		//checkEnemyTileCollision();	// Checks if enemies collides with <BlockTile>.
		//checkSenseCollisions();    	// Checks if <PlayerCharacter> enters <EnemyCharacter> sense areas.
	}
	
	/**
	 * Checks for  player tile collision
	 */
	private void checkPlayerTileCollision(){
		// For every block tile on the map
		for(Tile t : tiles){
			
			// create bounds for the blockTile
			Rectangle block = t.getBounds();
			
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
	 * Checks for  enemy tile collision
	 */
	private void checkEnemyTileCollision(){
		// For every block tile on the map
		for(Tile t : tiles){
			// And every character on the map
			for(EnemyCharacter e : enemies){
				// create bounds for the blockTile
				Rectangle block = t.getBounds();
			
				if(e.getBounds().intersects(block)){

					// while going up
					if(e.isUp()){
						e.setY(e.getY()+1);
					}

					// while going left
					if(e.isLeft()){
						e.setX(e.getX()+1);
					}

					// while going right
					if(e.isRight()){
						e.setX(e.getX()-1);
					}

					// while going down
					if(e.isDown()){
						e.setY(e.getY()-1);
					}
				}
			}
		}
	}
	
	/*
	 * Checks if player has intersected with any sensor areas
	 *
	private void checkSenseCollisions(){
		for(EnemyCharacter e : enemies){
			// Get circular sense area for the enemy
			Ellipse2D.Double circle = e.getSenseArea();
			
			if(circle.intersects(player.getBounds()) ){
				// When player enters the area, do something here.
				// ex:  e.detectedPlayer();
			}
			
		}
	}*/
}
