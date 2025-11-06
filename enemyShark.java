import greenfoot.*;

public class enemyShark extends Actor implements Damageable {
    private int speed = 2;
    private int direction;
    private int health;
    private SimpleTimer hitCooldown = new SimpleTimer();
    private boolean canHit = true; // cooldown untuk MENYAKITI boat
    private int bob = 0;

    private final SimpleTimer hurtIFrame = new SimpleTimer();
    private int hurtCooldownMs = 150;

    public enemyShark(int initialHealth) {
        this.health = initialHealth;
    }

    public void setDirection(int dir) {
        this.direction = dir;
        GreenfootImage image;
        if (dir == 1) { // ke kanan = pakai Shark2
            image = new GreenfootImage("Shark2.png");
        } else {        // ke kiri = pakai Shark, lalu mirror
            image = new GreenfootImage("Shark.png");
            image.mirrorHorizontally();
        }
        image.scale(120, 60);
        setImage(image);
    }

    public void act() {
        if (getWorld() == null) return;
        move();
        checkHitBoat();
        checkBoundary();
        
    }

    @Override
    public void takeDamage(int amount) {
        // i-frame vs serangan supaya tidak “terbakar” multi hit dalam 1–2 frame
        if (!hurtIFrame.hasElapsed(hurtCooldownMs)) return;
        hurtIFrame.mark();

        health -= amount;
        flash();

        if (health <= 0) {
            addScoreToWorld(10);
            getWorld().removeObject(this);
        }
    }

    private void flash() {
        GreenfootImage img = getImage();
        int old = img.getTransparency();
        img.setTransparency(140);
        Greenfoot.delay(5);
        if (getWorld() != null) { // world bisa null kalau sudah dihapus
            img.setTransparency(old);
        }
    }

    private void addScoreToWorld(int score) {
        World world = getWorld();
        if (world instanceof GameWorld) ((GameWorld) world).addScore(score);
    }

    private void move() {
        int x = getX() + (speed * direction);
        bob = (bob + 1) % 250;
        int y = getY() + ((bob < 125) ? 1 : -1);
        setLocation(x, y);
    }

    private void checkHitBoat() {
        if (!canHit) {
            if (hitCooldown.hasElapsed(2000)) canHit = true;
            return;
        }
        
        // Check for a boat within a 50-pixel radius (a circle)
        // Adjust "50" to be smaller or larger.
        if (!getObjectsInRange(80, Boat.class).isEmpty()) {
            // We're touching the boat.
            // Get the boat object to damage it.
            Boat boat = (Boat) getObjectsInRange(80, Boat.class).get(0);
            boat.takeDamage(1);
            canHit = false;
            hitCooldown.mark();
        }
    }

    private void checkBoundary() {
        World w = getWorld();
        if (w == null) return;
        if (direction == -1 && getX() <= 50) {
            setDirection(1);
        } else if (direction == 1 && getX() >= w.getWidth() - 50) {
            setDirection(-1);
        }
    }
}