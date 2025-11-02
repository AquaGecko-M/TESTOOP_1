import greenfoot.*;

public class itemResume extends Actor {
    private static final int TARGET_WIDTH = 200; // ubah sesuai selera

    public itemResume() {
        setSizedImage();
    }

    @Override
    protected void addedToWorld(World world) {
        setSizedImage();
    }

    private void setSizedImage() {
        GreenfootImage img = new GreenfootImage("btResume.png");
        int targetHeight = img.getHeight() * TARGET_WIDTH / img.getWidth();
        img.scale(TARGET_WIDTH, targetHeight);
        setImage(img);
    }

    public void act() {
        if (Greenfoot.mouseClicked(this)) {
            World w = getWorld();
            if (w instanceof bgShop) {
                ((bgShop) w).resumeGame();
            }
        }
    }
}
