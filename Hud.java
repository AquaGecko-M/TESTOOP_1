import greenfoot.*;

public class Hud extends Actor {
    private final int w, h;

    // hearts
    private GreenfootImage heartFull = new GreenfootImage("heart2.png");
    private GreenfootImage heartEmpty;
    private int heartSize = 30;        
    private int heartSpacing;         

    // text
    private final Font font = new Font("Arial", true, false, 18); 

    private int score = 0, life = 5, maxLife = 5, timeLeft = 0;

    public Hud(int width, int height, int maxLife) {
        this.w = width; this.h = Math.max(height, 40); // minimal 40 px biar muat
        this.maxLife = maxLife;

        double scale = heartSize / (double)heartFull.getHeight();
        int newW = (int)Math.max(1, Math.round(heartFull.getWidth() * scale));
        int newH = (int)Math.max(1, Math.round(heartFull.getHeight() * scale));
        heartFull.scale(newW, newH);

        heartEmpty = new GreenfootImage(heartFull);
        heartEmpty.setTransparency(80);

        heartSpacing = newW + 4;

        setImage(new GreenfootImage(w, this.h));
        redraw();
    }

    public void update(int score, int life, int timeLeft) {
        this.score = score;
        this.life = Math.max(0, Math.min(maxLife, life)); // clamp
        this.timeLeft = Math.max(0, timeLeft);
        redraw();
    }

    private void redraw() {
        GreenfootImage img = getImage();
        img.clear();

        // background bar agar semua kontras
        img.setColor(new Color(0,0,0,90));
        img.fillRect(0, 0, w, h);

        // hearts
        int hx = 15;
        int hy = (h - heartFull.getHeight()) / 2; // center vertikal
        for (int i = 0; i < maxLife; i++) {
            img.drawImage(i < life ? heartFull : heartEmpty, hx + i * heartSpacing, hy);
        }

        // text
        img.setFont(font);
        int textY = (h / 2) + 7; 
        int baseX = hx + maxLife * heartSpacing + 16;

        // shadow
        img.setColor(new Color(0,0,0,180));
        img.drawString("Score: " + score, baseX + 11, textY + 1);
        img.drawString("Time: "  + timeLeft, baseX + 245 + 1, textY + 1);

        // white
        img.setColor(Color.WHITE);
        img.drawString("Score: " + score, baseX + 10, textY);
        img.drawString("Time: "  + timeLeft, baseX + 245, textY);
    }
}
