import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

public class Boat extends Actor
{
    private double speed = 2.0;    // kecepatan normal
    private double boost = 5.0;    // kecepatan saat boost (space)
    private double x = 300;      // simpan posisi x dalam double
    private Hook hook;           // referensi ke kail

    @Override
    protected void addedToWorld(World world) {
        // Atur posisi x awal berdasarkan tempat ia ditambahkan
        this.x = getX(); 
        
        // Buat kail saat perahu ditambahkan
        hook = new Hook();
        world.addObject(hook, getX(), getY() + 50); // tempatkan kail di bawah perahu
    }
    
    public void act()
    {
        handleMovement();
    }
    
    /**
     * Mengatur semua pergerakan perahu, batas dunia, dan kail
     */
    public void handleMovement()
    {
        double currentSpeed = speed;

        // kalau tekan space, pakai kecepatan boost
        if (Greenfoot.isKeyDown("space")) {
            currentSpeed = boost;
        }

        // Tombol Panah kiri atau A
        if (Greenfoot.isKeyDown("left") || Greenfoot.isKeyDown("a")) {
            x -= currentSpeed;
        }

        // Tombol Panah kanan atau D
        if (Greenfoot.isKeyDown("right") || Greenfoot.isKeyDown("d")) {
            x += currentSpeed;
        }
        
        // --- LOGIKA BATAS DUNIA (dari mapWidth()) ---
        int worldWidth = getWorld().getWidth();
        int halfWidth  = getImage().getWidth() / 2;

        if (x < halfWidth) {
            x = halfWidth;
        }
        if (x > worldWidth - halfWidth) {
            x = worldWidth - halfWidth;
        }
        
        // --- ATUR POSISI BARU ---
        // Gunakan getY() untuk mengambil posisi Y saat ini, BUKAN variabel 'y'
        setLocation((int)x, getY()); 
        
        // --- PERBAIKAN HOOK ---
        // Atur posisi kail SETELAH perahu pindah, agar tidak tertinggal
        if (hook != null) {
            hook.setLocation(getX(), hook.getY());
        }
    }
}