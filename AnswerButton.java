import greenfoot.*;

/**
 * Tombol jawaban untuk QuizWorld.
 * Tombol ini menggunakan BtnGreen.png dan menuliskan angka jawaban di atasnya.
 */
public class AnswerButton extends Actor
{
    private boolean isCorrect;
    private int answerValue;

    public AnswerButton(int answer, boolean correct)
    {
        this.isCorrect = correct;
        this.answerValue = answer;
        
        // --- UBAHAN DI SINI ---
        
        // 1. Muat gambar BtnGreen.png
        GreenfootImage bg = new GreenfootImage("BtnGreen.png");
        
        // 2. Atur ukuran tombol (sesuaikan jika perlu)
        bg.scale(1, 70); 
        
        // 3. Tulis teks jawaban di atas tombol
        String text = "" + answerValue;
        bg.setColor(Color.WHITE); // Atur warna teks
        bg.setFont(new Font("Arial", true, false, 36)); // Atur font teks
        
        // 4. Buat gambar teks terpisah untuk diukur
        GreenfootImage textImg = new GreenfootImage(text, 36, Color.WHITE, new Color(0,0,0,0));
        
        // 5. Atur teks agar rata tengah di atas gambar background
        int textX = (bg.getWidth() - textImg.getWidth()) / 2;
        int textY = (bg.getHeight() - textImg.getHeight()) / 2;
        
        bg.drawImage(textImg, textX, textY);
        
        // 6. Set gambar yang sudah jadi
        setImage(bg);
        // --- BATAS PERUBAHAN ---
    }
    
    public void act()
    {
        // Saat tombol ini diklik
        if (Greenfoot.mouseClicked(this))
        {
            // Panggil method checkAnswer di QuizWorld
            ((QuizWorld)getWorld()).checkAnswer(isCorrect);
        }
    }
}