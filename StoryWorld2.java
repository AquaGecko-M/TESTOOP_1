import greenfoot.*;
public class StoryWorld2 extends World {
    private ClickIndicator indicator = new ClickIndicator();

    private final String[] PanelImages = {
        "story2_panel1.png", 
        "story2_panel2.png", 
        "story2_panel4.png", 
        "story2_panel3.png", 
        "story2_panel5.png"  
    };

    private final int[][] PanelLocations = {
        {310, 280},    
        {700, 160},    
        {980, 160},    
        {700, 405},    
        {980, 405}     
    };

    private final int[][] PanelScales = {
        {480, 500},    
        {250, 240},    
        {250, 240},    
        {250, 240},    
        {250, 240}     
    };
    
    private int panelIndex = 0;
    private boolean panelsDone = false;
    private boolean waitingForTransition = false;
    private SimpleTimer clickCooldown = new SimpleTimer();
    private int nextStageNumber; 

    public StoryWorld2(String difficulty, int stageNum) { 
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