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
    private GreenfootImage dashIcon;
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
    private int coins = 2000;
    private Koin coinIcon;
    private int dashCapacity;
    private int dashCharges;
    
    public static final int COIN_REWARD_COMMON = 5;
    public static final int COIN_REWARD_RARE = 12;
    public static final int COIN_REWARD_EPIC = 25;
    public static final int COIN_REWARD_TREASURE = 40;

    private static final int LONG_SPEAR_MAX_LEVEL = 3;
    private static final int SPEED_MAX_LEVEL = 5;
    private static final int BOOST_MAX_LEVEL = 2;
    private static final int HEART_MAX_PURCHASE = 20;
    private static final int[] SPEED_LEVEL_VALUES = {1, 2, 3, 4, 5, 7};
    private static final int[] DASH_CAPACITY_VALUES = {3, 5, 7};

    private static final Color HUD_TEXT_COLOR = Color.WHITE;
    private static final Color HUD_TEXT_BG = new Color(0, 0, 0, 0);
    
    //Boss
    private boolean bossHasSpawned = false;
    private int bossSpawnTime = 120; // 300s - 180s = 120s left
    private boolean goldFishGuaranteedSpawn = false;
    
    public GameWorld(int stageNum) {
        super(1280, 720, 1, false);
        this.stageNumber = stageNum; // Store the stage number we were given
        dashCapacity = dashCapacityForLevel(boostUpgrades);
        dashCharges = dashCapacity;
        GreenfootImage bg; // Create a temporary variable for the background
        
        if (stageNumber == 1) {
            // --- STAGE 1 setup ---
            bg = new GreenfootImage("24.jpg"); 
            bg.scale(1280, 720);
            
            // Add ALL treasures for Stage 1 HERE
            addObject(new Treasure(), 30, 700);
            addObject(new Treasure(), 890, 700);
            addObject(new Treasure(), 1123, 700);
        } else if (stageNumber == 2) {
            // --- STAGE 2 setup ---
            bg = new GreenfootImage("25.jpg"); 
            bg.scale(1280, 720);
            
            // Add ALL treasures for Stage 2 HERE
            addObject(new Treasure(), 30, 700);
            addObject(new Treasure(), 456, 700);
            addObject(new Treasure(), 1130, 700);
        } else if (stageNumber == 3) {
            // --- STAGE 3 setup ---
            bg = new GreenfootImage("26.jpg"); 
            bg.scale(1280, 720);
            
            // Add ALL treasures for Stage 3 HERE
            addObject(new Treasure(), 30, 700);
            addObject(new Treasure(), 300, 700);
            addObject(new Treasure(), 1200, 700);

        } else {
            // Failsafe: Default to Stage 1
            bg = new GreenfootImage("24.jpg"); 
            bg.scale(1280, 720);
        }
        setPaintOrder(Hud.class, DamageFlash.class, Koin.class, Kail.class,  Fish.class,Boat.class); // HUD dan ikon tetap di depan 
        hud = new Hud(getWidth(), 36, 5);
        addObject(hud, getWidth()/2, 20);
        bg.scale(1280, 720);

        originalBg = new GreenfootImage(bg); 
        setBackground(bg);
        
        keyItemIcon = new GreenfootImage("key_item.png"); // You need to create this image
        keyItemIcon.scale(50, 25); // Scale it for the HUD
        dashIcon = new GreenfootImage("key_item.png");
        dashIcon.scale(50, 25);
        setBackground(bg);
    
        menuButton = new MenuGameplay();
        addObject(menuButton, getWidth() - 55, 70);

        shopButton = new btnShop();
        addObject(shopButton, getWidth() - 55, 135);
        
        coinIcon = new Koin();
        addObject(coinIcon, 43, 110);
        
        prepare();
        updateHUD();
        updateLevelFromSettings();
    }
    
    private void prepare() {
        int boatX = getWidth() / 2;
        int boatY = 250; 

        boat = new Boat(this);
        addObject(boat, boatX + 20, 320);
        applyBoatSpeed();
        applyDashCapacity();    
        boat.syncWeaponFromStats();
        applyLongSpearToPlayer();
        
        hook = new Kail(boat);           // hook “terikat” ke boat
        addObject(hook, boatX + 20, 250 + 180); // Posisi kail di bawah boat

        startTimer(121);
    }

    public void act() {
        if (gameOverTriggered) {
            return;
        }
        
        if (!bossHasSpawned && timeLeft <= bossSpawnTime) {
                // Only spawn on Stage 2 or 3
                if (stageNumber == 2 || stageNumber == 3) {
                    spawnBoss();
                    bossHasSpawned = true; // Set the switch so it only spawns once!
                }
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

        if (fishSpawnTimer.hasElapsed(1200)) {
            spawnFish();
            fishSpawnTimer.mark();
        }
        if (Greenfoot.isKeyDown("h")) { boat.takeDamage(1); Greenfoot.delay(5); }
        
        if (life <= 0) {
            triggerGameOver("You Died!");
        }
        
        if (!goldFishGuaranteedSpawn && timeLeft <= 180) {
            spawnGoldFish();
            goldFishGuaranteedSpawn = true; // Set flag agar tidak spawn lagi
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
        
        getBackground().drawImage(keyItemIcon, 20, 50);
        GreenfootImage keyLabel = new GreenfootImage(keyItems + " / " + keysNeeded, 20, HUD_TEXT_COLOR, HUD_TEXT_BG);
        getBackground().drawImage(keyLabel, 80, 65 - keyLabel.getHeight() / 2);

        GreenfootImage coinLabel = new GreenfootImage(coins + "$", 20, HUD_TEXT_COLOR, HUD_TEXT_BG);
        getBackground().drawImage(coinLabel, 80, 115 - coinLabel.getHeight() / 2);
        
        getBackground().drawImage(dashIcon, 20, 145);
        GreenfootImage dashLabel = new GreenfootImage("Sisa Dash: " + dashCharges + "/" + dashCapacity, 20, HUD_TEXT_COLOR, HUD_TEXT_BG);
        getBackground().drawImage(dashLabel, 80, 165 - dashLabel.getHeight() / 2);
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
        
        // --- Ambil indeks satu kali ---
        int d_idx = difficultyIndex(); // 0, 1, or 2 (Easy, Med, Hard)
        int s_idx = levelIndex();      // 0, 1, or 2 (Stage 1, 2, 3)
        
        // --- Hitung pengurang ukuran berdasarkan difficulty ---
        // Easy: 0, Medium: -10, Hard: -20
        int difficultyMod = d_idx * 10; 
        
        int roll = Greenfoot.getRandomNumber(100);

        if (roll < 20) { // Epic
            ikanBaru = new EpicFish();
            EpicFish e = (EpicFish) ikanBaru;

            // --- LOGIKA UKURAN BARU (dengan penalti -3%) ---
            int category;
            int sizeRoll = Greenfoot.getRandomNumber(100);
            // Normal: 33% Kecil, 33% Normal, 33% Besar
            // Baru: 35% Kecil, 35% Normal, 30% Besar
            if (sizeRoll < 35) {
                category = 0; // Kecil
                e.setValue(15); // Skor 4
                e.setCoinReward(25);
            } else if (sizeRoll < 70) {
                category = 1; // Normal
                e.setValue(30); 
                e.setCoinReward(30);
            } else {
                category = 2; // Besar (lebih jarang)
                e.setValue(45); 
                e.setCoinReward(50); //50
            }
            // ---
            
            int width = GameSettings.epicFishSize[category][0] - difficultyMod;
            int height = GameSettings.epicFishSize[category][1] - difficultyMod;
            e.setFishSize(width, height);
            
            int speed = Greenfoot.getRandomNumber(
                GameSettings.epicFishSpeed[d_idx][1] - GameSettings.epicFishSpeed[d_idx][0] + 1
            ) + GameSettings.epicFishSpeed[d_idx][0];
            e.setSpeed(speed);

        } else if (roll < 55) { // Rare
            ikanBaru = new RareFish();
            RareFish r = (RareFish) ikanBaru;

            // --- LOGIKA UKURAN BARU (dengan penalti -5%) ---
            int category;
            int sizeRoll = Greenfoot.getRandomNumber(100);
            // Baru: 50% Kecil, 30% Normal, 20% Besar
            if (sizeRoll < 50) {
                category = 0; // Kecil
                r.setValue(4); // Skor 4
                r.setCoinReward(9); //koin 9
            } else if (sizeRoll < 80) {
                category = 1; // Normal
                r.setValue(5); // Skor 4
                r.setCoinReward(11);
            } else {
                category = 2; // Besar (lebih jarang)
                r.setValue(6); // Skor 4
                r.setCoinReward(13);
            }
            // ---
            
            int width = GameSettings.rareFishSize[category][0] - difficultyMod;
            int height = GameSettings.rareFishSize[category][1] - difficultyMod;
            r.setFishSize(width, height);
            
            int speed = Greenfoot.getRandomNumber(
                GameSettings.rareFishSpeed[d_idx][1] - GameSettings.rareFishSpeed[d_idx][0] + 1
            ) + GameSettings.rareFishSpeed[d_idx][0];
            r.setSpeed(speed);

        } else { // Common
            ikanBaru = new CommonFish();
            CommonFish c = (CommonFish) ikanBaru;

            // --- LOGIKA UKURAN BARU (dengan penalti -10%) ---
            int category;
            int sizeRoll = Greenfoot.getRandomNumber(100);
            // Baru: 50% Kecil, 40% Normal, 10% Besar
            if (sizeRoll < 50) {
                category = 0; // Kecil
                c.setValue(2); // Skor 2
                c.setCoinReward(3); //koin 3
            } else if (sizeRoll < 80) {
                category = 1; // Normal
                c.setValue(3); // Skor 3
                c.setCoinReward(4); // Koin 4
            } else {
                category = 2; // Besar (paling jarang)
                c.setValue(4); // Skor 3
                c.setCoinReward(5); // Koin 5
            }
            // ---
            
            int width = GameSettings.commonFishSize[category][0] - difficultyMod;
            int height = GameSettings.commonFishSize[category][1] - difficultyMod;
            c.setFishSize(width, height);
            
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
            int health = GameSettings.EnemyHealth[d_idx][s_idx];
            enemyShark shark = new enemyShark(health);
            int yHiu = 320;
            int sideHiu = Greenfoot.getRandomNumber(2); 

            if (sideHiu == 0) {
                // spawn dari kiri, jalan ke kanan
                shark.setDirection(1); 
                addObject(shark, -50, yHiu);
            }   else {
                // spawn dari kanan, jalan ke kiri
                shark.setDirection(-1);
                addObject(shark, getWidth() + 50, yHiu);
            }
        }
        
        // --- PUFFER SPAWNING (NOW CORRECT) ---
        int pufferRoll = Greenfoot.getRandomNumber(100);
        if (pufferRoll < 5) { 
            int health = GameSettings.EnemyHealth[d_idx][s_idx]; 
            EnemyPuffer puffer = new EnemyPuffer(health);
            int yPuffer = Greenfoot.getRandomNumber(getHeight() - 200) + 300;

            if (Greenfoot.getRandomNumber(2) == 0) {
                // Spawn kiri
                addObject(puffer, -50, yPuffer);
            } else {
                // Spawn kanan
                addObject(puffer, getWidth() + 50, yPuffer);
            }
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
    
    private void spawnGoldFish() {
        GoldFish goldie = new GoldFish();

        int waterTop = 300; // Supaya tidak spawn di atas HUD/boat
        int waterBottom = getHeight() - 60; // Hindari spawn terlalu bawah sampai tidak terlihat
        int range = Math.max(1, waterBottom - waterTop);
        int yPos = Greenfoot.getRandomNumber(getHeight() - 200) + 300; // Area air

        // Acak sisi
        int side = Greenfoot.getRandomNumber(2);
        if (side == 0) {
            // Muncul di KIRI, bergerak ke KANAN
            goldie.setDirection(1); 
            addObject(goldie, -50, yPos);
        } else {
            // Muncul di KANAN, bergerak ke KIRI
            goldie.setDirection(-1); 
            addObject(goldie, getWidth() + 50, yPos);
        }
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
    
    public void applyLongSpearToPlayer() {
        // Map jumlah upgrade ke weaponTier (clamp ke max tier)
        PlayerStats.weaponTier = Math.max(0, Math.min(longSpearUpgrades, PlayerStats.MAX_WEAPON_TIER));
        // Jika boat sudah dibuat, sinkronkan stat ke boat sekarang juga
        if (boat != null) {
            boat.syncWeaponFromStats();
        }
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
        applyBoatSpeed();
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
        applyDashCapacity();
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
    
    private void spawnBoss() {
        // Get the difficulty and stage indexes you already made!
        int d_idx = difficultyIndex();
        int s_idx = levelIndex(); // This will be 1 (for Stage 2) or 2 (for Stage 3)
        
        // Get the boss health from our new GameSettings array
        int bossHealth = GameSettings.BossHealth[d_idx][s_idx];
    
        // We only spawn the Croc on Stage 2
        if (stageNumber == 2) {
            
            // 1. Create the boss (it starts in its "ENTERING" state)
            crocBoss croc = new crocBoss(bossHealth);
            
            // 2. Create the health bar and tell it to track the boss
            BossHealthBar healthBar = new BossHealthBar(croc);
            
            // 3. Add the health bar to the top of the screen
            addObject(healthBar, getWidth() / 2, 40);
            
            // 4. Add the boss off-screen to the left
            addObject(croc, -100, 230); // (Adjust 350 Y-coordinate as needed)
    
        }
        else if (stageNumber == 3) {
            
            // 1. Create the Nyi Roro boss
            nyiRoroBoss roro = new nyiRoroBoss(bossHealth);
            
            // 2. Create the health bar and tell it to track her
            // This also works because 'nyiRoroBoss' will implement 'IBoss'
            BossHealthBar healthBar = new BossHealthBar(roro);
            
            // 3. Add them to the world
            addObject(healthBar, getWidth() / 2, 40);
            addObject(roro, 100, 300); // (Her addedToWorld will handle positioning)
        }
        
    }
    
    private void applyBoatSpeed() {
        if (boat == null) {
            return;
        }
        int index = Math.max(0, Math.min(speedUpgrades, SPEED_LEVEL_VALUES.length - 1));
        boat.setSpeed(SPEED_LEVEL_VALUES[index]);
    }
    
    public int getDashCharges() {
        return dashCharges;
    }

    public int getDashCapacity() {
        return dashCapacity;
    }

    public int getDashCapacityForLevel(int level) {
        return dashCapacityForLevel(level);
    }

    public void notifyDashChanged(int charges, int capacity) {
        dashCharges = charges;
        dashCapacity = capacity;
        updateHUD();
    }

    private void applyDashCapacity() {
        dashCapacity = dashCapacityForLevel(boostUpgrades);
        dashCharges = dashCapacity;
        if (boat != null) {
            boat.setDashCapacity(dashCapacity);
            boat.restoreDashFull();
        }
    }

    private int dashCapacityForLevel(int level) {
        int index = Math.max(0, Math.min(level, DASH_CAPACITY_VALUES.length - 1));
        return DASH_CAPACITY_VALUES[index];
    }
    
}