import greenfoot.*;  


public class GameMode extends World
{

    
    public GameMode()
    {    

        super(960, 540, 1); 

        GreenfootImage bg = new GreenfootImage("background_mode.png"); 

        bg.scale(960, 540);

        setBackground(bg);

        prepare();
    }
    
    
    private void prepare()
    {
        BtnBack btnBack = new BtnBack();
        addObject(btnBack,899,45);
        BtnEasy btnEasy = new BtnEasy();
        addObject(btnEasy,460,235);
        BtnMedium btnMedium = new BtnMedium();
        addObject(btnMedium,460,335);
        BtnHard btnHard = new BtnHard();
        addObject(btnHard,460,440);
    }
}