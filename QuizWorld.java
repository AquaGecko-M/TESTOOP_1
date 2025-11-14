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
    
    private GreenfootImage timerBg;
    public QuizWorld(GameWorld originalGameWorld, Treasure treasure, int difficulty)
    {    
        super(originalGameWorld.getWidth(), originalGameWorld.getHeight(), 1);        
        this.originWorld = originalGameWorld;
        this.treasure = treasure;
        this.treasureValue = treasure.getValue();
        this.difficultyLevel = difficulty;  
        timerBg = new GreenfootImage("BtnBlack.png");
        timerBg.scale(100, 50); 
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
            case 0: generateEasyQuestion(); break;
            case 1: generateMediumQuestion(); break;
            case 2: generateHardQuestion(); break;
            default: generateEasyQuestion(); break;
        }
        GreenfootImage bg = getBackground();
        bg.setColor(greenfoot.Color.WHITE);
        bg.setFont(new Font("Arial", true, false, 60));
        GreenfootImage textImg = new GreenfootImage(questionString, 60, Color.WHITE, new Color(0,0,0,0));
        bg.drawImage(textImg, (getWidth() - textImg.getWidth()) / 2, getHeight() / 4); 
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
    private void generateEasyQuestion() {
        int num1 = Greenfoot.getRandomNumber(10) + 1;
        int num2 = Greenfoot.getRandomNumber(10) + 1;
        if (Greenfoot.getRandomNumber(2) == 0) {
            questionString = num1 + " + " + num2 + " = ?";
            correctAnswer = num1 + num2;
        } else {
            if (num1 < num2) { int temp = num1; num1 = num2; num2 = temp; }
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
            if (num1 < num2) { int temp = num1; num1 = num2; num2 = temp; }
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
    
    
    private void updateQuizTimerDisplay() {
        GreenfootImage bg = getBackground();
        String text = "Time: " + quizTimeLeft;
        int bgX = getWidth() / 2 - timerBg.getWidth() / 2;
        int bgY = 40;
        bg.drawImage(timerBg, bgX, bgY);
        GreenfootImage textImg = new GreenfootImage(text, 30, Color.WHITE, new Color(0,0,0,0));
        int textX = bgX + (timerBg.getWidth() - textImg.getWidth()) / 2;
        int textY = bgY + (timerBg.getHeight() - textImg.getHeight()) / 2 + 1; 
        bg.drawImage(textImg, textX, textY);
    }
    
    public void checkAnswer(boolean wasCorrect) 
    {
        quizTimeLeft = -1;
        GreenfootImage bg = getBackground();
        bg.setFont(new Font("Arial", true, false, 60));
        int coinsEarned = 0;
        
        if (wasCorrect) {
            coinsEarned = GameWorld.CoinRewardTreasure;

            String s = "Correct! +" + treasureValue + " pts / +" + coinsEarned + "$";
            GreenfootImage textImg = new GreenfootImage(s, 60, Color.WHITE, new Color(0,0,0,0));
            bg.drawImage(textImg, (getWidth() - textImg.getWidth()) / 2, getHeight() / 2 - 50);     
            originWorld.addScore(treasureValue); 
            originWorld.addCoins(coinsEarned); 
            boolean levelComplete = originWorld.addKeyItem();
            originWorld.removeObject(this.treasure); 
            treasure.startCooldown();
            if (levelComplete) {
                return; 
            }
        } else {
            String s = "Incorrect! -10s";
            GreenfootImage textImg = new GreenfootImage(s, 60, Color.WHITE, new Color(0,0,0,0));
            bg.drawImage(textImg, (getWidth() - textImg.getWidth()) / 2, getHeight() / 2 - 50);
            
            originWorld.reduceTimer(10);
            treasure.startCooldown();
        }
        
        Greenfoot.delay(60);    
        Greenfoot.setWorld(originWorld);
        originWorld.onResumeFromPause(); 
    }
}
