import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Tombol Start di menu.
 */
public class BtnStart extends Actor
{
    public BtnStart()
    {
        GreenfootImage image = getImage();
        image.scale(200, 150); 
        setImage(image);
    }
    
    public void act()
    {
        if (Greenfoot.mouseClicked(this)) 
        {
            // Hentikan musik menu sebelum pindah ke world GameMode
            Menu.stopMenuMusic();
            
            Greenfoot.setWorld(new GameMode());
        }
    }
}
