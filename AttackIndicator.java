import greenfoot.*;
import java.util.List; 
public class AttackIndicator extends Actor
{
    private SimpleTimer flashTimer = new SimpleTimer();
    private boolean isVisible = true;
    
 
    private int attackRadius = 160; 

    public AttackIndicator()
    {
        setImage("attack_indicator.png"); 
        getImage().scale(500, 500);
        
        flashTimer.mark();
    }
    
    public void act()
    {
        if (flashTimer.hasElapsed(100)) {
            isVisible = !isVisible; 
            
            if (isVisible) {
                getImage().setTransparency(255); 
            } else {
                getImage().setTransparency(100); 
            }
            
            flashTimer.mark();
        }
    }
    
    
    public void dealDamage(int damage)
    {

        List<Boat> boats = getObjectsInRange(attackRadius, Boat.class);
        
        if (!boats.isEmpty()) {
            Boat boat = boats.get(0);
            boat.takeDamage(damage);
        }
    }
}
