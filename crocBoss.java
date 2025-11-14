import greenfoot.*;
import java.util.List;

/**
 * The boss for Stage 2. A giant crocodile with a full AI state machine.
 * (UPDATED to include Walk and Tired animations)
 */
public class crocBoss extends Actor implements IBoss
{
    // --- Health ---
    private int health;
    private final int maxHealth;
    
    // --- State Machine ---
    private enum State {
        Entering,           // 1. Moving onto the screen
        Indicating,         // 2. Showing the 2-second attack indicator
        Attacking,          // 3. Chomping and dealing damage
        Vulnerable,         // 4. Pausing for 5 seconds (player can hit)
        Leaving             // 5. Moving off-screen to reposition
    }
    private State currentState;
    private int direction = -1; // 1 = faces right (moves right), -1 = faces left (moves left)
    private int yPos;           // The Y-position the boss likes
    private int speed = 2;      // Movement speed
    
    // --- Timers ---
    private SimpleTimer stateTimer = new SimpleTimer(); // For timing states
    private SimpleTimer animTimer = new SimpleTimer();  // For animations
    private int animFrame = 0;
    
    // --- Attack ---
    private AttackIndicator currentIndicator; // A reference to our indicator
    private int attackDamage = 1; // How much the chomp hurts
    
    // --- Invincibility ---
    private final SimpleTimer hurtIFrame = new SimpleTimer();
    private int hurtCooldownMs = 150; // 0.15s i-frame
    
    // --- IMAGES (NEW) ---
    private GreenfootImage imgIdleRight;
    private GreenfootImage imgIdleLeft;
    private GreenfootImage imgTiredRight;
    private GreenfootImage imgTiredLeft;
    private GreenfootImage[] imgWalkRight = new GreenfootImage[4];
    private GreenfootImage[] imgWalkLeft = new GreenfootImage[4];
    private GreenfootImage[] chompAnimRight = new GreenfootImage[4];
    private GreenfootImage[] chompAnimLeft = new GreenfootImage[4];

    public crocBoss(int initialHealth)
    {
        this.health = initialHealth;
        this.maxHealth = initialHealth;
        this.yPos = 300; // (Adjust this Y-coordinate to your liking)
        
        // --- Load ALL images ---
        imgIdleRight = new GreenfootImage("crocClose.png");
        imgIdleRight.scale(300, 125); // Your scale
        imgIdleLeft = new GreenfootImage(imgIdleRight);
        imgIdleLeft.mirrorHorizontally();
        
        imgTiredRight = new GreenfootImage("crocTired.png");
        imgTiredRight.scale(300, 125); // Your scale
        imgTiredLeft = new GreenfootImage(imgTiredRight);
        imgTiredLeft.mirrorHorizontally();

        // Load walk animation
        imgWalkRight[0] = new GreenfootImage("crocWalk1.png");
        imgWalkRight[1] = new GreenfootImage("crocWalk2.png");
        imgWalkRight[2] = new GreenfootImage("crocWalk3.png");
        imgWalkRight[3] = new GreenfootImage("crocWalk4.png");
        
        // Load chomp animation
        chompAnimRight[0] = new GreenfootImage("crocHalfOpen.png");
        chompAnimRight[1] = new GreenfootImage("crocOpen.png");
        chompAnimRight[2] = new GreenfootImage("crocHalfClose.png");
        chompAnimRight[3] = new GreenfootImage("crocClose.png");
        
        // Create mirrored versions of walk and chomp
        for (int i = 0; i < 4; i++) {
            // --- Apply your scale to ALL animations ---
            imgWalkRight[i].scale(300, 125);
            chompAnimRight[i].scale(400, 150);
            
            imgWalkLeft[i] = new GreenfootImage(imgWalkRight[i]);
            imgWalkLeft[i].mirrorHorizontally();
            
            chompAnimLeft[i] = new GreenfootImage(chompAnimRight[i]);
            chompAnimLeft[i].mirrorHorizontally();
        }
    }

    protected void addedToWorld(World world) {
        // Start on the RIGHT side, moving LEFT, as requested.
        direction = -1;
        setLocation(world.getWidth() + 100, yPos); 
        
        // --- SETSTATE FIX ---
        // We must call setState *first* so the image is set correctly
        setState(State.Entering);
        // setImage(imgLeft); // This line is now in setState
    }

