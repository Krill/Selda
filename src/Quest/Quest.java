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
	 * @return Message
	 */
	public String getMessage()
	{
		return message;
	}
	
	/**
	 * Returns the ID of the quest
	 * @return ID
	 */
	public int getID()
	{
		return id;
	}
	
	public boolean isRecieved()
	{
		return rewardRecieved;
	}
	
	
	/**
	 * Returns a boolean telling if the quest has been started
	 * @return started
	 */
	public boolean isStarted()
	{
		return started;
	}
	
	/**
	 * Set the quest status to the specified boolean
	 * @param started
	 */
	public void setStarted(boolean started)
	{
		this.started = started;
	}
	
	/**
	 * Returns true if the quest is started, false otherwise.
	 * @return
	 */
	public boolean getStarted()
	{
		return started;
	}
	
	/**
	 * returns the number to do before the quest is compelted
	 * @return numberToDo
	 */
	public int getNumberToDo()
	{
		return numberToDo;
	}
	
	
	/**
	 * returns how many part of the quest currently is done
	 * @return numberDone
	 */
	public int getNumberDone()
	{
		return numberDone;
	}
	
	/**
	 * Sets how many parts currently done
	 * @param nr
	 */
	public void setNumberDone(int nr)
	{
		numberDone = nr;
	}
	
	/**
	 * Returns true if the quest is completed, false otherwise
	 * @return completed
	 */
	public boolean isComplete()
	{
		
		return completed;
	}
	
	public void updateStatus()
	{
		completed = (numberDone >= numberToDo);
	}
	
	
	/**
	 * returns the cash reward as an int
	 * @return reward
	 */
	public int getReward()
	{
		return reward;
	}
	
	public void setRecieved(boolean recieved)
	{
		this.rewardRecieved = recieved;
	}
	
	
	
	
	//Abstract methods
	/**
	 * Update the quest stats i.e if it's done
	 */
	public abstract void update(String name, PlayerCharacter p);
}
