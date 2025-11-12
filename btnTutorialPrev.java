import greenfoot.*;

/**
 * The "Previous" button for the tutorial slideshow.
 * Calls the prevSlide() method in the world.
 */
public class btnTutorialPrev extends btnAnimation
{
    private boolean mouseWasDown = true; // Fixes click-through

    public btnTutorialPrev()
    {
        setImage("btnTutorialPrev.png"); // Uses your 'prev.png' image
        // You can scale it if needed:
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
            
            // Tell the world to go to the previous slide
            world.prevSlide();
        }
    }
}