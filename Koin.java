import greenfoot.*;  


public class Koin extends Actor {
    private static final int ICON_SIZE = 42;

    public Koin() {
        GreenfootImage img = new GreenfootImage("KoinLogo.png");
        img.scale(ICON_SIZE, ICON_SIZE);
        setImage(img);
    }
}
