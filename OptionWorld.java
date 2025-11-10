import greenfoot.*;

/**
 * The new, simplified Options screen.
 * It ONLY shows Reset Progress and a Back button.
 */
public class OptionWorld extends World
{
    private World returnToWorld; // The world we came from (Menu or bgMenu)

    public OptionWorld(World returnTo)
    {    
        super(returnTo.getWidth(), returnTo.getHeight(), 1, false); // `false` = unbounded
        this.returnToWorld = returnTo;
        
        // 1. Create a dark, see-through background
        GreenfootImage bg = new GreenfootImage(getWidth(), getHeight());
        
        // Try to get a snapshot of the world we came from
        try {
             bg = new GreenfootImage(returnToWorld.getBackground());
        } catch (Exception e) {
            // Failsafe if the world is weird
            bg.setColor(Color.BLACK);
            bg.fill();
        }

        GreenfootImage overlay = new GreenfootImage(getWidth(), getHeight());
        overlay.setColor(new Color(0, 0, 0, 180)); // 180 = semi-transparent
        overlay.fill();
        bg.drawImage(overlay, 0, 0);
        setBackground(bg);
        
        // 2. Add the title
        getBackground().setColor(Color.WHITE);
        getBackground().setFont(new Font("Arial", true, false, 48));
        getBackground().drawString("Options", getWidth() / 2 - 100, 150);

        // 3. Add the new buttons
        addObject(new btnResetProgress(), getWidth() / 2, 300);
        addObject(new btnBackToWorld(returnToWorld), getWidth() / 2, 420);
    }
}