import greenfoot.*;

public class GameWorld extends World {
    private int score = 0;
    private int life = 3;
    private int timeLeft = 60; // detik per level
    private final SimpleTimer secondTimer = new SimpleTimer();
    private int keyItems = 0;
    private int keysNeeded = 5;
    private GreenfootImage keyItemIcon;
    private GreenfootImage originalBg; // To fix HUD overlapping

    private Boat boat;
    private Kail hook;
    
    // Fish
    private final SimpleTimer fishSpawnTimer = new SimpleTimer();
    private MenuGameplay menuButton;

    public GameWorld() {
        super(960, 540, 1);
        setPaintOrder(Kail.class, Boat.class); // Mengganti ke Kail.class
    
        originalBg = new GreenfootImage("24.jpg");
        originalBg.scale(960, 540);

        keyItemIcon = new GreenfootImage("key_item.png"); // You need to create this image
        keyItemIcon.scale(30, 30); // Scale it for the HUD
        
        setBackground(new GreenfootImage(originalBg)); // Set the background to a *copy*
        prepare();
        updateHUD();
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
        // 1. Reset the background to its clean, original state
        getBackground().drawImage(originalBg, 0, 0); 
    
        // 2. Show all HUD text
        showText("Score: " + score, 70, 20);
        showText("Life: " + life, 150, 20);
        showText("Time: " + timeLeft, 230, 20);
        
        // 3. Draw the Key Item icon and text
        getBackground().drawImage(keyItemIcon, 310, 10);
        showText(keyItems + " / " + keysNeeded, 370, 25);
    }
    
    public void reduceTimer(int seconds) {
        timeLeft = Math.max(0, timeLeft - seconds); // Ensure timer doesn't go below 0
        updateHUD(); // Immediately show the change
    }
    
    private void spawnFish() {
        Actor ikanBaru; 
        
        int roll = Greenfoot.getRandomNumber(100); // Acak angka 0-99

        if (roll < 10) { // 10% kemungkinan (angka 0-9)
            ikanBaru = new EpicFish();
        } else if (roll < 35) { // 25% kemungkinan (angka 10-34)
            ikanBaru = new RareFish();
        } else { // 65% sisanya (angka 35-99)
            ikanBaru = new CommonFish();
        }
        
        // Kode ini sama persis seperti kodemu sebelumnya
        int side = Greenfoot.getRandomNumber(2); // 0 kiri, 1 kanan
        int y = Greenfoot.getRandomNumber(getHeight() - 200) + 200; // area air
        int x = (side == 0) ? -20 : getWidth() + 20;
    
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
