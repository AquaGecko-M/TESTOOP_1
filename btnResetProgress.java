import greenfoot.*;

// Your "Reset Progress" button
public class btnResetProgress extends Actor
{
    private boolean clickedOnce = false;
    private GreenfootImage imgDefault = new GreenfootImage("Reset Progress", 24, Color.RED, new Color(0,0,0,0));
    private GreenfootImage imgConfirm = new GreenfootImage("Click again to confirm", 24, Color.ORANGE, new Color(0,0,0,0));
    private GreenfootImage imgDone = new GreenfootImage("Progress Reset!", 24, Color.GREEN, new Color(0,0,0,0));

    public btnResetProgress() {
        setImage(imgDefault);
    }
    
    public void act() {
        if (Greenfoot.mouseClicked(this)) {
            if (clickedOnce) {
                // 2. Clicked a second time: Reset and show confirmation
                ProgressTracker.resetProgress();
                setImage(imgDone);
                clickedOnce = false; // Reset button
            } else {
                // 1. Clicked once: Show a warning
                setImage(imgConfirm);
                clickedOnce = true;
            }
        }
    }
}