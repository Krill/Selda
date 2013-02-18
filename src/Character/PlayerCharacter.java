package Character;

import java.util.ArrayList;
import Quest.Quest;
import Item.Item;

public class PlayerCharacter extends AttributeCharacter implements Moveable
{
    private boolean gender;
    private boolean sexPref;
    private int money;
    private int maxInventorySize;
    private ArrayList<Quest> quests;
    private ArrayList<Item> inventory;
    private Item primaryAttack;    

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
    	return inventory.size();
    }
    
    public void addToInventory(Item item)
    {
    	inventory.add(item);
    }
    
    public void update()
    {
        move();
    }
    
    @Override
    public void move()
    {
    	// reference to set direction
    	int dx = getX();
    	int dy = getY();
    	
    	// move character
        if(isUp()){
        	setY(getY()-1);
        }
        if(isLeft()){
        	setX(getX()-1);
        }
        if(isRight()){
        	setX(getX()+1);
        }
        if(isDown()){
        	setY(getY()+1);
        }  
        
        // set the current direction
        if(dx-getX() < 0){
        	setDirection("right");
        } else if(dx-getX() > 0) {
        	setDirection("left");
        }
        
        if(dy-getY() < 0){
        	setDirection("down");
        } else if(dy-getY() > 0){
        	setDirection("up");
        }
    }
    
    public void primaryAttack(){
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
