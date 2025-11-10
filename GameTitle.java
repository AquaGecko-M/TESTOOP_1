import greenfoot.*;

/**
 * An animated game title.
 * 1. It scales up and fades in.
 * 2. It pulses slowly.
 */
public class GameTitle extends Actor
{
    // --- States ---
    private enum AnimState {
        ENTERING, // Scaling up and fading in
        IDLING    // Pulsing
    }
    private AnimState currentState;
    
    // --- Images ---
    private GreenfootImage originalImage; // The full-size, 100% visible image
    
    // --- Animation Timers & Values ---
    private int entryFrames = 100; // How long the "ENTERING" animation takes (in frames)
    private int currentEntryFrame = 0;
    
    private int pulseMinAlpha = 200; // The dimmest the pulse will be
    private int pulseMaxAlpha = 255; // The brightest the pulse will be
    private int pulseSpeed = 2;    // How fast it pulses (lower is slower)
    private int currentAlpha = 255;
    private int pulseDirection = -1; // Start by fading out

    /**
     * This runs once when the actor is added to the world.
     */
    protected void addedToWorld(World world)
    {
        // --- !!! CHANGE THIS LINE !!! ---
        originalImage = new GreenfootImage("titleName.png");
        // ---------------------------------
        
        // You can scale the original image here if it's too big
        originalImage.scale(500, 350); 
        
        // Start the animation
        setState(AnimState.ENTERING);
        
        // Start as a tiny, invisible dot to scale up from
        GreenfootImage firstFrame = new GreenfootImage(originalImage);
        firstFrame.scale(1, 1);
        firstFrame.setTransparency(0);
        setImage(firstFrame);
    }
    
    /**
     * The main animation loop
     */
    public void act()
    {
        if (currentState == AnimState.ENTERING) {
            runEnteringAnimation();
        } else if (currentState == AnimState.IDLING) {
            runIdlingAnimation();
        }
    }
    
    /**
     * This method runs every frame while the title is scaling in.
     */
    private void runEnteringAnimation()
    {
        currentEntryFrame++;
        
        // 1. Check if the animation is finished
        if (currentEntryFrame > entryFrames) {
            setState(AnimState.IDLING); // Switch to the "pulse" animation
            return;
        }
        
        // 2. Calculate the progress (a number from 0.0 to 1.0)
        double progress = (double)currentEntryFrame / entryFrames;
        
        // 3. Calculate the new scale (from 10% to 100%)
        double scale = 0.1 + (0.9 * progress); 
        int newWidth = (int)(originalImage.getWidth() * scale);
        int newHeight = (int)(originalImage.getHeight() * scale);

        // 4. Calculate the new transparency (from 0 to 255)
        int newAlpha = (int)(255 * progress);
        
        // 5. Create a new image frame for this step
        GreenfootImage newFrame = new GreenfootImage(originalImage); // Get a clean copy
        newFrame.scale(newWidth, newHeight); // Scale it
        newFrame.setTransparency(newAlpha);  // Fade it
        setImage(newFrame); // Display it
    }
    
    /**
     * This method runs every frame *after* the title has entered.
     * It creates the slow, pulsing fade effect.
     */
    private void runIdlingAnimation()
    {
        // Move the transparency up or down
        currentAlpha += (pulseSpeed * pulseDirection);

        // Check if we hit the top (255) and need to reverse
        if (currentAlpha >= pulseMaxAlpha) {
            currentAlpha = pulseMaxAlpha;
            pulseDirection = -1; // Go down
        }
        // Check if we hit the bottom (200) and need to reverse
        else if (currentAlpha <= pulseMinAlpha) {
            currentAlpha = pulseMinAlpha;
            pulseDirection = 1; // Go up
        }

        // Apply the new transparency
        getImage().setTransparency(currentAlpha);
    }
    
    /**
     * A helper method to cleanly switch states
     */
    private void setState(AnimState newState)
    {
        this.currentState = newState;
        
        if (newState == AnimState.IDLING) {
            // Set the image to the final, full-size version
            // so the "pulse" animation has a clean image to work with.
            setImage(originalImage);
            currentAlpha = 255;
            pulseDirection = -1; // Start by fading out
        }
    }
}