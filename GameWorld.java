import greenfoot.*;

public class GameWorld extends World {
    private int score = 0;
    private int life = 5;
    private int timeLeft = 60; 
    private int currentLevel = 0; 
    private int stageNumber; 
    
    
    private int keyItems = 0;
    private int keysNeeded = 5;
    private GreenfootImage keyItemIcon;
    private GreenfootImage originalBg; 
    private GreenfootImage dashIcon;
    private final SimpleTimer secondTimer = new SimpleTimer();
    
    private int totalFish = 0;
    private final SimpleTimer fishSpawnTimer = new SimpleTimer();
    
    private Boat boat;
    private Kail hook;
    private Hud hud;
    private MenuGameplay menuButton;
    private btnShop shopButton;
    private boolean gameOverTriggered = false;
    
    
    private int longSpearUpgrades = 0;
    private int speedUpgrades = 0;
    private int boostUpgrades = 0;
    private int heartPurchases = 0;
    private int coins = 0;
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
    
    
    private boolean bossHasSpawned = false;
    private int bossSpawnTime = 120; 
    private boolean goldFishGuaranteedSpawn = false;
    
    public GameWorld(int stageNum) {
        super(960, 540, 1, false);
        this.stageNumber = stageNum; 
        dashCapacity = dashCapacityForLevel(boostUpgrades);
        dashCharges = dashCapacity;
        GreenfootImage bg; 
        
        if (stageNumber == 1) {
            
            bg = new GreenfootImage("24.jpg"); 
            bg.scale(960, 540);
            
            addObject(new Treasure(), 30, 500);
            addObject(new Treasure(), 200, 500);
            addObject(new Treasure(), 400, 500);
            addObject(new Treasure(), 600, 500);
            addObject(new Treasure(), 850, 500);
            
        } else if (stageNumber == 2) {
            
            bg = new GreenfootImage("25.jpg"); 
            bg.scale(960, 540);
            
            addObject(new Treasure(), 30, 500);
            addObject(new Treasure(), 200, 500);
            addObject(new Treasure(), 400, 500);
            addObject(new Treasure(), 600, 500);
            addObject(new Treasure(), 850, 500);
        } else if (stageNumber == 3) {
            
            bg = new GreenfootImage("26.jpg"); 
            bg.scale(960, 540);
            
            addObject(new Treasure(), 30, 500);
            addObject(new Treasure(), 200, 500);
            addObject(new Treasure(), 400, 500);
            addObject(new Treasure(), 600, 500);
            addObject(new Treasure(), 850, 500);

        } else {
            bg = new GreenfootImage("24.jpg"); 
            bg.scale(960, 540);
        }
        setPaintOrder(Hud.class, Koin.class, Kail.class, Boat.class, Fish.class); 
        hud = new Hud(getWidth(), 36, 5);
        addObject(hud, getWidth()/2, 20);
        bg.scale(960, 540);

        originalBg = new GreenfootImage(bg); 
        setBackground(bg);
        
        keyItemIcon = new GreenfootImage("key_item.png"); 
        keyItemIcon.scale(50, 50); 
        dashIcon = new GreenfootImage("key_item.png");
        dashIcon.scale(50, 50);
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

        boat = new Boat(this);
        addObject(boat, boatX + 20, 250);
        applyBoatSpeed();
        applyDashCapacity();    

        hook = new Kail(boat);           
        addObject(hook, boatX + 20, 250 + 180); 

        startTimer(130);
    }

