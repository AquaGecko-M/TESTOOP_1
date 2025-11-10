import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

public class btMainMenu extends btnAnimation {
    public btMainMenu() {
        GreenfootImage image = new GreenfootImage("btMainmenu.png");
        if (image != null) {
            int newWidth = 150; 
            
            double aspectRatio = (double) image.getHeight() / image.getWidth();
            int newHeight = (int) (newWidth * aspectRatio);
            
            image.scale(newWidth, newHeight); 
            setImage(image);
        }
    }

    public void act() {
        super.act();
        if (Greenfoot.mouseClicked(this)) {
            Greenfoot.setWorld(new Menu());
        }
    }
}
