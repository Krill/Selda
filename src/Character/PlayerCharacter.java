package Character;

import java.util.ArrayList;
import java.util.Iterator;

import Quest.Quest;
import Handler.TimeHandler;
import Item.ArmorItem;
import Item.Item;
import Item.WeaponItem;

@SuppressWarnings("serial")
public class PlayerCharacter extends AttributeCharacter implements Moveable
{
    private boolean gender;
    private boolean sexPref;
    private int money;
    private int maxInventorySize;
    private ArrayList<Quest> quests;
    private ArrayList<Item> inventory;
    private WeaponItem equippedWeapon;
    private ArmorItem equippedArmor;
    private int armor;
    private int damage;

    public PlayerCharacter(int id, int x, int y, int width, int height, String name, int health,
    		boolean isAttackable, int speed, boolean gender,
    		boolean sexPref, int money, int maxInventorySize)
    {
    	//Sends 0 as area atm, no use for playercharacter.
    	super(id, x, y, width, height, name, health, isAttackable, speed, 0);
    	this.gender = gender;
    	this.sexPref = sexPref;
    	this.money = money;
    	this.maxInventorySize = maxInventorySize;       

    	quests = new ArrayList<Quest>();       
    	inventory = new ArrayList<Item>();      
    }
    
    public void addQuest(Quest quest)
    {
    	quests.add(quest);
    }
    
    public void updateQuests(String name, PlayerCharacter p)
    {
    	for(Quest quest : quests)
    	{
    		quest.update(name, p);
    	}
    }
    
    public boolean getGender()
    {
        return gender;
    }
    
    public boolean getSexPref()
    {
        return sexPref;
    }
    
    public int getMoney()
    {
        return money;
    }
    
    public int getDamage(){
    	damage = 5;   // If no weapon is equipped
    	if(equippedWeapon!=null){
    		damage = equippedWeapon.getAttackDamage();
    	}
    	return(damage);
    }
    
    public int getArmorRating(){
    	armor = 0;
    	if(equippedArmor!=null){
    		armor = equippedArmor.getDefenceRating();
    	}
    	return(armor);
    }
    
    /**
     * Sets the player new amount of money
     * @param money
     */
    public void setMoney(int money)
    {
        this.money = money;
    } 
    
    /**
     * Returns the inventory of this player
     * @return inventory
     */
    public ArrayList<Item> getInventory(){
    	return inventory;
    }
    
    public int getMaxInventorySize()
    {
        return maxInventorySize;
    }
    
    public int getCurrentInventorySize()
    {
    	int size = inventory.size();
    	if(equippedWeapon != null){
    		size++;
    	}
    	if(equippedArmor != null){
    		size++;
    	}
    	return size;
    }
    
    public void addToInventory(Item item)
    {
    	inventory.add(item);
    	setChanged();
        notifyObservers(inventory);    	
    }
    
    public void update()
    {
        move();
        
        // Is character still attacking
        if(isAttacking()){
        	if(TimeHandler.timePassed(getTimeStamp(), 450)){
        		setAttacking(false);
        	}
        }  
    }
    
    @Override
    public void move()
    {
    	// move character
    	setY(getY()+getDy());
        setX(getX()+getDx());

        
        // set the current direction
        if(getDx() > 0){
        	setDirection("right");
        } else if(getDx() < 0) {
        	setDirection("left");
        }
        
        if(getDy() > 0){
        	setDirection("down");
        } else if(getDy() < 0){
        	setDirection("up");
        }
    }
    
    /**
     * Uses the players primary attack and sets isAttacking to true for
     * a short while. Plays sound effect.
     */
    public void primaryAttack(){
    	setTimeStamp(TimeHandler.getTime());   	
    	setAttacking(true);   	
    	
    	setChanged();
    	notifyObservers("audio/sounds/sword_swing.mp3");
    }
    
    /**
     * Updates the equipped items
     * @param item
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
     * Removes the currently equipped weapon
     */
    public void unEquipWeapon(){
    	inventory.add(equippedWeapon);
    	equippedWeapon = null;
    	setChanged();
        notifyObservers(inventory);	
    }

    /**
     * Removes the currently equipped armor
     */
    public void unEquipArmor(){
    	inventory.add(equippedArmor);
    	equippedArmor = null;
    	setChanged();
        notifyObservers(inventory);	
    }
    
    /**
     * Returns the equipped weapon, null if you have nothing equipped
     * @return equippedWeapon
     */
    public WeaponItem getWeapon(){
    	return equippedWeapon;
    }
    
    /**
     * Returns the equipped armor, null if you have nothing equipped
     * @return equippedArmor
     */
    public ArmorItem getArmor(){
    	return equippedArmor;
    }
    
    public void pickUpItem(Item item){
    	// Do something funny :D
    }
    
    
    //HITTA ANVÄNDNING TILL DENNA
    @Override
    public void interact(PlayerCharacter player)
    {
    	
    }
    
   
    
}
