import greenfoot.*;

/**
 * The main "Information" screen.
 * This is the hub that holds the 4 tutorial category buttons.
 */
public class TutorialHubWorld extends World
{
    public TutorialHubWorld()
    {    
        super(960, 540, 1); // Your standard world size
        
        // Set the background (I'll guess the filename)
        GreenfootImage bg = new GreenfootImage("1.png");
        bg.scale(getWidth(), getHeight());
        setBackground(bg);
        
        // Add the title
        getBackground().setColor(Color.YELLOW);
        getBackground().setFont(new Font("Arial", true, false, 48));
        
        // Add all your new buttons
        prepare();
    }
    
    /**
     * Prepare the world by adding the buttons.
     */
    private void prepare()
    {
        // Add your 4 tutorial buttons
        addObject(new btnTutorialT(), 250, 200);
        addObject(new btnTutorialEconomy(), 250, 360);
        addObject(new btnTutorialFish(), 710, 200);
        addObject(new btnTutorialMore(), 710, 360);
        
        // Add a "Back" button to return to the main menu
        addObject(new BtnBack(), 80, 500); 
    }
}