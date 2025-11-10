import greenfoot.*;
import java.util.List;

/**
 * The boss for Stage 3.
 * (UPDATED with new "Patrol" AI and new animations)
 */
public class nyiRoroBoss extends Actor implements IBoss
{
    // --- Health ---
    private int health;
    private final int maxHealth;
    private AttackIndicator currentIndicator;
    
    // --- State Machine (NEW) ---
    private enum State {
        ENTERING,       // 1. Walking from off-screen
        REPOSITIONING,  // 2. Walking to a new random spot (VULNERABLE)
        ATTACKING,      // 3. Spawning projectiles (INVINCIBLE)
        VULNERABLE,     // 4. Tired animation (VULNERABLE)
        SHIELDING       // 5. Casting shield (INVINCIBLE)
    }
    private State currentState;
    private int direction = 1; // 1 = faces right, -1 = faces left
    private int yPos = 300; // (Adjust this to your liking)
    private int speed = 2; // Walking speed
    private int targetX; // The random spot she is walking towards
    
    // --- Timers ---
    private SimpleTimer stateTimer = new SimpleTimer(); // For timing states
    private SimpleTimer animTimer = new SimpleTimer();  // For animations
    private int animFrame = 0;
    
    // --- Attack ---
    private int attackDamage = 1; // Damage for her projectiles
    
    // --- Invincibility ---
    private final SimpleTimer hurtIFrame = new SimpleTimer();
    private int hurtCooldownMs = 150; 
    
    // --- IMAGES (All New) ---
    // (Loading LEFT-facing images first, as requested)
    private GreenfootImage[] imgWalk_L = new GreenfootImage[5];
    private GreenfootImage[] imgWalk_R = new GreenfootImage[5];
    
    private GreenfootImage[] imgAttack_L = new GreenfootImage[2];
    private GreenfootImage[] imgAttack_R = new GreenfootImage[2];

    private GreenfootImage[] imgTired_L = new GreenfootImage[2];
    private GreenfootImage[] imgTired_R = new GreenfootImage[2];
    
    private GreenfootImage[] imgShield_L = new GreenfootImage[2];
    private GreenfootImage[] imgShield_R = new GreenfootImage[2];


    public nyiRoroBoss(int initialHealth)
    {
        this.health = initialHealth;
        this.maxHealth = initialHealth;
        
        // --- Load ALL images (LEFT-facing first) ---
        
        // --- WALK (5 frames) ---
        imgWalk_L[0] = new GreenfootImage("nyiRoroWalk1.png");
        imgWalk_L[1] = new GreenfootImage("nyiRoroWalk2.png");
        imgWalk_L[2] = new GreenfootImage("nyiRoroWalk3.png");
        imgWalk_L[3] = new GreenfootImage("nyiRoroWalk4.png");
        imgWalk_L[4] = new GreenfootImage("nyiRoroWalk5.png");
        
        // --- ATTACK (2 frames) ---
        imgAttack_L[0] = new GreenfootImage("nyiRoroAttack1.png");
        imgAttack_L[1] = new GreenfootImage("nyiRoroAttack2.png");
        
        // --- TIRED (2 frames) ---
        imgTired_L[0] = new GreenfootImage("nyiRoroTired1.png");
        imgTired_L[1] = new GreenfootImage("nyiRoroTired2.png");
        
        // --- SHIELD (2 frames) ---
        imgShield_L[0] = new GreenfootImage("nyiRoroBubbleShield1.png");
        imgShield_L[1] = new GreenfootImage("nyiRoroBubbleShield2.png");
        
        
        // --- Create all mirrored (RIGHT-facing) images ---
        for(int i=0; i<5; i++) {
            imgWalk_L[i].scale(150, 200); // Scale if needed
            imgWalk_R[i] = new GreenfootImage(imgWalk_L[i]);
            imgWalk_R[i].mirrorHorizontally();
        }
        
        for(int i=0; i<2; i++) {
            imgAttack_L[i].scale(150, 200); // Scale if needed
            imgAttack_R[i] = new GreenfootImage(imgAttack_L[i]);
            imgAttack_R[i].mirrorHorizontally();
            
            imgTired_L[i].scale(150, 200); // Scale if needed
            imgTired_R[i] = new GreenfootImage(imgTired_L[i]);
            imgTired_R[i].mirrorHorizontally();
            
            imgShield_L[i].scale(150, 200); // Scale if needed
            imgShield_R[i] = new GreenfootImage(imgShield_L[i]);
            imgShield_R[i].mirrorHorizontally();
        }
    }

    /**
     * This is called by GameWorld to set the starting position.
     */
    protected void addedToWorld(World world) {
        // Randomly pick a side (0 = left, 1 = right)
        if (Greenfoot.getRandomNumber(2) == 0) {
            // Spawn on the Left
            direction = 1; // She faces/moves Right
            setLocation(-100, yPos); // Start off-screen left
            setImage(imgWalk_R[0]); 
            targetX = 100; // Set her *first* target spot
        } else {
            // Spawn on the Right
            direction = -1; // She faces/moves Left
            setLocation(world.getWidth() + 100, yPos); // Start off-screen right
            setImage(imgWalk_L[0]); 
            targetX = world.getWidth() - 100; // Set her *first* target spot
        }
        
        setState(State.ENTERING); // Start walking in
    }

