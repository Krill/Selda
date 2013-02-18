package Character;

public abstract class AttributeCharacter extends Character
{
    private int speed;
    
    public AttributeCharacter(int id, int x, int y, int width, int height, String name, int health, boolean isAttackable, int speed, int radius)
    {
        super(id, x, y, width, height, name, health, isAttackable, radius);
        this.speed = speed;
    }
    
    public int getSpeed()
    {
        return speed;
    }
}
