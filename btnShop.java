import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

public class btnShop extends btnAnimation
{
    public btnShop() {
        GreenfootImage image = getImage();
        if (image != null) {
            image = new GreenfootImage(image);
            image.scale(80, 65);
            setImage(image);
        }
    }

    public void act() {
        super.act();
        if (Greenfoot.mouseClicked(this)) {
            World w = getWorld();
            if (w instanceof GameWorld) {
                ((GameWorld) w).openShopMenu();
            }
        }
    }
}
