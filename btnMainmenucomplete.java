import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class btnMainmenucomplete here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class btnMainmenucomplete extends btnAnimation
{
    private boolean readyToClick = false;
    public btnMainmenucomplete() {
        GreenfootImage img = new GreenfootImage("btnMainmenucomplete.png");
        // You can scale it if you need to
        img.scale(200, 100);
        setImage(img);
    }
    
    public void act() {
        super.act();
        if (!readyToClick) {
            readyToClick = true;
            return;
        }
        if (Greenfoot.mouseClicked(this)) {
            Greenfoot.setWorld(new Menu()); // Assumes your main menu is 'Menu'
        }
    }
}
