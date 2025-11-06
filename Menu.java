import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class Menu here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Menu extends World
{

    /**
     * Constructor for objects of class Menu.
     * 
     */
    public Menu()
    {    

        super(960, 540, 1); 

 
        GreenfootImage bg = new GreenfootImage("menu_ui_1.jpg"); 

        bg.scale(960, 540);

        setBackground(bg);


        ButtonMenu();
        prepare();
    }

    private void ButtonMenu(){
        BtnTutor Tutorial = new BtnTutor();
        BtnStart Start = new BtnStart();
        BtnExit Exit = new BtnExit();

        addObject(Tutorial, 220, 445); // (actor, x, y)
        addObject(Start,    475, 440); // (actor, x, y)
        addObject(Exit,     740, 450); // (actor, x, y)
    }
    
    /**
     * Prepare the world for the start of the program.
     * That is: create the initial objects and add them to the world.
     */
    private void prepare()
    {
    }
}
