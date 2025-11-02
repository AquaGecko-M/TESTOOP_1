import greenfoot.*;

public class AttackRing extends Actor {
    private int life;        // berapa frame hidup
    private int radius;
    private int growPerFrame = 0; // 0 = fix; >0 = membesar tiap frame (opsional)

    public AttackRing(int radius, int lifeFrames) {
        this.radius = radius;
        this.life   = lifeFrames;
        redraw(radius);
    }

    private void redraw(int r) {
        int d = r * 2;
        GreenfootImage img = new GreenfootImage(d, d);

        // fill transparan + outline (ubah warna sesuka hati)
        img.setColor(new Color(255, 215, 0, 70));
        img.fillOval(0, 0, d-1, d-1);
        img.setColor(new Color(255, 215, 0, 160));
        img.drawOval(0, 0, d-1, d-1);

        setImage(img);
    }

    public void act() {
        life--;
        if (growPerFrame > 0) {
            radius += growPerFrame;
            redraw(radius);
        }
        if (life <= 0) getWorld().removeObject(this);
    }

    public int getRadius() { return radius; } // kalau mau dicek di luar
}
