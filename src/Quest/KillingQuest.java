package Quest;

import Character.Character;

public class KillingQuest extends Quest{
	
	private Character characterToKill;
	
	public KillingQuest(int id, Character character, int nr, int reward)
	{
		super(id, false, nr, reward);
		characterToKill = character;
	}
	
	public void update()
	{
		
	}
}
