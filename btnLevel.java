import greenfoot.*;

/**
 * A "smart" button for the level select screen.
 * (SIMPLIFIED VERSION)
 * It just knows its stage number and checks if it's locked.
 */
public class btnLevel extends Actor
{
    private int stageNumber; // The stage this button loads (1, 2, 3...)
    private boolean isLocked = true;
    
    private GreenfootImage unlockedImage;
    private GreenfootImage lockedImage;

    /**
     * Creates a new level button.
     * @param stageNum The level this button leads to (e.g., 1, 2, or 3).
     * @param unlockedImg The filename for the "unlocked" button image.
     * @param lockedImg The filename for the "locked" button image.
     */
    public btnLevel(int stageNum, String unlockedImg, String lockedImg)
    {
        this.stageNumber = stageNum;
        
        unlockedImage = new GreenfootImage(unlockedImg);
        lockedImage = new GreenfootImage(lockedImg);
        
        // You can scale them here if you need to
        unlockedImage.scale(65, 65);
        lockedImage.scale(65, 65);
        
        checkLockStatus();
    }
    
    /**
     * Checks if the button should be locked.
     */
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

    /**
     * Check for clicks. Only work if not locked.
     */
    public void act()
    {
        if (Greenfoot.mouseClicked(this) && !isLocked) {
            // It's unlocked! Load the GameWorld with this stage number.
            // GameWorld will automatically read the difficulty from GameSettings.
            Greenfoot.setWorld(new GameWorld(this.stageNumber));
        }
    }
}