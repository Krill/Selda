package Character;

import java.util.ArrayList;
import java.util.Iterator;

import Item.ArmorItem;
import Item.Item;
import Item.LifeItem;
import Item.MoneyItem;
import Item.WeaponItem;

/**
 * The user-controlled player in the game. Holds information about the current inventory and quests, as well
 * as some attributes. Can interact with other characters in the game and is able to use attacks.
 * 
 * @author Alexander Persson & Jimmy Svensson
 * @version 2013-02-28
 */
@SuppressWarnings("serial")
public class PlayerCharacter extends AttributeCharacter
{
    private int money;
    private int maxInventorySize;
    private ArrayList<Item> inventory;
    private WeaponItem equippedWeapon;
    private ArmorItem equippedArmor;
    private int maxHealth;
    private int armor;
    private int damage;

    /**
     * Constructor
     * 
     * @param id the id of a character
     * @param x the x coordination of a character
     * @param y the y coordination of a character
     * @param width the width of a character
     * @param height the height of a character
     * @param name the name of a character
     * @param health the health of a character
     * @param isAttackable if a character is attackable or not
     * @param speed the speed of a AttributeCharacter
     * @param money the money that the PlayerCharacter have
     * @param maxInventorySize the max size of an inventory
     * @param maxHealth the max health a PlayerCharacter can have
     */
    public PlayerCharacter(int id, int x, int y, int width, int height, String name, int health,
    		boolean isAttackable, int speed, int money, int maxInventorySize, int maxHealth){
    	
    	//Sends 0 as area atm, no use for playercharacter.
    	super(id, x, y, width, height, name, health, isAttackable, speed, 0);
    	this.money = money;
    	this.maxInventorySize = maxInventorySize;    
    	this.maxHealth = maxHealth;
     
    	inventory = new ArrayList<Item>();      
    }
    
    /**
     * Returns how much money the player has.
     * @return How much money the player has.
     */
    public int getMoney(){
        return money;
    }
     
    /**
     * Returns the damage of the player.
     * @return The weapons attack, or 5 if none equipped.
     */
    public int getDamage(){
    	damage = 5;   // If no weapon is equipped
    	if(equippedWeapon!=null){
    		damage = equippedWeapon.getAttackDamage();
    	}
    	return(damage);
    }
    
    /**
     * Returns the armor of the player.
     * @return The armors defense rating, or 0 if none equipped.
     */
    public int getArmorRating(){
    	armor = 0;
    	if(equippedArmor!=null){
    		armor = equippedArmor.getDefenceRating();
    	}
    	return(armor);
    }
    
    /**
     * Sets the player new amount of money
     * @param money The new value for player's money.
     */
    public void setMoney(int money){
        this.money = money;
    	setChanged();
        notifyObservers("information");  
    } 
    
    /**
     * Returns the inventory of this player
     * @return Player's inventory.
     */
    public ArrayList<Item> getInventory(){
    	return inventory;
    }
    
    /**
     * Returns the maximum size of the players inventory.
     * @return The max inventory capacity.
     */
    public int getMaxInventorySize(){
        return maxInventorySize;
    }
    
    /**
     * Returns the current inventory size, including the equipped items
     * @return How many items the player holds in the inventory.
     */
    public int getCurrentInventorySize(){
    	int size = inventory.size();
    	if(equippedWeapon != null){
    		size++;
    	}
    	if(equippedArmor != null){
    		size++;
    	}
    	return size;
    }
    
    
    /**
     * Adds the supplied item to the players inventory.
     * @param item The item to be added.
     */
    public void addToInventory(Item item){
    	//kollar om det är moneyItem, i så fall läggs penagr till men moneyitem läggs inte till inventory.
    	if(item instanceof MoneyItem){
    		setMoney((((MoneyItem) item).getMoney() + getMoney()));
    	}
    	else if(getCurrentInventorySize() < getMaxInventorySize()){
	    	inventory.add(item);
    	}
    	setChanged();
        notifyObservers(inventory); 
    }
    
    /**
     * Removes the specified item from the player's inventory.     * 
     * @param itemName Name of item to be removed.
     */
    public void removeFromInventory(String itemName){
    	Iterator<Item> it = inventory.iterator();
     	while(it.hasNext()){
     		Item i = it.next();
     		if(i.getName() == itemName){
     			it.remove();
     			break;
     		}
     	}
         	
     	setChanged();
        notifyObservers(inventory);
    }
    
    /**
     * Moves the player and updates its attacking status.
     */
    public void update(){
        move();
    }
    
