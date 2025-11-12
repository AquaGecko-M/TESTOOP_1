import greenfoot.*;

/**
 * The "Next" button for the tutorial slideshow.
 * Calls the nextSlide() method in the world.
 */
public class btnTutorialNext extends btnAnimation
{
    private boolean mouseWasDown = true; // Fixes click-through

    public btnTutorialNext()
    {
        setImage("btnTutorialNext.png"); // Uses your 'next.png' image
        getImage().scale(150, 75);
    }
    
    public void act() 
    {
        super.act();
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