package Character;

public class EnemyCharacter extends AttributeCharacter implements Moveable
{
    private float dropRate;
    private boolean isHostile;
    private int senseRadius;

    public EnemyCharacter(int x, int y, int width, int height, String name,
                            boolean isAttackable, int health, int speed,float dropRate,
                                boolean isHostile, int senseRadius)
    {
        super(x, y, width, height, name, isAttackable, health, speed);       
        this.dropRate = dropRate;
        this.isHostile = isHostile;
        this.senseRadius = senseRadius;
    }
    
    public float getDropRate()
    {
        return dropRate;
    }
    
    public boolean isHostile()
    {
        return isHostile;
    }
    
    public int getSenseArea()
    {
        return senseRadius;
    }
    
    @Override
    public void move()
    {
        //Move character..
    }

}
