import greenfoot.*;

/**
 * Button that scales up when hovered, and stays big while hovered.
 */
public class btnAnimation extends Actor
{
    private GreenfootImage originalImage;
    private GreenfootImage scaledImage;
    private boolean isHovering = false;

    protected void addedToWorld(World world)
    {
        originalImage = getImage();
        scaledImage = new GreenfootImage(originalImage);
        int newWidth = (int)(originalImage.getWidth() * 1.2);
        int newHeight = (int)(originalImage.getHeight() * 1.2);
        scaledImage.scale(newWidth, newHeight);
    }

    public void act() 
    {
        // Only react to actual mouse movement, not every frame
        if (Greenfoot.mouseMoved(this) && !isHovering) {
            // Mouse entered this actor
            setImage(scaledImage);
            isHovering = true;
        } 
        else if (Greenfoot.mouseMoved(null) && isHovering && !Greenfoot.mouseMoved(this)) {
            // Mouse moved somewhere else (off this actor)
            setImage(originalImage);
            isHovering = false;
        }
    }
}
