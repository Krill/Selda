package Statistics;

import java.io.Serializable;
import java.util.Observable;
import java.util.Observer;


/**
 * Class responsible for keeping statistics for a player.
 * It saves the number of quests completed and monsters killed by the player.
 * Also it will calculate the worth of these two combined, into a common score.
 * @author Johan
 *
 */
public class Statistics implements Serializable{
	private int monsterKilled;
	private int questsCompleted;
	
	/**
	 * Creates a clean statistics with 0 in all statistics.
	 */
	public Statistics()
	{
		monsterKilled = 0;
		questsCompleted = 0;
	}
	
	public int getTotalScore()
	{
		return monsterKilled + (questsCompleted * 5);
	}
	
	
	/**
	 * Increases the number of monsters killed.
	 */
	public void incMonstersKilled()
	{
		monsterKilled++;
	}
	
	
	/**
	 * Increases the number of quests completed.
	 */
	public void incQuestsCompleted()
	{
		questsCompleted++;
	}
	
	
	/**
	 * Returns the number of monsters killed.
	 * @return Monsters killed
	 */
	public int getMonstersKilled()
	{
		return monsterKilled;
	}
	
	
	/**
	 * Returns the number of quests completed.
	 * @return Quests completed
	 */
	public int getQuestsCompleted()
	{
		return questsCompleted;
	}
	
}
