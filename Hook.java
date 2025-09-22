import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class Hook here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Hook extends Actor
{
    /**
     * Act - do whatever the Hook wants to do. This method is called whenever
     * the 'Act' or 'Run' button gets pressed in the environment.
     */
    public void act()
    {
        if (Greenfoot.isKeyDown("up")) 
        {
            setLocation(getX(), getY() - 2);
        }
        if (Greenfoot.isKeyDown("down")) {
            setLocation(getX(), getY() + 2);
        }
        
        Actor fish = getOneIntersectingObject(Fish.class);
        if (fish != null) {
            getWorld().removeObject(fish);
            // You can later add score logic here
        }
    }
}
