import greenfoot.*; 


public class btnNextStage extends Actor
{
    private int levelToUnlock;
    private boolean mouseWasDown = true; 

    public btnNextStage(int stageJustCompleted) 
    {
        this.levelToUnlock = stageJustCompleted; 
        
        GreenfootImage img = new GreenfootImage("btnNextStage.png");
        img.scale(300, 250); 
        setImage(img);
    }
    
    public void act() {
        if (mouseWasDown) {
            if (Greenfoot.mousePressed(null)) {
                return; 
            } else {
                mouseWasDown = false; 
            }
        }
        
        if (Greenfoot.mouseClicked(this)) {
            
            
            
            ProgressTracker.unlockNextLevel(levelToUnlock);
            
            
            Greenfoot.setWorld(new LevelSelectWorld()); 
        }
    }
}
