package Quest;


public abstract class Quest {

	//Fields
	private int id;
	private boolean started;
	private int numberToDo;
	private int numberDone;
	private int reward;
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
	}
	
	
	//Methods
	public String getMessage()
	{
		return message;
	}
	
	public int getID()
	{
		return id;
	}
	
	public boolean isStarted()
	{
		return started;
	}
	
	public void setStarted(boolean started)
	{
		this.started = started;
	}
	
	public boolean getStarted()
	{
		return started;
	}
	
	public int getNumberToDo()
	{
		return numberToDo;
	}
	
	public int getNumberDone()
	{
		return numberDone;
	}
	
	public void setNumberDone(int nr)
	{
		numberDone = nr;
	}
	
	public boolean isComplete()
	{
		return completed;
	}
	
	public void setComplete(boolean completed)
	{
		this.completed = completed;
	}
	
	public int getReward()
	{
		return reward;
	}
	
	public void update()
	{
		setComplete(getNumberDone() >= getNumberToDo());
	}
	
	//Abstract methods
	
}
