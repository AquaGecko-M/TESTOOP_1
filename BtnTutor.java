import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class BtnTutor here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class BtnTutor extends Actor
{
    public BtnTutor()
    {
        GreenfootImage image = new GreenfootImage("BtnTutorial.png");
        
        image.scale(150, 100); 
        
        setImage(image);
    }
    public void act()
    {
    if (Greenfoot.mouseClicked(this)) 
        {
            Greenfoot.setWorld(new tutorialkeyboard());
        }
    }
}
