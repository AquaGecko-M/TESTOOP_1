import greenfoot.*;

/**
 * Button for the "Tutorial" section.
 * Opens the slideshow from slide 1 to 5.
 */
public class btnTutorialT extends btnAnimation
{
    private boolean mouseWasDown = true; // Fixes click-through

    public btnTutorialT()
    {
        // Set your image for this button
        setImage(new GreenfootImage("btnTutorialT.png"));
        getImage().scale(250, 100); // Scale as needed
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
            // Open the slideshow at slides 1-5
            Greenfoot.setWorld(new TutorialSlideWorld(2, 5));
        }
    }
}