import greenfoot.*;

public class OptionWorld extends World
{
    private World returnToWorld;

    public OptionWorld(World returnTo)
    {    
        super(returnTo.getWidth(), returnTo.getHeight(), 1, false); 
        this.returnToWorld = returnTo;
        GreenfootImage bg = new GreenfootImage(getWidth(), getHeight());
        try {
             bg = new GreenfootImage(returnToWorld.getBackground());
        } catch (Exception e) {
            bg.setColor(Color.BLACK);
            bg.fill();
        }

        GreenfootImage overlay = new GreenfootImage(getWidth(), getHeight());
        overlay.setColor(new Color(0, 0, 0, 180)); 
        overlay.fill();
        bg.drawImage(overlay, 0, 0);
        setBackground(bg);
        getBackground().setColor(Color.WHITE);
        getBackground().setFont(new Font("Arial", true, false, 48));
        getBackground().drawString("Options", getWidth() / 2 - 100, 150);
        addObject(new btnResetProgress(), getWidth() / 2, 300);
        addObject(new btnBackToWorld(returnToWorld), getWidth() / 2, 420);
    }
}