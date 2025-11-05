import greenfoot.*;

public class Boat extends Actor {
    private GreenfootImage[] right;
    private GreenfootImage[] left;

    // Stats yang dipakai performAttack()
    private int attackRadius; // Ukuran Lingkaran
    private int attackDamage;
    private int attackCooldownMs; //Cooldown Attk
    private int attackLifeFrames; //Lama Tampil Ring
    private int slashW;
    private int slashH;
    
    //Animation
    // --- Attack animation (4 frame per tier) ---
    private GreenfootImage[][] atkRight = new GreenfootImage[PlayerStats.MAX_WEAPON_TIER + 1][4];
    private GreenfootImage[][] atkLeft  = new GreenfootImage[PlayerStats.MAX_WEAPON_TIER + 1][4];
    private boolean attacking = false;
    private int atkFrame = 0, atkTick = 0, atkDelay = 10; // ganti atkDelay untuk cepat/lambat anim
    
    //Patch
    private int wOffXRight = 12;   // offset senjata saat hadap kanan
    private int wOffXLeft  = 5; // offset saat hadap kiri (biasanya negatif)
    private int wOffY      = 70;  // offset vertikal


    
    private int frame = 0;
    private int dir = 1; // 1 = kanan, -1 = kiri
    private boolean moving = false;
    private SimpleTimer animTimer = new SimpleTimer();
    private int frameMs = 100;
    private int speed = 6;
    
    private final SimpleTimer hurtTimer = new SimpleTimer();
    private int invincibleMs = 2000; // 2.0 detik
    private final SimpleTimer attackTimer = new SimpleTimer();

    public Boat() {
        right = new GreenfootImage[4]; // ubah 4 sesuai jumlah frame animasi kamu
        left = new GreenfootImage[4];

        loadRightFrames();
        // Pilih salah satu:
        loadLeftFrames();       // kalau punya file arah kiri
        loadAttackFrames();   // muat 4 frame per tier (kanan + mirror kiri)
        // buildLeftByMirror(); // kalau mau mirror otomatis

        setImage(right[0]); // idle awal
    }
    
    private void loadAttackFrames() {
        for (int t = 0; t <= PlayerStats.MAX_WEAPON_TIER; t++) {
            for (int f = 0; f < 4; f++) {
                GreenfootImage r = new GreenfootImage("atk_t" + t + "_" + f + ".png");
                r.scale(100,100); // jika perlu samakan skala dengan boat
                atkRight[t][f] = r;
    
                GreenfootImage l = new GreenfootImage(r);
                l.mirrorHorizontally();
                atkLeft[t][f] = l;
            }
        }
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
    if (attacking) {
        if (++atkTick >= atkDelay) { atkTick = 0; atkFrame++; }
        if (atkFrame >= 4) { attacking = false; atkFrame = 0; }

        int tier = Math.max(0, Math.min(PlayerStats.weaponTier, PlayerStats.MAX_WEAPON_TIER));

        // 1) ambil frame boat (idle atau jalan)
        GreenfootImage boatBase = (moving ? (dir > 0 ? right[frame] : left[frame])
                                          : (dir > 0 ? right[0]   : left[0]));

        // 2) copy dulu (JANGAN gambar langsung ke array sumber)
        GreenfootImage composed = new GreenfootImage(boatBase);

        // 3) ambil frame weapon sesuai arah
        GreenfootImage weaponImg = (dir > 0) ? atkRight[tier][atkFrame] : atkLeft[tier][atkFrame];

        // 4) gambar weapon di atas boat dengan offset
        int offX = (dir > 0) ? wOffXRight : wOffXLeft;
        composed.drawImage(weaponImg, offX, wOffY);

        // 5) tampilkan
        setImage(composed);
        return;
    }

    // …lanjutan anim jalan/idle seperti biasa…
    if (moving) {
        if (animTimer.millisElapsed() > frameMs) { frame = (frame + 1) % right.length; animTimer.mark(); }
    } else frame = 0;

    setImage((dir > 0) ? right[frame] : left[frame]);
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
        
        attacking = true;
        atkFrame = 0; atkTick = 0;

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
            SlashEffect fx = new SlashEffect(facing, slashW, slashH);
            w.addObject(fx, oldX, oldY);
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
            SlashEffect fx = new SlashEffect(facing, slashW, slashH);
            w.addObject(fx, oldX, oldY);
            }
        }
    }
    
    public void syncWeaponFromStats() {
        int t = Math.max(0, Math.min(PlayerStats.weaponTier, PlayerStats.MAX_WEAPON_TIER));
        attackRadius     = PlayerStats.WPN_RADIUS[t];
        attackDamage     = PlayerStats.WPN_DAMAGE[t];
        attackCooldownMs = PlayerStats.WPN_COOLDOWN[t];
        attackLifeFrames = PlayerStats.WPN_RING_LIFE[t];
        slashW           = PlayerStats.WPN_SLASH_W[t];
        slashH           = PlayerStats.WPN_SLASH_H[t];
    }
}
