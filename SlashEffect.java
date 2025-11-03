import greenfoot.*;

public class SlashEffect extends Actor {
    private int life = 12;          // durasi frame
    private int alphaStep = 18;     // kecepatan fade (0..255)

    public SlashEffect(int dir, int scaleW, int scaleH) {
        GreenfootImage img = new GreenfootImage("Slash.png");
        if (scaleW > 0 && scaleH > 0) img.scale(scaleW, scaleH);
        if (dir < 0) img.mirrorHorizontally();   // dir: -1 kiri, 1 kanan
        setImage(img);
    }

    public void act() {
        life--;
        // fade out
        GreenfootImage img = getImage();
        img.setTransparency(Math.max(0, img.getTransparency() - alphaStep));
        setImage(img);

        if (life <= 0) getWorld().removeObject(this);
    }
}
