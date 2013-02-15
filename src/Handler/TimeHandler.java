package Handler;

public class TimeHandler {
	
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
	
	public static long getTime(){
		return( System.currentTimeMillis() );
	}
}
