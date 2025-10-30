import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

public class btResume extends Actor {
    public btResume() {
        GreenfootImage image = getImage();
        if (image != null) {
            image.scale(200, 80);
            setImage(image);
        }
    }

    public void act() {
        if (Greenfoot.mouseClicked(this)) {
            World world = getWorld();
            if (world instanceof bgMenu) {
                ((bgMenu) world).resumeGame();
            }
        }
    }
}
