import greenfoot.*;

public class GameWorld extends World {
    private int score = 0;
    private int life = 5;
    private int timeLeft = 60; 
    private int currentLevel = 0;
    private int stageNumber;
    //Keys
    private int keyItems = 0;
    private int keysNeeded = 5;
    private GreenfootImage originalBg; 
    private final SimpleTimer secondTimer = new SimpleTimer();
    private StatDisplay statDisplay;
    // Fish
    private int totalFish = 0;
    private final SimpleTimer fishSpawnTimer = new SimpleTimer();
    //Player
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
    private int dashCapacity;
    private int dashCharges;

    //shop
    private static final int LongSpearMaxLevel = 3;
    private static final int SpeedMaxLevel = 5;
    private static final int BoostMaxLevel = 2;
    private static final int HeartMaxPurchase = 20;
    private static final int[] SpeedLevelValues = {2, 3, 4, 5, 6, 7};
    private static final int[] DashCapacityValues = {3, 5, 7};

    
    public static final int CoinRewardTreasure = 40;

    //Boss
    private boolean bossHasSpawned = false;
    private int bossSpawnTime = 120;
    private boolean goldFishGuaranteedSpawn = false;
    private GreenfootImage[] bgFrames = null;
    private int currentFrame = 0;
    private SimpleTimer animTimer = new SimpleTimer();
    private int animSpeedMs = 500; 

    public GameWorld(int stageNum) {
        super(1152, 648, 1, false);
        this.stageNumber = stageNum; 
        dashCapacity = dashCapacityForLevel(boostUpgrades);
        dashCharges = dashCapacity;
        GreenfootImage bg;
        String musicFile;

        if (stageNumber == 1) {
            musicFile = "level1.mp3";
            SoundManager.play(musicFile, 20);

            bgFrames = new GreenfootImage[4]; 
            bgFrames[0] = new GreenfootImage("Map1F1.png");
            bgFrames[1] = new GreenfootImage("Map1F2.png");
            bgFrames[2] = new GreenfootImage("Map1F3.png");
            bgFrames[3] = new GreenfootImage("Map1F4.png");

            for (int i = 0; i < bgFrames.length; i++) {
                bgFrames[i].scale(1152, 648);
            }
            setBackground(bgFrames[0]); 
            animTimer.mark();
            bg = bgFrames[0]; 
            addObject(new Treasure(), 30, 630);
            addObject(new Treasure(), 690, 630);
            addObject(new Treasure(), 1000, 630);
            addObject(new Treasure(), 309, 630);
        } else if (stageNumber == 2) {
            musicFile = "level2.mp3";
            SoundManager.play(musicFile, 30);

            bgFrames = new GreenfootImage[4]; 
            bgFrames[0] = new GreenfootImage("Map2F1.png");
            bgFrames[1] = new GreenfootImage("Map2F2.png");
            bgFrames[2] = new GreenfootImage("Map2F3.png");
            bgFrames[3] = new GreenfootImage("Map2F4.png");

            for (int i = 0; i < bgFrames.length; i++) {
                bgFrames[i].scale(1152, 648);
            }
            setBackground(bgFrames[0]);
            animTimer.mark();
            bg = bgFrames[0]; 
            addObject(new Treasure(), 30, 630);
            addObject(new Treasure(), 456, 630);
            addObject(new Treasure(), 1100, 630);
        } else if (stageNumber == 3) {
            musicFile = "level3.mp3";
            SoundManager.play(musicFile, 50);

            bgFrames = new GreenfootImage[4]; 
            bgFrames[0] = new GreenfootImage("Map3F1.png");
            bgFrames[1] = new GreenfootImage("Map3F2.png");
            bgFrames[2] = new GreenfootImage("Map3F3.png");
            bgFrames[3] = new GreenfootImage("Map3F4.png");

            for (int i = 0; i < bgFrames.length; i++) {
                bgFrames[i].scale(1152, 648);
            }
            setBackground(bgFrames[0]); 
            animTimer.mark();
            bg = bgFrames[0];
            addObject(new Treasure(), 30, 630);
            addObject(new Treasure(), 300, 630);
            addObject(new Treasure(), 1140, 630);

        }
        setPaintOrder(Hud.class, DamageFlash.class, Koin.class, Kail.class,Boat.class);  
        hud = new Hud(getWidth(), 36, 5);
        addObject(hud, getWidth()/2, 20);
        originalBg = new GreenfootImage(getBackground()); 
        statDisplay = new StatDisplay();
        addObject(statDisplay, 100, 110);
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
        boat = new Boat(this);
        addObject(boat, boatX + 20, 320);
        applyBoatSpeed();
        applyDashCapacity();    
        boat.syncWeaponFromStats();
        applyLongSpearToPlayer();

        hook = new Kail(boat);         
        addObject(hook, boatX + 20, 250 + 180);        
        startTimer(150);
    }

