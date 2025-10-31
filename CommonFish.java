import greenfoot.*;

public class CommonFish extends Actor {
    private int dir;        // -1 = kanan→kiri, 1 = kiri→kanan
    private int speed;      // kecepatan horizontal
    private int bob = 0;    // efek naik-turun kecil
    private int value = 2;

    public CommonFish() {
        setImage("CFish.png");
        speed = Greenfoot.getRandomNumber(2) + 2; // 2–3
        // skala sesuai kebutuhanmu
        GreenfootImage img = getImage();
        img.scale(160, 180);
        setImage(img);
    }

    public int getValue() { return value; }

    @Override
    protected void addedToWorld(World w) {
        // arah otomatis dari sisi spawn
        if (getX() < w.getWidth() / 2) {
            dir = 1; // dari kiri → kanan
        } else {
            dir = -1; // dari kanan → kiri
            getImage().mirrorHorizontally();
        }
    }

    @Override
    public void act() {
        // gerak horizontal
        setLocation(getX() + dir * speed, getY());

        // bobbing kecil
        bob = (bob + 1) % 40;
        int offset = (bob < 20) ? 1 : -1;
        setLocation(getX(), getY() + offset);

        // HAPUS DI TEPI: karena X dikunci, cek tepi + arah gerak
        World w = getWorld();
        if (w == null) return;
        int rightEdge = w.getWidth() - 1;

        if ((dir < 0 && getX() <= 0) || (dir > 0 && getX() >= rightEdge)) {
            w.removeObject(this);
        }
    }
}
