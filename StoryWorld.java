import greenfoot.*;
public class StoryWorld extends World {
    private ClickIndicator indicator = new ClickIndicator();
    private final String[] PanelImages = {
        "story1_panel1.png", 
        "story1_panel2.png", 
        "story1_panel3.png", 
        "story1_panel4.png"
    };

    // Koordinat (X, Y) untuk setiap panel di layar
    private final int[][] PanelLocations = {
        {150, 290},   // Panel 1 (Kiri Atas)
        {435, 350},   // Panel 3 (Kiri Bawah)
        {720, 290},   // Panel 2 (Kanan Atas)
        {1005, 350}    // Panel 4 (Kanan Bawah)
    };
    private int panelIndex = 0;
    private boolean panelsDone = false;
    private boolean waitingForTransition = false;
    private SimpleTimer clickCooldown = new SimpleTimer();

    public StoryWorld(String difficulty) {
        super(1152, 648, 1, false); 
        GreenfootImage bg = new GreenfootImage(getWidth(), getHeight());
        bg.setColor(Color.BLACK);
        bg.fill();
        setBackground(bg);
        addObject(indicator, getWidth() / 2, 600);
        GameSettings.difficulty = difficulty; 
        clickCooldown.mark();
    }

    public void act() {
        if (waitingForTransition) {
            return;
        }

        if (Greenfoot.mouseClicked(null) && clickCooldown.millisElapsed() > 300) {
            clickCooldown.mark();
            
            if (panelIndex < PanelImages.length) {
                showNextPanel();            
            } else if (!panelsDone) {
                removeObject(indicator);
                goToNextStory();
                panelsDone = true;
            }
        }
    }
    
    private void showNextPanel() {
        String imageName = PanelImages[panelIndex];
        int x = PanelLocations[panelIndex][0];
        int y = PanelLocations[panelIndex][1];
        StoryPanel panel = new StoryPanel(imageName);
        addObject(panel, x, y);        
        panelIndex++;
    }
    

    public void goToNextStory() {
        Greenfoot.setWorld(new LevelSelectWorld()); 
    }
}
