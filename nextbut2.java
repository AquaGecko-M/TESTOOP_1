import greenfoot.*;  


public class nextbut2 extends Actor
{
    public nextbut2()
    {
        
        GreenfootImage image = new GreenfootImage("next.png");
        
        image.scale(150, 100); 
        
        
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
