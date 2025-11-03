import greenfoot.*;

public class Boat extends Actor {
    private GreenfootImage[] right;
    private GreenfootImage[] left;
    
    private int attackDamage = 1;

    private int frame = 0;
    private int dir = 1; // 1 = kanan, -1 = kiri
    private boolean moving = false;
    private SimpleTimer animTimer = new SimpleTimer();
    private int frameMs = 100;
    private int speed = 6;
    
    private final SimpleTimer hurtTimer = new SimpleTimer();
    private int invincibleMs = 2000; // 2.0 detik
    private final SimpleTimer attackTimer = new SimpleTimer();
    private int attackCooldownMs = 1000;  // 1 detik
    private int attackRadius     = 140;   // ukuran lingkaran
    private int attackLifeFrames = 15;    // lama tampil ring

    public Boat() {
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
        handleMove();
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
        Greenfoot.delay(10); // sebentar
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
        
        for (Object obj : getObjectsInRange(attackRadius, enemyShark.class)) {
        enemyShark t = (enemyShark) obj;
        int oldX = t.getX();
        int oldY = t.getY();
        int facing = t.getFacing();

        t.takeDamage(attackDamage);

        if (t.getWorld() != null) {
            SlashEffect fx = new SlashEffect(facing, 150, 150);
            getWorld().addObject(fx, oldX, oldY);
            }
        }
        
        //Puffer
        for (Object obj : getObjectsInRange(attackRadius, EnemyPuffer.class)) {
        EnemyPuffer p = (EnemyPuffer) obj;
        int oldX = p.getX();
        int oldY = p.getY();
        int facing = p.getFacing();

        p.takeDamage(attackDamage);

        if (p.getWorld() != null) {
            SlashEffect fx = new SlashEffect(facing, 150, 150);
            getWorld().addObject(fx, oldX, oldY);
            }
        }


        // (opsional) sedikit efek recoil/flash seperti saat takeDamage
    }
}
