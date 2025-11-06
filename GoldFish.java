import greenfoot.*;

/**
 * GoldFish bergerak bolak-balik tanpa pernah hilang saat menyentuh border.
 */
public class GoldFish extends Actor {
    private static final int EDGE_PADDING = 24;

    private final int speed = 4; // Dia lebih cepat dari ikan biasa
    private final int value = 100;
    private final int coinReward = 200;

    private GreenfootImage facingRightImage;
    private GreenfootImage facingLeftImage;
    private boolean facingLeft;

    private int direction = 1;
    private int bob = 0;

    public GoldFish() {
        GreenfootImage base = new GreenfootImage("goldFish.png");
        base.scale(80, 60);
        facingRightImage = base;
        facingLeftImage = new GreenfootImage(base);
        facingLeftImage.mirrorHorizontally();
        setImage(facingRightImage);
        facingLeft = false;
    }
    
    /**
     * Dipanggil oleh GameWorld untuk menentukan arah gerak awal.
     */
    public void setDirection(int dir) {
        direction = (dir < 0) ? -1 : 1;
        setFacing(direction < 0);
    }

    public void act() {
        World world = getWorld();
        if (world == null) return; // Safety bila world null
        moveHorizontally();
        bounceAtEdges(world);
    }

    private void moveHorizontally() {
        int nextX = getX() + (speed * direction);
        bob = (bob + 1) % 40; // Siklus 40 frame untuk efek bobbing ringan
        int offset = (bob < 20) ? 1 : -1;
        int nextY = getY() + offset;
        setLocation(nextX, nextY);
    }

    /**
     * Jika menyentuh border horizontal, balik arah tanpa naik ke permukaan.
     */
    private void bounceAtEdges(World world) {
        int halfW = getImage().getWidth() / 2;
        int leftBound = EDGE_PADDING + halfW;
        int rightBound = world.getWidth() - EDGE_PADDING - halfW;

        if (getX() <= leftBound && direction < 0) {
            setLocation(leftBound, getY());
            direction = 1;
            setFacing(false);
        } else if (getX() >= rightBound && direction > 0) {
            setLocation(rightBound, getY());
            direction = -1;
            setFacing(true);
        }
    }

    private void setFacing(boolean left) {
        if (left == facingLeft) {
            return;
        }
        facingLeft = left;
        setImage(left ? facingLeftImage : facingRightImage);
    }
    
    public int getValue() {
        return this.value;
    }
    
    public int getCoinReward() {
        return this.coinReward;
    }
}