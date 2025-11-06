import greenfoot.*;  


public class BtnEasy extends Actor
{
    public BtnEasy()
    {
        GreenfootImage image = new GreenfootImage("btnEasy.png");
        
        image.scale(150,150); 
        
        setImage(image);
    }
    
    public void act()
    {
        
        if (Greenfoot.mouseClicked(this)) 
        {   
            GameSettings.difficulty = "Easy";
            Greenfoot.setWorld(new LevelSelectWorld());
        }
    }
}
