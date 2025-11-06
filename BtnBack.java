import greenfoot.*;  


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
        
            if (Greenfoot.mouseClicked(this)) 
        {
            Greenfoot.setWorld(new Menu());
        }
    }
}