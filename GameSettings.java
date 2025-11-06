
public class GameSettings  
{
    public static String difficulty = "Easy";
    
    public static final int[][] EnemyHealth = {
        {3, 5, 7}, 
        {5, 7, 9}, 
        {7, 9, 11}  
    };
    
    
    
    public static final int[][] commonFishSize = {
        {60, 75},   
        {90, 120},  
        {120, 150}  
    };

    public static final int[][] rareFishSize = {
        {50, 65},   
        {80, 100},  
        {90, 120}
    };

    public static final int[][] epicFishSize = {
        {40, 55},   
        {60, 75},   
        {75, 100}   
    };

    public static final int[][] commonFishSpeed = {
        {2, 3},
        {3, 4},
        {4, 5}
    };

    public static final int[][] rareFishSpeed = {
        {3, 4},
        {4, 5},
        {5, 6}
    };

    public static final int[][] epicFishSpeed = {
        {4, 5},
        {5, 6},
        {6, 7}
    };
    
    public static int[][] BossHealth = {
      
        { 0,        20,    200 }, 
        { 0,        30,    300 }, 
        { 0,        50,    400 }  
    };

    
    public GameSettings()
    {
    }

    
}