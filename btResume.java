import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

public class btResume extends btnAnimation {
    public btResume() {
        GreenfootImage image = new GreenfootImage("btResume.png");
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
            World world = getWorld();
            if (world instanceof bgMenu) {
                ((bgMenu) world).resumeGame();
            }
        }
    }
}
