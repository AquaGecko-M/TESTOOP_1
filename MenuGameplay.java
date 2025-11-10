import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

public class MenuGameplay extends BtnAnima {
    public MenuGameplay() {
        GreenfootImage image = new GreenfootImage("MenuGameplay.png");
        if (image != null) {
            image.scale(100, 100);
            setImage(image);
        }
    }

    public void act() {
        super.act();
        if (Greenfoot.mouseClicked(this)) {
            World world = getWorld();
            if (world instanceof GameWorld) {
                ((GameWorld) world).openPauseMenu();

            }
        }
    }
}
