/**
 * Write a description of class GameSettings here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class GameSettings  
{
    public static String difficulty = "Easy";
    
    public static final int[][] EnemyHealth = {
        {3, 5, 7}, // Easy (Lvl 1=3, Lvl 2=5, Lvl 3=7)
        {5, 7, 9}, // Medium (Lvl 1=5, Lvl 2=7, Lvl 3=9)
        {7, 9, 11}  // Hard (Lvl 1=7, Lvl 2=9, Lvl 3=11)
    };
    
    // Ukuran ikan berdasarkan level
    // [level][0] = lebar, [level][1] = tinggi
    public static final int[][] commonFishSize = {
        {60, 40},   // Kecil
        {80, 50},  // Normal
        {150, 90}  //hard
    };

    public static final int[][] rareFishSize = {
        {50, 35},   // Kecil
        {75, 45},  // Normal
        {130, 80}
    };

    public static final int[][] epicFishSize = {
        {40, 30},   // Kecil
        {65, 40},   // Normal
        {100, 60}   //epic
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
      // Stage 1, Stage 2, Stage 3
        { 0,        20,    1 }, // Easy (Stage 1 has no boss, so 0 health)
        { 0,        30,    1 }, // Medium
        { 0,        50,    1 }  // Hard
    };

    /**
     * Constructor for objects of class GameSettings
     */
    public GameSettings()
    {
    }

    /**
     * An example of a method - replace this comment with your own
     * 
     * @param  y   a sample parameter for a method
     * @return     the sum of x and y 
     */
}