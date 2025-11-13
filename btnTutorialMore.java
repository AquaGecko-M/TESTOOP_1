import greenfoot.*;

/**
 * Button for the "More" section.
 * Opens the slideshow from slide 18 to 19.
 */
public class btnTutorialMore extends btnAnimation
{
    private boolean mouseWasDown = true; 

    public btnTutorialMore()
    {
        setImage(new GreenfootImage("btnTutorialMore.png"));
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
            // Open the slideshow at slides 18-19
            Greenfoot.setWorld(new TutorialSlideWorld(18, 20));
        }
    }
}
