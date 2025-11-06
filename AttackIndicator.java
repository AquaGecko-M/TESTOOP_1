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
            isVisible = !isVisible; // Toggle visibility
            
            if (isVisible) {
                getImage().setTransparency(255); // Solid
            } else {
                getImage().setTransparency(100); // Faded
            }
            
            flashTimer.mark();
        }
    }
    
    /**
     * Called by the crocBoss. This indicator will now check for a boat
     * and deal damage to it using a circular radius.
     */
    public void dealDamage(int damage)
    {

        List<Boat> boats = getObjectsInRange(attackRadius, Boat.class);
        
        if (!boats.isEmpty()) {
            Boat boat = boats.get(0);
            boat.takeDamage(damage);
        }
    }
}
