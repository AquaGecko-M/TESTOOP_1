import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

public class GameWorld extends World
{
    public GameWorld()
    {    
        // Create a new world with 600x400 cells with a cell size of 1x1 pixels.
        super(1280, 720, 1);

        setBackground("background_sunny.png");

        // Add the player's boat
        Boat playerBoat = new Boat();
        addObject(playerBoat, 300, 10);

        // Add some fish
        Fish fish1 = new Fish();
        addObject(fish1, 100, 100);

        Fish fish2 = new Fish();
        addObject(fish2, 400, 320);
    }
    
    public void limitFish()
    {
            if (Greenfoot.getRandomNumber(100) < 1) { 
        
        int minY = 150; // y limit the fish can spawn
        int maxY = 450; // 
        
        int spawnRangeY = maxY - minY;
        
        int randomY = Greenfoot.getRandomNumber(spawnRangeY) + minY;
        
        Fish newFish = new Fish();
        addObject(newFish, 0, randomY); // Gunakan posisi Y yang baru
    }
    }

    public void act()
    {
         limitFish();
    }
}

