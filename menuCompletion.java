import greenfoot.*;

/**
 * The stage completion screen. It displays the player's stats
 * and a star rating.
 * (Image: menuCompletion.png)
 */
public class menuCompletion extends World
{
    /**
     * Constructor for the completion screen.
     * @param finalScore The player's total score.
     * @param finalTime The player's time left.
     * @param totalFish The total fish collected.
     */
    public menuCompletion(int finalScore, int finalTime, int totalFish)
    {    
        super(960, 540, 1); // Use your standard world size
        
        // Set the background
        GreenfootImage bg = new GreenfootImage("menuCompletion.png");
        bg.scale(getWidth(), getHeight());
        setBackground(bg);
        
        // --- Add Buttons ---
        addObject(new btnMainmenucomplete(), 200, 450); // Adjust X/Y as needed
        addObject(new btnNextStage(), 760, 450);        // Adjust X/Y as needed
        
        // --- Display Stats ---
        // You'll need to adjust the X/Y coordinates to match your background image
        GreenfootImage textBg = getBackground();
        textBg.setColor(greenfoot.Color.YELLOW); // Set text color
        textBg.setFont(new Font("Arial", true, false, 38));
        
        textBg.drawString("" + totalFish, 660, 265);
        textBg.drawString("" + finalTime, 510, 320);
        textBg.drawString("" + finalScore, 563 , 375);

        // --- Display Stars based on Score ---
        if (finalScore >= 200) {
            addObject(new Star(), 375, 50); // Adjust X/Y for 1st star
        }
        if (finalScore >= 350) {
            addObject(new Star(), 475, 50); // Adjust X/Y for 2nd star
        }
        if (finalScore >= 500) {
            addObject(new Star(), 575, 50); // Adjust X/Y for 3rd star
        }
    }


    private void prepare()
    {
        btnMainmenucomplete btnMainmenucomplete = new btnMainmenucomplete();
        addObject(btnMainmenucomplete,146,463);
        btnMainmenucomplete.setLocation(170,482);
        btnMainmenucomplete.setLocation(147,479);
        btnNextStage btnNextStage = new btnNextStage();
        addObject(btnNextStage,746,436);
        btnNextStage.setLocation(818,479);
        btnNextStage.setLocation(819,488);
        btnNextStage.setLocation(806,464);
        btnNextStage.setLocation(805,476);
        btnNextStage.setLocation(812,485);
    }
}