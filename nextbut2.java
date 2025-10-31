import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class nextbut2 here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class nextbut2 extends Actor
{

    public nextbut2()
    {
        // 1. Ambil gambar asli dari tombolnya
        GreenfootImage image = getImage();
        
        image.scale(150, 100); 
        
        // 3. Atur gambar yang sudah dikecilkan kembali ke aktor
        setImage(image);
    }
    
    public void act()
    {
        if (Greenfoot.mouseClicked(this)) 
        {
            Greenfoot.setWorld(new Menu());
        }    }
}
