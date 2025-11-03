import greenfoot.*;

public class EpicFish extends Actor {
    private int dir;
    private int speed;
    private int bob = 0;
    private int value;
    private int coinReward;

    public EpicFish() {
        setImage("EpicFish.png");
        value = 10;
        speed = Greenfoot.getRandomNumber(2) + 4; // 4–5);
    }
    
    public void setSpeed(int s) {
        speed = s;
    }

    public void setFishSize(int width, int height) {
        GreenfootImage img = new GreenfootImage("EpicFish.png");
        img.scale(width, height);
        setImage(img);
    }

    public void setValue(int score) {
        this.value = score;
    }
    
    public void setCoinReward(int amount) {
        this.coinReward = amount;
    }
    // ---

    // --- Method untuk mengambil SKOR dan KOIN ---
    public int getValue() {
        return value;
    }
    
    public int getCoinReward() {
        return coinReward;
    }

    protected void addedToWorld(World w) {
        int worldWidth = w.getWidth();

        if (getX() <= 50) {
            dir = 1;
        } else if (getX() >= worldWidth - 50) {
            dir = -1;
            getImage().mirrorHorizontally();
        } else {
            dir = (Greenfoot.getRandomNumber(2) == 0) ? 1 : -1;
        }
    }

    public void act() {
        moveFish();
        checkBoundary();
    }

    private void moveFish() {
        setLocation(getX() + dir * speed, getY());
        bob = (bob + 1) % 40;
        int offset = (bob < 20) ? 1 : -1;
        setLocation(getX(), getY() + offset);
    }

    private void checkBoundary() {
        World w = getWorld();
        if (w == null) return;

        int worldWidth = w.getWidth();
        int half = getImage().getWidth() / 2;

        if (dir < 0 && getX() - half <= 0) {
            w.removeObject(this);
        } else if (dir > 0 && getX() + half >= worldWidth) {
            w.removeObject(this);
        }
    }
}