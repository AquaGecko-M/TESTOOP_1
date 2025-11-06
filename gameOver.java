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
        this.stageToRetry = stageNumber; 
        setupBackground();
        setupUI();
    }

    private void setupBackground() {
        GreenfootImage bg = new GreenfootImage("gameOverScreen.png");
        bg.scale(getWidth(), getHeight());
        setBackground(bg);
    }

    private void setupUI() {
        GreenfootImage bg = getBackground();
        
        
        Font reasonFont = new Font("Arial", true, false, 48); // 48pt, bold
        
        bg.setFont(reasonFont);
        bg.setColor(greenfoot.Color.RED); 
        
 
        int reasonX = getWidth() / 2 - 100; 
        int reasonY = getHeight() / 2 + 180; 
        bg.drawString(reasonText, reasonX, reasonY);
    
        
        Font scoreFont = new Font("Arial", false, false, 28);
        
        bg.setFont(scoreFont);
        bg.setColor(greenfoot.Color.WHITE); 
        
        int scoreX = getWidth() / 2 - 50; 
        int scoreY = getHeight() / 2 + 220; 
        bg.drawString("Score: " + finalScore, scoreX, scoreY);
    
        btnTryAgain tryAgain = new btnTryAgain(stageToRetry);
        btnMainMenuOver toMenu = new btnMainMenuOver();
    
        addObject(tryAgain, getWidth() - 120, getHeight() - 70);
        addObject(toMenu, 120, getHeight() - 70);
    }
}