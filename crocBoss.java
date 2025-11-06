import greenfoot.*;
import java.util.List;
public class crocBoss extends Actor implements Damageable
{
    
    private int health;
    private final int maxHealth;
    
    
    private enum State {
        ENTERING,           
        INDICATING,         
        ATTACKING,          
        VULNERABLE,         
        LEAVING             
    }
    private State currentState;
    private int direction = -1; 
    private int yPos;           
    private int speed = 2;      
    
    
    private SimpleTimer stateTimer = new SimpleTimer(); 
    private SimpleTimer animTimer = new SimpleTimer();  
    private int animFrame = 0;
    
    
    private AttackIndicator currentIndicator; 
    private int attackDamage = 1; 
    
    
    private final SimpleTimer hurtIFrame = new SimpleTimer();
    private int hurtCooldownMs = 150; 
    
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
        this.yPos = 230; 
        
        
        imgIdleRight = new GreenfootImage("crocClose.png");
        imgIdleRight.scale(300, 250); 
        imgIdleLeft = new GreenfootImage(imgIdleRight);
        imgIdleLeft.mirrorHorizontally();
        
        imgTiredRight = new GreenfootImage("crocTired.png");
        imgTiredRight.scale(300, 250); 
        imgTiredLeft = new GreenfootImage(imgTiredRight);
        imgTiredLeft.mirrorHorizontally();

        
        imgWalkRight[0] = new GreenfootImage("crocWalk1.png");
        imgWalkRight[1] = new GreenfootImage("crocWalk2.png");
        imgWalkRight[2] = new GreenfootImage("crocWalk3.png");
        imgWalkRight[3] = new GreenfootImage("crocWalk4.png");
        
        
        chompAnimRight[0] = new GreenfootImage("crocHalfOpen.png");
        chompAnimRight[1] = new GreenfootImage("crocOpen.png");
        chompAnimRight[2] = new GreenfootImage("crocHalfClose.png");
        chompAnimRight[3] = new GreenfootImage("crocClose.png");
        
        
        for (int i = 0; i < 4; i++) {
            imgWalkRight[i].scale(300, 250);
            chompAnimRight[i].scale(400, 400);
            
            imgWalkLeft[i] = new GreenfootImage(imgWalkRight[i]);
            imgWalkLeft[i].mirrorHorizontally();
            
            chompAnimLeft[i] = new GreenfootImage(chompAnimRight[i]);
            chompAnimLeft[i].mirrorHorizontally();
        }
    }

    protected void addedToWorld(World world) {
        direction = -1;
        setLocation(world.getWidth() + 100, yPos); 
        

        setState(State.ENTERING);
    }

    public void act()
    {
        if (getWorld() == null) {
            return;
        }

        switch (currentState)
        {
            case ENTERING:
                
                if (animTimer.hasElapsed(150)) { 
                    animFrame = (animFrame + 1) % 4; 
                    setImage(direction == 1 ? imgWalkRight[animFrame] : imgWalkLeft[animFrame]);
                    animTimer.mark();
                }
            
                
                setLocation(getX() + (speed * direction), yPos);
                
                
                int stopX_Right = getWorld().getWidth() - 100;
                int stopX_Left = 100;
                
                if ( (direction == -1 && getX() <= stopX_Right) || (direction == 1 && getX() >= stopX_Left) ) {
                    
                    int finalX = (direction == -1) ? stopX_Right : stopX_Left;
                    setLocation(finalX, yPos); 
                    setState(State.INDICATING);
                }
                break;
                
            case INDICATING:
                if (stateTimer.hasElapsed(800)) { 
                    setState(State.ATTACKING);
                }
                break;
                
            case ATTACKING:
                if (animTimer.hasElapsed(400)) { 
                    setImage(direction == 1 ? chompAnimRight[animFrame] : chompAnimLeft[animFrame]);
                    
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
                
                
                if (stateTimer.hasElapsed(3800)) { 
                    setState(State.LEAVING);
                }
                break;
                
            case LEAVING:
                
                if (animTimer.hasElapsed(150)) { 
                    animFrame = (animFrame + 1) % 4; 
                    setImage(direction == 1 ? imgWalkRight[animFrame] : imgWalkLeft[animFrame]);
                    animTimer.mark();
                }

                
                setLocation(getX() + (speed * direction), yPos);
                
                
                if (getX() > getWorld().getWidth() + 300 || getX() < -300) {
                    
                    if (Greenfoot.getRandomNumber(2) == 0) {
                        direction = 1; 
                        setLocation(-300, yPos); 
                    } else {
                        direction = -1; 
                        setLocation(getWorld().getWidth() + 300, yPos);
                    }
                    setState(State.ENTERING); 
                }
                break;
        }
    }
    
    private void setState(State newState)
    {
        this.currentState = newState;
        animFrame = 0;     
        animTimer.mark();  
        
        if (newState == State.ENTERING) {
            
            setImage(direction == 1 ? imgWalkRight[0] : imgWalkLeft[0]);
        }
        else if (newState == State.INDICATING) {
            
            setImage(direction == 1 ? imgIdleRight : imgIdleLeft);
            
            
            stateTimer.mark();
            
            
            List<Boat> boats = getWorld().getObjects(Boat.class);
            if (!boats.isEmpty()) {
                Boat boat = boats.get(0);
                currentIndicator = new AttackIndicator();
                getWorld().addObject(currentIndicator, boat.getX(), boat.getY());
            } else {
                setState(State.VULNERABLE); 
                return;
            }
        }
        else if (newState == State.ATTACKING) {
            
            setImage(direction == 1 ? chompAnimRight[0] : chompAnimLeft[0]);
        }
        else if (newState == State.VULNERABLE) {
            
            setImage(direction == 1 ? imgTiredRight : imgTiredLeft);
            
            
            stateTimer.mark();
        }
        else if (newState == State.LEAVING) {
            direction *= -1; 
            
            
            setImage(direction == 1 ? imgWalkRight[0] : imgWalkLeft[0]);
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
