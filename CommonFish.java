import greenfoot.*;

public class CommonFish extends Actor {
    private int dir;
    private int speed;
    private int bob = 0;
    private int value; // Ini untuk SKOR
    private int coinReward; // Ini untuk KOIN ($)
    private int halfWidth;

    public CommonFish() {
        setImage("CFish.png");
    }

    public void setSpeed(int s) {
        speed = s;
    }

    public void setFishSize(int width, int height) {
        GreenfootImage img = new GreenfootImage("CFish.png");
        img.scale(width, height);
        setImage(img);
        halfWidth = img.getWidth() / 2;
    }
    
    public void setValue(int score) {
        this.value = score;
    }
    
    public void setCoinReward(int amount) {
        this.coinReward = amount;
    }
    
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
            getImage().mirrorHorizontally();
        } else if (getX() >= worldWidth - 50) {
            dir = -1;
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

        // Kalau keluar kiri → muncul dari kanan
        if (dir < 0 && getX() + halfWidth < 0) {
            setLocation(worldWidth + halfWidth, getY());
        } 
        // Kalau keluar kanan → muncul dari kiri
        else if (dir > 0 && getX() - halfWidth > worldWidth) {
            setLocation(-halfWidth, getY());
        }
    }
    
}