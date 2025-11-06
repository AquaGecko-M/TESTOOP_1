import greenfoot.*;  


public class BtnStart extends Actor
{
      public BtnStart()
    {
        GreenfootImage image = getImage();
        
        image.scale(200, 150); 
        
        setImage(image);
    }
    
    public void act()
    {
        
            if (Greenfoot.mouseClicked(this)) 
        {
            Greenfoot.setWorld(new GameMode());
        }
    }
}
