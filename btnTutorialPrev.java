import greenfoot.*;

/**
 * The "Previous" button for the tutorial slideshow.
 * Calls the prevSlide() method in the world.
 */
public class btnTutorialPrev extends Actor
{
    private boolean mouseWasDown = true; // Fixes click-through

    public btnTutorialPrev()
    {
        setImage("btnTutorialPrev.png"); // Uses your 'prev.png' image
        // You can scale it if needed:
        getImage().scale(150, 100);
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
            // Get the world we are in
            TutorialSlideWorld world = (TutorialSlideWorld) getWorld();
            
            // Tell the world to go to the previous slide
            world.prevSlide();
        }
    }
}