import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class btnMainMenuOver here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class btnMainMenuOver extends Actor {
    private static final int TARGET_WIDTH = 240;
    private boolean mouseWasDown = true; // Click-fix
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
        // ... (click-fix logic) ...
        if (mouseWasDown) {
            if (Greenfoot.mousePressed(null)) return;
            else mouseWasDown = false;
        }

        if (Greenfoot.mouseClicked(this)) {
            Greenfoot.setWorld(new Menu());
        }
    }
}
