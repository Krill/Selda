package Item;

/**
 * WeaponItem extends Item and has two instance variables for a attack damage and a attack speed.
 * WeaponItem contains a weapons attack speed and how much damage a weapon inflicts on its opponents, weapons are added to the inventory and are equipped when in use.
 * @author norling/kevin
 * @version 0.1
 */

public class WeaponItem extends Item{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 123878;
	private int attackDamage;
	private int attackSpeed;
	private int attackRange;
	
	/**
	 * Constructor
	 * 
	 * @param id The id of the Armor
	 * @param x The x coord 
	 * @param y The y coord
	 * @param width The width of armor
	 * @param height The height of the armor
	 * @param name The armors name
	 * @param isVisible True if the armor is visible
	 * @param itemValue The armors value
	 * @param attackDamage The damage of the weapong
	 * @param attackSpeed the attackspeed
	 * @param attackRange The range of the weapong
	 */
	public WeaponItem(int id, int x, int y, int width, int height, String name, boolean isVisible, int itemValue, int attackDamage, int attackSpeed,  int attackRange){	
		
		super(id,x,y,width,height,name,isVisible, itemValue);
		this.attackDamage = attackDamage;
		this.attackSpeed = attackSpeed;
		this.attackRange = attackRange;
	}
	
	
	/**
	 * Sets attack damage for the item
	 * @param attackDamage The new damage
	 */
	public void setAttackDamage(int attackDamage){
		this.attackDamage = attackDamage;
	}
	
	/**
	 * Sets attack speed for the item
	 * @param attackSpeed The new attackspeed
	 */
	public void setAttackSpeed(int attackSpeed){
		this.attackSpeed = attackSpeed;
	}
	
	/**
	 * Returns the attack damage
	 * @return attackDamage The attack damage
	 */
	public int getAttackDamage(){
		return attackDamage;
	}
	
	/**
	 * Returns the attack speed
	 * @return attackSpeed The attackspeed
	 */
	public int getAttackSpeed(){
		return(attackSpeed);
	}
	
	/**
	 * Sets Wepons attackRange
	 * @param attackRange The new attackrange
	 */
	public void setAttackRange(int attackRange){
		this.attackRange = attackRange;
	}
	/**
	 * Returns the attack range
	 * @return attackRange The attackrange
	 */
	public int getAttackRange(){
		return attackRange;
	}
}