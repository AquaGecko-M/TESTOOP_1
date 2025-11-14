import greenfoot.*;

public class menuCompletion extends World
{
    private int stageJustCompleted;

    public menuCompletion(int finalScore, int finalTime, int totalFish, int stageJustCompleted)
    {    
        super(960, 540, 1); 
        
        this.stageJustCompleted = stageJustCompleted; 
        
        GreenfootImage bg = new GreenfootImage("menuCompletion.png");
        bg.scale(getWidth(), getHeight());
        setBackground(bg);
        
        GreenfootImage textBg = getBackground();
        textBg.setColor(greenfoot.Color.YELLOW); 
        textBg.setFont(new Font("Arial", true, false, 38));
        
        textBg.drawString("" + totalFish, 660, 265);
        textBg.drawString("" + finalTime, 510, 320);
        textBg.drawString("" + finalScore, 563 , 375);

        if (finalScore >= 400) {
            addObject(new Star(), 375, 50); 
        }
        if (finalScore >= 600) {
            addObject(new Star(), 475, 50); 
        }
        if (finalScore >= 800) {
            addObject(new Star(), 575, 50); 
        }
        
        prepare();
    }

    private void prepare()
    {
        btnMainmenucomplete btnMainmenucomplete = new btnMainmenucomplete();
        addObject(btnMainmenucomplete,200,460); 
        
        btnNextStage btnNextStage = new btnNextStage(stageJustCompleted); 
        addObject(btnNextStage,800,460); 
    }
}