import greenfoot.*;  


public class nextbut extends Actor
{

    public nextbut()
    {
        
        GreenfootImage image = new GreenfootImage("next.png");;
        
        image.scale(150, 100); 
        
        
        setImage(image);
    }
    
    public void act()
    {
        if (Greenfoot.mouseClicked(this)) 
        {
            Greenfoot.setWorld(new tutorialmouse());
        }
    }
}
