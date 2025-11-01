import greenfoot.*;
import java.util.Collections;
import java.util.ArrayList;

/**
 * An overlay world that pauses the game and displays a 10-second math quiz.
 */
public class QuizWorld extends World
{
    private GameWorld originWorld; // Reference to the GameWorld it came from
    private Treasure treasure;     // The specific treasure chest that was touched
    private int treasureValue;     // How many coins this treasure is worth
    
    private int correctAnswer;     // The correct answer to the math problem
    
    // Timer for the quiz
    private SimpleTimer quizTimer = new SimpleTimer();
    private int quizTimeLeft = 10; // 10 seconds to answer

    /**
     * Constructor for QuizWorld.
     * Takes the original GameWorld instance and the Treasure object.
     */
    public QuizWorld(GameWorld originalGameWorld, Treasure treasure)
    {    
        super(originalGameWorld.getWidth(), originalGameWorld.getHeight(), 1); 
        
        this.originWorld = originalGameWorld;
        this.treasure = treasure;
        this.treasureValue = treasure.getValue();
        
        // Create the semi-transparent overlay
        GreenfootImage snapshot = new GreenfootImage(originalGameWorld.getBackground());
        GreenfootImage overlay = new GreenfootImage(getWidth(), getHeight());
        overlay.setColor(new greenfoot.Color(0, 0, 0, 150)); 
        overlay.fill();
        snapshot.drawImage(overlay, 0, 0);
        setBackground(snapshot);

        // Add the quiz UI on top
        prepareQuiz();
        
        // Start the 10-second timer
        quizTimer.mark();
        updateQuizTimerDisplay(); // Show initial time
    }

    /**
     * This method runs 60 times/sec, checking the quiz timer.
     */
    public void act() {
        // Check if 1 second has passed
        if (quizTimer.hasElapsed(1000)) {
            if (quizTimeLeft > 0) { // Only count down if time is left
                quizTimeLeft--;
                updateQuizTimerDisplay();
                quizTimer.mark();
                
                if (quizTimeLeft == 0) {
                    // Time's up! Treat as incorrect.
                    checkAnswer(false);
                }
            }
        }
    }
    
    /**
     * Draws the "Time: X" text on the screen.
     */
    private void updateQuizTimerDisplay() {
        // We must re-draw the text on the background image
        // (We can't use showText() because it stacks up)
        GreenfootImage bg = getBackground();
        
        // Draw a small black box to clear the old timer text
        bg.setColor(new greenfoot.Color(0, 0, 0, 150)); 
        bg.fillRect(getWidth() / 2 - 50, 40, 150, 40); // x, y, width, height
        
        // Draw the new timer text
        bg.setColor(greenfoot.Color.WHITE);
        bg.setFont(new Font("Arial", true, false, 24));
        bg.drawString("Time: " + quizTimeLeft, getWidth() / 2 - 30, 70);
    }

    /**
     * Generates the question, answers, and buttons.
     */
    private void prepareQuiz()
    {
        // 1. Generate the math question
        int num1 = Greenfoot.getRandomNumber(10) + 1;
        int num2 = Greenfoot.getRandomNumber(10) + 1;
        correctAnswer = num1 + num2;
        
        // 2. Display the question
        GreenfootImage bg = getBackground();
        bg.setColor(greenfoot.Color.WHITE);
        bg.setFont(new Font("Arial", true, false, 36));
        bg.drawString(num1 + " + " + num2 + " = ?", getWidth() / 2 - 50, getHeight() / 4);

        // 3. Prepare answer values
        ArrayList<Integer> answers = new ArrayList<>();
        answers.add(correctAnswer);
        while (answers.size() < 4) {
            int wrongAnswer = Greenfoot.getRandomNumber(20) + 1;
            if (!answers.contains(wrongAnswer)) {
                answers.add(wrongAnswer);
            }
        }
        Collections.shuffle(answers); // Randomize the order

        // 4. Add answer buttons
        int yPos = getHeight() / 2 + 50; 
        int xOffset = getWidth() / 5; 
        addObject(new AnswerButton(answers.get(0), answers.get(0) == correctAnswer), xOffset, yPos);
        addObject(new AnswerButton(answers.get(1), answers.get(1) == correctAnswer), xOffset * 2, yPos);
        addObject(new AnswerButton(answers.get(2), answers.get(2) == correctAnswer), xOffset * 3, yPos);
        addObject(new AnswerButton(answers.get(3), answers.get(3) == correctAnswer), xOffset * 4, yPos);
    }
    
    /**
     * This method is called by the AnswerButton when one is clicked.
     */
    public void checkAnswer(boolean wasCorrect) 
    {
        quizTimeLeft = -1; // Stop the timer
    
        GreenfootImage bg = getBackground();
        bg.setFont(new Font("Arial", true, false, 48));
        
        if (wasCorrect) {
            bg.drawString("Correct! + " + treasureValue, getWidth() / 2 - 100, getHeight() / 2 - 50);
            originWorld.addScore(treasureValue); 
            originWorld.removeObject(this.treasure); 
            
        } else {
            bg.drawString("Incorrect! -20s", getWidth() / 2 - 80, getHeight() / 2 - 50);
            originWorld.reduceTimer(20);
            
            // --- THIS IS THE NEW LINE ---
            // Tell the treasure to start its 2-second cooldown
            treasure.startCooldown(); 
        }
        
        Greenfoot.delay(60); 
        
        Greenfoot.setWorld(originWorld);
        originWorld.onResumeFromPause(); 
    }
}