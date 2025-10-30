import greenfoot.*;


public class GameWorld extends World {
    // --- HUD State ---
    private int score = 0;
    private int life  = 3;
    private int timeLeft = 60;              // detik per level (ubah sesukamu)
    private final SimpleTimer secondTimer = new SimpleTimer();

    // --- Player ---
    private Boat boat;
    private Hook hook;
    
    // Fish
    private final SimpleTimer fishSpawnTimer = new SimpleTimer();

    public GameWorld() {
        super(960, 540, 1);
        setPaintOrder(Hook.class, Boat.class); // hook di depan boat (opsional)
        prepare();
        updateHUD();
        
         // --- KODE UNTUK MEMPERBAIKI LATAR BELAKANG ---


        // 2. Ambil gambar asli (GANTI "nama_background_menu.png" DENGAN NAMA FILE ANDA)
        GreenfootImage bg = new GreenfootImage("24.jpg"); 


        // 3. Paksa gambar untuk pas dengan ukuran dunia (648x468)
        bg.scale(960, 540);


        // 4. Atur gambar yang sudah dikecilkan
        setBackground(bg);
    }

    private void prepare() {
        // posisi boat (fix di “permukaan”)
        int boatX = getWidth()/2;
        int boatY = 120;

        boat = new Boat();
        addObject(boat, boatX, 250);

        hook = new Hook(boat);              // hook “terikat” ke boat
        addObject(hook, boatX, boatY + 180);

        startTimer(300);                      // mulai timer 60s (ubah via Level nanti)
    }

    public void act() {
        // Kurangi timer setiap 1000 ms
        if (secondTimer.hasElapsed(1000)) {
            timeLeft = Math.max(0, timeLeft - 1);
            secondTimer.mark();
            updateHUD();

            if (timeLeft == 0) {
                showText("Waktu Habis! Skor: " + score, getWidth()/2, getHeight()/2);
                Greenfoot.stop();
            }
        }
        // --- spawn ikan tiap ~0.9 detik ---
        if (fishSpawnTimer.hasElapsed(900)) {
        spawnFish();
        fishSpawnTimer.mark();
        }
    }

    // --- API kecil untuk dipakai kelas lain ---
    public void addScore(int v) { score += v; updateHUD(); }
    public void addLife(int v)  { life  += v; updateHUD(); }
    public void startTimer(int seconds) {
        timeLeft = seconds;
        secondTimer.mark();
        updateHUD();
    }

    private void updateHUD() {
        showText("Score: " + score,   70, 20);
        showText("Life: "  + life,   150, 20);
        showText("Time: "  + timeLeft,230, 20);
    }
    
        private void spawnFish() {
        boolean rare = Greenfoot.getRandomNumber(100) < 15; // 15% rare
        Fish f = new Fish(rare);
    
        int side = Greenfoot.getRandomNumber(2); // 0 kiri, 1 kanan
        int y = Greenfoot.getRandomNumber(getHeight() - 200) + 200; // area air
        int x = (side == 0) ? -20 : getWidth() + 20;
    
        addObject(f, x, y);
    }

}
