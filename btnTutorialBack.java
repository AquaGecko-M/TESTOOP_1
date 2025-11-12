import greenfoot.*;

/**
 * The "Back" button for the tutorial slideshow.
 * Returns to the main TutorialHubWorld.
 */
public class btnTutorialBack extends btnAnimation
{
    private boolean mouseWasDown = true; // Fixes click-through
    
    public btnTutorialBack()
    {
        GreenfootImage image = new GreenfootImage("btnTutorialBack.png");
        
        image.scale(150, 75); 
        
        // 3. Atur gambar yang sudah dikecilkan kembali ke aktor
        setImage(image);
    }
    
    public void act() 
    {
        super.act();
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
