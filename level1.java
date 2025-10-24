import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class level1 here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class level1 extends World
{


    public level1()
    {    
        // Create a new world with 600x400 cells with a cell size of 1x1 pixels.
        super(648, 468, 1);
        // 2. Ambil gambar asli (GANTI "nama_background_menu.png" DENGAN NAMA FILE ANDA)
        GreenfootImage bg = new GreenfootImage("24.jpg"); 
        
        // 3. Paksa gambar untuk pas dengan ukuran dunia (648x468)
        bg.scale(648, 468);
        
        // 4. Atur gambar yang sudah dikecilkan
        setBackground(bg);

        // Add the player's boat
        Boat playerBoat = new Boat();
        addObject(playerBoat, 300, 120);

        // Add some fish
        Fish fish1 = new Fish();
        addObject(fish1, 100, 300);

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
