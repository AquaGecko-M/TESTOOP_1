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
     */
    public Menu()
    {    
        // Create a new world with 600x400 cells with a cell size of 1x1 pixels.
        // 1. Atur ukuran dunia agar SAMA dengan GameWorld
        super(648, 468, 1); 
        
        // --- KODE UNTUK MEMPERBAIKI LATAR BELAKANG ---
        
        // 2. Ambil gambar asli (GANTI "nama_background_menu.png" DENGAN NAMA FILE ANDA)
        GreenfootImage bg = new GreenfootImage("menu.jpg"); 
        
        // 3. Paksa gambar untuk pas dengan ukuran dunia (648x468)
        bg.scale(648, 468);
        
        // 4. Atur gambar yang sudah dikecilkan
        setBackground(bg);
        
        // --- AKHIR DARI PERUBAHAN ---

        // Panggil method untuk menambahkan tombol
        ButtonMenu();
    }
    
    private void ButtonMenu(){
    BtnTutor Tutorial = new BtnTutor();
    BtnStart Start = new BtnStart();
    BtnExit Exit = new BtnExit();
    
    addObject(Tutorial, 103, 350); // (actor, x, y)
    addObject(Start,    305, 350); // (actor, x, y)
    addObject(Exit,     508, 350); // (actor, x, y)
    }
}
