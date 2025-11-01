import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)



/**

 * Write a description of class menuCompletion here.

 * 

 * @author (your name) 

 * @version (a version number or a date)

 */

public class menuCompletion extends World

{


    /**

     * Constructor for objects of class menuCompletion.

     * 

     */

    public menuCompletion()

    {    

        // Create a new world with 600x400 cells with a cell size of 1x1 pixels.

        // 1. Atur ukuran dunia agar SAMA dengan GameWorld

        super(960, 540, 1); 

        // --- KODE UNTUK MEMPERBAIKI LATAR BELAKANG ---

        // 2. Ambil gambar asli (GANTI "nama_background_menu.png" DENGAN NAMA FILE ANDA)

        GreenfootImage bg = new GreenfootImage("menuCompletion.png"); 

        // 3. Paksa gambar untuk pas dengan ukuran dunia (648x468)
        bg.scale(960, 540);

        // 4. Atur gambar yang sudah dikecilkan

        setBackground(bg);

        prepare();
    }

    /**
     * Prepare the world for the start of the program.
     * That is: create the initial objects and add them to the world.
     */
    private void prepare()
    {
        btnMainmenucomplete btnMainmenucomplete = new btnMainmenucomplete();
        addObject(btnMainmenucomplete,146,463);
        btnMainmenucomplete.setLocation(170,482);
        btnMainmenucomplete.setLocation(147,479);
        btnNextStage btnNextStage = new btnNextStage();
        addObject(btnNextStage,746,436);
        btnNextStage.setLocation(818,479);
        btnNextStage.setLocation(819,488);
        btnNextStage.setLocation(806,464);
        btnNextStage.setLocation(805,476);
        btnNextStage.setLocation(812,485);
    }
}