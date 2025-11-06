import greenfoot.*; 

/**
 * Button to save progress and proceed to the next stage.
 */
public class btnNextStage extends Actor
{
    private int levelToUnlock;
    private boolean mouseWasDown = true; 

    /**
     * --- CONSTRUCTOR IS UPDATED ---
     * It now receives the level that was just beaten.
     */
    public btnNextStage(int stageJustCompleted) 
    {
        this.levelToUnlock = stageJustCompleted; 
        
        GreenfootImage img = new GreenfootImage("btnNextStage.png");
        img.scale(300, 250); 
        setImage(img);
    }
    
    /**
     * --- ACT METHOD IS UPDATED ---
     */
    public void act() {
        if (mouseWasDown) {
            if (Greenfoot.mousePressed(null)) {
                return; // Wait for release
            } else {
                mouseWasDown = false; // Armed
            }
        }
        
        if (Greenfoot.mouseClicked(this)) {
            
            // 1. Save the new progress!
            // This calls our "brain" class to save the next level
            ProgressTracker.unlockNextLevel(levelToUnlock);
            
            // 2. Go back to the level select screen
            Greenfoot.setWorld(new LevelSelectWorld()); 
        }
    }
}