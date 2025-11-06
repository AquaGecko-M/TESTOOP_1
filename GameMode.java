import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class GameMode here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class GameMode extends World
{

    /**
     * Constructor for objects of class GameMode.
     * 
     */
    public GameMode()
    {    

        super(960, 540, 1); 

        GreenfootImage bg = new GreenfootImage("background_mode.png"); 

        bg.scale(960, 540);

        setBackground(bg);

        prepare();
    }
    
    /**
     * Prepare the world for the start of the program.
     * That is: create the initial objects and add them to the world.
     */
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