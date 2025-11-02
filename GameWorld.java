import greenfoot.*;

public class GameWorld extends World {
    private int score = 0;
    private int life = 5;
    private int timeLeft = 60; // detik per level (ubah sesukamu)
    private int currentLevel = 0; // This is for DIFFICULTY (Easy=0, etc.)
    private int stageNumber; // This is for the STAGE (Level 1, 2, etc.)
    
    //Keys
    private int keyItems = 0;
    private int keysNeeded = 5;
    private GreenfootImage keyItemIcon;
    private GreenfootImage originalBg; // Untuk memperbaiki HUD overlapping
    private final SimpleTimer secondTimer = new SimpleTimer();
    // Fish
    private int totalFish = 0;
    private final SimpleTimer fishSpawnTimer = new SimpleTimer();
    // --- Player ---
    private Boat boat;
    private Kail hook;
    private Hud hud;
    private MenuGameplay menuButton;
    private btnShop shopButton;
    private boolean gameOverTriggered = false;
    
    // Shop state
    private int longSpearUpgrades = 0;
    private int speedUpgrades = 0;
    private int boostUpgrades = 0;
    private int heartPurchases = 0;
    private int coins = 0;
    private Koin coinIcon;
    
    public static final int COIN_REWARD_COMMON = 5;
    public static final int COIN_REWARD_RARE = 12;
    public static final int COIN_REWARD_EPIC = 25;
    public static final int COIN_REWARD_TREASURE = 40;

    private static final int LONG_SPEAR_MAX_LEVEL = 3;
    private static final int SPEED_MAX_LEVEL = 5;
    private static final int BOOST_MAX_LEVEL = 5;
    private static final int HEART_MAX_PURCHASE = 20;

    private static final Color HUD_TEXT_COLOR = Color.WHITE;
    private static final Color HUD_TEXT_BG = new Color(0, 0, 0, 0);
    
    
    public GameWorld(int stageNum) {
        super(960, 540, 1);
        this.stageNumber = stageNum; // Store the stage number we were given
        setPaintOrder(Hud.class, Koin.class, Kail.class, Boat.class, Fish.class); // HUD dan ikon tetap di depan 
        hud = new Hud(getWidth(), 36, 5);
        addObject(hud, getWidth()/2, 20);
        GreenfootImage bg = new GreenfootImage("24.jpg");
        bg.scale(960, 540);

        originalBg = new GreenfootImage(bg); 
        
        keyItemIcon = new GreenfootImage("key_item.png"); // You need to create this image
        keyItemIcon.scale(50, 50); // Scale it for the HUD
        setBackground(bg);
    
        menuButton = new MenuGameplay();
        addObject(menuButton, getWidth() - 55, 70);

        shopButton = new btnShop();
        addObject(shopButton, getWidth() - 55, 120);
        
        coinIcon = new Koin();
        addObject(coinIcon, 50, 105);

        Treasure treasure = new Treasure();
        addObject(treasure,914,507);
        Treasure treasure2 = new Treasure();
        addObject(treasure2,507,500);
        Treasure treasure3 = new Treasure();
        addObject(treasure3,58,496);
        
        prepare();
        updateHUD();
        updateLevelFromSettings();
    }
    
    private void prepare() {
        int boatX = getWidth() / 2;
        int boatY = 120; 

        boat = new Boat();
        addObject(boat, boatX + 20, 250);

        hook = new Kail(boat);           // hook “terikat” ke boat
        addObject(hook, boatX + 20, 250 + 180); // Posisi kail di bawah boat

        startTimer(300);
        Treasure treasure = new Treasure();
        addObject(treasure,757,428);
        Treasure treasure2 = new Treasure();
        addObject(treasure2,230,421);
    }

    public void act() {
        if (gameOverTriggered) {
            return;
        }
        
        if (secondTimer.hasElapsed(1000)) {
            timeLeft = Math.max(0, timeLeft - 1);
            secondTimer.mark();
            updateHUD();
            if (timeLeft == 0) {
                triggerGameOver("Times Up!");
                return;
            }
        }

        if (fishSpawnTimer.hasElapsed(900)) {
            spawnFish();
            fishSpawnTimer.mark();
        }
        if (Greenfoot.isKeyDown("h")) { boat.takeDamage(1); Greenfoot.delay(5); }
        
        if (life <= 0) {
            triggerGameOver("You Died!");
        }
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
        if (gameOverTriggered) {
            return;
        }
        secondTimer.mark();
        fishSpawnTimer.mark();
        Greenfoot.setWorld(new bgMenu(this));
    }

    // --- FITUR DARI VERSI 2 ---
    public void openShopMenu() {
        if (gameOverTriggered) {
            return;
        }
        secondTimer.mark();
        fishSpawnTimer.mark();
        Greenfoot.setWorld(new bgShop(this)); 
    }
    
    private void triggerGameOver(String reason) {
        // 1. Set the flag so this only runs once
        gameOverTriggered = true;
        
        // 2. Stop all game timers
        secondTimer.mark();
        fishSpawnTimer.mark();
        
        // 3. Go to the gameOver screen
        // We pass the score, reason, AND the stageNumber so "Try Again" works
        Greenfoot.setWorld(new gameOver(score, reason, stageNumber));
    }

    public void onResumeFromPause() {
        refreshHUD();
        secondTimer.mark();
        fishSpawnTimer.mark();
    }

