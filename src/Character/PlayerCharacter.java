package Character;

import java.util.ArrayList;
import Quest.Quest;
import Item.Item;

public class PlayerCharacter extends AttributeCharacter implements Moveable
{
    private boolean gender;
    private boolean sexPref;
    private int money;
    private int inventorySize;
    private ArrayList<Quest> quests;
    private ArrayList<Item> inventory;
    private Item primaryAttack;    

    public PlayerCharacter(int id, int x, int y, int width, int height, String name,
                            boolean isAttackable, int health, int speed, boolean gender,
                                boolean sexPref, int money, int inventorySize)
    {
        super(id, x, y, width, height, name, isAttackable, health, speed);
        this.gender = gender;
        this.sexPref = sexPref;
        this.money = money;
        this.inventorySize = inventorySize;       
        
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
    
    public int getInventorySize()
    {
        return inventorySize;
    }
    
    public void update()
    {
        move();
    }
    
    @Override
    public void move()
    {
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
    }
    
    public void primaryAttack(){
    	// Do something funny :D
    }
    public void pickUpItem(Item item){
    	// Do something funny :D
    }
    
}