    /**
     * --- act() METHOD IS UPDATED ---
     */
    public void act()
    {
        if (getWorld() == null) {
            return;
        }

        switch (currentState)
        {
            case Entering:
                // --- 1. Animate Walking ---
                if (animTimer.hasElapsed(150)) { // 150ms per walk frame
                    animFrame = (animFrame + 1) % 4; // Loop frames 0-3
                    setImage(direction == 1 ? imgWalkRight[animFrame] : imgWalkLeft[animFrame]);
                    animTimer.mark();
                }
            
                // --- 2. Move ---
                setLocation(getX() + (speed * direction), yPos);
                
                // --- 3. Check for arrival (This is your correct logic) ---
                int stopXRight = getWorld().getWidth() - 100;
                int stopXLeft = 100;
                
                if ( (direction == -1 && getX() <= stopXRight) || (direction == 1 && getX() >= stopXLeft) ) {
                    // We've arrived. Stop moving.
                    int finalX = (direction == -1) ? stopXRight : stopXLeft;
                    setLocation(finalX, yPos); 
                    setState(State.Indicating);
                }
                break;
                
            case Indicating:
                if (stateTimer.hasElapsed(800)) { // Your timer
                    setState(State.Attacking);
                }
                break;
                
            case Attacking:
                if (animTimer.hasElapsed(400)) { // Your timer
                    setImage(direction == 1 ? chompAnimRight[animFrame] : chompAnimLeft[animFrame]);
                    
                    if (animFrame == 2) { 
                        performChompDamage();
                    }
                    
                    animFrame++; 
                    animTimer.mark();
                }
                
                if (animFrame >= 4) {
                    setState(State.Vulnerable);
                }
                break;
                
            case Vulnerable:
                // The image is already "crocTired.png"
                // We are just waiting for the timer
                if (stateTimer.hasElapsed(3800)) { // Your timer
                    setState(State.Leaving);
                }
                break;
                
            case Leaving:
                // --- 1. Animate Walking ---
                if (animTimer.hasElapsed(150)) { 
                    animFrame = (animFrame + 1) % 4; 
                    setImage(direction == 1 ? imgWalkRight[animFrame] : imgWalkLeft[animFrame]);
                    animTimer.mark();
                }

                // --- 2. Move ---
                setLocation(getX() + (speed * direction), yPos);
                
                // --- 3. Check if fully off-screen (Using your coords) ---
                if (getX() > getWorld().getWidth() + 300 || getX() < -300) {
                    // --- REPOSITION ---
                    if (Greenfoot.getRandomNumber(2) == 0) {
                        direction = 1; 
                        setLocation(-300, yPos); 
                    } else {
                        direction = -1; 
                        setLocation(getWorld().getWidth() + 300, yPos);
                    }
                    setState(State.Entering); 
                }
                break;
        }
    }
    
    /**
     * --- setState() METHOD IS UPDATED ---
     */
    private void setState(State newState)
    {
        this.currentState = newState;
        animFrame = 0;     // Reset animation frame for all states
        animTimer.mark();  // Reset animation timer
        
        if (newState == State.Entering) {
            // Set first frame of walk animation
            setImage(direction == 1 ? imgWalkRight[0] : imgWalkLeft[0]);
        }
        else if (newState == State.Indicating) {
            // Set idle image while indicating
            setImage(direction == 1 ? imgIdleRight : imgIdleLeft);
            
            // Start the state timer
            stateTimer.mark();
            
            // Spawn the indicator
            List<Boat> boats = getWorld().getObjects(Boat.class);
            if (!boats.isEmpty()) {
                Boat boat = boats.get(0);
                currentIndicator = new AttackIndicator();
                getWorld().addObject(currentIndicator, boat.getX(), boat.getY());
            } else {
                setState(State.Vulnerable); // No boat? Skip attack
                return;
            }
        }
        else if (newState == State.Attacking) {
            // Set first frame of chomp animation
            setImage(direction == 1 ? chompAnimRight[0] : chompAnimLeft[0]);
        }
        else if (newState == State.Vulnerable) {
            // --- SET TIRED IMAGE ---
            setImage(direction == 1 ? imgTiredRight : imgTiredLeft);
            
            // Start the vulnerable timer
            stateTimer.mark();
        }
        else if (newState == State.Leaving) {
            direction *= -1; // Reverse direction
            
            // Set first frame of walk animation
            setImage(direction == 1 ? imgWalkRight[0] : imgWalkLeft[0]);
        }
    }
    
    // --- The rest of your file is unchanged and correct ---
    
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
        Greenfoot.playSound("Takedamage.mp3");
        if (currentState != State.Vulnerable) {
            return;
        }
        
        if (!hurtIFrame.hasElapsed(hurtCooldownMs)) return;
        hurtIFrame.mark();
        Greenfoot.playSound("hit.mp3");
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
        if (getWorld() != null) img.setTransparency(old);
    }
    
    public int getFacing() { 
        return direction; 
    }
    
    public boolean isAlive() {
        // If the actor is in a world, it is alive.
        return getWorld() != null;
    }
}

