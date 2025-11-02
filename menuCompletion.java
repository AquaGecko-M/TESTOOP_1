import greenfoot.*;

/**
 * The stage completion screen. It displays the player's stats
 * and a star rating.
 * (UPDATED VERSION)
 */
public class menuCompletion extends World
{
    private int stageJustCompleted;

    /**
     * --- CONSTRUCTOR IS NOW CORRECT ---
     * It now accepts all 4 variables from GameWorld.
     */
    public menuCompletion(int finalScore, int finalTime, int totalFish, int stageJustCompleted)
    {    
        super(960, 540, 1); // Use your standard world size
        
        // Store the level we just beat
        this.stageJustCompleted = stageJustCompleted; 
        
        // Set the background
        GreenfootImage bg = new GreenfootImage("menuCompletion.png");
        bg.scale(getWidth(), getHeight());
        setBackground(bg);
        
        // --- Display Stats (Your code is perfect) ---
        GreenfootImage textBg = getBackground();
        textBg.setColor(greenfoot.Color.YELLOW); 
        textBg.setFont(new Font("Arial", true, false, 38));
        
        textBg.drawString("" + totalFish, 660, 265);
        textBg.drawString("" + finalTime, 510, 320);
        textBg.drawString("" + finalScore, 563 , 375);

        // --- Display Stars (Your code is perfect) ---
        if (finalScore >= 200) {
            addObject(new Star(), 375, 50); 
        }
        if (finalScore >= 350) {
            addObject(new Star(), 475, 50); 
        }
        if (finalScore >= 500) {
            addObject(new Star(), 575, 50); 
        }
        
        // --- NOW, call prepare() at the END ---
        prepare();
    }

    /**
     * This method now correctly adds the buttons.
     */
    private void prepare()
    {
        btnMainmenucomplete btnMainmenucomplete = new btnMainmenucomplete();
        addObject(btnMainmenucomplete,147,479); // Using your coordinates
        
        // Create btnNextStage and pass it the level we just completed
        btnNextStage btnNextStage = new btnNextStage(stageJustCompleted); 
        addObject(btnNextStage,812,485); // Using your coordinates
    }
}
