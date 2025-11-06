import greenfoot.*;  


public class BtnExit extends Actor
{
    public BtnExit()
    {
        GreenfootImage image = new GreenfootImage("BtnExit.png");
        
        image.scale(150, 100); 
        
        setImage(image);
    }
    
    public void act()
    {
        if(Greenfoot.mouseClicked(this)){
            Greenfoot.stop();
        }
    }
}
