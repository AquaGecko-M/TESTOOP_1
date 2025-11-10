import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class Menu here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Menu extends World
{

    /**
     * Constructor for objects of class Menu.
     * 
     */private GreenfootSound backgroundMusic;
    public Menu()
    {    
        // Create a new world with 600x400 cells with a cell size of 1x1 pixels.
        // 1. Atur ukuran dunia agar SAMA dengan GameWorld
        super(960, 540, 1); 

        // 1. Load saved settings and apply them

        // 2. Ambil gambar asli (GANTI "nama_background_menu.png" DENGAN NAMA FILE ANDA)
        GreenfootImage bg = new GreenfootImage("menu_ui_1.jpg"); 

        // 3. Paksa gambar untuk pas dengan ukuran dunia (648x468)
        bg.scale(960, 540);

        // 4. Atur gambar yang sudah dikecilkan
        setBackground(bg);
        addObject(new GameTitle(), getWidth() / 2, 200);
        // --- AKHIR DARI PERUBAHAN ---
        // 2. Atur volume (opsional, 50 adalah setengah volume)
        // 3. Putar musiknya secara berulang (loop)

        backgroundMusic = new GreenfootSound("Menu_Awal.mp3");
        backgroundMusic.setVolume(10); // <-- Volume di sini 50
        backgroundMusic.playLoop();
        // Panggil method untuk menambahkan tombol
        ButtonMenu();
        prepare();
    }

    public void stopMusic() {
        if (backgroundMusic != null && backgroundMusic.isPlaying()) {
            backgroundMusic.stop();
        }
    }

    private void ButtonMenu(){
        BtnTutor Tutorial = new BtnTutor();
        BtnStart Start = new BtnStart();
        BtnExit Exit = new BtnExit();

        addObject(Tutorial, 250, 445); // (actor, x, y)
        addObject(Start,    480, 440); // (actor, x, y)
        addObject(Exit,     710, 450); // (actor, x, y)
    }

    /**
     * Prepare the world for the start of the program.
     * That is: create the initial objects and add them to the world.
     */
    private void prepare()
    {
        
    }
}
