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
	 * @param id
	 * @param x
	 * @param y
	 * @param width
	 * @param height
	 * @param toMap
	 * @param toTileId
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
	 * @return toMap
	 */
	public int getToMap(){
		return toMap;
	}
	
	/**
	 * Returns the id on the tile this "door" is connected to
	 * @return toTileId
	 */
	public int getToTileId(){
		return toTileId;
	}
	
	/**
	 * Returns the id on this "door"
	 * @return fromDoorId
	 */
	public int getFromDoorId(){
		return fromDoorId;
	}
	
	/**
	 * Sets if this door is active
	 * @param active
	 */
	public void setActive(boolean active){
		this.active = active;
	}
	
	/**
	 * Returns true if this door is activated
	 * @return active
	 */
	public boolean isActive(){
		return active;
	}
	
	/**
	 * Creates a thread that keeps track of how long this door is inactive
	 * @param time
	 */
	public void setInactive(final long time){
		
		// Set door to inactive
		setActive(false);
		
		Thread delay = new Thread(){
			public void run(){
				try{
					try {Thread.sleep(time);} catch (InterruptedException e) {e.printStackTrace();}
					
					// When delay is done, set door to active again
					setActive(true);
					
				} catch (Exception e){
					System.out.println("An error has occured during setInactive()");
					e.printStackTrace();
				}
			}
		};
		delay.start();
	}
}
