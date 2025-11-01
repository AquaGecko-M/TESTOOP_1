/**
 * Write a description of class GameSettings here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class GameSettings  
{
    public static String difficulty = "Easy";
    
    // Ukuran ikan berdasarkan level
    // [level][0] = lebar, [level][1] = tinggi
    public static final int[][] commonFishSize = {
        {160, 180}, //easy
        {150, 170}, //medium
        {140, 160} //hard
    };

    public static final int[][] rareFishSize = {
        {120, 140},
        {110, 130},
        {100, 120}
    };

    public static final int[][] epicFishSize = {
        {100, 120},
        {90, 110},
        {80, 100}
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
