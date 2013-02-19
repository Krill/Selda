package Quest;

import Utility.Entity;
import Character.Character;
import Character.PlayerCharacter;

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
	public void update(String name, PlayerCharacter p)
	{
		//Tests if the name of the character is the same as the one you should kill in the quest
		if(name.equals(characterToKill.getName()))
		{
			setNumberDone(getNumberDone() + 1);
			if(isComplete())
			{
				p.setMoney(p.getMoney() + getReward());
			}
		}
	}
}
