import greenfoot.*;

public class enemyShark extends Actor
{
    private int speed = 2;
    private int direction; // Akan diatur oleh spawner (1 = kanan, -1 = kiri)

    public enemyShark()
    {
        // Ganti "shark.png" dengan nama file Anda
        setImage("Shark.png"); 
        
        GreenfootImage image = getImage();
        // Ganti 150, 75 dengan ukuran Anda
        image.scale(150, 75); 
        setImage(image);
        // Asumsi: Gambar asli menghadap ke KANAN
    }
    
    /**
     * Mengatur arah gerak hiu.
     * @param dir 1 untuk bergerak ke kanan, -1 untuk bergerak ke kiri.
     */
    public void setDirection(int dir)
    {
        this.direction = dir;
        
        if (dir == -1) {
            // Jika bergerak ke kiri, balik gambarnya
            getImage().mirrorHorizontally();
        }
        // Jika bergerak ke kanan (dir == 1), tidak perlu dibalik
    }

    public void act()
    {
        if (getWorld() == null) return; // Hentikan jika sudah dihapus
        
        move();
        checkHitBoat();
        checkBoundary();
    }
    
    /**
     * Bergerak berdasarkan arah (direction).
     */
    private void move() {
        setLocation(getX() + (speed * direction), getY());
    }

    /**
     * Memeriksa tabrakan dengan Boat.
     */
    private void checkHitBoat() {
        // Cek apakah kita menyentuh aktor 'Boat'
        Boat boat = (Boat) getOneIntersectingObject(Boat.class);
        
        if (boat != null) {
            // Panggil method 'takeDamage' di Boat
            boat.takeDamage(1); 
            
            // Hapus hiu ini
            getWorld().removeObject(this); 
        }
    }

    /**
     * Memeriksa apakah hiu sudah keluar dari layar.
     */
    private void checkBoundary() {
        World w = getWorld();
        if (w == null) return; // Sudah dihapus

        // Hapus jika keluar layar, berdasarkan arah
        if (direction == -1 && getX() < -100) { // Bergerak ke kiri
            w.removeObject(this);
        } else if (direction == 1 && getX() > w.getWidth() + 100) { // Bergerak ke kanan
            w.removeObject(this);
        }
    }
}