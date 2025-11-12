import greenfoot.*;
import java.util.Arrays;

/**
 * StoryWorld2: Displays sequential narrative panels upon mouse clicks,
 * initiated by a level select button.
 */
public class StoryWorld3 extends World {
    private ClickIndicator indicator = new ClickIndicator();

    private final String[] PANEL_IMAGES = {
        "story3_panel1.png", // Large Left Panel
        "story3_panel2.png", // Top-Left of Right Group
        "story3_panel3.png", // Top-Right of Right Group
        "story3_panel4.png", // Bottom-Left of Right Group
    };

    // ✅ ADJUSTED Coordinates (X, Y) for the 1-Large, 4-Small layout
    private final int[][] PANEL_LOCATIONS = {
        {150, 290},   // Panel 1 (Kiri Atas)
        {435, 350},   // Panel 3 (Kiri Bawah)
        {720, 290},   // Panel 2 (Kanan Atas)
        {1005, 350}    // Panel 4 (Kanan Bawah)
    };

    // ✅ ADJUSTED Scale settings (Width, Height) for each panel
    private final int[][] PANEL_SCALES = {
        {260, 450},  
        {260, 450},   
        {260, 450},    
        {260, 450},  
    };
    
    private int panelIndex = 0;
    private boolean panelsDone = false;
    private boolean waitingForTransition = false;
    private SimpleTimer clickCooldown = new SimpleTimer();
    private int nextStageNumber; 

    public StoryWorld3(String difficulty, int stageNum) { 
        super(1152, 648, 1, false); 
        this.nextStageNumber = stageNum;
        
        // Background
        GreenfootImage bg = new GreenfootImage(getWidth(), getHeight());
        bg.setColor(Color.BLACK); // Use Greenfoot.Color for safety
        bg.fill();
        setBackground(bg);
        
        GameSettings.difficulty = difficulty; 
        addObject(indicator, getWidth() / 2, 600);
        clickCooldown.mark();
    }

    public void act() {
        if (waitingForTransition) return;

        if (Greenfoot.mouseClicked(null) && clickCooldown.millisElapsed() > 300) {
            clickCooldown.mark();
            
            if (panelIndex < PANEL_IMAGES.length) {
                showNextPanel();
            } else if (!panelsDone) {
                removeObject(indicator);
                goToNextStage();
                panelsDone = true;
            }
        }
    }
    
    private void showNextPanel() {
        String imageName = PANEL_IMAGES[panelIndex];
        int x = PANEL_LOCATIONS[panelIndex][0];
        int y = PANEL_LOCATIONS[panelIndex][1];
        int width = PANEL_SCALES[panelIndex][0];
        int height = PANEL_SCALES[panelIndex][1];
        
        // Load the image and scale it BEFORE creating the StoryPanel actor
        GreenfootImage scaledImage = new GreenfootImage(imageName);
        scaledImage.scale(width, height); 
        
        // Pass the scaled image to a modified StoryPanel constructor, 
        // OR rely on the default StoryPanel constructor and update the image
        StoryPanel panel = new StoryPanel(imageName);
        
        // --- RELYING ON StoryPanel's default constructor: MUST use setImage() ---
        panel.setImage(scaledImage);
        // ------------------------------------------------------------------------

        addObject(panel, x, y);
        panelIndex++;
    }
    
    private void goToNextStage() {
        Greenfoot.setWorld(new GameWorld(this.nextStageNumber)); 
    }
}