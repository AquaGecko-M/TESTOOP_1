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
        addObject(playerBoat, 300, 10);

        // Add some fish
        Fish fish1 = new Fish();
        addObject(fish1, 100, 300);

        Fish fish2 = new Fish();
        addObject(fish2, 400, 320);
    }

    public void act()
    {
 // Peluang 1% untuk memunculkan ikan baru
    if (Greenfoot.getRandomNumber(100) < 1) { 
        
        int minY = 150; // batas ikannya spawn
        int maxY = 450; // Posisi Y paling bawah
        
        // Hitung rentang ketinggian tempat ikan bisa muncul
        int spawnRangeY = maxY - minY;
        
        // Dapatkan posisi Y acak di dalam rentang tersebut
        int randomY = Greenfoot.getRandomNumber(spawnRangeY) + minY;
        
        Fish newFish = new Fish();
        addObject(newFish, 0, randomY); // Gunakan posisi Y yang baru
    }
    }
}

