import greenfoot.*;
import java.util.List;

/**
 * The boss for Stage 2. A giant crocodile with a full AI state machine.
 * (COMPLETE AND FIXED VERSION 3.0)
 */
public class crocBoss extends Actor implements Damageable
{
    // --- Health ---
    private int health;
    private final int maxHealth;
    
    // --- State Machine ---
    private enum State {
        ENTERING,           // 1. Moving onto the screen
        INDICATING,         // 2. Showing the 2-second attack indicator
        ATTACKING,          // 3. Chomping and dealing damage
        VULNERABLE,         // 4. Pausing for 5 seconds (player can hit)
        LEAVING             // 5. Moving off-screen to reposition
    }
    private State currentState;
    private int direction = -1; // 1 = faces right (moves right), -1 = faces left (moves left)
    private int yPos;           // The Y-position the boss likes
    private int speed = 2;      // Movement speed
    
    // --- Timers ---
    private SimpleTimer stateTimer = new SimpleTimer(); // For timing states
    private SimpleTimer animTimer = new SimpleTimer();  // For attack animation
    private int animFrame = 0;
    
    // --- Attack ---
    private AttackIndicator currentIndicator; // A reference to our indicator
    private int attackDamage = 1; // How much the chomp hurts
    
    // --- Invincibility ---
    private final SimpleTimer hurtIFrame = new SimpleTimer();
    private int hurtCooldownMs = 150; // 0.15s i-frame
    
    // --- IMAGES ---
    private GreenfootImage imgRight;
    private GreenfootImage imgLeft;
    
    // Animation frames for the chomp (NEEDS 4 IMAGES)
    private GreenfootImage[] chompAnimRight = new GreenfootImage[4];
    private GreenfootImage[] chompAnimLeft = new GreenfootImage[4];

    public crocBoss(int initialHealth)
    {
        this.health = initialHealth;
        this.maxHealth = initialHealth;
        this.yPos = 300; // (Adjust this Y-coordinate to your liking)
        
        // --- Load ALL images ---
        imgRight = new GreenfootImage("crocClose.png");
        // imgRight.scale(200, 150); // Scale as needed
        imgLeft = new GreenfootImage(imgRight);
        imgLeft.mirrorHorizontally();
        
        chompAnimRight[0] = new GreenfootImage("crocHalfOpen.png");
        chompAnimRight[1] = new GreenfootImage("crocOpen.png");
        chompAnimRight[2] = new GreenfootImage("crocHalfClose.png");
        chompAnimRight[3] = new GreenfootImage("crocClose.png");
        
        for (int i = 0; i < 4; i++) {
            // (Scale them if needed)
            // chompAnimRight[i].scale(200, 150);
            chompAnimLeft[i] = new GreenfootImage(chompAnimRight[i]);
            chompAnimLeft[i].mirrorHorizontally();
        }
    }

    protected void addedToWorld(World world) {
        // Start on the RIGHT side, moving LEFT, as requested.
        direction = -1;
        setLocation(world.getWidth() + 100, yPos); 
        setImage(imgLeft);
        setState(State.ENTERING);
    }

