import greenfoot.*;
import java.util.List; // <-- Make sure this is imported

/**
 * A visual warning indicator for the boss's attack.
 * (UPDATED with circular hitbox)
 */
public class AttackIndicator extends Actor
{
    private SimpleTimer flashTimer = new SimpleTimer();
    private boolean isVisible = true;
    
    // --- THIS IS YOUR NEW HITBOX SIZE ---
    // Change this number until it "feels" right.
    // This is a 500-pixel radius circle.
    private int attackRadius = 160; 

    public AttackIndicator()
    {
        setImage("attack_indicator.png"); 
        getImage().scale(500, 500);
        
        flashTimer.mark();
    }
    
    public void act()
    {
        // This flashing logic is unchanged
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
        // --- THIS IS THE NEW HITBOX LOGIC ---
        
        // 1. Get a list of all Boat objects within our 'attackRadius'
        List<Boat> boats = getObjectsInRange(attackRadius, Boat.class);
        
        // 2. Check if the list is NOT empty (meaning we hit a boat)
        if (!boats.isEmpty()) {
            // 3. Get the first boat in the list and damage it
            Boat boat = boats.get(0);
            boat.takeDamage(damage);
        }
    }
}
