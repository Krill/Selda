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

	public static boolean timePassed(float timeStamp, float timeDiff){
	
		boolean hasPassed = false;
		
		float currentTime = System.currentTimeMillis();
		
		if( (currentTime - timeStamp) > timeDiff){
			hasPassed = true;
		}
		
		return(hasPassed);
	}
}
