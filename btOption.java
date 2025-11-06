import greenfoot.*;  

public class btOption extends Actor {
    private boolean messageShown;

    public btOption() {
        GreenfootImage image = new GreenfootImage("btOption.png");
        if (image != null) {
        int newWidth = 150; 
        
        double aspectRatio = (double) image.getHeight() / image.getWidth();
        int newHeight = (int) (newWidth * aspectRatio);
        
        image.scale(newWidth, newHeight); 
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
