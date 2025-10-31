import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class Treasure here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Treasure extends Actor
{
    private int value; 
    private boolean isInteractable = true; // Switch to control interaction
    private SimpleTimer cooldownTimer = new SimpleTimer();

    public Treasure() {
        this.value = 50; 
        setImage("treasure_chest.png"); 
        getImage().scale(60, 60); 
    }

    public int getValue() {
        return value;
    }
    
    /**
     * This method runs continuously. We use it to check if the
     * cooldown has finished.
     */
    public void act() {
        // If we are NOT interactable (on cooldown)
        if (!isInteractable) {
            // Check if 2 seconds (2000 milliseconds) have passed
            if (cooldownTimer.hasElapsed(2000)) {
                // Time is up, become interactable again
                isInteractable = true;
            }
        }
    }
    
    /**
     * This will be called by the QuizWorld when the answer is wrong.
     * It starts the 2-second cooldown.
     */
    public void startCooldown() {
        isInteractable = false;
        cooldownTimer.mark(); // Start the timer
    }
    
    /**
     * The hook will ask the treasure if it's "ready" before
     * starting the quiz.
     */
    public boolean isInteractable() {
        return isInteractable;
    }
}