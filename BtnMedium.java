import greenfoot.*;  


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
        
        if (Greenfoot.mouseClicked(this)) 
        {
            GameSettings.difficulty = "Medium";
            Greenfoot.setWorld(new LevelSelectWorld());
        }
    }
}
