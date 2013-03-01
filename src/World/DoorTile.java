package World;

/**
 *  This class keeps track of which doors will be active when the respective map is used,
 * leaving the rest up to its superclass, Tile.
 * DoorTile
 * @author kristoffer
 */
@SuppressWarnings("serial")
public class DoorTile extends Tile{

	// fields:
	private int toMap;
	private int toTileId;
	private int fromDoorId;
	private boolean active;
	
	/**
	 * 
	 * @param id The tiles ID
	 * @param x The X coord of the tile
	 * @param y The Y coord of the tile
	 * @param width The width of the tile
	 * @param height The height of the tile
	 * @param toMap The map to go to
	 * @param toTileId The tileID to go to on the next map
	 * @param fromDoorId The tileID to go from
	 */
	public DoorTile(int id, int x, int y, int width, int height, int toMap, int toTileId, int fromDoorId) {
		super(id, x, y, width, height);
	
		this.toMap = toMap;
		this.toTileId = toTileId;
		this.fromDoorId = fromDoorId;
		this.active = true;
	}
	
	/**
	 * Returns the map this "door" is connected to
	 * @return toMap The map its connected to
	 */
	public int getToMap(){
		return toMap;
	}
	
	/**
	 * Returns the id on the tile this "door" is connected to
	 * @return toTileId The tile ID of the door
	 */
	public int getToTileId(){
		return toTileId;
	}
	
	/**
	 * Returns the id on this "door"
	 * @return fromDoorId The door ID
	 */
	public int getFromDoorId(){
		return fromDoorId;
	}
	
	/**
	 * Sets if this door is active
	 * @param active true to activate, false otherwise.
	 */
	public void setActive(boolean active){
		this.active = active;
	}
	
	/**
	 * Returns true if this door is activated
	 * @return active true to active, false otherwise.
	 */
	public boolean isActive(){
		return active;
	}
	
	/**
	 * Creates a thread that keeps track of how long this door is inactive
	 * @param time the delaytime for the door
	 */
	public void setInactive(final long time){
		
		try{
			// Set door to inactive
			setActive(false);

			Thread delay = new Thread(){
				public void run(){
					try {Thread.sleep(time);} catch (InterruptedException e) {e.printStackTrace();}

					// When delay is done, set door to active again
					setActive(true);

				}
			};
			delay.start();
		} catch (Exception e){
			System.out.println("An error has occured during setInactive()");
			e.printStackTrace();
		}
	}
}
