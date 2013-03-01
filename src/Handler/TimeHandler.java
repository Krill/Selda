package Handler;


/**
 * Contains static methods related to time management. Is able to return the current system time,
 * as well as determine if a certain time window has passed based on a specified time.
 * 
 * @author Richard Norling & Alexander Persson
 * @version 2013-03-01
 */
public class TimeHandler{
	
	/**
	 * Returns true if timeDiff in milliseconds has passed
	 * 
	 * @param timeStamp The time the action was initiated.
	 * @param timeDiff The time the action should last.
	 * @return Boolean showing if the specified time has passed.
	 */
	public static boolean timePassed(long timeStamp, long timeDiff){
	
		boolean hasPassed = false;
		
		long currentTime = TimeHandler.getTime();
		
		if( (currentTime - timeStamp) > timeDiff){
			hasPassed = true;
		}
		
		return(hasPassed);
	}
	
	/**
	 * Returns the current system time.
	 * @return Current system time.
	 */
	public static long getTime(){
		return System.currentTimeMillis();
	}
}
