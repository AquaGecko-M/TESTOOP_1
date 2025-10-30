import greenfoot.*;

public class Boat extends Actor {
    private int speed = 6;

    public Boat() {
        loadRightFrames();
        // Pilih salah satu:
        loadLeftFrames();       // <-- gunakan ini kalau kamu SUDAH punya file kiri
        // buildLeftByMirror(); // <-- gunakan ini kalau BELUM punya file kiri (mirror otomatis)

        setImage(right[0]);     // idle awal
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
            dir = -1;         // arah terakhir = kiri
            moving = true;
        } else if (Greenfoot.isKeyDown("right") || Greenfoot.isKeyDown("d")) {
            setLocation(getX() + speed, getY());
            dir = 1;          // arah terakhir = kanan
            moving = true;
        } else {
            moving = false;   // berhenti tapi dir tetap disimpan
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
            if (animTimer.hasElapsed(frameMs)) {
                frame = (frame + 1) % right.length;
                animTimer.mark();
            }
        } else {
            frame = 0; // idle frame
    }

    // gunakan arah terakhir yang disimpan di `dir`
        if (dir > 0) { 
            setImage(right[frame]); 
        } else if (dir < 0) { 
            setImage(left[frame]); 
        } else {
            // kalau belum pernah bergerak sama sekali (awal game)
            setImage(right[0]);
    }
    }

    // ---------- Load frames ----------
    private void loadRightFrames() {
        for (int i = 0; i < right.length; i++) {
            right[i] = new GreenfootImage("Boat_Kiri_Frame_" + i + ".png");
            right[i].scale(150, 150);
        }
    }

    /** Gunakan ini jika kamu punyafile 'boat_left_i.png'. */
    private void loadLeftFrames() {
        for (int i = 0; i < left.length; i++) {
            left[i] = new GreenfootImage("Boat_Animation_Frame_" + i + ".png");
            left[i].scale(150, 150);
        }
    }

    /** Alternatif: bikin frame kiri dari mirror kanan (kalau tidak punya file kiri). */
    @SuppressWarnings("unused")
    private void buildLeftByMirror() {
        for (int i = 0; i < left.length; i++) {
            left[i] = new GreenfootImage(right[i]); // copy dulu
            left[i].mirrorHorizontally();
        }
    }
}
