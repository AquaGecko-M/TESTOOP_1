import greenfoot.*;

/**
 * A dedicated Actor to display stats like Keys, Coins, and Dash.
 * This fixes the "blurry text" bug by drawing all stats on its
 * own transparent image, NOT on the world's background.
 */
public class StatDisplay extends Actor
{
    // --- Images ---
    private GreenfootImage iconKey;
    private GreenfootImage iconCoin;
    private GreenfootImage iconDash;
    
    // --- Text Settings ---
    private static final Color TextColor = Color.WHITE;
    private static final Color TextBg = new Color(0, 0, 0, 0); // Transparent
    private Font textFont = new Font(true, false, 20); // Bold, not italic, size 20

    public StatDisplay()
    {
        // 1. Create a large, transparent image for our "canvas"
        // (Adjust 200 width, 150 height if needed)
        setImage(new GreenfootImage(200, 150));
        
        // 2. Load all the icons
        iconKey = new GreenfootImage("key_item.png");
        iconKey.scale(50, 25);
        
        iconCoin = new GreenfootImage("Shark.png"); // (Assuming Koin.png is your coin icon)
        // iconCoin.scale( ... ); // Scale if needed
        
        iconDash = new GreenfootImage("DashIcon.png"); // (Using key_item as a placeholder)
        iconDash.scale(70, 70);
    }
    
    /**
     * This is the new "update" method that GameWorld will call.
     * It clears and redraws all stats.
     */
    public void update(int keys, int keysNeeded, int coins, int dashCharges, int dashCapacity)
    {
        // 1. Get our canvas and clear away all old text
        GreenfootImage img = getImage();
        img.clear();
        
        // --- 2. Draw Keys ---
        img.drawImage(iconKey, 10, 15); // Icon at (0, 0) in this actor's image
        GreenfootImage keyLabel = new GreenfootImage(keys + " / " + keysNeeded, 24, TextColor, TextBg, Color.BLACK);
        keyLabel.setFont(textFont);
        img.drawImage(keyLabel, 70, 15); // Text next to icon

        // --- 3. Draw Coins ---
        img.drawImage(iconCoin, -50, 65); // Icon at (0, 50)
        GreenfootImage coinLabel = new GreenfootImage(coins + "$", 24, TextColor, TextBg, Color.BLACK);
        coinLabel.setFont(textFont);
        img.drawImage(coinLabel, 70, 65);

        // --- 4. Draw Dash ---
        img.drawImage(iconDash, 5, 90); // Icon at (0, 100)
        GreenfootImage dashLabel = new GreenfootImage(dashCharges + "/" + dashCapacity, 24, TextColor, TextBg, Color.BLACK);
        dashLabel.setFont(textFont);
        img.drawImage(dashLabel, 70, 115);
    }
        
}