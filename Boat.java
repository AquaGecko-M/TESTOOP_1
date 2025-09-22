import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

public class Boat extends Actor
{
    private Hook hook; // ✅ declare hook here (not inside act)

    @Override
    protected void addedToWorld(World world) {
        // create the hook when the boat is added to the world
        hook = new Hook();
        world.addObject(hook, getX(), getY() + 50); // place hook below boat
    }

    public void act()
    {
        // --- Boat movement ---
        if (Greenfoot.isKeyDown("left")) {
            setLocation(getX() - 3, getY());
        }
        if (Greenfoot.isKeyDown("right")) {
            setLocation(getX() + 3, getY());
        }

        // --- Make hook follow boat horizontally ---
        if (hook != null) {
            hook.setLocation(getX(), hook.getY());
        }
    }
}

