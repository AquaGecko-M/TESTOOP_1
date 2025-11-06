import greenfoot.*; 

public class AnswerButton extends Actor
{
    private int answerValue;
    private boolean isCorrect;

    public AnswerButton(int value, boolean correct) {
        answerValue = value;
        isCorrect = correct;
        
        GreenfootImage img = new GreenfootImage(150, 50); 
        
        img.setColor(new greenfoot.Color(100, 100, 255)); 
        img.fill();
        img.setColor(greenfoot.Color.WHITE); 

        img.setFont(new Font("Arial", true, false, 24)); 
        img.drawString(String.valueOf(answerValue), 
                       img.getWidth() / 2 - (String.valueOf(answerValue).length() * 7), 
                       img.getHeight() / 2 + 8); 
        setImage(img);
    }
    
    public void act() {
        if (Greenfoot.mouseClicked(this)) {
            // Tell the QuizWorld to check this button's answer
            ((QuizWorld)getWorld()).checkAnswer(isCorrect);
        }
    }
}