    /**
     * Uses the players primary attack and sets isAttacking to true for
     * a short while. Plays sound effect.
     */
    public void primaryAttack(){
    	
    	if(!isAttacking()){	  		
    		try{
    			// Set attacking to true
    			setAttacking(true);
    			
				Thread delay = new Thread(){
					public void run(){
						try {Thread.sleep(375);} catch (InterruptedException e) {e.printStackTrace();}

						// When delay is done, make player able to attack again
						setAttacking(false);
					}
				};
				
				delay.start();
		    	setChanged();
		    	notifyObservers("audio/sounds/sword_swing.mp3");
				
			} catch (Exception e){
				System.out.println("An error has occured during setAttacking()");
				e.printStackTrace();
			}
    	}
    }
    
    /**
     * Updates the equipped items.
     * @param item Item to be equipped.
     */
    public void equipItem(Item item){
    	
    	// weaponitem
    	if(item instanceof WeaponItem){
    		
    		// nothing equipped?
    		if(equippedWeapon == null){
    		} else {
    			inventory.add(equippedWeapon);
    		}
    		equippedWeapon = (WeaponItem) item;
    	}
    	
    	// armoritem
    	if(item instanceof ArmorItem){
    		
    		// equippedArmor equipped?
    		if(equippedArmor == null){
    		} else {
    			inventory.add(equippedArmor);
    		}
    		equippedArmor = (ArmorItem) item;
    	}
    	
    	// Remove newly equipped item from inventory
    	Iterator<Item> it = inventory.iterator();
    	boolean found = false; //tar inte bort båda items ifall man fått två med samma id.
    	while(it.hasNext() && !found){
    		Item i = it.next();
    		if(i.equals(item)){
    			it.remove();
    			found = true;
    		}
    	}
        	
    	setChanged();
        notifyObservers(inventory);	
    }
    
    /**
     * Removes the currently equipped weapon.
     */
    public void unEquipWeapon(){
    	inventory.add(equippedWeapon);
    	equippedWeapon = null;
    	setChanged();
        notifyObservers(inventory);	
    }

    /**
     * Removes the currently equipped armor.
     */
    public void unEquipArmor(){
    	inventory.add(equippedArmor);
    	equippedArmor = null;
    	setChanged();
        notifyObservers(inventory);	
    }
    
    /**
     * Returns the equipped weapon, null if you have nothing equipped.
     * @return The equipped weapon or null if there is none.
     */
    public WeaponItem getWeapon(){
    	return equippedWeapon;
    }
    
    /**
     * Based on the players equipped weapon this method returns how much damage the player does.
     * @return How much damage the player does.
     */
    public int getPlayerDamage(){
    	if(equippedWeapon != null)
    		return equippedWeapon.getAttackDamage();
    	else
    		return 5;
    }
    
    /**
     * Returns the equipped armor, null if you have nothing equipped
     * @return The equipped armor or null if there is none.
     */
    public ArmorItem getArmor(){
    	return equippedArmor;
    }
    
    /**
     * Based on the players equipped armor this method returns how much armor this player has
     * @return The armor rating of this player.
     */
    public int getPlayerArmor(){
  		if(equippedArmor != null){
  			return equippedArmor.getDefenceRating(); 			
  		}else{
  			return 5;
  		}
  	}   
	
	/**

	 * Returns the max health this player can have.
	 * @return The max capacity for player's health.
	 */
	public int getMaxHealth(){
		return maxHealth;
	}
	     
	/**
	 * Sets a new value for maxHealth.
	 * @param maxHealth The new value for max capacity health.
	 */
	public void setMaxHealth(int maxHealth){
		this.maxHealth = maxHealth;
	}	     

	/**
	 * Pick up item from the ground.
	 * @param item The item to be picked up.
	 */
    public void pickUpItem(Item item){
        // Do something funny :D
       }
	
	/**
	 * Consumes an item, removes it from the inventory.
	 * @param item The item to be consumed.
	 */
	public void useItem(Item item)
	{
		// LifeItem
		if(item instanceof LifeItem){
			if(((LifeItem) item).getLifeValue() + getHealth() >= getMaxHealth()){
				setHealth(getMaxHealth());
			}
			else{
				setHealth(((LifeItem) item).getLifeValue() + getHealth());
			}
		}

		// Remove newly used item from inventory
		Iterator<Item> it = inventory.iterator();
		while(it.hasNext()){
			Item i = it.next();
			if(i.equals(item)){
				it.remove();
			}
		}

		setChanged();
		notifyObservers(inventory);	
	}
	
	
	/**
	 * Player interacts with a player.
	 * @param player The player to be interacted with.
	 */
	@Override
	public void interact(PlayerCharacter player) {
		// TODO Auto-generated method stub	
	} 
}
