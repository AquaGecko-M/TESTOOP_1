import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class tutorialmouse here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class tutorialmouse extends World
{

    /**
     * Constructor for objects of class tutorialmouse.
     * 
     */
    public tutorialmouse()
    {    
         // Create a new world with 600x400 cells with a cell size of 1x1 pixels.
        // 1. Atur ukuran dunia agar SAMA dengan GameWorld
        super(960, 540, 1); 
        
        // --- KODE UNTUK MEMPERBAIKI LATAR BELAKANG ---
        
        // 2. Ambil gambar asli (GANTI "nama_background_menu.png" DENGAN NAMA FILE ANDA)
        GreenfootImage bg = new GreenfootImage("5.jpg"); 
        
        // 3. Paksa gambar untuk pas dengan ukuran dunia (648x468)
        bg.scale(960, 540);
        
        // 4. Atur gambar yang sudah dikecilkan
        setBackground(bg);
        
        Buttonnav();
    
    }
    
        private void Buttonnav(){
    nextbut2 next2 = new nextbut2();
    prevbut2 prev2 = new prevbut2();
    
    addObject(prev2, 103, 467); // (actor, x, y)
    addObject(next2,    864, 467); // (actor, x, y)
    }
    
}
