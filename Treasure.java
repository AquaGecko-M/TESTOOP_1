import greenfoot.*;  


public class Treasure extends Actor
{
    private int value; 
    private boolean isInteractable = true; 
    private SimpleTimer cooldownTimer = new SimpleTimer();

    public Treasure() {
        this.value = 50; 
        setImage("treasure_chest.png"); 
        getImage().scale(60, 60); 
    }

    public int getValue() {
        return value;
    }
    
    
    public void act() {
        
        if (!isInteractable) {
            
            if (cooldownTimer.hasElapsed(2000)) {
                
                isInteractable = true;
            }
        }
    }
    
    
    public void startCooldown() {
        isInteractable = false;
        cooldownTimer.mark(); 
    }
    
    
    public boolean isInteractable() {
        return isInteractable;
    }
}