package GUI;

/**
 * GameView
 * @author kristoffer & johan
 */
public class GameView implements Runnable{

	/**
	 * Constructor
	 */
	public GameView(){
		// Shit
	}
	
	@Override
	public void run() {
		while(true){
			
			// Here goes the things that should be updated constantly...
			
			try {Thread.sleep(10);} catch (InterruptedException e) {e.printStackTrace();}
		}	
	}
}
