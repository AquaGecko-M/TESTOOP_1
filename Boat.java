import greenfoot.*;

public class Boat extends Actor {
    private GreenfootImage[] right;
    private GreenfootImage[] left;
    
    // --- THIS IS THE FIX (Step 1) ---
    // We create our own variable to store the GameWorld.
    // We call it "gameWorld" to avoid the name conflict.
    private GameWorld gameWorld;
    
    private int attackDamage = 1;

    private int frame = 0;
    private int dir = 1; // 1 = kanan, -1 = kiri
    private boolean moving = false;
    private SimpleTimer animTimer = new SimpleTimer();
    private int frameMs = 100;
    private int speed = 1;
    
    private final SimpleTimer hurtTimer = new SimpleTimer();
    private int invincibleMs = 2000; // 2.0 detik
    private final SimpleTimer attackTimer = new SimpleTimer();
    private int attackCooldownMs = 500;  // 0.5 detik
    private int attackRadius     = 140;   // ukuran lingkaran
    private int attackLifeFrames = 15;    // lama tampil ring
    
    // --- Dash ---
    private int dashCapacity = 3;
    private int dashCharges = 3;
    private boolean isDashing = false;
    private int dashFramesRemaining = 0;
    private final int dashDurationFrames = 12;
    private final int dashSpeed = 14;
    private final SimpleTimer dashCooldownTimer = new SimpleTimer();
    private int dashCooldownMs = 500;
    private boolean dashKeyHeld = false;
    private boolean dashCooldownReady = true;
    
    public Boat(GameWorld world) {
        this.gameWorld = world;
        right = new GreenfootImage[4]; // ubah 4 sesuai jumlah frame animasi kamu
        left = new GreenfootImage[4];

        loadRightFrames();
        // Pilih salah satu:
        loadLeftFrames();       // kalau punya file arah kiri
        // buildLeftByMirror(); // kalau mau mirror otomatis

        setImage(right[0]); // idle awal
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

    // ---------- Movement ----------
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

    // ---------- Animation ----------
    private void animate() {
        if (moving) {
            if (animTimer.millisElapsed() > frameMs) {
                frame = (frame + 1) % right.length;
                animTimer.mark();
            }
        } else {
            frame = 0;
        }

        if (dir > 0) {
            setImage(right[frame]);
        } else {
            setImage(left[frame]);
        }
    }

    // ---------- Load frames ----------
    private void loadRightFrames() {
        for (int i = 0; i < right.length; i++) {
            right[i] = new GreenfootImage("Boat_Kiri_Frame_" + i + ".png");
            right[i].scale(150, 150);
        }
    }

    private void loadLeftFrames() {
        for (int i = 0; i < left.length; i++) {
            left[i] = new GreenfootImage("Boat_Animation_Frame_" + i + ".png");
            left[i].scale(150, 150);
        }
    }
    
    public boolean canBeHit() {
        return hurtTimer.hasElapsed(invincibleMs);
    }
    
    private void flash() {
        GreenfootImage img = getImage();
        int old = img.getTransparency();
        img.setTransparency(120);
        Greenfoot.delay(30); // sebentar
        img.setTransparency(old);
    }
    
    public void takeDamage(int dmg) {
        if (!canBeHit()) return;   // i-frame aktif → abaikan

        GameWorld gw = (GameWorld) getWorld();
        gw.addLife(-dmg);
        hurtTimer.mark();

        // knockback: arah berlawanan dari facing (dir)
        int knockbackDist = 15;
        int newX = getX() - dir * knockbackDist;  // dorong ke belakang
        // clamp supaya tidak keluar layar
        newX = Math.max(30, Math.min(newX, getWorld().getWidth() - 30));
        setLocation(newX, getY());

        // efek flash
        flash();
    }
    
    private void handleAttack() {
        if (Greenfoot.mouseClicked(null) && attackTimer.hasElapsed(attackCooldownMs)) {
        performAttack();
        attackTimer.mark();
        }
    }
    
    private void performAttack() {
        World w = getWorld();
        if (w == null) return;

        // 1) efek visual
        AttackRing ring = new AttackRing(attackRadius, attackLifeFrames);
        w.addObject(ring, getX(), getY());

        // 2) logika hit (sementara: hapus ikan di radius).
        // Nanti tinggal ganti ke Enemy: for (Enemy e : getObjectsInRange(attackRadius, Enemy.class)) e.takeDamage(1);
        /*for (Object obj : getObjectsInRange(attackRadius, CommonFish.class)) {
            ((Actor)obj).getWorld().removeObject((Actor)obj);
        }
        for (Object obj : getObjectsInRange(attackRadius, RareFish.class)) {
            ((Actor)obj).getWorld().removeObject((Actor)obj);
        }
        for (Object obj : getObjectsInRange(attackRadius, EpicFish.class)) {
            ((Actor)obj).getWorld().removeObject((Actor)obj);
        }*/
        for (Object obj : getObjectsInRange(attackRadius, enemyShark.class)) {
        ((enemyShark)obj).takeDamage(attackDamage);
        }
        for (Object obj : getObjectsInRange(attackRadius, EnemyPuffer.class)) {
        ((EnemyPuffer)obj).takeDamage(attackDamage);
        }
        for (Object obj : getObjectsInRange(attackRadius, crocBoss.class)) {
            ((crocBoss)obj).takeDamage(attackDamage);
        }
        // (opsional) sedikit efek recoil/flash seperti saat takeDamage
    }
    
    private void handleDash() {
        if (isDashing) {
            continueDash();
            return;
        }

        boolean spaceDown = Greenfoot.isKeyDown("space");
        if (spaceDown && !dashKeyHeld && dashCharges > 0
                && (dashCooldownReady || dashCooldownTimer.hasElapsed(dashCooldownMs))) {
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
        gameWorld.notifyDashChanged(dashCharges, dashCapacity);
    }

    private void continueDash() {
        moving = true;
        setLocation(getX() + dir * dashSpeed, getY());
        dashFramesRemaining--;
        if (dashFramesRemaining <= 0) {
            isDashing = false;
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
}
