import greenfoot.*;

/**
 * Write a description of class Menu here.
 * * @author (your name) 
 * @version (a version number or a date)
 */
public class Menu extends World
{

    /**
     * Constructor for objects of class Menu.
     * *
     */
    private GreenfootSound backgroundMusic;
    private GreenfootImage[] bgFrames = new GreenfootImage[4];
    private int currentFrame = 0;
    private SimpleTimer animTimer = new SimpleTimer();
    private int animSpeedMs = 500;
    public Menu()
    {    

        // Create a new world with 600x400 cells with a cell size of 1x1 pixels.
        // 1. Atur ukuran dunia agar SAMA dengan GameWorld
        super(960, 540, 1); 
        bgFrames[0] = new GreenfootImage("MenuF1.png");
        bgFrames[1] = new GreenfootImage("MenuF2.png");
        bgFrames[2] = new GreenfootImage("MenuF3.png");
        bgFrames[3] = new GreenfootImage("MenuF4.png");

        // 2. Ubah ukuran semua frame agar pas
        for (int i = 0; i < bgFrames.length; i++) {
            bgFrames[i].scale(960, 540);
        }

        // 3. Atur frame pertama sebagai background awal
        setBackground(bgFrames[currentFrame]);

        // 4. Mulai timer animasi
        animTimer.mark();
        // --- BATAS UBAHAN -
        // --- AKHIR DARI PERUBAHAN ---
        // 2. Atur volume (opsional, 50 adalah setengah volume)
        // 3. Putar musiknya secara berulang (loop)

        // --- THIS IS THE FIX ---
        // You were missing the line to add the GameTitle
        addObject(new GameTitle(), getWidth() / 2, 200); // (Adjust the 200 Y-position)
        // --- END FIX ---
        
        // (This line is from your project, it should be SettingsManager)
 

        SoundManager.play("Menu_Awal.mp3", 20);
        
        // Panggil method untuk menambahkan tombol
        ButtonMenu();
        prepare();
    }

    private void ButtonMenu(){
        BtnTutor Tutorial = new BtnTutor();
        BtnStart Start = new BtnStart();
        BtnExit Exit = new BtnExit();

        addObject(Tutorial, 250, 445); // (actor, x, y)
        addObject(Start,     480, 440); // (actor, x, y)
        addObject(Exit,      710, 450); // (actor, x, y)
    }

    public void act()
    {
        // Cek apakah sudah waktunya ganti frame
        if (animTimer.hasElapsed(animSpeedMs))
        {
            // Pindah ke frame berikutnya
            currentFrame = (currentFrame + 1) % 4; // % 4 akan loop (0, 1, 2, 3, 0, ...)

            // Atur background ke frame yang baru
            setBackground(bgFrames[currentFrame]);

            // Reset timer
            animTimer.mark();
        }
    }

    /**
     * Prepare the world for the start of the program.
     * That is: create the initial objects and add them to the world.
     */
    private void prepare()
    {

    }
}