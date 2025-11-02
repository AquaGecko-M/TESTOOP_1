import greenfoot.*;

public class enemyShark extends Actor
{
    private int speed = 2;
    private int direction;
    private int health;// Akan diatur oleh spawner (1 = kanan, -1 = kiri)
    private SimpleTimer hitCooldown = new SimpleTimer();
    private boolean canHit = true;

    public enemyShark(int initialHealth)
    {
        this.health = initialHealth; // Simpan health yang dikirim dari GameWorld

    }
    
    /**
     * Mengatur arah gerak hiu.
     * @param dir 1 untuk bergerak ke kanan, -1 untuk bergerak ke kiri.
     */
    public void setDirection(int dir)
    {
        this.direction = dir;
        GreenfootImage image; // Variabel untuk menyimpan gambar

        if (dir == 1) {
            // dir == 1 artinya SPAWN DARI KIRI, bergerak ke kanan
            // Anda minta "dari kiri pakai shark2"
            image = new GreenfootImage("Shark2.png"); 
            
            // Asumsi shark2.png menghadap ke kanan, jadi tidak perlu flip
            
        } else {
            // dir == -1 artinya SPAWN DARI KANAN, bergerak ke kiri
            // Anda minta "dari kanan shark 1"
            image = new GreenfootImage("Shark.png");
            
            // Asumsi shark1.png menghadap ke kanan, jadi kita FLIP
            image.mirrorHorizontally(); 
        }
        
        // Atur ukuran gambar (ganti 150, 75 dengan ukuran Anda)
        image.scale(150, 75);
        
        // Set gambar yang sudah benar ke hiu
        setImage(image);
    }

    public void act()
    {
        if (getWorld() == null) return; // Hentikan jika sudah dihapus
        
        move();
        checkHitBoat();
        checkBoundary();
    }
    public void takeDamage(int amount)
    {
        health = health - amount; // Kurangi darah
        
        // Jika darah 0, hiu hilang
        if (health <= 0)
        {
            addScoreToWorld(10); // Beri skor 10
            getWorld().removeObject(this); // Hapus hiu
        }
    }
    
    /**
     * Method helper untuk menambahkan skor (jika hiu mati)
     */
    private void addScoreToWorld(int score) {
        World world = getWorld(); 
        
        if (world instanceof GameWorld) {
            ((GameWorld)world).addScore(score);
        }
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
        if (!canHit) {
            if (hitCooldown.hasElapsed(2000)) {
                canHit = true; 
            }
            return; 
        }
        Boat boat = (Boat) getOneIntersectingObject(Boat.class);
        if (boat != null) {
            boat.takeDamage(1);
            canHit = false;
            hitCooldown.mark();
        }
        
    }

    /**
     * Memeriksa apakah hiu sudah keluar dari layar.
     */
    private void checkBoundary() {
        World w = getWorld();
        if (w == null) return; 

        // Cek jika hiu kena batas KIRI (saat bergerak ke kiri)
        // Batas 50 pixel dari tepi kiri
        if (direction == -1 && getX() <= 50) {
            // Balik arah ke KANAN (dir = 1)
            setDirection(1); // Ini akan otomatis memanggil 'shark2.png'
        } 
        // Cek jika hiu kena batas KANAN (saat bergerak ke kanan)
        // Batas (lebar dunia - 50) pixel dari tepi kanan
        else if (direction == 1 && getX() >= w.getWidth() - 50) {
            // Balik arah ke KIRI (dir = -1)
            setDirection(-1); // Ini akan otomatis memanggil 'Shark.png'
        }
    }
}