import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

public class btOption extends Actor {
    private boolean messageShown;

    public btOption() {
        GreenfootImage image = getImage();
        if (image != null) {
            image.scale(200, 80);
            setImage(image);
        }
    }

    public void act() {
        if (Greenfoot.mouseClicked(this)) {
            World w = getWorld();
            if (w != null && !messageShown) {
                w.showText("Option belum tersedia", w.getWidth() / 2, w.getHeight() / 2 + 180);
                messageShown = true;
            }
        }
    }
}
