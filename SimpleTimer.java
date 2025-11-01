import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class SimpleTimer here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
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
