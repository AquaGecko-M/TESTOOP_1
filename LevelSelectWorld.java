import greenfoot.*;

/**
 * The world that holds the level selection buttons.
 */
public class LevelSelectWorld extends World
{
    /**
     * Constructor for objects of class LevelSelectWorld.
     */
    public LevelSelectWorld()
    {    
        super(960, 540, 1); 
        
        GreenfootImage bg = new GreenfootImage("background_level.png");
        bg.scale(getWidth(), getHeight());
        setBackground(bg);
        
        prepare();
    }
    
    /**
     * Prepare the world by adding the level buttons.
     */
    private void prepare()
    {

        btnLevel button1 = new btnLevel(1, "btnLevelOne.png", "btnLevelOne_locked.png");
        addObject(button1, 261, 256); 

        // Level 2 Button (Stage 2)
        btnLevel button2 = new btnLevel(2, "btnLevelTwo.png", "btnLevelTwo_locked.png");
        addObject(button2, 462, 372); 

        // Level 3 Button (Stage 3)
        btnLevel button3 = new btnLevel(3, "btnLevelThree.png", "btnLevelThree_locked.png");
        addObject(button3, 735, 364); 
    }
    
    public void act()
    {
        if (Greenfoot.isKeyDown("r"))
        {
            ProgressTracker.resetProgress();
            
            Greenfoot.setWorld(new LevelSelectWorld());
        }
    }
}