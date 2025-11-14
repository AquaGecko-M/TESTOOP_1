import greenfoot.*;
import java.util.List; 

public class Boat extends Actor {
    private GreenfootImage[] right;
    private GreenfootImage[] left;
    private GameWorld gameWorld;

    private int frame = 0;
    private int dir = 1; 
    private boolean moving = false;
    private SimpleTimer animTimer = new SimpleTimer();
    private int frameMs = 100;
    private int speed = 1;
    
    private final SimpleTimer hurtTimer = new SimpleTimer();
    private int invincibleMs = 2000; 
    private final SimpleTimer attackTimer = new SimpleTimer();
    
    private int dashCapacity = 3;
    private int dashCharges = 3;
    private boolean isDashing = false;
    private int dashFramesRemaining = 0;
    private final int dashDurationFrames = 14;
    private final int dashSpeed = 14;
    private final SimpleTimer dashCooldownTimer = new SimpleTimer();
    private int dashCooldownMs = 500;
    private boolean dashKeyHeld = false;
    private boolean dashCooldownReady = true;
    
    private int attackRadius; 
    private int attackDamage;
    private int attackCooldownMs; 
    private int attackLifeFrames; 
    private int slashW;
    private int slashH;
    
    private int wOffXRight = -5;   
    private int wOffXLeft  = -40; 
    private int wOffY = 5;  
    
    private GreenfootImage[][] atkRight = new GreenfootImage[PlayerStats.MaxWeaponTier + 1][4];
    private GreenfootImage[][] atkLeft  = new GreenfootImage[PlayerStats.MaxWeaponTier + 1][4];
    private boolean attacking = false;
    private int atkFrame = 0, atkTick = 0, atkDelay = 10; 
    
    
    public Boat(GameWorld world) {
        this.gameWorld = world;

        right = new GreenfootImage[4]; 
        left = new GreenfootImage[4];

        loadRightFrames();
        loadLeftFrames();      
        loadAttackFrames();   
        setImage(right[0]); 
    }

    public void act() {
        moving = false;
        handleDash();
        if (!isDashing) {
            handleMove();
        } else {
            moving = true;
        }
        clampToWorld();
        animate();
        handleAttack();
    }

    private void handleMove() {
        if (Greenfoot.isKeyDown("left") || Greenfoot.isKeyDown("a")) {
            setLocation(getX() - speed, getY());
            dir = -1;
            moving = true;
        } else if (Greenfoot.isKeyDown("right") || Greenfoot.isKeyDown("d")) {
            setLocation(getX() + speed, getY());
            dir = 1;
            moving = true;
        } else {
            moving = false;
        }
    }

    private void clampToWorld() {
        World w = getWorld();
        int x = Math.max(30, Math.min(getX(), w.getWidth() - 30));
        setLocation(x, getY());
    }

    private void animate() {
        if (attacking) {
            if (++atkTick >= atkDelay) { atkTick = 0; atkFrame++; }
            if (atkFrame >= 4) { attacking = false; atkFrame = 0; }
    
            int tier = Math.max(0, Math.min(PlayerStats.weaponTier, PlayerStats.MaxWeaponTier));
    
            GreenfootImage boatBase = (moving ? (dir > 0 ? right[frame] : left[frame])
                                                : (dir > 0 ? right[0]   : left[0]));
    
            GreenfootImage weaponImg = (dir > 0) ? atkRight[tier][atkFrame] : atkLeft[tier][atkFrame];
    
            int bW = boatBase.getWidth();
            int bH = boatBase.getHeight();

            int wW = weaponImg.getWidth();
            int wH = weaponImg.getHeight();

            int offX = (dir > 0) ? wOffXRight : wOffXLeft;
            int offY = wOffY;

            int minX = Math.min(0, offX);
            int minY = Math.min(0, offY);
            int maxX = Math.max(bW, offX + wW);
            int maxY = Math.max(bH, offY + wH);

            int canvasW = maxX - minX;
            int canvasH = maxY - minY;

            int PAD = 6;
            canvasW += PAD * 2;
            canvasH += PAD * 2;

            GreenfootImage composed = new GreenfootImage(canvasW, canvasH);

            int boatDrawX = (canvasW - bW) / 2;
            int boatDrawY = (canvasH - bH) / 2;

            composed.drawImage(boatBase, boatDrawX, boatDrawY);

            int weaponDrawX = boatDrawX + offX;
            int weaponDrawY = boatDrawY + offY;

            composed.drawImage(weaponImg, weaponDrawX, weaponDrawY);

            setImage(composed);
            return;
        }

        if (moving) {
            if (animTimer.millisElapsed() > frameMs) { frame = (frame + 1) % right.length; animTimer.mark(); }
        } else frame = 0;

        setImage((dir > 0) ? right[frame] : left[frame]);
    }

    private void loadRightFrames() {
        for (int i = 0; i < right.length; i++) {
            right[i] = new GreenfootImage("Boat_Kiri_Frame_" + i + ".png");
            right[i].scale(100, 80);
        }
    }

    private void loadLeftFrames() {
        for (int i = 0; i < left.length; i++) {
            left[i] = new GreenfootImage("Boat_Animation_Frame_" + i + ".png");
            left[i].scale(100, 80);
        }
    }
    
    public boolean canBeHit() {
        return hurtTimer.millisElapsed() > invincibleMs;
    }
    
    private void flash() {
        GreenfootImage img = getImage();
        int old = img.getTransparency();
        img.setTransparency(120); 
        img.setTransparency(old);
    }
    
