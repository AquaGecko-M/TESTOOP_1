import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class BtnBack here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class BtnBack extends Actor
{
      public BtnBack()
    {
        GreenfootImage image = new GreenfootImage("btnBack.png");
        
        image.scale(200,150); 
        
        setImage(image);
    }
    
    public void act()
    {
        // Add your action code here.if (Greenfoot.mouseClicked(this)) {
            if (Greenfoot.mouseClicked(this)) 
        {
            Greenfoot.setWorld(new Menu());
        }
    }
}