import greenfoot.*;

/**
 * The stage completion screen.
 */
public class menuCompletion extends World
{
    private int stageJustCompleted;

    public menuCompletion(int finalScore, int finalTime, int totalFish, int stageJustCompleted)
    {    
        super(960, 540, 1); 
        
        this.stageJustCompleted = stageJustCompleted; 
        
        // Set the background
        GreenfootImage bg = new GreenfootImage("menuCompletion.png");
        bg.scale(getWidth(), getHeight());
        setBackground(bg);
        
        // --- Display Stats  ---
        GreenfootImage textBg = getBackground();
        textBg.setColor(greenfoot.Color.YELLOW); 
        textBg.setFont(new Font("Arial", true, false, 38));
        
        textBg.drawString("" + totalFish, 660, 265);
        textBg.drawString("" + finalTime, 510, 320);
        textBg.drawString("" + finalScore, 563 , 375);

        // --- Display Stars  ---
        if (finalScore >= 200) {
            addObject(new Star(), 375, 50); 
        }
        if (finalScore >= 350) {
            addObject(new Star(), 475, 50); 
        }
        if (finalScore >= 500) {
            addObject(new Star(), 575, 50); 
        }
        
        prepare();
    }

    private void prepare()
    {
        btnMainmenucomplete btnMainmenucomplete = new btnMainmenucomplete();
        addObject(btnMainmenucomplete,147,479); 
        
        btnNextStage btnNextStage = new btnNextStage(stageJustCompleted); 
        addObject(btnNextStage,812,485); 
    }
}
