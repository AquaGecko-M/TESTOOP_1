import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Menu utama dengan musik latar.
 */
public class Menu extends World
{
    // Gunakan static agar musik dapat dihentikan dari class lain (misalnya BtnStart)
    private static GreenfootSound menuMusic;

    /**
     * Constructor untuk objek Menu.
     */
    public Menu()
    {    
        // Buat world ukuran 960x540
        super(960, 540, 1); 

        // --- LATAR BELAKANG MENU ---
        GreenfootImage bg = new GreenfootImage("menu_ui_1.jpg"); 
        bg.scale(960, 540);
        setBackground(bg);
        // --- AKHIR LATAR BELAKANG ---

        // --- MUSIK MENU ---
        // Pastikan file "menu_music.mp3" ada di folder "sounds"
        try {
            if (menuMusic == null) {
                menuMusic = new GreenfootSound("glory.mp3");
            }
            menuMusic.playLoop();
        } catch (Exception e) {
            System.out.println("Gagal memutar musik menu: " + e.getMessage());
        }
        // --- AKHIR MUSIK MENU ---

        // Tambahkan tombol-tombol menu
        ButtonMenu();
        prepare();
    }

    private void ButtonMenu(){
        BtnTutor Tutorial = new BtnTutor();
        BtnStart Start = new BtnStart();
        BtnExit Exit = new BtnExit();

        addObject(Tutorial, 220, 445);
        addObject(Start,    475, 440);
        addObject(Exit,     740, 450);
    }

    /**
     * Berhentikan musik menu (dipanggil oleh BtnStart sebelum ganti world)
     */
    public static void stopMenuMusic() {
        if (menuMusic != null) {
            menuMusic.stop();
        }
    }

    /**
     * Jika simulasi dihentikan (Stop di Greenfoot), hentikan musik juga.
     */
    public void stopped() {
        stopMenuMusic();
    }

    private void prepare()
    {
    }
}
