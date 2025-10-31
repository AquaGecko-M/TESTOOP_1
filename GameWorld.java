import greenfoot.*;

public class GameWorld extends World {
    private int score = 0;
    private int life  = 5;
    private int timeLeft = 60;              // detik per level (ubah sesukamu)
    
    private final SimpleTimer secondTimer = new SimpleTimer();
    // Fish
    private final SimpleTimer fishSpawnTimer = new SimpleTimer();
    // --- Player ---
    private Boat boat;
    private Kail hook;
    private Hud hud;
    private MenuGameplay menuButton;
    
    public GameWorld() {
        super(960, 540, 1);
        setPaintOrder(Hud.class, Kail.class, Boat.class, Fish.class); // hook di depan boat (opsional)
        
        prepare();
        
        hud = new Hud(getWidth(), 36, 5);
        addObject(hud, getWidth()/2, 20);
        updateHUD();

        GreenfootImage bg = new GreenfootImage("24.jpg");
        bg.scale(960, 540);
        setBackground(bg);
    }

    private void prepare() {
        int boatX = getWidth() / 2;
        int boatY = 120; // Variabel ini tidak terpakai, tapi tidak apa-apa

        boat = new Boat();
        addObject(boat, boatX, 250);

        hook = new Kail(boat);           // hook “terikat” ke boat
        addObject(hook, boatX, 250 + 180); // Posisi kail di bawah boat

        menuButton = new MenuGameplay();
        addObject(menuButton, getWidth() - 60, 50);

        startTimer(300);
        Treasure treasure = new Treasure();
        addObject(treasure,914,507);
        Treasure treasure2 = new Treasure();
        addObject(treasure2,507,500);
        Treasure treasure3 = new Treasure();
        addObject(treasure3,58,496);
    }

    public void act() {
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

    public void refreshHUD() {
        updateHUD();
    }

    public void openPauseMenu() {
        secondTimer.mark();
        fishSpawnTimer.mark();
        Greenfoot.setWorld(new bgMenu(this));
    }

    public void onResumeFromPause() {
        refreshHUD();
        secondTimer.mark();
        fishSpawnTimer.mark();
    }

    private void updateHUD() {
        showText("Score: " + score, 70, 20);
        showText("Life: " + life, 150, 20);
        showText("Time: " + timeLeft, 230, 20);
    }
    
    public void reduceTimer(int seconds) {
    timeLeft = Math.max(0, timeLeft - seconds); // Ensure timer doesn't go below 0
    updateHUD(); // Immediately show the change
    }

    private void updateHUD() {
        if (hud != null) {
            hud.update(score, life, timeLeft);
        }
    }
    
    private void spawnFish() {
        Actor ikanBaru; 
        
        int roll = Greenfoot.getRandomNumber(100) < 15; // Acak angka 0-99

        if (roll < 10) { // 10% kemungkinan (angka 0-9)
            ikanBaru = new EpicFish();
        } else if (roll < 35) { // 25% kemungkinan (angka 10-34)
            ikanBaru = new RareFish();
        } else { // 65% sisanya (angka 35-99)
            ikanBaru = new CommonFish();
        }
        
        // Kode ini sama persis seperti kodemu sebelumnya
        int side = Greenfoot.getRandomNumber(2); // 0 kiri, 1 kanan
        int y = Greenfoot.getRandomNumber(getHeight() - 200) + 300; // area air
        int x = (side == 0) ? -40 : getWidth() + 40;
    
        addObject(ikanBaru, x, y);
    }
}
