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
        GreenfootImage bg; // Create a temporary variable for the background
        
        if (stageNumber == 1) {
            // --- STAGE 1 setup ---
            bg = new GreenfootImage("24.jpg"); 
            bg.scale(960, 540);
            
            // Add ALL treasures for Stage 1 HERE
            addObject(new Treasure(), 30, 500);
            addObject(new Treasure(), 200, 500);
            addObject(new Treasure(), 400, 500);
            addObject(new Treasure(), 600, 500);
            addObject(new Treasure(), 850, 500);
            
        } else if (stageNumber == 2) {
            // --- STAGE 2 setup ---
            bg = new GreenfootImage("25.jpg"); 
            bg.scale(960, 540);
            
            // Add ALL treasures for Stage 2 HERE
            addObject(new Treasure(), 30, 500);
            addObject(new Treasure(), 200, 500);
            addObject(new Treasure(), 400, 500);
            addObject(new Treasure(), 600, 500);
            addObject(new Treasure(), 850, 500);
        } else if (stageNumber == 3) {
            // --- STAGE 3 setup ---
            bg = new GreenfootImage("26.jpg"); 
            bg.scale(960, 540);
            
            // Add ALL treasures for Stage 3 HERE
            addObject(new Treasure(), 30, 500);
            addObject(new Treasure(), 200, 500);
            addObject(new Treasure(), 400, 500);
            addObject(new Treasure(), 600, 500);
            addObject(new Treasure(), 850, 500);

        } else {
            // Failsafe: Default to Stage 1
            bg = new GreenfootImage("24.jpg"); 
            bg.scale(960, 540);
        }
        setPaintOrder(Hud.class, Koin.class, Kail.class, Boat.class, Fish.class); // HUD dan ikon tetap di depan 
        hud = new Hud(getWidth(), 36, 5);
        addObject(hud, getWidth()/2, 20);
        bg.scale(960, 540);

        originalBg = new GreenfootImage(bg); 
        setBackground(bg);
        
        keyItemIcon = new GreenfootImage("key_item.png"); // You need to create this image
        keyItemIcon.scale(50, 50); // Scale it for the HUD
        setBackground(bg);
    
        menuButton = new MenuGameplay();
        addObject(menuButton, getWidth() - 55, 70);

        shopButton = new btnShop();
        addObject(shopButton, getWidth() - 55, 120);
        
        coinIcon = new Koin();
        addObject(coinIcon, 50, 105);
        
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
        // We use stageNumber (which is 1, 2, or 3)
        // and subtract 1 to get an array index (0, 1, or 2).
        int index = stageNumber - 1; 
        
        // Safety check to make sure the index is valid
        return Math.max(0, Math.min(index, GameSettings.EnemyHealth[0].length - 1));
    }

    private void spawnFish() {
        Actor ikanBaru; 
        
        // --- Get your two indexes ONE time ---
        int d_idx = difficultyIndex(); // 0, 1, or 2 (for Easy, Med, Hard)
        int s_idx = levelIndex();      // 0, 1, or 2 (for Stage 1, 2, 3)
        // ---
        
        int roll = Greenfoot.getRandomNumber(100);

        if (roll < 10) { // Epic
            ikanBaru = new EpicFish();
            EpicFish e = (EpicFish) ikanBaru;

            // Use Difficulty for size
            e.setFishSize(GameSettings.epicFishSize[d_idx][0], GameSettings.epicFishSize[d_idx][1]);
            // Use Difficulty for speed
            int speed = Greenfoot.getRandomNumber(
                GameSettings.epicFishSpeed[d_idx][1] - GameSettings.epicFishSpeed[d_idx][0] + 1
            ) + GameSettings.epicFishSpeed[d_idx][0];
            e.setSpeed(speed);

        } else if (roll < 35) { // Rare
            ikanBaru = new RareFish();
            RareFish r = (RareFish) ikanBaru;

            // Use Difficulty for size
            r.setFishSize(GameSettings.rareFishSize[d_idx][0], GameSettings.rareFishSize[d_idx][1]);
            // Use Difficulty for speed
            int speed = Greenfoot.getRandomNumber(
                GameSettings.rareFishSpeed[d_idx][1] - GameSettings.rareFishSpeed[d_idx][0] + 1
            ) + GameSettings.rareFishSpeed[d_idx][0];
            r.setSpeed(speed);

        } else { // Common
            ikanBaru = new CommonFish();
            CommonFish c = (CommonFish) ikanBaru;

            // Use Difficulty for size
            c.setFishSize(GameSettings.commonFishSize[d_idx][0], GameSettings.commonFishSize[d_idx][1]);
            // Use Difficulty for speed
            int speed = Greenfoot.getRandomNumber(
                GameSettings.commonFishSpeed[d_idx][1] - GameSettings.commonFishSpeed[d_idx][0] + 1
            ) + GameSettings.commonFishSpeed[d_idx][0];
            c.setSpeed(speed);
        }
        
        int side = Greenfoot.getRandomNumber(2); 
        int y = Greenfoot.getRandomNumber(getHeight() - 200) + 300; 
        int x = (side == 0) ? -40 : getWidth() + 40;
        addObject(ikanBaru, x, y);
        
        // --- SHARK SPAWNING (NOW CORRECT) ---
        int sharkRoll = Greenfoot.getRandomNumber(1000);
        if (sharkRoll < 75) { 
            // Get health based on BOTH Difficulty and Stage
            int health = GameSettings.EnemyHealth[d_idx][s_idx];
            enemyShark shark = new enemyShark(health);

            int yHiu = 273;
            int sideHiu = Greenfoot.getRandomNumber(2); 
            if (sideHiu == 0) {
               shark.setDirection(1); 
               addObject(shark, -50, yHiu);
            } else {
               shark.setDirection(-1);
               addObject(shark, getWidth() + 50, yHiu);
            }
        }
        
        // --- PUFFER SPAWNING (NOW CORRECT) ---
        int pufferRoll = Greenfoot.getRandomNumber(1000);
        if (pufferRoll < 50) { 
            // Get health based on BOTH Difficulty and Stage
            int health = GameSettings.EnemyHealth[d_idx][s_idx]; // (I am assuming this is your array name)
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