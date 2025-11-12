import greenfoot.*;
import java.util.Arrays;

/**
 * StoryWorld: Displays sequential narrative panels upon mouse clicks.
 * This is used for the story prologue after selecting difficulty.
 */
public class StoryWorld extends World {
    // --- Data Panel Story 1 ---
    // Ganti nama file ini dengan gambar panel cerita Anda
    private ClickIndicator indicator = new ClickIndicator();
    private final String[] PANEL_IMAGES = {
        "story1_panel1.png", 
        "story1_panel2.png", 
        "story1_panel3.png", 
        "story1_panel4.png"
    };

    // Koordinat (X, Y) untuk setiap panel di layar
    private final int[][] PANEL_LOCATIONS = {
        {150, 290},   // Panel 1 (Kiri Atas)
        {435, 350},   // Panel 3 (Kiri Bawah)
        {720, 290},   // Panel 2 (Kanan Atas)
        {1005, 350}    // Panel 4 (Kanan Bawah)
    };
    
    // --- State Management ---
    private int panelIndex = 0;
    private boolean panelsDone = false;
    private boolean waitingForTransition = false;
    private SimpleTimer clickCooldown = new SimpleTimer();

    public StoryWorld(String difficulty) {
        // Asumsi ukuran dunia sama dengan GameWorld Anda
        super(1152, 648, 1, false); 
        
        // Atur latar belakang menjadi hitam
        GreenfootImage bg = new GreenfootImage(getWidth(), getHeight());
        bg.setColor(Color.BLACK);
        bg.fill();
        setBackground(bg);
        addObject(indicator, getWidth() / 2, 600);
        // Simpan pengaturan kesulitan jika diperlukan untuk transisi
        GameSettings.difficulty = difficulty; 
        
        // Cooldown untuk menghindari klik ganda
        clickCooldown.mark();
    }

    public void act() {
        if (waitingForTransition) {
            // Tunggu aktor tombol menangani klik
            return;
        }

        if (Greenfoot.mouseClicked(null) && clickCooldown.millisElapsed() > 300) {
            clickCooldown.mark();
            
            if (panelIndex < PANEL_IMAGES.length) {
                // Tampilkan panel berikutnya
                showNextPanel();            
            } else if (!panelsDone) {
                // Semua panel telah ditampilkan, saatnya menampilkan tombol
                removeObject(indicator);
                goToNextStory();
                panelsDone = true;
            }
        }
    }
    
    private void showNextPanel() {
        String imageName = PANEL_IMAGES[panelIndex];
        int x = PANEL_LOCATIONS[panelIndex][0];
        int y = PANEL_LOCATIONS[panelIndex][1];
        
        // Buat dan tambahkan panel baru
        StoryPanel panel = new StoryPanel(imageName);
        addObject(panel, x, y);
        
        panelIndex++;
    }
    
    
    /**
     * Dipanggil oleh tombol Level Select setelah diklik.
     */
    public void goToNextStory() {
        Greenfoot.setWorld(new LevelSelectWorld()); 
    }
}