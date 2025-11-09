import greenfoot.*;

/**
 * An actor that covers the screen with a red tint and fades out.
 * (UPDATED to accept width and height)
 */
public class DamageFlash extends Actor
{
    private int fadeSpeed = 10; // How fast it fades (higher is faster)
    
    /**
     * --- CONSTRUCTOR IS UPDATED ---
     * It now receives the world's width and height from
     * whatever created it (the Boat).
     */
    public DamageFlash(int width, int height)
    {
        // 1. Create a new image that is the size of the screen
        GreenfootImage img = new GreenfootImage(width, height);
        
        // 2. Fill it with a semi-transparent red
        img.setColor(new greenfoot.Color(255, 0, 0, 100));
        img.fill();
        
        // 3. Set this as the actor's image
        setImage(img);
    }
    
    public void act()
    {
        // 1. Get the current transparency
        int transparency = getImage().getTransparency();
        
        // 2. Reduce the transparency
        transparency = transparency - fadeSpeed;
        
        // 3. If it's almost invisible, remove it
        if (transparency < 10) {
            getWorld().removeObject(this);
        } else {
            // 4. Otherwise, apply the new, faded transparency
            getImage().setTransparency(transparency);
        }
    }
}