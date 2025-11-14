import greenfoot.*;
public class StoryWorld3 extends World {
    private ClickIndicator indicator = new ClickIndicator();

    private final String[] PanelImages = {
        "story3_panel1.png", 
        "story3_panel2.png", 
        "story3_panel3.png", 
        "story3_panel4.png", 
    };

    private final int[][] PanelLocations = {
        {150, 290},    
        {435, 350},    
        {720, 290},    
        {1005, 350}     
    };

    private final int[][] PanelScales = {
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
        
        GreenfootImage bg = new GreenfootImage(getWidth(), getHeight());
        bg.setColor(Color.BLACK); 
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
            
            if (panelIndex < PanelImages.length) {
                showNextPanel();
            } else if (!panelsDone) {
                removeObject(indicator);
                goToNextStage();
                panelsDone = true;
            }
        }
    }
    
    private void showNextPanel() {
        String imageName = PanelImages[panelIndex];
        int x = PanelLocations[panelIndex][0];
        int y = PanelLocations[panelIndex][1];
        int width = PanelScales[panelIndex][0];
        int height = PanelScales[panelIndex][1];
        
        GreenfootImage scaledImage = new GreenfootImage(imageName);
        scaledImage.scale(width, height); 
        
        StoryPanel panel = new StoryPanel(imageName);
        
        panel.setImage(scaledImage);

        addObject(panel, x, y);
        panelIndex++;
    }
    
    private void goToNextStage() {
        Greenfoot.setWorld(new GameWorld(this.nextStageNumber)); 
    }
}