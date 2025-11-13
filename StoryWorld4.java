import greenfoot.*;
import java.util.Arrays;

/**
 * StoryWorld4: Displays sequential narrative panels upon mouse clicks,
 * initiated by a level select button.
 */
public class StoryWorld4 extends World {
    private ClickIndicator indicator = new ClickIndicator();


    // The order must match the sequence P1, P2, P3, P4, P5, P6
    private final String[] PANEL_IMAGES = {
        "story4_panel1.png", // P1: Left Top
        "story4_panel2.png", // P2: Left Bottom
        "story4_panel4.png", // P3: Middle Top
        "story4_panel3.png", // P4: Middle Middle
        "story4_panel5.png", // P5: Middle Bottom
        "story4_panel6.png"  // P6: Large Right
    };

    // ✅ FINAL ADJUSTED Coordinates (X, Y)
    private final int[][] PANEL_LOCATIONS = {
        {190, 200},    // P1: Left Top
        {190, 500},    // P2: Left Bottom
        {556, 100},    // P3: Middle To
        {556, 285},    // P4: Middle Middle
        {556, 475},    // P5: Middle Bottom
        {950, 324}     // P6: Large Right
    };

    // ✅ FINAL ADJUSTED Scale settings (Width, Height)
    private final int[][] PANEL_SCALES = {
        {330, 380},    // P1: Left Top
        {330, 200},    // P2: Left Bottom
        {330, 180},    // P3: Middle Top
        {330, 180},    // P4: Middle Middle
        {330, 180},    // P5: Middle Bottom
        {380, 580}     // P6: Large Right
    };
    
    private int panelIndex = 0;
    private boolean panelsDone = false;
    private boolean waitingForTransition = false;
    private SimpleTimer clickCooldown = new SimpleTimer();
    private int nextStageNumber; 

    public StoryWorld4(String difficulty, int stageNum) { 
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
        SoundManager.play("WINNER.mp3", 80);
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
        
        // Load the image and scale it 
        GreenfootImage scaledImage = new GreenfootImage(imageName);
        scaledImage.scale(width, height); 
        
        // Create the panel actor
        StoryPanel panel = new StoryPanel(imageName);
        
        // Set the scaled image
        panel.setImage(scaledImage);

        addObject(panel, x, y);
        panelIndex++;
    }
    
    private void goToNextStage() {
        Greenfoot.setWorld(new Menu()); 
    }
}