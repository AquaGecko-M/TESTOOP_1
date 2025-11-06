import greenfoot.*;  


public class btnTryAgain extends Actor {
    private static final int TARGET_WIDTH = 260;
    private int stageToRetry;
    private boolean mouseWasDown = true; 
    
    public btnTryAgain(int stageNum) {
        this.stageToRetry = stageNum;
        applyImage();
    }

    @Override
    protected void addedToWorld(World world) {
        applyImage();
    }

    private void applyImage() {
        GreenfootImage img = new GreenfootImage("TRY AGAIN.png");
        int targetHeight = img.getHeight() * TARGET_WIDTH / img.getWidth();
        img.scale(TARGET_WIDTH, targetHeight);
        setImage(img);
    }

    public void act() {
        if (mouseWasDown) {
            if (Greenfoot.mousePressed(null)) return;
            else mouseWasDown = false;
        }

        if (Greenfoot.mouseClicked(this)) {
            Greenfoot.setWorld(new GameWorld(stageToRetry));
        }
    }
}