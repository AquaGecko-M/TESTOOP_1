import greenfoot.*;

/**
 * Button for the "Economy" section.
 * Opens the slideshow from slide 6 to 10.
 */
public class btnTutorialEconomy extends btnAnimation
{
    private boolean mouseWasDown = true; 

    public btnTutorialEconomy()
    {
        setImage(new GreenfootImage("btnTutorialEconomy.png"));
        getImage().scale(250, 100); 
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
            // Open the slideshow at slides 6-10
            Greenfoot.setWorld(new TutorialSlideWorld(6, 10));
        }
    }
}