    public void takeDamage(int dmg) {
        if (isDashing) return;   
        if (!canBeHit()) return;   
        Greenfoot.playSound("Takedamage.mp3");
        GameWorld gw = (GameWorld) getWorld();
        gw.addLife(-dmg);
        hurtTimer.mark();

        int knockbackDist = 15;
        int newX = getX() - dir * knockbackDist;  
        newX = Math.max(30, Math.min(newX, getWorld().getWidth() - 30));
        setLocation(newX, getY());

        flash();
        gw.addObject(new DamageFlash(gw.getWidth(), gw.getHeight()), gw.getWidth() / 2, gw.getHeight() / 2);
    }
    
    private void handleAttack() {
        if (Greenfoot.mouseClicked(null) && attackTimer.millisElapsed() > attackCooldownMs) {
        performAttack();
        attackTimer.mark();
        }
    }
    
    private void performAttack() {
        World w = getWorld();
        if (w == null) return;
        Greenfoot.playSound("hit.mp3");
        
        attacking = true;
        atkFrame = 0; atkTick = 0;

        AttackRing ring = new AttackRing(attackRadius, attackLifeFrames);
        w.addObject(ring, getX(), getY());
        
        for (Object obj : getObjectsInRange(attackRadius, enemyShark.class)) {
        enemyShark t = (enemyShark) obj;
        int oldX = t.getX();
        int oldY = t.getY();
        int facing = t.getFacing();

        t.takeDamage(attackDamage);

        if (t.getWorld() != null) {
            SlashEffect fx = new SlashEffect(facing, slashW, slashH);
            w.addObject(fx, oldX, oldY);
            }
        }
        
        for (Object obj : getObjectsInRange(attackRadius, EnemyPuffer.class)) {
        EnemyPuffer p = (EnemyPuffer) obj;
        int oldX = p.getX();
        int oldY = p.getY();
        int facing = p.getFacing();

        p.takeDamage(attackDamage);

        if (p.getWorld() != null) {
            SlashEffect fx = new SlashEffect(facing, slashW, slashH);
            w.addObject(fx, oldX, oldY);
            }
        }
        
        for (Object obj : getObjectsInRange(attackRadius, crocBoss.class)) {
        crocBoss c = (crocBoss) obj;
        int oldX = c.getX();
        int oldY = c.getY();
        int facing = c.getFacing();

        c.takeDamage(attackDamage);

        if (c.getWorld() != null) {
            SlashEffect fx = new SlashEffect(facing, slashW, slashH);
            w.addObject(fx, oldX, oldY);
            }
        }
        
        for (Object obj : getObjectsInRange(attackRadius, nyiRoroBoss.class)) {
            nyiRoroBoss n = (nyiRoroBoss) obj;
            int oldX = n.getX();
            int oldY = n.getY();
            int facing = n.getFacing(); 

            n.takeDamage(attackDamage);

            if (n.getWorld() != null) {
                SlashEffect fx = new SlashEffect(facing, slashW, slashH);
                w.addObject(fx, oldX, oldY);
            }
        }
    }
    
    private void handleDash() {
        if (isDashing) {
            continueDash();
            return;
        }

        boolean spaceDown = Greenfoot.isKeyDown("space");
        if (spaceDown && !dashKeyHeld && dashCharges > 0
                && (dashCooldownReady || dashCooldownTimer.millisElapsed() > dashCooldownMs)) {
            startDash();
        }
        dashKeyHeld = spaceDown;
    }

    private void startDash() {
        isDashing = true;
        dashFramesRemaining = dashDurationFrames;
        dashCharges = Math.max(0, dashCharges - 1);
        dashCooldownReady = false;
        dashCooldownTimer.mark();
        Greenfoot.playSound("Dash.mp3");
        gameWorld.notifyDashChanged(dashCharges, dashCapacity);
    }

    private void continueDash() {
        moving = true;
        setLocation(getX() + dir * dashSpeed, getY());
        dashFramesRemaining--;
        if (dashFramesRemaining <= 0) {
            isDashing = false;
            hurtTimer.mark();
        }
    }

    public void setSpeed(int newSpeed) {
        speed = Math.max(0, newSpeed);
    }

    public void setDashCapacity(int capacity) {
        dashCapacity = Math.max(0, capacity);
        dashCharges = Math.min(dashCharges, dashCapacity);
        gameWorld.notifyDashChanged(dashCharges, dashCapacity);
    }

    public void restoreDashFull() {
        dashCharges = dashCapacity;
        dashCooldownReady = true;
        dashCooldownTimer.mark();
        gameWorld.notifyDashChanged(dashCharges, dashCapacity);
    }

    public int getDashCharges() {
        return dashCharges;
    }

    public int getDashCapacity() {
        return dashCapacity;
    }
    
    private void loadAttackFrames() {
        for (int t = 0; t <= PlayerStats.MaxWeaponTier; t++) {
            for (int f = 0; f < 4; f++) {
                GreenfootImage r = new GreenfootImage("atk_t" + t + "_" + f + ".png");
                atkRight[t][f] = r;
    
                GreenfootImage l = new GreenfootImage(r);
                l.mirrorHorizontally();
                atkLeft[t][f] = l;
            }
        }
    }
    
    public void syncWeaponFromStats() {
        int t = Math.max(0, Math.min(PlayerStats.weaponTier, PlayerStats.MaxWeaponTier));
        attackRadius     = PlayerStats.WeaponRadius[t];
        attackDamage     = PlayerStats.WeaponDamage[t];
        attackCooldownMs = PlayerStats.WeaponCooldown[t];
        attackLifeFrames = PlayerStats.WeaponRingLife[t];
        slashW           = PlayerStats.WeaponSlashW[t];
        slashH           = PlayerStats.WeaponSlashH[t];
    }
}