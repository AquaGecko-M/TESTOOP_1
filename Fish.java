import greenfoot.*;

public class Fish extends Actor {
    private int dir;          // -1 = dari kanan ke kiri, 1 = kiri ke kanan
    private int speed;        // kecepatan horizontal
    private int bob = 0;      // buat efek naik-turun kecil
    private int value;        // poin saat tertangkap

    public Fish(boolean rare) {
        // atur gambar & nilai
        if (rare) {
            setImage("fish2.png");   // ganti sesuai asetmu
            value = 5;
            speed = Greenfoot.getRandomNumber(2) + 3;  // 3–4
        } else {
            setImage("fish3.png");        // ganti sesuai asetmu
            value = 2;
            speed = Greenfoot.getRandomNumber(2) + 2;  // 2–3
        }
    }

    public int getValue() { return value; }

    protected void addedToWorld(World w) {
        // dir ditentukan dari posisi spawn (kiri/kanan)
        dir = (getX() < w.getWidth()/2) ? 1 : -1;
        if (dir < 0) getImage().mirrorHorizontally();
    }

    public void act() {
        // gerak horizontal
        setLocation(getX() + dir * speed, getY());

        // bobbing kecil biar hidup
        bob = (bob + 1) % 40;
        int offset = (bob < 20) ? 1 : -1;
        setLocation(getX(), getY() + offset);

        // hilang bila keluar layar
        if (getX() < -40 || getX() > getWorld().getWidth() + 40) {
            getWorld().removeObject(this);
        }
    }
}
