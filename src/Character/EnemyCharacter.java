package Character;

import java.awt.geom.Ellipse2D;

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
    
    public int getSenseRadius()
    {
        return senseRadius;
    }
    
    public void setSenseRadius(int senseRadius)
    {
        this.senseRadius = senseRadius;
    }
    
    public Ellipse2D.Double getSenseArea(){
    	return new Ellipse2D.Double(getX() - (senseRadius/2) + (getWidth()/2), 
    			 getY() - (senseRadius/2) + (getHeight()/2), senseRadius, senseRadius);
    }
    
    @Override
    public void move()
    {
        //Move character..
    }
    
    public void update(){
    	
    	// Update character
    }

}
