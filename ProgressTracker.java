import greenfoot.*;


public class ProgressTracker  
{
    private static final int SAVE_KEY = 1;

    
    public static int getHighestLevelUnlocked() {
        if (UserInfo.isStorageAvailable()) {
            UserInfo storage = UserInfo.getMyInfo();
            String savedLevel = storage.getString(SAVE_KEY); 
            
            
            if (savedLevel == null || savedLevel.isEmpty()) {
                
                return 1;
            } else {
                
                return Integer.parseInt(savedLevel); 
            }
        } else {
            
            return 1;
        }
    }

    
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
    
    public static void resetProgress() {
        if (UserInfo.isStorageAvailable()) {
            UserInfo storage = UserInfo.getMyInfo();
            storage.setString(SAVE_KEY, "1");
            storage.store();
        }
    }
}
