import greenfoot.*;

/**
 * This is a helper class, not an Actor.
 * Its job is to save and load the player's progress.
 *
 * (CORRECTED VERSION 3: Handles null *and* empty strings)
 */
public class ProgressTracker  
{
    private static final int SAVE_KEY = 1;

    /**
     * Gets the highest level the player has currently unlocked.
     * If no save file is found, it defaults to 1.
     */
    public static int getHighestLevelUnlocked() {
        if (UserInfo.isStorageAvailable()) {
            UserInfo storage = UserInfo.getMyInfo();
            String savedLevel = storage.getString(SAVE_KEY); 
            
            // Check if the saved value is null OR if it's an empty string ""
            if (savedLevel == null || savedLevel.isEmpty()) {
                // No save file found, or save file is empty. Default to level 1.
                return 1;
            } else {
                // We found a valid number string (like "1" or "2"). Convert it.
                return Integer.parseInt(savedLevel); 
            }
        } else {
            // Storage is not available. Default to 1.
            return 1;
        }
    }

    /**
     * Call this when a player beats a level.
     * (e.g., if you beat level 1, call this with completedLevel = 1)
     */
    public static void unlockNextLevel(int completedLevel) {
        int newHighestLevel = completedLevel + 1;
        int currentHighest = getHighestLevelUnlocked(); 

        if (newHighestLevel > currentHighest) {
            if (UserInfo.isStorageAvailable()) {
                UserInfo storage = UserInfo.getMyInfo();
                storage.setString(SAVE_KEY, String.valueOf(newHighestLevel));
                storage.store(); 
            }
        }
    }
    /**
     * Resets all game progress back to the default.
     * Sets the highest level unlocked back to 1.
     */
    public static void resetProgress() {
        if (UserInfo.isStorageAvailable()) {
            UserInfo storage = UserInfo.getMyInfo();
            storage.setString(SAVE_KEY, "1");
            storage.store();
        }
    }
}
