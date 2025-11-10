import greenfoot.*;

/**
 * Button for the "Fish & Enemy" section.
 * Opens the slideshow from slide 11 to 17.
 */
public class btnTutorialFish extends Actor
{
    private boolean mouseWasDown = true; 

    public btnTutorialFish()
    {
        setImage(new GreenfootImage("btnTutorialFish.png"));
        getImage().scale(250, 100); 
    }
    
    public void act() 
    {
        if (mouseWasDown) {
            if (Greenfoot.mousePressed(null)) return;
            else mouseWasDown = false;
        }
        
        if (Greenfoot.mouseClicked(this))
        {
            // Open the slideshow at slides 11-17
            Greenfoot.setWorld(new TutorialSlideWorld(11, 17));
        }
    }
}
