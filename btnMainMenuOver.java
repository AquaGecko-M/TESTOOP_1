import greenfoot.*;  


public class btnMainMenuOver extends Actor {
    private static final int TARGET_WIDTH = 260;
    private boolean mouseWasDown = true; 
    public btnMainMenuOver() {
        applyImage();
    }

    @Override
    protected void addedToWorld(World world) {
        applyImage();
    }

    private void applyImage() {
        GreenfootImage img = new GreenfootImage("btnMainmenucomplete.png");
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
            Greenfoot.setWorld(new Menu());
        }
    }
}
