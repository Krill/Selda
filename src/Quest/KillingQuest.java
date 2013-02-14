package Quest;

import Character.Character;

public class KillingQuest extends Quest{
	
	private Character characterToKill;
	
	public KillingQuest(int id, Character character, int nr, int reward, String message)
	{
		super(id, false, nr, reward, message);
		characterToKill = character;
	}
	
	public void update()
	{
		
	}
}
