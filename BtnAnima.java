import greenfoot.*;

public class BtnAnima extends Actor
{
    private GreenfootImage gambarAsli;
    private GreenfootImage gambarBesar;
    private boolean isPressed = false; 
    
    /**
     * Berjalan satu kali SETELAH constructor child (BtnHard) selesai.
     */
    protected void addedToWorld(World world)
    {
        // 1. Ambil gambar yang SUDAH DI-SCALE oleh BtnHard (misal: 150x150)
        gambarAsli = getImage(); 
        
        // 2. Buat versi yang sedikit lebih besar (misal: 165x165)
        gambarBesar = new GreenfootImage(getImage());
        int newWidth = (int)(gambarAsli.getWidth() * 1.2); 
        int newHeight = (int)(gambarAsli.getHeight() * 1.2);
        gambarBesar.scale(newWidth, newHeight);
    }
    
    public void act() 
    {
        // 1. Cek jika tombol ini HARUS kembali ke normal
        if (isPressed) 
        {
            MouseInfo mi = Greenfoot.getMouseInfo();
            if (mi == null || mi.getActor() != this || mi.getButton() == 0)
            {
                setImage(gambarAsli); // Kembalikan ke gambar asli
                isPressed = false;
            }
        }
        
        // 2. Cek apakah mouse BARU SAJA DITEKAN pada tombol ini
        if (Greenfoot.mousePressed(this))
        {
            setImage(gambarBesar); // Ganti ke gambar besar
            isPressed = true;     // Set status "sedang ditekan"
        }
    }    
}