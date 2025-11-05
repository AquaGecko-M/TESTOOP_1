import greenfoot.*;

/**
    
 */
public class GoldFish extends Actor {
    private int speed = 4; // Dia lebih cepat dari ikan biasa
    private int direction;
    private int bob = 0;
    private int value = 100;
    private int coinReward = 200;

    public GoldFish() {
        setImage("ikanemas.png");
        GreenfootImage img = getImage();
        img.scale(80, 60); // Atur ukuran ikan emas (sesuaikan)
        setImage(img);
    }
    
    /**
     * Dipanggil oleh GameWorld untuk menentukan arah gerak
     */
    public void setDirection(int dir) {
        this.direction = dir;
        if (dir == -1) { // Bergerak ke kiri
            getImage().mirrorHorizontally();
        }
        // Jika dir == 1 (bergerak ke kanan), gambar tidak perlu dibalik
    }

    public void act() {
        if (getWorld() == null) return; // Hentikan jika sudah dihapus
        move();
        checkBoundary();
    }

    /**
     * Bergerak horizontal + bobbing (naik/turun)
     */
    private void move() {
        int x = getX() + (speed * direction);
        bob = (bob + 1) % 40; // Siklus 40 frame
        int offset = (bob < 20) ? 1 : -1; // 20 frame naik, 20 frame turun
        int y = getY() + offset;
        setLocation(x, y);
    }

    /**
     * Hapus ikan jika sudah keluar layar
     */
    private void checkBoundary() {
        World w = getWorld();
        if (w == null) return;
        int half = getImage().getWidth() / 2;
        
        // Logika realistis (hilang setelah seluruh badan keluar)
        if (direction < 0 && getX() + half <= 0) {
            w.removeObject(this);
        } else if (direction > 0 && getX() - half >= w.getWidth()) {
            w.removeObject(this);
        }
    }
    
    public int getValue() {
        return this.value;
    }
    
    public int getCoinReward() {
        return this.coinReward;
    }
}