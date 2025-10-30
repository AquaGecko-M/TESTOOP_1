import greenfoot.*;

public class Fish extends Actor {
    private int dir;                 // arah horizontal (-1 kiri, 1 kanan)
    private int speed;
    private int bob = 0;
    private int value;

    public Fish(boolean rare) {
        if (rare) {
            setImage("fish2.png");
            value = 5;
            speed = Greenfoot.getRandomNumber(2) + 3; // 3–4
        } else {
            setImage("fish3.png");
            value = 2;
            speed = Greenfoot.getRandomNumber(2) + 2; // 2–3
        }
    }

    public int getValue() { return value; }

    @Override
    protected void addedToWorld(World w) {
        // tentukan arah otomatis dari posisi spawn
        if (getX() < w.getWidth() / 2) {
            dir = 1;  // dari kiri → kanan
        } else {
            dir = -1; // dari kanan → kiri
            getImage().mirrorHorizontally(); // balik arah tampilan
        }
    }

    @Override
    public void act() {
        move();
        bobbing();
        checkDespawn();
    }

    private void move() {
        setLocation(getX() + dir * speed, getY());
    }

    private void bobbing() {
        bob = (bob + 1) % 40;
        int offset = (bob < 20) ? 1 : -1;
        setLocation(getX(), getY() + offset);
    }

    private void checkDespawn() {
        World w = getWorld();
        if (w == null) return;

        int rightEdge = w.getWidth() - 1;

        // Greenfoot mengunci x di [0..rightEdge], jadi cek tepi + arah gerak
        if ((dir < 0 && getX() <= 0) ||     // bergerak ke kiri & sudah di tepi kiri
        (dir > 0 && getX() >= rightEdge)) { // bergerak ke kanan & sudah di tepi kanan
        w.removeObject(this);
        }
    }
}
