package Quest;

import Character.Character;

/**
 * A quest to kill a specified monster X times.
 * @author Johan
 *
 */
public class KillingQuest extends Quest{
	
	private Character characterToKill;
	
	/**
	 * Inits the quest
	 * @param id Quest ID
	 * @param character Character to kill
	 * @param nr How many to kill
	 * @param reward Cash to recieve when completed
	 * @param message Message to be displayed when accepting the quest
	 */
	public KillingQuest(int id, Character character, int nr, int reward, String message)
	{
		super(id, false, nr, reward, message);
		characterToKill = character;
	}
	
	
	/**
	 * Update the quest stats i.e if it's done
	 */
	public void update()
	{
		
	}
}
