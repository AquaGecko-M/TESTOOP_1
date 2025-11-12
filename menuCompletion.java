import greenfoot.*;

/**
 * The stage completion screen.
 * (UPDATED VERSION)
 */
public class menuCompletion extends World
{
    // --- ADD THIS VARIABLE ---
    private int stageJustCompleted;

    /**
     * --- CONSTRUCTOR IS UPDATED ---
     * Now accepts 4 variables, including the stageNumber.
     */
    public menuCompletion(int finalScore, int finalTime, int totalFish, int stageJustCompleted)
    {    
        super(960, 540, 1); 
        
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
        if (finalScore >= 400) {
            addObject(new Star(), 375, 50); 
        }
        if (finalScore >= 600) {
            addObject(new Star(), 475, 50); 
        }
        if (finalScore >= 800) {
            addObject(new Star(), 575, 50); 
        }
        
        // --- Call prepare() at the END ---
        prepare();
    }

    /**
     * This method now correctly adds the buttons.
     */
    private void prepare()
    {
        btnMainmenucomplete btnMainmenucomplete = new btnMainmenucomplete();
        addObject(btnMainmenucomplete,200,460); // Using your coordinates
        
        // Create btnNextStage and pass it the level we just completed
        btnNextStage btnNextStage = new btnNextStage(stageJustCompleted); 
        addObject(btnNextStage,800,460); // Using your coordinates
    }
}
