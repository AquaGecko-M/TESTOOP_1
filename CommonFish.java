import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class CommonFish here.
 * 
 * @author (Steven I)
 * @version (a version number or a date)
 */
public class CommonFish extends Actor
{
    private int dir;        // -1 = dari kanan ke kiri, 1 = kiri ke kanan
    private int speed;      // kecepatan horizontal
    private int bob = 0;    // buat efek naik-turun kecil
    private int value;      // poin saat tertangkap
    private int halfWidth = 0;

    public CommonFish() {
        setImage("CFish.png");
        value = 2;
        speed = Greenfoot.getRandomNumber(2) + 2;

        GreenfootImage image = getImage();
        image.scale(160, 180);
        setImage(image);
        this.halfWidth = image.getWidth() / 2;
    }

    public int getValue() {
        return value;
    }

    protected void addedToWorld(World w) {
        dir = (getX() < w.getWidth() / 2) ? 1 : -1;
        if (dir < 0) {
            getImage().mirrorHorizontally();
        }
    }

    public void act() {
        setLocation(getX() + dir * speed, getY());

        bob = (bob + 1) % 40;
        int offset = (bob < 20) ? 1 : -1;
        setLocation(getX(), getY() + offset);

        if (dir < 0) { 
            if (getX() < 0 - halfWidth) {
                getWorld().removeObject(this);
            }
        } else { 
            if (getX() > getWorld().getWidth() + halfWidth) {
                getWorld().removeObject(this);
            }
        }
    }
}
