import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class tutorialmouse here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class tutorialmouse extends World
{

    /**
     * Constructor for objects of class tutorialmouse.
     * 
     */
    public tutorialmouse()
    {    
 
        super(960, 540, 1); 
    
        GreenfootImage bg = new GreenfootImage("5.jpg"); 
        
        bg.scale(960, 540);
        
        setBackground(bg);
        
        Buttonnav();
    
    }
    
        private void Buttonnav(){
    nextbut2 next2 = new nextbut2();
    prevbut2 prev2 = new prevbut2();
    
    addObject(prev2, 103, 467); // (actor, x, y)
    addObject(next2,    864, 467); // (actor, x, y)
    }
    
}
