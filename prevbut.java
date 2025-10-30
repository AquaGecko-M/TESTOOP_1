import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class prevbut here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class prevbut extends Actor
{
       public prevbut()
    {
        // 1. Ambil gambar asli dari tombolnya
        GreenfootImage image = getImage();
        
        image.scale(150, 100); 
        
        // 3. Atur gambar yang sudah dikecilkan kembali ke aktor
        setImage(image);
    }
    public void act()
    {
        // Add your action code here.
    }
}
