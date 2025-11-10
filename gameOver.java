import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
import greenfoot.GreenfootImage;
import greenfoot.Color;
import greenfoot.Font;
/**
 * Write a description of class bgGameOver here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class gameOver extends World {
    private final int finalScore;
    private final String reasonText;
    private final int stageToRetry;

    public gameOver(int finalScore, String reasonText, int stageNumber) {
        super(960, 540, 1);
        this.finalScore = finalScore;
        this.reasonText = reasonText;
        this.stageToRetry = stageNumber; // Store the level to retry
        setupBackground();
        setupUI();
    }

    private void setupBackground() {
        GreenfootImage bg = new GreenfootImage("gameOverScreen.jpeg");
        bg.scale(getWidth(), getHeight());
        setBackground(bg);
    }

    private void setupUI() {
        // 1. Get the background image so we can draw on it
        GreenfootImage bg = getBackground();
        
        // --- Draw the "Reason" Text (e.g., "Game Over!") ---
        
        // 2. Create a new Font: (Name, Bold, Italic, Size)
        Font reasonFont = new Font("Arial", true, false, 48); // 48pt, bold
        
        // 3. Set the font and color for the background
        bg.setFont(reasonFont);
        bg.setColor(greenfoot.Color.RED); // Example: Red color
        
        // 4. Draw the text.
        // NOTE: showText() auto-centers, but drawString() draws from the top-left.
        // You will need to adjust the X coordinate to make it look centered.
        // A good starting point is (getWidth() / 2) - (textWidth / 2).
        int reasonX = getWidth() / 2 - 100; // Manually adjust this X to center your text
        int reasonY = getHeight() / 2 + 180; // Your original Y
        bg.drawString(reasonText, reasonX, reasonY);
    
        // --- Draw the "Score" Text ---
        
        // 2. Create a new Font
        Font scoreFont = new Font("Arial", false, false, 28); // 28pt, normal
        
        // 3. Set the new font and color
        bg.setFont(scoreFont);
        bg.setColor(greenfoot.Color.WHITE); // Example: White color
        
        // 4. Draw the score text
        int scoreX = getWidth() / 2 - 50; // Manually adjust this X to center your text
        int scoreY = getHeight() / 2 + 220; // Your original Y
        bg.drawString("Score: " + finalScore, scoreX, scoreY);
    
        // --- Add your buttons (this code is unchanged) ---
        btnTryAgain tryAgain = new btnTryAgain(stageToRetry);
        btnMainMenuOver toMenu = new btnMainMenuOver();
    
        addObject(tryAgain, getWidth() - 160, getHeight() - 75);
        addObject(toMenu, 160, getHeight() - 75);
    }
}