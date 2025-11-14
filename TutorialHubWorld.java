import greenfoot.*;

public class TutorialHubWorld extends World
{
    public TutorialHubWorld()
    {    
        super(960, 540, 1); 
        
        GreenfootImage bg = new GreenfootImage("1.png");
        bg.scale(getWidth(), getHeight());
        setBackground(bg);
        
        getBackground().setColor(Color.YELLOW);
        getBackground().setFont(new Font("Arial", true, false, 48));
        
        prepare();
    }
    
    private void prepare()
    {
        addObject(new btnTutorialT(), 250, 200);
        addObject(new btnTutorialEconomy(), 250, 360);
        addObject(new btnTutorialFish(), 710, 200);
        addObject(new btnTutorialMore(), 710, 360);
        
        addObject(new BtnBack(), 80, 500);  
    }
}