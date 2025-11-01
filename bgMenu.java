import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Pause overlay world yang menampilkan menu sementara permainan dihentikan.
 */
public class bgMenu extends World {
    private final GameWorld pausedWorld;

    public bgMenu(GameWorld pausedWorld) {
        super(pausedWorld.getWidth(), pausedWorld.getHeight(), 1);
        this.pausedWorld = pausedWorld;

        GreenfootImage snapshot = new GreenfootImage(pausedWorld.getBackground());
        GreenfootImage overlay = new GreenfootImage(getWidth(), getHeight());
        overlay.setColor(new Color(0, 0, 0, 150));
        overlay.fill();
        snapshot.drawImage(overlay, 0, 0);
        setBackground(snapshot);

        showText("PAUSED", getWidth() / 2, getHeight() / 2 - 130);

        addObject(new btResume(), getWidth() / 2, getHeight() / 2 - 40);
        addObject(new btMainMenu(), getWidth() / 2, getHeight() / 2 + 40);
        addObject(new btOption(), getWidth() / 2, getHeight() / 2 + 120);
    }

    GameWorld getPausedWorld() {
        return pausedWorld;
    }

    public void resumeGame() {
        Greenfoot.setWorld(pausedWorld);
        pausedWorld.onResumeFromPause();
    }
}
