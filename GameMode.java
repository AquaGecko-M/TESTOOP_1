import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class GameMode here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class GameMode extends World
{

    /**
     * Constructor for objects of class GameMode.
     * 
     */
    public GameMode()
    {    
        // Create a new world with 600x400 cells with a cell size of 1x1 pixels.
        // 1. Atur ukuran dunia agar SAMA dengan GameWorld
        super(960, 540, 1); 

        // --- KODE UNTUK MEMPERBAIKI LATAR BELAKANG ---

        // 2. Ambil gambar asli (GANTI "nama_background_menu.png" DENGAN NAMA FILE ANDA)
        GreenfootImage bg = new GreenfootImage("background_mode.png"); 

        // 3. Paksa gambar untuk pas dengan ukuran dunia (648x468)
        bg.scale(960, 540);

        // 4. Atur gambar yang sudah dikecilkan
        setBackground(bg);

        // --- AKHIR DARI PERUBAHAN ---

        // Panggil method untuk menambahkan tombol
        //ButtonMenu(); 
        prepare();
    }
    
    /**
     * Prepare the world for the start of the program.
     * That is: create the initial objects and add them to the world.
     */
    private void prepare()
    {
        BtnBack btnBack = new BtnBack();
        addObject(btnBack,899,45);

        BtnEasy btnEasy = new BtnEasy();
        addObject(btnEasy,460,235);
        BtnMedium btnMedium = new BtnMedium();
        addObject(btnMedium,460,335);
        BtnHard btnHard = new BtnHard();
        addObject(btnHard,460,440);
    }
}