    public void act() {
        if (bgFrames != null) {
            if (animTimer.hasElapsed(animSpeedMs))
            {
                currentFrame = (currentFrame + 1) % bgFrames.length; 
                setBackground(bgFrames[currentFrame]);
                animTimer.mark();
                updateHUD();
            }
        }
        if (gameOverTriggered) {
            return;
        }
        if (!bossHasSpawned && timeLeft <= bossSpawnTime) {
            if (stageNumber == 2 || stageNumber == 3) {
                spawnBoss();
                bossHasSpawned = true;
            }
        }

        if (secondTimer.hasElapsed(1000)) {
            timeLeft = Math.max(0, timeLeft - 1);
            secondTimer.mark();
            if (timeLeft == 0) {
                triggerGameOver("Times Up!");
                return;
            }
        }

        if (fishSpawnTimer.hasElapsed(1210)) {
            spawnFish();
            fishSpawnTimer.mark();
        }
        if (life <= 0) {
            triggerGameOver("You Died!");
        }

        if (!goldFishGuaranteedSpawn && timeLeft <= 120) {
            spawnGoldFish();
            goldFishGuaranteedSpawn = true;
        }
        if (bgFrames == null) {
            updateHUD();
        }   
    }

    private void updateLevelFromSettings() {
        if (GameSettings.difficulty.equals("Medium")) {
            currentLevel = 1;
        } else if (GameSettings.difficulty.equals("Hard")) {
            currentLevel = 2;
        } else {
            currentLevel = 0; 
        }
    }    

    public void addScore(int v) { score += v; }
    
