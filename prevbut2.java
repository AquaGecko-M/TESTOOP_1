import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class prevbut2 here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class prevbut2 extends Actor
{
    public prevbut2()
    {
        GreenfootImage image = new GreenfootImage("prev.png");
        
        image.scale(150, 100); 
        
        setImage(image);
    }
    public void act()
    {
        if (Greenfoot.mouseClicked(this)) 
        {
            Greenfoot.setWorld(new tutorialkeyboard());
        }// Add your action code here.
    }
}
