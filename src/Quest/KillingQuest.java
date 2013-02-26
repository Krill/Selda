package Quest;

import Character.Character;
import Character.PlayerCharacter;

/**
 * This is a class that provides a quest to kill a certain character, specified in the constructor, x times.
 * This class will only know what character to kill, leaving the rest up to its superclass, Quest.
 * @author Johan
 *
 */
public class KillingQuest extends Quest{
	
	private static final long serialVersionUID = 111235L;
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
	 * Update the quest, updates it's completed status and counts how many parts the player completed.
	 */
	public void update(String name, PlayerCharacter p)
	{
		//Tests if the name of the character is the same as the one you should kill in the quest
		if(name.equals(characterToKill.getName()))
		{
			setNumberDone(getNumberDone() + 1);
			
			updateStatus();
		}
	}
}
