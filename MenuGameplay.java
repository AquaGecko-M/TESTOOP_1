import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

public class MenuGameplay extends Actor {
    public MenuGameplay() {
        GreenfootImage image = new GreenfootImage("MenuGameplay.png");
        if (image != null) {
            image.scale(80, 50);
            setImage(image);
        }
    }

    public void act() {
        if (Greenfoot.mouseClicked(this)) {
            World world = getWorld();
            if (world instanceof GameWorld) {
                ((GameWorld) world).openPauseMenu();

            }
        }
    }
}
