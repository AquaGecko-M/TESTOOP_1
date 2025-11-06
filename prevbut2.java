import greenfoot.*;  


public class prevbut2 extends Actor
{
    public prevbut2()
    {
        GreenfootImage image = new GreenfootImage("prev.png");
        
        image.scale(150, 100); 
        
        setImage(image);
    }
    public void act()
    {
        if (Greenfoot.mouseClicked(this)) 
        {
            Greenfoot.setWorld(new tutorialkeyboard());
        }
    }
}
