import greenfoot.*;

public class EpicFish extends Actor {
    private int dir;
    private int speed;
    private int bob = 0;
    private int value = 10;

    public EpicFish() {
        setImage("EpicFish.png"); // Pastikan nama file gambar benar
        speed = Greenfoot.getRandomNumber(2) + 4; // Kecepatan 4–5
        
        // 1. Ambil gambar yang sudah di-set
        GreenfootImage image = getImage();
        
        // 2. Kecilkan gambar (Ubah 40 dan 30 sesuai keinginanmu)
        image.scale(100, 120); // 40 = lebar baru, 30 = tinggi baru
        
        // 3. Set kembali gambar yang sudah dikecilkan
        setImage(image);
    }

    public int getValue() {
        return value;
    }

    protected void addedToWorld(World w) {
        if (getX() < w.getWidth() / 2) {
            dir = 1; // dari kiri → kanan
        } else {
            dir = -1; // dari kanan → kiri
            getImage().mirrorHorizontally();
        } else {
            dir = (Greenfoot.getRandomNumber(2) == 0) ? 1 : -1;
        }
    }

    public void act() {
        moveFish();
        checkBoundary();
    }

    private void moveFish() {
        setLocation(getX() + dir * speed, getY());

        // bobbing kecil
        bob = (bob + 1) % 40;
        int offset = (bob < 20) ? 1 : -1;
        setLocation(getX(), getY() + offset);
    }

        // HAPUS DI TEPI: karena X dikunci, cek tepi + arah gerak
        World w = getWorld();
        if (w == null) return;
        int rightEdge = w.getWidth() - 1;

        if ((dir < 0 && getX() <= 0) || (dir > 0 && getX() >= rightEdge)) {
            w.removeObject(this);
        }
    }
}