    private void updateHUD() {

        getBackground().drawImage(originalBg, 0, 0);
        
        if (hud != null) {
            hud.update(score, life, timeLeft);
        }
        
        getBackground().drawImage(keyItemIcon, 20, 40);
        GreenfootImage keyLabel = new GreenfootImage(keyItems + " / " + keysNeeded, 20, HUD_TEXT_COLOR, HUD_TEXT_BG);
        getBackground().drawImage(keyLabel, 80, 65 - keyLabel.getHeight() / 2);

        GreenfootImage coinLabel = new GreenfootImage(coins + "$", 20, HUD_TEXT_COLOR, HUD_TEXT_BG);
        getBackground().drawImage(coinLabel, 80, 110 - coinLabel.getHeight() / 2);
    }
    
    public void reduceTimer(int seconds) {
        timeLeft = Math.max(0, timeLeft - seconds); // Ensure timer doesn't go below 0
        updateHUD(); 
        if (timeLeft == 0) {
            triggerGameOver("Times Up!");
        }
    }
    
    private int difficultyIndex() {
        String d = GameSettings.difficulty;
        if ("Medium".equals(GameSettings.difficulty)) return 1;
        if ("Hard".equals(GameSettings.difficulty)) return 2;
        return 0; // Easy
    }
    
    private int levelIndex() {
        // kalau belum punya sistem level stage, untuk sekarang 0
        return Math.max(0, Math.min(currentLevel, 
        GameSettings.EnemyHealth[0].length - 1));
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
    
        int sharkRoll = Greenfoot.getRandomNumber(100);
        
        // 10% kemungkinan (jika angka 0-9)
        if (sharkRoll < 10) { 
            int health = GameSettings.EnemyHealth[difficultyIndex()][levelIndex()];
            enemyShark shark = new enemyShark(health);

            int yHiu = 273; // Ketinggian spesifik untuk hiu
            
            // Acak sisi (0 = kiri, 1 = kanan)
            int sideHiu = Greenfoot.getRandomNumber(2); 
            if (sideHiu == 0) {
            shark.setDirection(1); // <-- BENAR (memanggil Shark2.png)
            addObject(shark, -50, yHiu);
             } else {
            // Muncul di KANAN, bergerak ke KIRI
            shark.setDirection(-1); // <-- BENAR (memanggil Shark.png)
            addObject(shark, getWidth() + 50, yHiu);
            }
        }
        
        int pufferRoll = Greenfoot.getRandomNumber(100);
        
        // 5% kemungkinan (jika angka 0-4)
        if (pufferRoll < 5) { 
            int health = GameSettings.EnemyHealth[currentLevel][0];
            EnemyPuffer puffer = new EnemyPuffer(health);
            int yPuffer = Greenfoot.getRandomNumber(getHeight() - 200) + 300;
            addObject(puffer, -50, yPuffer);
        }
        
    }
    
    public boolean addKeyItem() { // <--- Changed from void to boolean
        if (keyItems < keysNeeded) {
            keyItems++;
            updateHUD();
        }
    
        // Check if the level is complete
        if (keyItems >= keysNeeded) {
            // We have all the keys! Time to end the level.
            secondTimer.mark();
            fishSpawnTimer.mark();
            
            // Go to the completion screen
            Greenfoot.setWorld(new menuCompletion(score, timeLeft, totalFish, stageNumber));

            return true; // Yes, the level is complete
        }
    
        // If we are here, the level is not complete
        return false;
    }
    
    public void addFishCollected(int amount) {
        totalFish += amount;
    }
    // --- Shop state helpers ---
    public int getLongSpearUpgrades() {
        return longSpearUpgrades;
    }

    public ShopPurchaseResult tryPurchaseLongSpear(int cost) {
        if (longSpearUpgrades >= LONG_SPEAR_MAX_LEVEL) {
            return ShopPurchaseResult.MAXED_OUT;
        }
        if (!withdrawCoins(cost)) {
            return ShopPurchaseResult.NOT_ENOUGH_COINS;
        }
        longSpearUpgrades++;
        return ShopPurchaseResult.PURCHASED;
    }

    public int getSpeedUpgrades() {
        return speedUpgrades;
    }

    public ShopPurchaseResult tryPurchaseSpeed(int cost) {
        if (speedUpgrades >= SPEED_MAX_LEVEL) {
            return ShopPurchaseResult.MAXED_OUT;
        }
        if (!withdrawCoins(cost)) {
            return ShopPurchaseResult.NOT_ENOUGH_COINS;
        }
        speedUpgrades++;
        return ShopPurchaseResult.PURCHASED;
    }

    public int getBoostUpgrades() {
        return boostUpgrades;
    }

    public ShopPurchaseResult tryPurchaseBoost(int cost) {
        if (boostUpgrades >= BOOST_MAX_LEVEL) {
            return ShopPurchaseResult.MAXED_OUT;
        }
        if (!withdrawCoins(cost)) {
            return ShopPurchaseResult.NOT_ENOUGH_COINS;
        }
        boostUpgrades++;
        return ShopPurchaseResult.PURCHASED;
    }

    public int getHeartPurchases() {
        return heartPurchases;
    }

    public ShopPurchaseResult tryPurchaseHeart(int cost) {
        if (heartPurchases >= HEART_MAX_PURCHASE) {
            return ShopPurchaseResult.MAXED_OUT;
        }
        if (!withdrawCoins(cost)) {
            return ShopPurchaseResult.NOT_ENOUGH_COINS;
        }
        heartPurchases++;
        addLife(2);
        return ShopPurchaseResult.PURCHASED;
    }
    
    public int getCurrentLevel() {
        return currentLevel;
    }
    
    public int getCoins() {
        return coins;
    }

    public void addCoins(int amount) {
        if (amount <= 0) {
            return;
        }
        coins += amount;
        updateHUD();
    }

    private boolean withdrawCoins(int cost) {
        if (cost <= 0) {
            return true;
        }
        if (coins < cost) {
            return false;
        }
        coins -= cost;
        updateHUD();
        return true;
    }
}