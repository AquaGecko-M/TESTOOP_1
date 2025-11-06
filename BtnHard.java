import greenfoot.*;  


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
        
            if (Greenfoot.mouseClicked(this)) 
        {
            GameSettings.difficulty = "Hard";
            Greenfoot.setWorld(new LevelSelectWorld());
        }
    }
}