    /**
     * This is the main AI loop for the boss
     */
    public void act()
    {
        if (getWorld() == null) return;

        // The AI State Machine
        switch (currentState)
        {
            case ENTERING:
                playWalkAnimation(5); // Play 5-frame walk animation
                moveTowardsTarget();  // Move
                
                // Check if she has arrived
                if (hasReachedTarget()) {
                    setState(State.ATTACKING); // Start attack loop
                }
                break;
                
            case REPOSITIONING:
                playWalkAnimation(5); // Play 5-frame walk animation
                moveTowardsTarget();  // Move
                
                // Check if she has arrived
                if (hasReachedTarget()) {
                    setState(State.ATTACKING); // Start attack loop
                }
                break;
                
            case ATTACKING:
                // Loop the 2-frame attack animation
                if (animTimer.hasElapsed(200)) { 
                    animFrame = (animFrame + 1) % 2; 
                    setImage(direction == 1 ? imgAttack_R[animFrame] : imgAttack_L[animFrame]);
                    animTimer.mark();
                }
                
                // Wait for the 2-second attack phase to end
                if (stateTimer.hasElapsed(2000)) {
                    setState(State.VULNERABLE); // Go to "tired" state
                }
                break;
                
            case VULNERABLE:
                // Loop the 2-frame "tired" animation
                if (animTimer.hasElapsed(300)) { 
                    animFrame = (animFrame + 1) % 2; 
                    setImage(direction == 1 ? imgTired_R[animFrame] : imgTired_L[animFrame]);
                    animTimer.mark();
                }

                // Wait for the 3-second vulnerable phase to end
                if (stateTimer.hasElapsed(3000)) {
                    setState(State.SHIELDING);
                }
                break;
                
            case SHIELDING:
                // Loop the 2-frame "shield" animation
                if (animTimer.hasElapsed(250)) { 
                    animFrame = (animFrame + 1) % 2; 
                    setImage(direction == 1 ? imgShield_R[animFrame] : imgShield_L[animFrame]);
                    animTimer.mark();
                }
                
                // Wait for the 2-second shield phase to end
                if (stateTimer.hasElapsed(2000)) {
                    setState(State.REPOSITIONING); // Go back to walking
                }
                break;
        }
    }
    
    /**
     * A helper method to change the boss's state and start timers.
     */
    private void setState(State newState)
    {
        this.currentState = newState;
        stateTimer.mark(); // Reset the main state timer
        animFrame = 0;     // Reset animation frame
        animTimer.mark();
        
        // Set the first frame of the new state's animation
        if (newState == State.ENTERING) {
            setImage(direction == 1 ? imgWalk_R[0] : imgWalk_L[0]);
        }
        else if (newState == State.REPOSITIONING) {
            // Pick a new random X target, far from the edges
            targetX = Greenfoot.getRandomNumber(getWorld().getWidth() - 200) + 100; 
            
            // Check direction and flip image if needed
            int newDir = (targetX > getX()) ? 1 : -1;
            if (newDir != direction) {
                direction = newDir;
            }
            
            setImage(direction == 1 ? imgWalk_R[0] : imgWalk_L[0]);
        }
        else if (newState == State.ATTACKING) {
            setImage(direction == 1 ? imgAttack_R[0] : imgAttack_L[0]);
            spawnProjectiles(); // Spawn 3 projectiles
        }
        else if (newState == State.VULNERABLE) {
            setImage(direction == 1 ? imgTired_R[0] : imgTired_L[0]);
        }
        else if (newState == State.SHIELDING) {
            setImage(direction == 1 ? imgShield_R[0] : imgShield_L[0]);
        }
    }
    
    // --- New Helper Methods for Movement ---
    
    private void playWalkAnimation(int numFrames) {
        if (animTimer.hasElapsed(150)) { // 150ms per frame
            animFrame = (animFrame + 1) % numFrames; 
            setImage(direction == 1 ? imgWalk_R[animFrame] : imgWalk_L[animFrame]);
            animTimer.mark();
        }
    }
    
    private void moveTowardsTarget() {
        if (getX() < targetX) {
            setLocation(getX() + speed, yPos);
        } else if (getX() > targetX) {
            setLocation(getX() - speed, yPos);
        }
    }
    
    private boolean hasReachedTarget() {
        // Check if we are within one "step" of the target
        return Math.abs(getX() - targetX) < speed;
    }

    // --- The rest of your methods ---
    
    private void spawnProjectiles() {
        for (int i = 0; i < 3; i++) {
            int x = Greenfoot.getRandomNumber(getWorld().getWidth());
            int y = getWorld().getHeight() + 40;
            
            BossProjectile p = new BossProjectile();
            getWorld().addObject(p, x, y);
        }
    }

    public double getHealthPercentage()
    {
        return (double)health / maxHealth;
    }
    
    public boolean isAlive() {
        return getWorld() != null;
    }

    @Override
    public void takeDamage(int amount)
    {
        Greenfoot.playSound("hit.mp3");
        // --- UPDATED: Vulnerable while WALKING, ENTERING, or VULNERABLE ---
        if (currentState == State.ATTACKING || currentState == State.SHIELDING) {
            return; // Invincible!
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
        if (getWorld() != null) img.setTransparency(old);
    }
    
    public int getFacing() { 
        return direction; 
    }
}