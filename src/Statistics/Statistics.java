package Statistics;

import java.util.Observable;
import java.util.Observer;

public class Statistics implements Observer{
	private int monsterKilled;
	private int questsCompleted;
	
	public Statistics()
	{
		monsterKilled = 0;
		questsCompleted = 0;
	}
	
	public void update(Observable o, Object arg)
	{
		
	}
	
}
