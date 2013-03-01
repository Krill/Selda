package Quest;

import java.io.Serializable;

import Character.PlayerCharacter;

/**
 * A abstract class providing basic quest methods and variables. 
 * It will keep track of how many parts of the quest is done, if its started and whether the reward has been recieved.
 * @author Johan
 * @version 2013-02-18
 */
public abstract class Quest implements Serializable{


	//Fields
	private static final long serialVersionUID = 9L;
	private int id;
	private boolean started;
	private int numberToDo;
	private int numberDone;
	private int reward;
	private boolean rewardRecieved;
	private boolean completed;
	private String message;
	
	
	/**
	 * Initiates the quest.
	 * @param id The id of the quest
	 * @param started A boolean saying if it's started or not.
	 * @param numberToDo How many part to complete?
	 * @param reward The cash reward for when completed.
	 * @param message A message for displaying quest info.
	 */
	public Quest(int id, boolean started, int numberToDo, int reward, String message)
	{
		this.id = id;
		this.started = started;
		this.numberToDo = numberToDo;
		numberDone = 0;
		this.reward = reward;
		this.message = message;
		completed = false;
		rewardRecieved = false;
	}
	
	
	//Methods
	/**
	 * Retuns the quests message.
	 * @return Message The quest info.
	 */
	public String getMessage()
	{
		return message;
	}
	
	/**
	 * Returns the ID of the quest
	 * @return ID The id od the quest
	 */
	public int getID()
	{
		return id;
	}
	
	/**
	 * Returns a boolean saying if the reward has been recived.
	 * @return rewardRecieved Is the reward recieved?
	 */
	public boolean isRecieved()
	{
		return rewardRecieved;
	}
	
	
	/**
	 * Returns a boolean telling if the quest has been started
	 * @return started Is the quest started?
	 */
	public boolean isStarted()
	{
		return started;
	}
	
	/**
	 * Set the quest status to the specified boolean
	 * @param started Boolean saying if the quest is started.
	 */
	public void setStarted(boolean started)
	{
		this.started = started;
	}
	
	
	
	/**
	 * returns the number to do before the quest is compelted
	 * @return numberToDo The number to do
	 */
	public int getNumberToDo()
	{
		return numberToDo;
	}
	
	
	/**
	 * returns how many part of the quest currently is done
	 * @return numberDone The number done
	 */
	public int getNumberDone()
	{
		return numberDone;
	}
	
	/**
	 * Sets how many parts currently done
	 * @param nr The number completed
	 */
	public void setNumberDone(int nr)
	{
		numberDone = nr;
	}
	
	/**
	 * Returns true if the quest is completed, false otherwise
	 * @return completed True if completed, false if not.
	 */
	public boolean isComplete()
	{
		
		return completed;
	}
	
	
	/**
	 * Updates the completed status.
	 */
	public void updateStatus()
	{
		completed = (numberDone >= numberToDo);
	}
	
	
	/**
	 * returns the cash reward as an int
	 * @return reward The cash reward
	 */
	public int getReward()
	{
		return reward;
	}
	
	
	/**
	 * Sets the quests recieved status to the specified boolean.
	 * @param recieved Is the quest recived?
	 */
	public void setRecieved(boolean recieved)
	{
		this.rewardRecieved = recieved;
	}
	
	
	
	
	//Abstract methods
	/**
	 * Update the quest stats i.e if it's done
	 * @param name The name of the quest part which to update
	 * @param p The player to update the quest for.
	 */
	public abstract void update(String name, PlayerCharacter p);
}
