import greenfoot.*;

public class Hook extends Actor {
    private final Boat owner;
    private int downSpeed = 3;
    private int upSpeed   = 3;

    // batas vertikal (atur sesuai layout air-mu)
    private int minY;  // dekat boat
    private int maxY;  // kedalaman maksimum

    public Hook(Boat owner) {
        this.owner = owner;
        // setImage("hook.png"); // ganti bila perlu
    }

    protected void addedToWorld(World w) {
        // setel batas saat hook ditambahkan
        minY = owner.getY() + 40;
        maxY = w.getHeight() - 40;
    }

    public void act() {
        followBoatX();
        handleVertical();
        clampVertical();
        
        Fish caught = (Fish) getOneIntersectingObject(Fish.class);
        if (caught != null) {
        ((GameWorld) getWorld()).addScore(caught.getValue());
        getWorld().removeObject(caught);
    }
    }

    private void followBoatX() {
        setLocation(owner.getX(), getY()); // selalu sejajar X dengan boat
    }

    private void handleVertical() {
        // Tombol: up/down atau w/s
        if (Greenfoot.isKeyDown("down") || Greenfoot.isKeyDown("s")) {
            setLocation(getX(), getY() + downSpeed);
        } else if (Greenfoot.isKeyDown("up") || Greenfoot.isKeyDown("w")) {
            setLocation(getX(), getY() - upSpeed);
        } else {
            // idle: perlahan naik (rasa kail ditarik balik)
            if (getY() > minY) setLocation(getX(), getY() - 1);
        }
    }

    private void clampVertical() {
        int y = Math.max(minY, Math.min(getY(), maxY));
        setLocation(getX(), y);
    }
}
