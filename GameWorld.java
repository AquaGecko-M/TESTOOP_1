import greenfoot.*;

public class GameWorld extends World {
    private int score = 0;
    private int life = 3;
    private int timeLeft = 300; // detik per level
    private final SimpleTimer secondTimer = new SimpleTimer();

    private Boat boat;
    private Kail hook;

    //variabel
    private int[] currentFishSize = new int[2];
    private int currentSpeed;
    private int currentLevel = 0; // 0 = easy, 1 = medium, 2 = hard
    
    // Fish
    private final SimpleTimer fishSpawnTimer = new SimpleTimer();
    private MenuGameplay menuButton;

    public GameWorld() {
        super(960, 540, 1);
        setPaintOrder(Kail.class, Boat.class);
        prepare();
        updateHUD();

        GreenfootImage bg = new GreenfootImage("24.jpg");
        bg.scale(960, 540);
        setBackground(bg);
    }

    private void prepare() {
        int boatX = getWidth() / 2;
        int boatY = 120;

        boat = new Boat();
        addObject(boat, boatX, 250);

        hook = new Kail(boat);              // hook “terikat” ke boat
        addObject(hook, boatX, boatY + 180);

        menuButton = new MenuGameplay();
        addObject(menuButton, getWidth() - 60, 50);


        updateLevelFromSettings();
    }

    public void act() {
        if (secondTimer.hasElapsed(1000)) {
            timeLeft = Math.max(0, timeLeft - 1);
            secondTimer.mark();
            updateHUD();

            if (timeLeft == 0) {
                showText("Waktu Habis! Skor: " + score, getWidth() / 2, getHeight() / 2);
                Greenfoot.stop();
            }
        }

        if (fishSpawnTimer.hasElapsed(900)) {
            spawnFish();
            fishSpawnTimer.mark();
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

    public void addScore(int value) {
        score += value;
        updateHUD();
    }

    public void addLife(int value) {
        life += value;
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
        int y = Greenfoot.getRandomNumber(getHeight() - 200) + 200; // area air
        int x = (side == 0) ? -100 : getWidth() + 100;
    
        addObject(ikanBaru, x, y);
        
    }
}

