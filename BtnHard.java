import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class BtnHard here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class BtnHard extends Actor
{
      public BtnHard()
    {
        GreenfootImage image = new GreenfootImage("bntHard.png");
        
        image.scale(150,150); 
        
        setImage(image);
    }
    
    public void act()
    {
        // Add your action code here.if (Greenfoot.mouseClicked(this)) {
            if (Greenfoot.mouseClicked(this)) 
        {
            GameSettings.difficulty = "Hard";
            Greenfoot.setWorld(new LevelSelectWorld());
        }
    }
}
