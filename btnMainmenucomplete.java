import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class btnMainmenucomplete here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
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
