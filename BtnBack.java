import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class BtnBack here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class BtnBack extends BtnAnima
{
      public BtnBack()
    {
        // 1. Ambil gambar asli dari tombolnya
        GreenfootImage image = new GreenfootImage("btnBack.png");
        
        image.scale(200,150); 
        
        // 3. Atur gambar yang sudah dikecilkan kembali ke aktor
        setImage(image);
    }
    
    public void act()
    {
        super.act();
        // Add your action code here.if (Greenfoot.mouseClicked(this)) {
            if (Greenfoot.mouseClicked(this)) 
        {
            Greenfoot.setWorld(new Menu());
        }
    }
}