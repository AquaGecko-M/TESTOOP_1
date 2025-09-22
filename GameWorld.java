import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

public class GameWorld extends World
{
    public GameWorld()
    {    
        // Create a new world with 600x400 cells with a cell size of 1x1 pixels.
        super(648, 468, 1);
        setBackground("ocean_background.png");

        // Add the player's boat
        Boat playerBoat = new Boat();
        addObject(playerBoat, getWidth() / 2, 50);

        // Add some fish
        Fish fish1 = new Fish();
        addObject(fish1, 100, 300);

        Fish fish2 = new Fish();
        addObject(fish2, 400, 320);
    }
    public void act()
    {
    if (Greenfoot.getRandomNumber(100) < 1) { // Roughly 1% chance each act cycle
        Fish newFish = new Fish();
        addObject(newFish, 0, Greenfoot.getRandomNumber(getHeight()));
        }
    }
}

