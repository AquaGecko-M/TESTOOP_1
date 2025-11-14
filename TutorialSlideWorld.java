import greenfoot.*;

public class TutorialSlideWorld extends World
{
    private String[] imageNames; 
    private int currentSlide = 0; 
    
    public TutorialSlideWorld(int start, int end)
    {    
        super(960, 540, 1, false); 
        int totalSlides = (end - start) + 1;
        imageNames = new String[totalSlides];
        for (int i = 0; i < totalSlides; i++) {
            imageNames[i] = (start + i) + ".png";
        }
        prepare();
        showSlide(0);
    }
    
    private void prepare()
    {
        addObject(new btnTutorialBack(), 870, 50);  
        addObject(new btnTutorialPrev(), 75, 50);  
        addObject(new btnTutorialNext(), 225, 50);
    }
    
    private void showSlide(int slideIndex)
    {
        if (slideIndex < 0) {
            slideIndex = 0; 
        }
        if (slideIndex >= imageNames.length) {
            slideIndex = imageNames.length - 1; 
        }
        
        currentSlide = slideIndex;
        
        GreenfootImage bg = new GreenfootImage(imageNames[currentSlide]);
        bg.scale(getWidth(), getHeight());
        setBackground(bg);
    }
    
    public void nextSlide()
    {
        showSlide(currentSlide + 1);
    }
    
    public void prevSlide()
    {
        showSlide(currentSlide - 1);
    }
}