import greenfoot.*;
import java.util.List;

/**
 * The boss for Stage 2. A giant crocodile with a full AI state machine.
 * (COMPLETE AND FIXED VERSION 4.0 - Timer Fix)
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
    
    // --- TIMERS (THE FIX) ---
    // We now have TWO timers, one for states, one for animation.
    private SimpleTimer stateTimer = new SimpleTimer(); // For INDICATING and VULNERABLE
    private SimpleTimer animTimer = new SimpleTimer();  // For ATTACKING
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
    
    private GreenfootImage[] chompAnimRight = new GreenfootImage[4];
    private GreenfootImage[] chompAnimLeft = new GreenfootImage[4];

    public crocBoss(int initialHealth)
    {
        this.health = initialHealth;
        this.maxHealth = initialHealth;
        this.yPos = 300; 
        
        // --- Load ALL images ---
        imgRight = new GreenfootImage("crocClose.png");
        // imgRight.scale(200, 150); 
        imgLeft = new GreenfootImage(imgRight);
        imgLeft.mirrorHorizontally();
        
        chompAnimRight[0] = new GreenfootImage("crocHalfOpen.png");
        chompAnimRight[1] = new GreenfootImage("crocOpen.png");
        chompAnimRight[2] = new GreenfootImage("crocHalfClose.png");
        chompAnimRight[3] = new GreenfootImage("crocClose.png");
        
        for (int i = 0; i < 4; i++) {
            // chompAnimRight[i].scale(200, 150);
            chompAnimLeft[i] = new GreenfootImage(chompAnimRight[i]);
            chompAnimLeft[i].mirrorHorizontally();
        }
    }

    protected void addedToWorld(World world) {
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
                
                int stopX_Right = getWorld().getWidth() - 100;
                int stopX_Left = 100;
                
                // --- This logic is correct ---
                if ( direction == -1 && getX() <= stopX_Right ) {
                    setLocation(stopX_Right, yPos); 
                    setState(State.INDICATING);
                } 
                else if ( direction == 1 && getX() >= stopX_Left ) {
                    setLocation(stopX_Left, yPos);
                    setState(State.INDICATING);
                }
                break;
                
            case INDICATING:
                // We are waiting for the 2-second STATE timer
                if (stateTimer.hasElapsed(2000)) {
                    setState(State.ATTACKING);
                }
                break;
                
            case ATTACKING:
                // We are waiting for the 100ms ANIM timer
                if (animTimer.hasElapsed(100)) { 
                    if (direction == 1) setImage(chompAnimRight[animFrame]);
                    else setImage(chompAnimLeft[animFrame]);
                    
                    if (animFrame == 2) { 
                        performChompDamage();
                    }
                    
                    animFrame++; 
                    animTimer.mark(); // Reset the ANIM timer
                }
                
                if (animFrame >= 4) {
                    setState(State.VULNERABLE);
                }
                break;
                
            case VULNERABLE:
                // We are waiting for the 5-second STATE timer
                if (stateTimer.hasElapsed(5000)) {
                    setState(State.LEAVING);
                }
                break;
                
            case LEAVING:
                // Move off-screen
                setLocation(getX() + (speed * direction), yPos);
                
                // Check if we are fully off-screen
                if (getX() > getWorld().getWidth() + 100 || getX() < -100) {
                    // --- REPOSITION ---
                    if (Greenfoot.getRandomNumber(2) == 0) {
                        direction = 1; 
                        setLocation(-100, yPos); 
                        setImage(imgRight);      
                    } else {
                        direction = -1; 
                        setLocation(getWorld().getWidth() + 100, yPos);
                        setImage(imgLeft);                           
                    }
                    setState(State.ENTERING); // Repeat the loop
                }
                break;
        }
    }
    
    /**
     * --- THIS IS THE CORRECTED setState() METHOD ---
     */
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
            // Start the STATE timer for 2 seconds
            stateTimer.mark();
        }
        else if (newState == State.ATTACKING) {
            // Start the ANIM timer
            animFrame = 0;
            animTimer.mark();
        }
        else if (newState == State.VULNERABLE) {
            setImage(direction == 1 ? imgRight : imgLeft);
            // Start the STATE timer for 5 seconds
            stateTimer.mark();
        }
        else if (newState == State.LEAVING) {
            direction *= -1; 
            setImage(direction == 1 ? imgRight : imgLeft);
            // No timers are started here
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
            
            getWorld().removeObject(this);
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

