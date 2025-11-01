import greenfoot.*;

public class GameWorld extends World {
    private int score = 0;
    private int life  = 5;
    private int timeLeft = 60;              // detik per level (ubah sesukamu)
    
    
    //Keys
    private int keyItems = 0;
    private int keysNeeded = 5;
    private GreenfootImage keyItemIcon;
    private GreenfootImage originalBg; // To fix HUD overlapping
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
        
        hud = new Hud(getWidth(), 36, 5);
        addObject(hud, getWidth()/2, 20);
        
        bg.scale(960, 540);
        keyItemIcon = new GreenfootImage("key_item.png"); // You need to create this image
        keyItemIcon.scale(50, 50); // Scale it for the HUD
        setBackground(bg);
    
        prepare();
        updateHUD();
        boat = new Boat();
        addObject(boat, boatX + 20, 250);

        hook = new Kail(boat);           // hook “terikat” ke boat
        addObject(hook, boatX + 20, 250 + 180); // Posisi kail di bawah boat

        menuButton = new MenuGameplay();
        addObject(menuButton, getWidth() - 55, 70);

        startTimer(300);
        Treasure treasure = new Treasure();
        addObject(treasure,914,507);
        Treasure treasure2 = new Treasure();
        addObject(treasure2,507,500);
        Treasure treasure3 = new Treasure();
        addObject(treasure3,58,496);

        updateLevelFromSettings();
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
    
    private void updateLevelFromSettings() {
    if (GameSettings.difficulty.equals("Medium")) {
        currentLevel = 1;
    } else if (GameSettings.difficulty.equals("Hard")) {
        currentLevel = 2;
    } else {
        currentLevel = 0; // Easy
    }
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
        if (hud != null) {
            hud.update(score, life, timeLeft);
        }
        
        // 3. Draw the Key Item icon and text
        getBackground().drawImage(keyItemIcon, 20, 40);
        showText(keyItems + " / " + keysNeeded, 100, 65);
    }
    
    public void reduceTimer(int seconds) {
        timeLeft = Math.max(0, timeLeft - seconds); // Ensure timer doesn't go below 0
        updateHUD(); // Immediately show the change
    }

    private void spawnFish() {
        Actor ikanBaru; 
        
        int roll = Greenfoot.getRandomNumber(100); // Acak angka 0-99

        if (roll < 10) { // Epic
        ikanBaru = new EpicFish();
        EpicFish e = (EpicFish) ikanBaru;

        e.setFishSize(GameSettings.epicFishSize[currentLevel][0], GameSettings.epicFishSize[currentLevel][1]);
        int speed = Greenfoot.getRandomNumber(
            GameSettings.epicFishSpeed[currentLevel][1] - GameSettings.epicFishSpeed[currentLevel][0] + 1
        ) + GameSettings.epicFishSpeed[currentLevel][0];
        e.setSpeed(speed);

    } else if (roll < 35) { // Rare
        ikanBaru = new RareFish();
        RareFish r = (RareFish) ikanBaru;

        r.setFishSize(GameSettings.rareFishSize[currentLevel][0], GameSettings.rareFishSize[currentLevel][1]);
        int speed = Greenfoot.getRandomNumber(
            GameSettings.rareFishSpeed[currentLevel][1] - GameSettings.rareFishSpeed[currentLevel][0] + 1
        ) + GameSettings.rareFishSpeed[currentLevel][0];
        r.setSpeed(speed);

    } else { // Common
        ikanBaru = new CommonFish();
        CommonFish c = (CommonFish) ikanBaru;

        c.setFishSize(GameSettings.commonFishSize[currentLevel][0], GameSettings.commonFishSize[currentLevel][1]);
        int speed = Greenfoot.getRandomNumber(
            GameSettings.commonFishSpeed[currentLevel][1] - GameSettings.commonFishSpeed[currentLevel][0] + 1
        ) + GameSettings.commonFishSpeed[currentLevel][0];
        c.setSpeed(speed);
    }
        
        // Kode ini sama persis seperti kodemu sebelumnya
        int side = Greenfoot.getRandomNumber(2); // 0 kiri, 1 kanan
        int y = Greenfoot.getRandomNumber(getHeight() - 200) + 300; // area air
        int x = (side == 0) ? -40 : getWidth() + 40;
    
        addObject(ikanBaru, x, y);
        
    }
    
    public void addKeyItem() {
        if (keyItems < keysNeeded) {
            keyItems++;
            updateHUD();
        }    
        // Check if the level is complete
        if (keyItems >= keysNeeded) {
            // Later, this will trigger the Stage Completion screen.
            // For now, we can just log it to see that it works.
            System.out.println("LEVEL COMPLETE! All keys collected.");
            
            // --- TODO: Go to StageCompleteWorld ---
            // Greenfoot.setWorld(new StageCompleteWorld(this)); 
        }
    }
}
