import greenfoot.*;

/**
 * The "Next" button for the tutorial slideshow.
 * Calls the nextSlide() method in the world.
 */
public class btnTutorialNext extends Actor
{
    private boolean mouseWasDown = true; // Fixes click-through

    public btnTutorialNext()
    {
        setImage("btnTutorialNext.png"); // Uses your 'next.png' image
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
            
            // Tell the world to go to the next slide
            world.nextSlide();
        }
    }
}