import greenfoot.*;

/**
 * A health bar that VISUALLY TRACKS the crocBoss.
 * It is NOT the boss itself.
 * It must be given the boss object to track.
 */
public class BossHealthBar extends Actor
{
    private crocBoss boss; // The boss we are tracking
    private int barWidth = 400;  // Total width of the health bar
    private int barHeight = 20;  // Total height
    
    // Create a new blank image to draw on
    private GreenfootImage barImage = new GreenfootImage(barWidth, barHeight);

    /**
     * Constructor.
     * @param bossToTrack The crocBoss object this health bar should follow.
     */
    public BossHealthBar(crocBoss bossToTrack)
    {
        this.boss = bossToTrack;
        updateBar(); // Draw the bar for the first time
    }

    public void act()
    {
        // Check if the boss is dead or has been removed from the world
        if (boss.getWorld() == null) {
            getWorld().removeObject(this); // Remove the health bar
            return;
        }
        
        // Redraw the health bar every frame to show damage
        updateBar();
    }
    
    /**
     * Redraws the health bar based on the boss's current health percentage.
     */
    private void updateBar()
    {
        // 1. Get the boss's health percentage (a number from 0.0 to 1.0)
        double healthPct = boss.getHealthPercentage();
        
        // 2. Clear the old bar
        barImage.clear();
        
        // 3. Draw the red "background" of the bar
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
