import greenfoot.*;

public class Boat extends Actor {
    private GreenfootImage[] right;
    private GreenfootImage[] left;

    private int frame = 0;
    private int dir = 1; // 1 = kanan, -1 = kiri
    private boolean moving = false;
    private SimpleTimer animTimer = new SimpleTimer();
    private int frameMs = 100;
    private int speed = 3;

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
            right[i] = new GreenfootImage("Boat_Animation_Frame_" + i + ".png");
            right[i].scale(150, 150);
        }
    }

    private void loadLeftFrames() {
        for (int i = 0; i < left.length; i++) {
            left[i] = new GreenfootImage("Boat_Kiri_Frame_" + i + ".png");
            left[i].scale(150, 150);
        }
    }

    // ---------- Build mirror ----------
    @SuppressWarnings("unused")
    private void buildLeftByMirror() {
        for (int i = 0; i < left.length; i++) {
            left[i] = new GreenfootImage(right[i]);
            left[i].mirrorHorizontally();
        }
    }
}