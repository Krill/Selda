package Character;

public abstract class AttributeCharacter extends Character
{
    private int health;
    private int speed;
    
    public AttributeCharacter(int x, int y, int width, int height, String name, boolean isAttackable, int health, int speed)
    {
        super(x, y, width, height, name, isAttackable);
        this.health = health;
        this.speed = speed;
    }
    
    public int getHealth()
    {
        return health;
    }
    
    public int getSpeed()
    {
        return speed;
    }
}
