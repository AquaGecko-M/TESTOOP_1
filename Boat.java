import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

public class Boat extends Actor
{
    private double speed = 2.0;   // kecepatan normal
    private double boost = 5.0;   // kecepatan saat boost (space)
    private double x = 300;       // simpan posisi dalam double (biar bisa pakai pecahan)
    private double y = 120;
    private Hook hook; // ✅ declare hook here (not inside act)

    public void act()
    {
        movement_boat();
        mapWidth();
    }
    
    @Override
    protected void addedToWorld(World world) {
        // create the hook when the boat is added to the world
        hook = new Hook();
        world.addObject(hook, getX(), getY() + 50); // place hook below boat
    }

    public void movement_boat()
    {
        double currentSpeed = speed;

        // kalau tekan space, pakai kecepatan boost
        if (Greenfoot.isKeyDown("space")) {
            currentSpeed = boost;
        }
        else
        {
            currentSpeed = speed;
        }

        // Tombol Panah kiri atau A
        if (Greenfoot.isKeyDown("left") || Greenfoot.isKeyDown("a")) {
            x -= currentSpeed;
        }

        // Tombol Panah kanan atau D
        if (Greenfoot.isKeyDown("right") || Greenfoot.isKeyDown("d")) {
            x += currentSpeed;
        }
        if (hook != null) {
            hook.setLocation(getX(), hook.getY());
        }
    }
    
    public void mapWidth()
    {
        int worldWidth = getWorld().getWidth();
        int halfWidth  = getImage().getWidth() / 2;

        if (x < halfWidth) {
            x = halfWidth;
        }
        if (x > worldWidth - halfWidth) {
            x = worldWidth - halfWidth;
        }

        setLocation((int)x, (int)y);
    }
}

