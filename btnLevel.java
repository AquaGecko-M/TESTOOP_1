import greenfoot.*;

/**
 * A "smart" button for the level select screen.
 * It now loads a specific StoryWorld based on the stage number.
 */
public class btnLevel extends btnAnimation
{
    private int stageNumber; // The stage this button loads (1, 2, 3...)
    private boolean isLocked = true;
    
    private GreenfootImage unlockedImage;
    private GreenfootImage lockedImage;

    // ... (Constructor and checkLockStatus() remain the same) ...

    public btnLevel(int stageNum, String unlockedImg, String lockedImg)
    {
        this.stageNumber = stageNum;
        
        unlockedImage = new GreenfootImage(unlockedImg);
        lockedImage = new GreenfootImage(lockedImg);
        
        unlockedImage.scale(65, 65);
        lockedImage.scale(65, 65);
        
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

    /**
     * Check for clicks and load the appropriate Story World.
     */
    public void act()
    {   
        super.act();
        if (Greenfoot.mouseClicked(this) && !isLocked) {
            String currentDifficulty = GameSettings.difficulty;

            // **1. Determine which StoryWorld to load based on the stage number**
            World storyWorldToLoad = null;

            switch (this.stageNumber) {
                case 1:
                    // Stage 1 loads the first story (e.g., StoryWorld1, based on your previous 'StoryWorld' logic)
                    storyWorldToLoad = new StoryWorld2(currentDifficulty, this.stageNumber);
                    break;
                case 2:
                    // Stage 2 loads the second story (StoryWorld2)
                    storyWorldToLoad = new StoryWorld3(currentDifficulty, this.stageNumber);
                    break;
                case 3:
                    // Stage 3 loads the third story (You would need to create StoryWorld3.java)
                    Greenfoot.setWorld(new GameWorld(this.stageNumber));
                    break;
                // Add more cases for future levels...

                default:
                    // Fallback: If a stage number is unexpected, perhaps skip the story or load a default.
                    // For now, let's load the game directly if no story is defined for the level.
                    Greenfoot.setWorld(new GameWorld(this.stageNumber));
                    return; 
            }

            // **2. Transition to the selected Story World**
            if (storyWorldToLoad != null) {
                Greenfoot.setWorld(storyWorldToLoad);
            }
        }
    }
}