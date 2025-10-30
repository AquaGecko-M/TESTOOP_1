import greenfoot.*;

public class EpicFish extends Actor // <-- extends Actor
{
    private int dir;
    private int speed;
    private int bob = 0;
    private int value;

    public EpicFish() {
        setImage("EpicFish.png"); // Pastikan nama file gambar benar
        value = 10; // Nilai lebih tinggi
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
        dir = (getX() < w.getWidth() / 2) ? 1 : -1;
        if (dir < 0) {
            getImage().mirrorHorizontally();
        }
    }

    public void act() {
        setLocation(getX() + dir * speed, getY());
        
        bob = (bob + 1) % 40;
        int offset = (bob < 20) ? 1 : -1;
        setLocation(getX(), getY() + offset);

        if (getX() < -40 || getX() > getWorld().getWidth() + 40) {
            getWorld().removeObject(this);
        }
    }
}