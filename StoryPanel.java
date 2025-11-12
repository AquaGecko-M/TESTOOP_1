import greenfoot.*;

/**
 * StoryPanel: A visual panel that fades in when added to the world.
 */
public class StoryPanel extends Actor {
    private int fadeSpeed = 10;
    private int maxOpacity = 255;
    private int currentOpacity = 0;

    public StoryPanel(String imageName) {
        GreenfootImage img = new GreenfootImage(imageName);
        img.setTransparency(0);
        img.scale(260, 450);
        setImage(img);
    }

    public void act() {
        if (currentOpacity < maxOpacity) {
            currentOpacity += fadeSpeed;
            if (currentOpacity > maxOpacity) {
                currentOpacity = maxOpacity;
            }
            getImage().setTransparency(currentOpacity);
        }
    }
}