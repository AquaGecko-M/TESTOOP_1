import greenfoot.*;

public class RareFish extends Actor // <-- extends Actor
{
    private int dir;
    private int speed;
    private int bob = 0;
    private int value;

    public RareFish() {
        setImage("RareFish.png"); // Pastikan nama file gambar benar
        value = 5;
        speed = Greenfoot.getRandomNumber(2) + 3; // Kecepatan 3–4
        
        // 1. Ambil gambar yang sudah di-set
        GreenfootImage image = getImage();
        
        // 2. Kecilkan gambar (Ubah 40 dan 30 sesuai keinginanmu)
        image.scale(120, 140); // 40 = lebar baru, 30 = tinggi baru
        
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