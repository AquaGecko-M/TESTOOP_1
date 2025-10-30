import greenfoot.*;


public class GameWorld extends World {
    // --- HUD State ---
    private int score = 0;
    private int life  = 5;
    private int timeLeft = 60;              // detik per level (ubah sesukamu)
    
    private final SimpleTimer secondTimer = new SimpleTimer();
    // Fish
    private final SimpleTimer fishSpawnTimer = new SimpleTimer();
    // --- Player ---
    private Boat boat;
    private Hook hook;
    private Hud hud;
    
    public GameWorld() {
        super(960, 540, 1);
        setPaintOrder(Hud.class, Hook.class, Boat.class, Fish.class); // hook di depan boat (opsional)
        
        prepare();
        
        hud = new Hud(getWidth(), 36, 5);
        addObject(hud, getWidth()/2, 20);
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
            if (timeLeft == 0 || life <= 0) {
                showText((life <= 0 ? "You Died! " : "Time Up! ") + "Score: " 
                + score, getWidth()/2, getHeight()/2);
                Greenfoot.stop();
            }
        }
        // --- spawn ikan tiap ~0.9 detik ---
        if (fishSpawnTimer.hasElapsed(900)) {
        spawnFish();
        fishSpawnTimer.mark();
        }
        if (Greenfoot.isKeyDown("h")) { boat.takeDamage(1); Greenfoot.delay(5); }

    }

    // --- API kecil untuk dipakai kelas lain ---
    public void addScore(int v) { score += v; updateHUD(); }
    public void addLife(int v)  {
        life  = Math.max(0, Math.min(5, life + v)); 
        updateHUD();
    }
    
    public void startTimer(int seconds) {
        timeLeft = seconds;
        secondTimer.mark();
        updateHUD();
    }

    private void updateHUD() {
        if (hud != null) {
            hud.update(score, life, timeLeft);
        }
    }
    
    private void spawnFish() {
        boolean rare = Greenfoot.getRandomNumber(100) < 15;
        Fish f = new Fish(rare);

        int side = Greenfoot.getRandomNumber(2); // 0 = kiri, 1 = kanan
        int y = Greenfoot.getRandomNumber(getHeight() - 200) + 300;
        int x = (side == 0) ? -40 : getWidth() + 40; // spawn sedikit di luar layar

        addObject(f, x, y);
    }

}
