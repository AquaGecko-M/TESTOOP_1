import greenfoot.*;

public class Fish extends Actor {
    private int dir;                 
    private int speed;
    private int bob = 0;
    private int value;

    public Fish(boolean rare) {
        if (rare) {
            setImage("fish2.png");
            value = 5;
            speed = Greenfoot.getRandomNumber(2) + 3; 
        } else {
            setImage("fish3.png");
            value = 2;
            speed = Greenfoot.getRandomNumber(2) + 2; 
        }
    }

    public int getValue() { return value; }

    @Override
    protected void addedToWorld(World w) {
        if (getX() < w.getWidth() / 2) {
            dir = 1;  
        } else {
            dir = -1; 
            getImage().mirrorHorizontally(); 
        }
    }

    @Override
    public void act() {
        move();
        bobbing();
        checkDespawn();
    }

    private void move() {
        setLocation(getX() + dir * speed, getY());
    }

    private void bobbing() {
        bob = (bob + 1) % 40;
        int offset = (bob < 20) ? 1 : -1;
        setLocation(getX(), getY() + offset);
    }

    private void checkDespawn() {
        World w = getWorld();
        if (w == null) return;

        int rightEdge = w.getWidth() - 1;

        if ((dir < 0 && getX() <= 0) ||     
        (dir > 0 && getX() >= rightEdge)) { 
        w.removeObject(this);
        }
    }
}
