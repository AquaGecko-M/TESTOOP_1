import greenfoot.*;
import java.util.Collections;
import java.util.ArrayList;


public class QuizWorld extends World
{
    private GameWorld originWorld; 
    private Treasure treasure;     
    private int treasureValue;     
    private int correctAnswer;     
    private int difficultyLevel; 
    
 
    private String questionString; 

    private SimpleTimer quizTimer = new SimpleTimer();
    private int quizTimeLeft = 10; 

    
    public QuizWorld(GameWorld originalGameWorld, Treasure treasure, int difficulty)
    {    
        super(originalGameWorld.getWidth(), originalGameWorld.getHeight(), 1); 
        
        this.originWorld = originalGameWorld;
        this.treasure = treasure;
        this.treasureValue = treasure.getValue();
        this.difficultyLevel = difficulty;
        
        GreenfootImage snapshot = new GreenfootImage(originalGameWorld.getBackground());
        GreenfootImage overlay = new GreenfootImage(getWidth(), getHeight());
        overlay.setColor(new greenfoot.Color(0, 0, 0, 150)); 
        overlay.fill();
        snapshot.drawImage(overlay, 0, 0);
        setBackground(snapshot);

        prepareQuiz();
        
        quizTimer.mark();
        updateQuizTimerDisplay(); 
    }

    
    private void prepareQuiz()
    {
        switch (difficultyLevel) {
            case 0: 
                generateEasyQuestion();
                break;
            case 1: 
                generateMediumQuestion();
                break;
            case 2: 
                generateHardQuestion();
                break;
            default: 
                generateEasyQuestion();
                break;
        }

        
        GreenfootImage bg = getBackground();
        bg.setColor(greenfoot.Color.WHITE);
        bg.setFont(new Font("Arial", true, false, 36));
        bg.drawString(questionString, getWidth() / 2 - 100, getHeight() / 4); 

        ArrayList<Integer> answers = new ArrayList<>();
        answers.add(correctAnswer);

        int answerRange = (difficultyLevel == 0) ? 21 : 101; 
        
        while (answers.size() < 4) {
            int wrongAnswer = Greenfoot.getRandomNumber(answerRange); 
            if (wrongAnswer != correctAnswer && !answers.contains(wrongAnswer)) {
                answers.add(wrongAnswer);
            }
        }
        Collections.shuffle(answers); 

        int yPos = getHeight() / 2 + 50; 
        int xOffset = getWidth() / 5; 
        addObject(new AnswerButton(answers.get(0), answers.get(0) == correctAnswer), xOffset, yPos);
        addObject(new AnswerButton(answers.get(1), answers.get(1) == correctAnswer), xOffset * 2, yPos);
        addObject(new AnswerButton(answers.get(2), answers.get(2) == correctAnswer), xOffset * 3, yPos);
        addObject(new AnswerButton(answers.get(3), answers.get(3) == correctAnswer), xOffset * 4, yPos);
    }
    

    
    private void generateEasyQuestion() {
        int num1 = Greenfoot.getRandomNumber(10) + 1; 
        int num2 = Greenfoot.getRandomNumber(10) + 1; 

        if (Greenfoot.getRandomNumber(2) == 0) {
            
            questionString = num1 + " + " + num2 + " = ?";
            correctAnswer = num1 + num2;
        } else {
            
            if (num1 < num2) { 
                int temp = num1;
                num1 = num2;
                num2 = temp;
            }
            questionString = num1 + " - " + num2 + " = ?";
            correctAnswer = num1 - num2;
        }
    }

    
    private void generateMediumQuestion() {
        if (Greenfoot.getRandomNumber(2) == 0) {
            
            int num1 = Greenfoot.getRandomNumber(9) + 2; 
            int num2 = Greenfoot.getRandomNumber(9) + 2; 
            questionString = num1 + " * " + num2 + " = ?";
            correctAnswer = num1 * num2;
        } else {
            
            int answer = Greenfoot.getRandomNumber(9) + 2; 
            int divisor = Greenfoot.getRandomNumber(9) + 2; 
            int dividend = answer * divisor; 
            
            questionString = dividend + " / " + divisor + " = ?";
            correctAnswer = answer;
        }
    }

    
    private void generateHardQuestion() {
        
        
        int operationType = Greenfoot.getRandomNumber(4); 
    
        if (operationType == 0) {
            
            int num1 = Greenfoot.getRandomNumber(20) + 1; 
            int num2 = Greenfoot.getRandomNumber(20) + 1; 
            questionString = num1 + " + " + num2 + " = ?";
            correctAnswer = num1 + num2;
            
        } else if (operationType == 1) {
            
            int num1 = Greenfoot.getRandomNumber(20) + 1; 
            int num2 = Greenfoot.getRandomNumber(20) + 1; 
            
            if (num1 < num2) { 
                int temp = num1;
                num1 = num2;
                num2 = temp;
            }
            questionString = num1 + " - " + num2 + " = ?";
            correctAnswer = num1 - num2;
            
        } else if (operationType == 2) {
            
            int num1 = Greenfoot.getRandomNumber(11) + 2; 
            int num2 = Greenfoot.getRandomNumber(11) + 2; 
            questionString = num1 + " * " + num2 + " = ?";
            correctAnswer = num1 * num2;
            
        } else {
            
            int answer = Greenfoot.getRandomNumber(9) + 2; 
            int divisor = Greenfoot.getRandomNumber(9) + 2; 
            int dividend = answer * divisor; 
            
            questionString = dividend + " / " + divisor + " = ?";
            correctAnswer = answer;
        }
    }

    
    public void act() {
        if (quizTimer.hasElapsed(1000)) {
            if (quizTimeLeft > 0) {
                quizTimeLeft--;
                updateQuizTimerDisplay();
                quizTimer.mark();
                if (quizTimeLeft == 0) {
                    checkAnswer(false);
                }
            }
        }
    }
    
    private void updateQuizTimerDisplay() {
        GreenfootImage bg = getBackground();
        bg.setColor(new greenfoot.Color(0, 0, 0, 150)); 
        bg.fillRect(getWidth() / 2 - 50, 40, 150, 40); 
        bg.setColor(greenfoot.Color.WHITE);
        bg.setFont(new Font("Arial", true, false, 24));
        bg.drawString("Time: " + quizTimeLeft, getWidth() / 2 - 30, 70);
    }
    
    public void checkAnswer(boolean wasCorrect) 
    {
        quizTimeLeft = -1;
        GreenfootImage bg = getBackground();
        bg.setFont(new Font("Arial", true, false, 48));
        int coinsEarned = 0;
        
        if (wasCorrect) {
            coinsEarned = GameWorld.COIN_REWARD_TREASURE;
            bg.drawString("Correct! +" + treasureValue + " pts / +" + GameWorld.COIN_REWARD_TREASURE + "$", getWidth() / 2 - 200, getHeight() / 2 - 50);
            originWorld.addScore(treasureValue); 
            boolean levelComplete = originWorld.addKeyItem();
            originWorld.removeObject(this.treasure); 
            treasure.startCooldown();
            if (levelComplete) {
                return; 
            }
        } else {
            bg.drawString("Incorrect! -20s", getWidth() / 2 - 80, getHeight() / 2 - 50);
            originWorld.reduceTimer(20);
            treasure.startCooldown();
        }
        
        Greenfoot.delay(60); 
        if (coinsEarned > 0) {
            originWorld.addCoins(coinsEarned);
        }; 
        Greenfoot.setWorld(originWorld);
        originWorld.onResumeFromPause(); 
    }
}
