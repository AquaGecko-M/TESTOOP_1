import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class BtnEasy here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class BtnEasy extends btnAnimation
{
    public BtnEasy()
    {
        // 1. Ambil gambar asli dari tombolnya
        GreenfootImage image = new GreenfootImage("btnEasy.png");
        
        image.scale(140,90); 
        
        // 3. Atur gambar yang sudah dikecilkan kembali ke aktor
        setImage(image);
    }
    
    public void act()
    {
        super.act();
        if (Greenfoot.mouseClicked(this)) 
        {   
            GameSettings.difficulty = "Easy";
            Greenfoot.setWorld(new StoryWorld("Easy"));
        }
    }
}
