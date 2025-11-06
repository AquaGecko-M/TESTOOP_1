import greenfoot.*;  


public class SimpleTimer
{
    private long lastMark = System.currentTimeMillis();

    public int millisElapsed() {
        return (int)(System.currentTimeMillis() - lastMark);
    }

    public boolean hasElapsed(int ms) {
        return millisElapsed() >= ms;   
    }

    public void mark() {
        lastMark = System.currentTimeMillis();
    }
}
