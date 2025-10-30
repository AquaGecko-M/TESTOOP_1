import greenfoot.*;

public class GameWorld extends World {
    private int score = 0;
    private int life = 3;
    private int timeLeft = 60; // detik per level
    private final SimpleTimer secondTimer = new SimpleTimer();

    private Boat boat;
    private Hook hook;

    private final SimpleTimer fishSpawnTimer = new SimpleTimer();
    private MenuGameplay menuButton;

    public GameWorld() {
        super(960, 540, 1);
        setPaintOrder(Hook.class, Boat.class);
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

        hook = new Hook(boat);
        addObject(hook, boatX, boatY + 180);

        menuButton = new MenuGameplay();
        addObject(menuButton, getWidth() - 60, 50);

        startTimer(300);
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
        showText("Score: " + score, 70, 20);
        showText("Life: " + life, 150, 20);
        showText("Time: " + timeLeft, 230, 20);
    }

    private void spawnFish() {
        boolean rare = Greenfoot.getRandomNumber(100) < 15;
        Fish fish = new Fish(rare);

        int side = Greenfoot.getRandomNumber(2);
        int y = Greenfoot.getRandomNumber(getHeight() - 200) + 200;
        int x = (side == 0) ? -20 : getWidth() + 20;

        addObject(fish, x, y);
    }
}
