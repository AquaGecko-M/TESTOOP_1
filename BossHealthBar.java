import greenfoot.*;

/**
 * A GENERIC health bar that can track ANY boss.
 * It now tracks an 'IBoss' instead of just a 'crocBoss'.
 */
public class BossHealthBar extends Actor
{
    // --- THIS IS THE MAIN CHANGE ---
    private IBoss boss; // It now holds any object that is an IBoss
    
    private int barWidth = 400;  // Total width of the health bar
    private int barHeight = 20;  // Total height
    
    // Create a new blank image to draw on
    private GreenfootImage barImage = new GreenfootImage(barWidth, barHeight);

    /**
     * The constructor now accepts any object that implements IBoss.
     */
    public BossHealthBar(IBoss bossToTrack)
    {
        // We must cast the object to an Actor to store it
        Actor bossActor = (Actor) bossToTrack;
        if (bossActor == null) {
            // Safety check
            if (getWorld() != null) {
                getWorld().removeObject(this);
            }
            return;
        }
        
        this.boss = bossToTrack;
        updateBar(); // Draw the bar for the first time
    }

    public void act()
    {
        // Check if the boss is dead or has been removed
        if (!boss.isAlive()) { // We call the interface method
            getWorld().removeObject(this); // Remove the health bar
            return;
        }
        
        // Redraw the health bar every frame
        updateBar();
    }
    
    /**
     * Redraws the health bar based on the boss's current health.
     */
    private void updateBar()
    {
        // 1. Get the boss's health percentage (from the interface)
        double healthPct = boss.getHealthPercentage();
        
        // 2. Clear the old bar
        barImage.clear();
        
        // 3. Draw the red "background"
        barImage.setColor(greenfoot.Color.RED);
        barImage.fillRect(0, 0, barWidth, barHeight);
        
        // 4. Calculate the width of the green "current health"
        int greenWidth = (int)(barWidth * healthPct);
        
        // 5. Draw the green bar on top
        if (greenWidth > 0) {
            barImage.setColor(greenfoot.Color.GREEN);
            barImage.fillRect(0, 0, greenWidth, barHeight);
        }
        
        // 6. Set the new image
        setImage(barImage);
    }
}
