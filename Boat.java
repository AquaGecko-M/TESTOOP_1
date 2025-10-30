import greenfoot.*;

public class Boat extends Actor {
    private int speed = 6;

    public Boat() {
        // ganti bila perlu: setImage("boat.png");
    }

    public void act() {
        handleMove();
        clampToWorld();
    }

    private void handleMove() {
        if (Greenfoot.isKeyDown("left") || Greenfoot.isKeyDown("a")) {
            setLocation(getX() - speed, getY());
        }
        if (Greenfoot.isKeyDown("right") || Greenfoot.isKeyDown("d")) {
            setLocation(getX() + speed, getY());
        }
    }

    private void clampToWorld() {
        World w = getWorld();
        int x = Math.max(30, Math.min(getX(), w.getWidth() - 30));
        setLocation(x, getY()); // Y tetap (kapal tidak naik/turun)
    }
}