    public void addLife(int v)  {
        life  = Math.max(0, Math.min(5, life + v)); 
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

    public void openShopMenu() {
        if (gameOverTriggered) {
            return;
        }
        secondTimer.mark();
        fishSpawnTimer.mark();
        Greenfoot.setWorld(new bgShop(this)); 
    }

    private void triggerGameOver(String reason) {
        gameOverTriggered = true;
        secondTimer.mark();
        fishSpawnTimer.mark();
        Greenfoot.setWorld(new gameOver(score, reason, stageNumber));
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

        if (statDisplay != null) {
            statDisplay.update(keyItems, keysNeeded, coins, dashCharges, dashCapacity);
        }

    }

    public void reduceTimer(int seconds) {
        timeLeft = Math.max(0, timeLeft - seconds); 
        updateHUD(); 
        if (timeLeft == 0) {
            triggerGameOver("Times Up!");
        }
    }

    private int difficultyIndex() {
        String d = GameSettings.difficulty;
        if ("Medium".equals(GameSettings.difficulty)) return 1;
        if ("Hard".equals(GameSettings.difficulty)) return 2;
        return 0; 
    }

    private int levelIndex() {
        int index = stageNumber - 1; 
        return Math.max(0, Math.min(index, GameSettings.EnemyHealth[0].length - 1));
    }

    private void spawnFish() {
        Actor ikanBaru; 
        int d_idx = difficultyIndex(); 
        int s_idx = levelIndex(); 
        int difficultyMod = d_idx * 10; 

        int roll = Greenfoot.getRandomNumber(100);

        if (roll < 20) { // Epic
            ikanBaru = new EpicFish();
            EpicFish e = (EpicFish) ikanBaru;
            int category;
            int sizeRoll = Greenfoot.getRandomNumber(100);
            if (sizeRoll < 35) {
                category = 0; // Kecil
                e.setValue(15); // Skor 15
                e.setCoinReward(25);//koin 25
            } else if (sizeRoll < 70) {
                category = 1; // Normal
                e.setValue(30); //skor 30
                e.setCoinReward(30);// koin30
            } else {
                category = 2; // Besar (lebih jarang)
                e.setValue(45); //skor 45
                e.setCoinReward(50); // koin50
            }
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
            int category;
            int sizeRoll = Greenfoot.getRandomNumber(100);
            if (sizeRoll < 50) {
                category = 0; // Kecil
                r.setValue(4); // Skor 4
                r.setCoinReward(9); //koin 9
            } else if (sizeRoll < 80) {
                category = 1; // Normal
                r.setValue(5); // Skor 5
                r.setCoinReward(11); //koin 11
            } else {
                category = 2; // Besar 
                r.setValue(6); // Skor 6
                r.setCoinReward(13); //koin 13
            }

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
            int category;
            int sizeRoll = Greenfoot.getRandomNumber(100);

            if (sizeRoll < 50) {
                category = 0; // Kecil
                c.setValue(2); // Skor 2
                c.setCoinReward(5); //koin 5
            } else if (sizeRoll < 80) {
                category = 1; // Normal
                c.setValue(3); // Skor 3
                c.setCoinReward(7); // Koin 7
            } else {
                category = 2; // Besar
                c.setValue(4); // Skor 4
                c.setCoinReward(8); // Koin 8
            }

            int width = GameSettings.commonFishSize[category][0] - difficultyMod;
            int height = GameSettings.commonFishSize[category][1] - difficultyMod;
            c.setFishSize(width, height);

            int speed = Greenfoot.getRandomNumber(
                    GameSettings.commonFishSpeed[d_idx][1] - GameSettings.commonFishSpeed[d_idx][0] + 1
                ) + GameSettings.commonFishSpeed[d_idx][0];
            c.setSpeed(speed);
        }

        int side = Greenfoot.getRandomNumber(2); 
        int waterTop = 330;               
        int waterBottom = getHeight() - 70; 
        int y = Greenfoot.getRandomNumber(waterBottom - waterTop) + waterTop;

        int x = (side == 0) ? -40 : getWidth() + 40;
        addObject(ikanBaru, x, y);

        int sharkRoll = Greenfoot.getRandomNumber(100);
        if (sharkRoll < 8) { 
            int health = GameSettings.EnemyHealth[d_idx][s_idx];
            enemyShark shark = new enemyShark(health);
            int yHiu = 320;
            int sideHiu = Greenfoot.getRandomNumber(2); 

            if (sideHiu == 0) {
                shark.setDirection(1); 
                addObject(shark, -50, yHiu);
            }   else {
                shark.setDirection(-1);
                addObject(shark, getWidth() + 50, yHiu);
            }
        }
        int pufferRoll = Greenfoot.getRandomNumber(100);
        if (pufferRoll < 5) { 
            int health = GameSettings.EnemyHealth[d_idx][s_idx]; 
            EnemyPuffer puffer = new EnemyPuffer(health);
            int yPuffer = Greenfoot.getRandomNumber(getHeight() - 200) + 300;

            if (Greenfoot.getRandomNumber(2) == 0) {
                addObject(puffer, -50, yPuffer);
            } else {
                addObject(puffer, getWidth() + 50, yPuffer);
            }
        } 

        int goldFishRoll = Greenfoot.getRandomNumber(500);
        if (goldFishRoll < 2) { 
            spawnGoldFish();
        }
    }

    public boolean addKeyItem() { 
        if (keyItems < keysNeeded) {
            keyItems++;
            updateHUD();
        }
        if (keyItems >= keysNeeded) {
            secondTimer.mark();
            fishSpawnTimer.mark();
            if (stageNumber == 3) {
                String difficulty = GameSettings.difficulty;
                Greenfoot.setWorld(new StoryWorld4(difficulty, 99)); 

            } else {
                Greenfoot.setWorld(new menuCompletion(score, timeLeft, totalFish, stageNumber));
            }
            return true; 
        }
        return false;
    }

    private void spawnGoldFish() {
        GoldFish goldie = new GoldFish();

        int waterTop = 300; 
        int waterBottom = getHeight() - 70; 
        int range = Math.max(1, waterBottom - waterTop);
        int yPos = Greenfoot.getRandomNumber(range) + waterTop;
        int side = Greenfoot.getRandomNumber(2);
        if (side == 0) {
            goldie.setDirection(1); 
            addObject(goldie, -50, yPos);
        } else {
            goldie.setDirection(-1); 
            addObject(goldie, getWidth() + 50, yPos);
        }
    }

    public void addFishCollected(int amount) {
        totalFish += amount;
    }
    public int getLongSpearUpgrades() {
        return longSpearUpgrades;
    }

    public ShopPurchaseResult tryPurchaseLongSpear(int cost) {
        if (longSpearUpgrades >= LongSpearMaxLevel) {
            return ShopPurchaseResult.MaxedOut;
        }
        if (!withdrawCoins(cost)) {
            return ShopPurchaseResult.NotEnoughCoins;
        }
        longSpearUpgrades++;
        return ShopPurchaseResult.Purchased;
    }

    public void applyLongSpearToPlayer() {
        PlayerStats.weaponTier = Math.max(0, Math.min(longSpearUpgrades, PlayerStats.MaxWeaponTier));
        if (boat != null) {
            boat.syncWeaponFromStats();
        }
    }

    public int getSpeedUpgrades() {
        return speedUpgrades;
    }

    public ShopPurchaseResult tryPurchaseSpeed(int cost) {
        if (speedUpgrades >= SpeedMaxLevel) {
            return ShopPurchaseResult.MaxedOut;
        }
        if (!withdrawCoins(cost)) {
            return ShopPurchaseResult.NotEnoughCoins;
        }
        speedUpgrades++;
        applyBoatSpeed();
        return ShopPurchaseResult.Purchased;
    }

    public int getBoostUpgrades() {
        return boostUpgrades;
    }

    public ShopPurchaseResult tryPurchaseBoost(int cost) {
        if (boostUpgrades >= BoostMaxLevel) {
            return ShopPurchaseResult.MaxedOut;
        }
        if (!withdrawCoins(cost)) {
            return ShopPurchaseResult.NotEnoughCoins;
        }
        boostUpgrades++;
        applyDashCapacity();
        return ShopPurchaseResult.Purchased;
    }

    public int getHeartPurchases() {
        return heartPurchases;
    }

    public ShopPurchaseResult tryPurchaseHeart(int cost) {
        if (heartPurchases >= HeartMaxPurchase) {
            return ShopPurchaseResult.MaxedOut;
        }
        if (!withdrawCoins(cost)) {
            return ShopPurchaseResult.NotEnoughCoins;
        }
        heartPurchases++;
        addLife(2);
        return ShopPurchaseResult.Purchased;
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
        int d_idx = difficultyIndex();
        int s_idx = levelIndex(); 
        int bossHealth = GameSettings.BossHealth[d_idx][s_idx];

        if (stageNumber == 2) {
            crocBoss croc = new crocBoss(bossHealth);
            BossHealthBar healthBar = new BossHealthBar(croc);
            addObject(healthBar, getWidth() / 2, 70);
            addObject(croc, -100, 430);
        }
        else if (stageNumber == 3) {
            nyiRoroBoss roro = new nyiRoroBoss(bossHealth);
            BossHealthBar healthBar = new BossHealthBar(roro);
            addObject(healthBar, getWidth() / 2, 40);
            addObject(roro, 100, 300);
        }

    }

    private void applyBoatSpeed() {
        if (boat == null) {
            return;
        }
        int index = Math.max(0, Math.min(speedUpgrades, SpeedLevelValues.length - 1));
        boat.setSpeed(SpeedLevelValues[index]);
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
        int index = Math.max(0, Math.min(level, DashCapacityValues.length - 1));
        return DashCapacityValues[index];
    }
}
