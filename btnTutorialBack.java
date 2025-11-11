import greenfoot.*;

/**
 * The "Back" button for the tutorial slideshow.
 * Returns to the main TutorialHubWorld.
 */
public class btnTutorialBack extends Actor
{
    private boolean mouseWasDown = true; // Fixes click-through
    
    public btnTutorialBack()
    {
        GreenfootImage image = new GreenfootImage("btnTutorialBack.png");
        
        image.scale(150,90); 
        
        // 3. Atur gambar yang sudah dikecilkan kembali ke aktor
        setImage(image);
    }
    
    public void act() 
    {
        // Fix for click-through bug
        if (mouseWasDown) {
            if (Greenfoot.mousePressed(null)) return;
            else mouseWasDown = false;
        }
        
        if (Greenfoot.mouseClicked(this))
        {
            // Go back to the tutorial hub
            Greenfoot.setWorld(new TutorialHubWorld());
        }
    }
}
