import greenfoot.*;

/**
 * A smart "Back" button that returns to the previous world.
 * (CORRECTED VERSION)
 */
public class btnBackToWorld extends Actor
{
    private World returnToWorld;
    private boolean mouseWasDown = true; // Fixes click-through
    
    public btnBackToWorld(World returnTo) {
        this.returnToWorld = returnTo;
        // Simple text image. You can replace this with your own PNG.
        setImage(new GreenfootImage("Back", 32, Color.WHITE, new Color(100,100,100)));
    }
    
    public void act() {
        super.act();
        if (mouseWasDown) {
            if (Greenfoot.mousePressed(null)) return;
            else mouseWasDown = false;
        }
        
        if (Greenfoot.mouseClicked(this)) {
            
            // First, check if we are returning to the pause menu
            if (returnToWorld instanceof bgMenu) {
                // If so, call its 'resumeGame()' method,
                // which handles all the unpausing logic.
                ((bgMenu)returnToWorld).resumeGame();
            } else {
                // Otherwise, just go back to the world (e.g., the Main Menu)
                Greenfoot.setWorld(returnToWorld);
            }
        }
    }
}