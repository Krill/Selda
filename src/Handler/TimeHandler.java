package Handler;

import java.io.Serializable;

/**
 * 
 * @author alexander
 */
public class TimeHandler implements Serializable{
	
	// fields:
	private static final long serialVersionUID = 7L;
	
	/**
	 * Constructor
	 */
	public TimeHandler(){
		
	}
	
	/**
	 * Returns true if timeDiff in milliseconds has passed
	 * 
	 * @param timeStamp
	 * @param timeDiff
	 * @return
	 */
	public static boolean timePassed(long timeStamp, long timeDiff){
	
		boolean hasPassed = false;
		
		long currentTime = System.currentTimeMillis();
		
		if( (currentTime - timeStamp) > timeDiff){
			hasPassed = true;
		}
		
		return(hasPassed);
	}
	
	/**
	 * Returns the current time
	 * @return time
	 */
	public static long getTime(){
		return( System.currentTimeMillis() );
	}
}
