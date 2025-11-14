import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class Koin here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Koin extends Actor {
    private static final int IconSize = 42;

    public Koin() {
        GreenfootImage img = new GreenfootImage("KoinLogo.png");
        img.scale(IconSize, IconSize);
        setImage(img);
    }
}
