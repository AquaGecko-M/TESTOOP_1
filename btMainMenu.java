import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

public class btMainMenu extends Actor {
    public btMainMenu() {
        GreenfootImage image = getImage();
        if (image != null) {
            image.scale(200, 80);
            setImage(image);
        }
    }

    public void act() {
        if (Greenfoot.mouseClicked(this)) {
            Greenfoot.setWorld(new Menu());
        }
    }
}
