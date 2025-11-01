import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class BtnEasy here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class BtnEasy extends Actor
{
      public BtnEasy()
    {
        // 1. Ambil gambar asli dari tombolnya
        GreenfootImage image = new GreenfootImage("btnEasy.png");
        
        image.scale(150,150); 
        
        // 3. Atur gambar yang sudah dikecilkan kembali ke aktor
        setImage(image);
    }
    
    public void act()
    {
        // Add your action code here.if (Greenfoot.mouseClicked(this)) {
            if (Greenfoot.mouseClicked(this)) 
        {
            Greenfoot.setWorld(new GameWorld());
        }
    }
}
