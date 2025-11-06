import greenfoot.*;


public class btnLevel extends Actor
{
    private int stageNumber; 
    private boolean isLocked = true;
    
    private GreenfootImage unlockedImage;
    private GreenfootImage lockedImage;

    
    public btnLevel(int stageNum, String unlockedImg, String lockedImg)
    {
        this.stageNumber = stageNum;
        
        unlockedImage = new GreenfootImage(unlockedImg);
        lockedImage = new GreenfootImage(lockedImg);
        
        unlockedImage.scale(50, 50);
        lockedImage.scale(50, 50);
        
        checkLockStatus();
    }
    
    
    private void checkLockStatus()
    {
        int highestLevelUnlocked = ProgressTracker.getHighestLevelUnlocked();
        
        if (stageNumber <= highestLevelUnlocked) {
            isLocked = false;
            setImage(unlockedImage);
        } else {
            isLocked = true;
            setImage(lockedImage);
        }
    }

    
    public void act()
    {
        if (Greenfoot.mouseClicked(this) && !isLocked) {

            Greenfoot.setWorld(new GameWorld(this.stageNumber));
        }
    }
}