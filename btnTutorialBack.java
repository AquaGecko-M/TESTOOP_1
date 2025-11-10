import greenfoot.*;

/**
 * The "Back" button for the tutorial slideshow.
 * Returns to the main TutorialHubWorld.
 */
public class btnTutorialBack extends Actor
{
    private boolean mouseWasDown = true; // Fixes click-through
    
    public btnTutorialBack()
    {
        setImage("btnTutorialBack.png");
    }
    
    public void act() 
    {
        // Fix for click-through bug
        if (mouseWasDown) {
            if (Greenfoot.mousePressed(null)) return;
            else mouseWasDown = false;
        }
        
        if (Greenfoot.mouseClicked(this))
        {
            // Go back to the tutorial hub
            Greenfoot.setWorld(new TutorialHubWorld());
        }
    }
}
