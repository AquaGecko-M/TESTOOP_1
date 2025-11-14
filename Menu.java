import greenfoot.*;
public class Menu extends World
{
    private GreenfootSound backgroundMusic;
    private GreenfootImage[] bgFrames = new GreenfootImage[4];
    private int currentFrame = 0;
    private SimpleTimer animTimer = new SimpleTimer();
    private int animSpeedMs = 500;

    public Menu()
    {    
        super(960, 540, 1); 
        bgFrames[0] = new GreenfootImage("MenuF1.png");
        bgFrames[1] = new GreenfootImage("MenuF2.png");
        bgFrames[2] = new GreenfootImage("MenuF3.png");
        bgFrames[3] = new GreenfootImage("MenuF4.png");

        for (int i = 0; i < bgFrames.length; i++) {
            bgFrames[i].scale(960, 540);
        }

        setBackground(bgFrames[currentFrame]);

        animTimer.mark();
        addObject(new GameTitle(), getWidth() / 2, 200); 
        SoundManager.play("Menu_Awal.mp3", 0);

        prepare();
    }

    public void act()
    {

        if (animTimer.hasElapsed(animSpeedMs))
        {
            currentFrame = (currentFrame + 1) % 4;
            setBackground(bgFrames[currentFrame]);
            animTimer.mark();
        }
    }

    private void prepare()
    {
        BtnTutor Tutorial = new BtnTutor();
        BtnStart Start = new BtnStart();
        BtnExit Exit = new BtnExit();

        addObject(Tutorial, 250, 445);
        addObject(Start, 480, 440);
        addObject(Exit, 710, 450);
    }
}