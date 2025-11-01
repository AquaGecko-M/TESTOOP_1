import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class btnNextStage here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class btnNextStage extends Actor
{
    private boolean readyToClick = false;
    public btnNextStage() {
        GreenfootImage img = new GreenfootImage("btnNextStage.png");
        // You can scale it if you need to
        img.scale(300, 250);
        setImage(img);
    }
    
    public void act() {
        // --- ADD THESE 3 LINES ---
        // This makes the button wait one frame before it can be clicked,
        // which "consumes" the click from the QuizWorld.
        if (!readyToClick) {
            readyToClick = true;
            return;
        }
        // For now, it just restarts the GameWorld.
        // Later, this could be new GameWorld(level + 1)
        if (Greenfoot.mouseClicked(this)) {
            Greenfoot.setWorld(new GameWorld()); 
        }
    }
}

