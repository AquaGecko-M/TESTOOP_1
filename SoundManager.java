import greenfoot.*;

public class SoundManager {
    private static GreenfootSound currentMusic;

    public static void play(String fileName, int volume) {
        stop(); // hentikan musik lama
        currentMusic = new GreenfootSound(fileName);
        currentMusic.setVolume(volume);
        currentMusic.playLoop();
    }

    public static void stop() {
        if (currentMusic != null && currentMusic.isPlaying()) {
            currentMusic.stop();
        }
    }
}
