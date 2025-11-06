import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class BtnMedium here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class BtnMedium extends Actor
{
      public BtnMedium()
    {
        GreenfootImage image = new GreenfootImage("btnMedium.png");
        
        image.scale(150,150); 
        
        setImage(image);
    }
    
    public void act()
    {
        // Add your action code here.if (Greenfoot.mouseClicked(this)) {
        if (Greenfoot.mouseClicked(this)) 
        {
            GameSettings.difficulty = "Medium";
            Greenfoot.setWorld(new LevelSelectWorld());
        }
    }
}
