import greenfoot.*; 

public class btOption extends btnAnimation {

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
        super.act();
        if (Greenfoot.mouseClicked(this)) {
            // Get the world we are currently in (e.g., bgMenu)
            World currentWorld = getWorld();
            
            // Open the OptionWorld and tell it where to return to
            Greenfoot.setWorld(new OptionWorld(currentWorld));
        }
    }
}
