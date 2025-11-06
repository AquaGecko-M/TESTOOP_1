import greenfoot.*;  


public class btnMainmenucomplete extends Actor
{
    private boolean readyToClick = false;
    public btnMainmenucomplete() {
        GreenfootImage img = new GreenfootImage("btnMainmenucomplete.png");
        img.scale(300, 200);
        setImage(img);
    }
    
    public void act() {
        if (!readyToClick) {
            readyToClick = true;
            return;
        }
        if (Greenfoot.mouseClicked(this)) {
            Greenfoot.setWorld(new Menu()); 
        }
    }
}
