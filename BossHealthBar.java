import greenfoot.*;


public class BossHealthBar extends Actor
{
    private crocBoss boss; 
    private int barWidth = 400;  
    private int barHeight = 20;  
    
    
    private GreenfootImage barImage = new GreenfootImage(barWidth, barHeight);

    
    public BossHealthBar(crocBoss bossToTrack)
    {
        this.boss = bossToTrack;
        updateBar(); 
    }

    public void act()
    {
        
        if (boss.getWorld() == null) {
            getWorld().removeObject(this); 
            return;
        }
        
        
        updateBar();
    }
    
    
    private void updateBar()
    {
        
        double healthPct = boss.getHealthPercentage();
        
        
        barImage.clear();
        
        
        barImage.setColor(greenfoot.Color.RED);
        barImage.fillRect(0, 0, barWidth, barHeight);
        
        
        int greenWidth = (int)(barWidth * healthPct);
        
        
        if (greenWidth > 0) {
            barImage.setColor(greenfoot.Color.GREEN);
            barImage.fillRect(0, 0, greenWidth, barHeight);
        }
        
        
        setImage(barImage);
    }
}
