import greenfoot.*;

/**
 * A projectile summoned by nyiRoroBoss.
 * It moves from the bottom of the screen to the top.
 */
public class BossProjectile extends Actor
{
    private int speed = 1;
    private int damage = 1;

    public BossProjectile()
    {
        setImage("BosSplash.png"); // You will need to create this image
        // (e.g., a spear, water spout, or energy ball)
        getImage().scale(100, 100);
        
        // Point the projectile upwards
        setRotation(-90); 
    }

    public void act()
    {
        // Move straight up
        move(speed);
        
        // Check for collision with the boat    
        Boat boat = (Boat) getOneIntersectingObject(Boat.class);
        if (boat != null) {
            boat.takeDamage(damage);
            getWorld().removeObject(this); // Remove projectile on hit
            return; // Stop running code
        }
        if (getY() < 5) {
            getWorld().removeObject(this);
            return; // Stop running code
        }
    }
}