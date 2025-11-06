import greenfoot.*;

public class EnemyPuffer extends Actor implements Damageable
{
    // --- Stats ---
    private int speed = 1; // Kecepatan normal
    private int direction;
    private int health;
    private int bob = 0;
    
    // --- Logika Serangan ---
    private int attackRange = 200; // Jarak untuk mulai mengisi daya
    private SimpleTimer chargeTimer = new SimpleTimer();   // Timer 2 detik
    private SimpleTimer attackCooldown = new SimpleTimer(); // Timer 3 detik
    private boolean isCharging = false;
    private boolean isKembung = false;
    private boolean canAttack = true;
    
    // --- Gambar ---
    private GreenfootImage imgKempes;
    private GreenfootImage imgKembung;
    
    private final SimpleTimer hurtIFrame = new SimpleTimer();
    private int hurtCooldownMs = 150; // jeda antar-hit (ms)

    /**
     * Constructor: Menerima health dari GameWorld/Map2
     */
    public EnemyPuffer(int initialHealth)
    {
        this.health = initialHealth;
        attackCooldown.mark(); 
        
        imgKempes = new GreenfootImage("Kempes.png");
        imgKempes.scale(70, 70);
        imgKembung = new GreenfootImage("Kembung.png");
        imgKembung.scale(70, 70);
        
        setImage(imgKempes); // Mulai dengan kempes
        direction = 1; // Asumsi mulai menghadap kanan
    }

    public void act()
    {
        if (getWorld() == null) return;
        
        move(); // Selalu kejar boat
        handleAttackLogic(); // Kontrol status kembung/kempes
        checkHitBoat(); // Serang jika kembung & menyentuh
    }
    
    /**
     * Method ini HANYA mengontrol status kembung/kempes
     */
    private void handleAttackLogic() {
        // 1. Cek jika sedang Cooldown
        if (!canAttack) {
            if (attackCooldown.hasElapsed(3000)) { // Cooldown 3 detik
                canAttack = true;
                setKembung(false); // Kempes lagi
            }
            return; 
        }
        
        // 2. Cek jika sedang Mengisi Daya (Charging)
        if (isCharging) {
            if (chargeTimer.hasElapsed(2000)) { // 2 detik berlalu
                // Cek lagi apakah boat MASIH dalam jangkauan
                if (isBoatInRange()) {
                    // JADI KEMBUNG!
                    setKembung(true); 
                    canAttack = false; // Mulai cooldown
                    attackCooldown.mark();
                    // TIDAK ADA 'attackBoat()' DI SINI
                }
                isCharging = false; // Berhenti mengisi daya
            }
            return; 
        }
        
        // 3. Jika bisa menyerang dan tidak sedang mengisi daya
        if (canAttack && !isCharging) {
            if (isBoatInRange()) {
                // Boat terdeteksi! Mulai mengisi daya
                isCharging = true;
                chargeTimer.mark();
            }
        }
    }
    
    /**
     * BARU: Method ini HANYA memberi damage jika kembung & menyentuh
     */
    private void checkHitBoat() {
        if (isKembung) {
            // Check for a boat within a 35-pixel radius (a circle)
            // Adjust "35" to be smaller or larger as needed.
            if (!getObjectsInRange(70, Boat.class).isEmpty()) { 
                // We're touching the boat.
                // We need to get the actual boat object to damage it.
                Boat boat = (Boat) getObjectsInRange(70, Boat.class).get(0);
                boat.takeDamage(1); 
            }
        }
    }
    
    /**
     * Helper untuk mengecek jarak boat
     */
    private boolean isBoatInRange() {
        return !getObjectsInRange(attackRange, Boat.class).isEmpty();
    }
    
    /**
     * Helper untuk mengubah gambar (kembung/kempes)
     */
    private void setKembung(boolean kembung) {
        this.isKembung = kembung;
        updateImageDirection(); 
    }
    
    /**
     * Helper untuk mengatur gambar + arah
     */
    private void updateImageDirection() {
        GreenfootImage img;
        
        if (isKembung) {
            img = new GreenfootImage(imgKembung); 
        } else {
            img = new GreenfootImage(imgKempes);
        }
        
        if (direction == -1) { // Bergerak ke KIRI
            img.mirrorHorizontally(); 
        }
        
        setImage(img);
    }

    /**
     * Dipanggil oleh Kail untuk mengurangi darah
     */
    private void addScoreToWorld(int score) {
        World world = getWorld(); 
        if (world instanceof GameWorld) {
            ((GameWorld)world).addScore(score);
        }
    }
    
    /**
     * DIPERBARUI: Method ini SELALU mengejar boat
     */
    private void move() {
        World world = getWorld();
        if (world == null) return;
        
        java.util.List<Boat> boats = world.getObjects(Boat.class);
        if (!boats.isEmpty()) {
            Boat boat = boats.get(0);
            int boatX = boat.getX();
            int boatY = boat.getY();
            
            int newX = getX();
            int newY = getY();
            
            int currentSpeed = isKembung ? speed + 1 : speed;

            // 1. Gerak di sumbu X (menuju boat)
            if (boatX > getX() + 5) { // +5 agar tidak goyang-goyang
                newX += currentSpeed;
                if (direction != 1) { 
                    direction = 1;
                    updateImageDirection(); // Balik gambar
                }
            } else if (boatX < getX() - 5) { // -5 agar tidak goyang-goyang
                newX -= currentSpeed;
                if (direction != -1) {
                    direction = -1;
                    updateImageDirection(); // Balik gambar
                }
            }
            
            // 2. Gerak di sumbu Y (menuju boat) + bobbing
            bob = (bob + 1) % 80; 
            int offset = (bob < 40) ? 1 : -1; 
            
            if (boatY > getY()) {
                newY += currentSpeed;
            } else if (boatY < getY()) {
                newY -= currentSpeed;
            }
            
            setLocation(newX, newY + offset); // Terapkan gerakan Y + bobbing
        }
    }
    
    @Override
    public void takeDamage(int amount) {
        if (!hurtIFrame.hasElapsed(hurtCooldownMs)) return;
        hurtIFrame.mark();

        health -= amount;
        flash();

        if (health <= 0) {
            addScoreToWorld(5);   
            getWorld().removeObject(this);
        }
    }
    
    private void flash() {
    GreenfootImage img = getImage();
    int old = img.getTransparency();
    img.setTransparency(140);
    Greenfoot.delay(5);
    if (getWorld() != null) img.setTransparency(old);
    }

}