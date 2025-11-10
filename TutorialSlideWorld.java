import greenfoot.*;

/**
 * A "smart" slideshow world.
 * It can be told which images to load in a sequence.
 */
public class TutorialSlideWorld extends World
{
    private String[] imageNames; // An array to hold all our image filenames
    private int currentSlide = 0; // The index of the slide we are currently on
    
    /**
     * Constructor for our "smart" slideshow.
     * @param start The number of the first image (e.g., 1)
     * @param end The number of the last image (e.g., 5)
     */
    public TutorialSlideWorld(int start, int end)
    {    
        super(960, 540, 1, false); // Your standard world size

        // Figure out how many slides we need to load
        int totalSlides = (end - start) + 1;
        imageNames = new String[totalSlides];

        // Fill the array with all the image filenames
        for (int i = 0; i < totalSlides; i++) {
            // This creates the filename, e.g., "1.png", "2.png", etc.
            imageNames[i] = (start + i) + ".png";
        }
        
        // Add the navigation buttons
        prepare();
        
        // Show the very first slide
        showSlide(0);
    }
    
    /**
     * Adds the Next, Previous, and Back buttons to the world.
     */
    private void prepare()
    {
        // Add "Back" button (goes back to the Tutorial Hub)
        addObject(new btnTutorialBack(), 80, 500); 
        
        // Add "Previous" button
        addObject(new btnTutorialPrev(), 800, 500); 
        
        // Add "Next" button
        addObject(new btnTutorialNext(), 880, 500);
    }
    
    /**
     * This is the main method to change the background image.
     */
    private void showSlide(int slideIndex)
    {
        // 1. Make sure the index is valid
        if (slideIndex < 0) {
            slideIndex = 0; // Don't go past the beginning
        }
        if (slideIndex >= imageNames.length) {
            slideIndex = imageNames.length - 1; // Don't go past the end
        }
        
        // 2. Set the new index
        currentSlide = slideIndex;
        
        // 3. Load the new background image
        GreenfootImage bg = new GreenfootImage(imageNames[currentSlide]);
        bg.scale(getWidth(), getHeight());
        setBackground(bg);
    }
    
    /**
     * This method is called by the "Next" button.
     */
    public void nextSlide()
    {
        showSlide(currentSlide + 1);
    }
    
    /**
     * This method is called by the "Previous" button.
     */
    public void prevSlide()
    {
        showSlide(currentSlide - 1);
    }
}