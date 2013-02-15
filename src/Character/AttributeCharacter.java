package Character;

public abstract class AttributeCharacter extends Character
{
    private int health;
    private int speed;
    
    public AttributeCharacter(int id, int x, int y, int width, int height, String name, boolean isAttackable, int health, int speed, int radius)
    {
        super(id, x, y, width, height, name, isAttackable, radius);
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