    /**
     * --- THIS IS THE CORRECTED act() METHOD ---
     */
    public void act()
    {
        if (getWorld() == null) {
            return;
        }

        switch (currentState)
        {
            case ENTERING:
                // Move onto the screen
                setLocation(getX() + (speed * direction), yPos);
                
                // Define our "stop" positions
                int stopX_Right = getWorld().getWidth() - 100; // 100px from right edge
                int stopX_Left = 100;                       // 100px from left edge
                
                // --- THIS IS THE FIX ---
                // The check MUST be specific to the direction
                
                // If we are moving LEFT (dir -1) and we pass our stop point
                if ( direction == -1 && getX() <= stopX_Right ) {
                    setLocation(stopX_Right, yPos); // Lock position
                    setState(State.INDICATING);
                } 
                // If we are moving RIGHT (dir 1) and we pass our stop point
                else if ( direction == 1 && getX() >= stopX_Left ) {
                    setLocation(stopX_Left, yPos); // Lock position
                    setState(State.INDICATING);
                }
                // If neither is true, we just keep moving.
                break;
                
            case INDICATING:
                if (stateTimer.hasElapsed(500)) {
                    setState(State.ATTACKING);
                }
                break;
                
            case ATTACKING:
                if (animTimer.hasElapsed(100)) { 
                    if (direction == 1) setImage(chompAnimRight[animFrame]);
                    else setImage(chompAnimLeft[animFrame]);
                    
                    if (animFrame == 2) { 
                        performChompDamage();
                    }
                    
                    animFrame++; 
                    animTimer.mark();
                }
                
                if (animFrame >= 4) {
                    setState(State.VULNERABLE);
                }
                break;
                
            case VULNERABLE:
                if (stateTimer.hasElapsed(2000)) {
                    setState(State.LEAVING);
                }
                break;
                
            case LEAVING:
                // Move off-screen (direction was flipped in setState)
                setLocation(getX() + (speed * direction), yPos);
                
                // Check if we are fully off-screen
                if (getX() > getWorld().getWidth() + 100 || getX() < -100) {
                    // --- REPOSITION ---
                    if (Greenfoot.getRandomNumber(2) == 0) {
                        direction = 1; // Go right
                        setLocation(-300, yPos); // Start at left
                        setImage(imgRight);      
                    } else {
                        direction = -1; // Go left
                        setLocation(getWorld().getWidth() + 300, yPos); // Start at right
                        setImage(imgLeft);                           
                    }
                    setState(State.ENTERING); // Repeat the loop
                }
                break;
        }
    }
    
    private void setState(State newState)
    {
        this.currentState = newState;
        
        if (newState == State.INDICATING) {
            List<Boat> boats = getWorld().getObjects(Boat.class);
            if (!boats.isEmpty()) {
                Boat boat = boats.get(0);
                currentIndicator = new AttackIndicator();
                getWorld().addObject(currentIndicator, boat.getX(), boat.getY());
            } else {
                setState(State.VULNERABLE);
                return;
            }
            stateTimer.mark();
        }
        else if (newState == State.ATTACKING) {
            animFrame = 0;
            animTimer.mark();
        }
        else if (newState == State.VULNERABLE) {
            setImage(direction == 1 ? imgRight : imgLeft);
            stateTimer.mark();
        }
        else if (newState == State.LEAVING) {
            direction *= -1; // Reverse direction
            setImage(direction == 1 ? imgRight : imgLeft); // Flip image
        }
    }
    
    private void performChompDamage() {
        if (currentIndicator != null && currentIndicator.getWorld() != null) {
            currentIndicator.dealDamage(attackDamage);
            getWorld().removeObject(currentIndicator);
        }
    }

    public double getHealthPercentage()
    {
        return (double)health / maxHealth;
    }

    @Override
    public void takeDamage(int amount)
    {
        if (currentState != State.VULNERABLE) {
            return;
        }
        
        if (!hurtIFrame.hasElapsed(hurtCooldownMs)) return;
        hurtIFrame.mark();

        health -= amount;
        flash(); 

        if (health <= 0) {
            if (currentIndicator != null && currentIndicator.getWorld() != null) {
                getWorld().removeObject(currentIndicator);
            }
            
            GameWorld gw = (GameWorld)getWorld();
            gw.addScore(100); 
            gw.addKeyItem();
            
            getWorld().removeObject(this); // <-- THIS IS THE ONLY "DELETE"
        }
    }
    
    private void flash() {
        GreenfootImage img = getImage();
        int old = img.getTransparency();
        img.setTransparency(140);
        Greenfoot.delay(2); 
        if (getWorld() != null) img.setTransparency(old);
    }
}

