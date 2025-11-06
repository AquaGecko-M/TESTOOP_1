import greenfoot.*;  

public class btnShop extends Actor {
    public btnShop() {
        GreenfootImage image = getImage();
        if (image != null) {
            image = new GreenfootImage(image);
            image.scale(80, 80);
            setImage(image);
        }
    }

    public void act() {
        if (Greenfoot.mouseClicked(this)) {
            World w = getWorld();
            if (w instanceof GameWorld) {
                ((GameWorld) w).openShopMenu();
            }
        }
    }
}
