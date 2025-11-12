import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class BtnHard here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class BtnHard extends btnAnimation
{
      public BtnHard()
    {
        // 1. Ambil gambar asli dari tombolnya
        GreenfootImage image = new GreenfootImage("bntHard.png");
        
        image.scale(140,90); 
        
        // 3. Atur gambar yang sudah dikecilkan kembali ke aktor
        setImage(image);
    }
    
    public void act()
    {
        super.act();
            if (Greenfoot.mouseClicked(this)) 
        {
            GameSettings.difficulty = "Hard";
            Greenfoot.setWorld(new StoryWorld("Hard"));
        }
    }
}
