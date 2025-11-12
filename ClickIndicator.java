import greenfoot.*;

public class ClickIndicator extends Actor {
    private GreenfootImage baseImage;
    private int transparency = 255;
    private int fadeSpeed = -5;
    private static final int TEXT_Y_POSITION = 600;

    public ClickIndicator() {
        baseImage = new GreenfootImage("CLICK ANYWHERE", 32, Color.WHITE, new Color(0, 0, 0, 0)); 
        setImage(baseImage);
    }

    public void act() {
        transparency += fadeSpeed;

        // Clamp and reverse direction
        if (transparency > 255) {
            transparency = 255;
            fadeSpeed = -fadeSpeed;
        } else if (transparency < 50) {
            transparency = 50;
            fadeSpeed = -fadeSpeed;
        }

        getImage().setTransparency(transparency);
    }
}
