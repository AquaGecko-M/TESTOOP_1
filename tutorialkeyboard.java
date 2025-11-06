import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class tutorialkeyboard here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class tutorialkeyboard extends World
{

    /**
     * Constructor for objects of class tutorialkeyboard.
     * 
     */
    public tutorialkeyboard()
    {    

        super(960, 540, 1); 
        

        GreenfootImage bg = new GreenfootImage("4.jpg"); 
        

        bg.scale(960, 540);
        

        setBackground(bg);
        
        Buttonnav();
    }
    
    private void Buttonnav(){
    nextbut next = new nextbut();
    prevbut prev = new prevbut();
    
    addObject(next,    864, 467); // (actor, x, y)
    }
}
    

