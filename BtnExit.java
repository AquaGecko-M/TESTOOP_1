import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class BtnExit here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class BtnExit extends Actor
{
    public BtnExit()
    {
        GreenfootImage image = new GreenfootImage("BtnExit.png");
        
        image.scale(150, 100); 
        
        setImage(image);
    }
    
    public void act()
    {
        if(Greenfoot.mouseClicked(this)){
            Greenfoot.stop();
        }
    }
}