    public void act() {
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
        
        if (!goldFishGuaranteedSpawn && timeLeft <= 180) {
            spawnGoldFish();
            goldFishGuaranteedSpawn = true; 
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

        getBackground().drawImage(originalBg, 0, 0);
        
        if (hud != null) {
            hud.update(score, life, timeLeft);
        }
        
        getBackground().drawImage(keyItemIcon, 20, 40);
        GreenfootImage keyLabel = new GreenfootImage(keyItems + " / " + keysNeeded, 20, HUD_TEXT_COLOR, HUD_TEXT_BG);
        getBackground().drawImage(keyLabel, 80, 65 - keyLabel.getHeight() / 2);

        GreenfootImage coinLabel = new GreenfootImage(coins + "$", 20, HUD_TEXT_COLOR, HUD_TEXT_BG);
        getBackground().drawImage(coinLabel, 80, 110 - coinLabel.getHeight() / 2);
        
        GreenfootImage dashLabel = new GreenfootImage("Sisa Dash: " + dashCharges + "/" + dashCapacity, 20, HUD_TEXT_COLOR, HUD_TEXT_BG);
        getBackground().drawImage(dashLabel, 80, 145 - dashLabel.getHeight() / 2);
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

        if (roll < 10) { 
            ikanBaru = new EpicFish();
            EpicFish e = (EpicFish) ikanBaru;

            int category;
            int sizeRoll = Greenfoot.getRandomNumber(100);
            
            
            if (sizeRoll < 35) {
                category = 0; 
                e.setValue(15); 
                e.setCoinReward(25);
            } else if (sizeRoll < 70) {
                category = 1; 
                e.setValue(30); 
                e.setCoinReward(30);
            } else {
                category = 2; 
                e.setValue(45); 
                e.setCoinReward(50); 
            }
            
            
            int width = GameSettings.epicFishSize[category][0] - difficultyMod;
            int height = GameSettings.epicFishSize[category][1] - difficultyMod;
            e.setFishSize(width, height);
            
            int speed = Greenfoot.getRandomNumber(
                GameSettings.epicFishSpeed[d_idx][1] - GameSettings.epicFishSpeed[d_idx][0] + 1
            ) + GameSettings.epicFishSpeed[d_idx][0];
            e.setSpeed(speed);

        } else if (roll < 35) { 
            ikanBaru = new RareFish();
            RareFish r = (RareFish) ikanBaru;

            int category;
            int sizeRoll = Greenfoot.getRandomNumber(100);
            
            if (sizeRoll < 50) {
                category = 0; 
                r.setValue(4); 
                r.setCoinReward(9); 
            } else if (sizeRoll < 80) {
                category = 1; 
                r.setValue(5); 
                r.setCoinReward(11);
            } else {
                category = 2; 
                r.setValue(6); 
                r.setCoinReward(13);
            }
            
            
            int width = GameSettings.rareFishSize[category][0] - difficultyMod;
            int height = GameSettings.rareFishSize[category][1] - difficultyMod;
            r.setFishSize(width, height);
            
            int speed = Greenfoot.getRandomNumber(
                GameSettings.rareFishSpeed[d_idx][1] - GameSettings.rareFishSpeed[d_idx][0] + 1
            ) + GameSettings.rareFishSpeed[d_idx][0];
            r.setSpeed(speed);

        } else { 
            ikanBaru = new CommonFish();
            CommonFish c = (CommonFish) ikanBaru;

            int category;
            int sizeRoll = Greenfoot.getRandomNumber(100);
            
            if (sizeRoll < 50) {
                category = 0; 
                c.setValue(2); 
                c.setCoinReward(3); 
            } else if (sizeRoll < 90) {
                category = 1; 
                c.setValue(3); 
                c.setCoinReward(4); 
            } else {
                category = 2; 
                c.setValue(4); 
                c.setCoinReward(5); 
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
        int y = Greenfoot.getRandomNumber(getHeight() - 200) + 300; 
        int x = (side == 0) ? -40 : getWidth() + 40;
        addObject(ikanBaru, x, y);
        
        int sharkRoll = Greenfoot.getRandomNumber(100);
        if (sharkRoll < 1) { 
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
        
        int pufferRoll = Greenfoot.getRandomNumber(100);
        if (pufferRoll < 1) { 
            
            int health = GameSettings.EnemyHealth[d_idx][s_idx]; 
            EnemyPuffer puffer = new EnemyPuffer(health);
            int yPuffer = Greenfoot.getRandomNumber(getHeight() - 200) + 300;
            addObject(puffer, -50, yPuffer);
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
            
            
            Greenfoot.setWorld(new menuCompletion(score, timeLeft, totalFish, stageNumber));

            return true; 
        }
    
        
        return false;
    }
    
    private void spawnGoldFish() {
        GoldFish goldie = new GoldFish();
    
        int yPos = Greenfoot.getRandomNumber(getHeight() - 200) + 300; 
    
        
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
        int d_idx = difficultyIndex();
        int s_idx = levelIndex(); 
        
        int bossHealth = GameSettings.BossHealth[d_idx][s_idx];
    
        if (stageNumber == 2) {
            
            
            crocBoss croc = new crocBoss(bossHealth);
            
            
            BossHealthBar healthBar = new BossHealthBar(croc);
            
            
            addObject(healthBar, getWidth() / 2, 40);
            
            
            addObject(croc, -100, 230); 
    
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