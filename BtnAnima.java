import greenfoot.*;

public class BtnAnima extends Actor
{
    private GreenfootImage gambarAsli;
    private GreenfootImage gambarBesar;
    
    /**
     * Kita ganti 'boolean mouseDiAtas' dengan 'int mouseState'.
     * 0 = Mouse di luar
     * 1 = Mouse baru saja masuk (tampilkan gambar BESAR)
     * 2 = Mouse masih di dalam (tampilkan gambar KECIL)
     */
    private int mouseState = 0; 
    
    /**
     * Berjalan satu kali saat tombol ditambahkan ke dunia.
     */
    protected void addedToWorld(World world)
    {
        gambarAsli = getImage();
        
        gambarBesar = new GreenfootImage(getImage());
        int newWidth = (int)(gambarAsli.getWidth() * 1.2);
        int newHeight = (int)(gambarAsli.getHeight() * 1.2);
        gambarBesar.scale(newWidth, newHeight);
    }
    
    /**
     * Logika act() baru yang tidak akan bergetar
     */
    public void act() 
    {
        MouseInfo mi = Greenfoot.getMouseInfo();
        
        // Cek apakah mouse ada di atas tombol
        if (mi != null && mi.getActor() == this) 
        {
            // --- Mouse ada DI ATAS tombol ---
            
            if (mouseState == 0) {
                // Status 0 (di luar) -> Status 1 (Baru Masuk)
                // Ini adalah efek "Pop"
                setImage(gambarBesar);
                mouseState = 1;
            } 
            else if (mouseState == 1) {
                // Status 1 (Baru Masuk) -> Status 2 (Masih di Dalam)
                // Ini adalah kode yang Anda minta:
                setImage(gambarAsli); // Kembalikan ke kecil
                mouseState = 2;
            }
            // Jika mouseState == 2 (Masih di Dalam), kita tidak melakukan apa-apa.
            // Tombol akan tetap kecil (gambarAsli).
        } 
        else 
        {
            // --- Mouse ada DI LUAR tombol ---
            
            if (mouseState != 0) {
                // Jika mouse baru saja keluar
                setImage(gambarAsli); // Pastikan gambar kembali kecil
                mouseState = 0;      // Reset status (siap untuk "Pop" lagi)
            }
        }
    }    
}