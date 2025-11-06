import greenfoot.*;  


public class tutorialmouse extends World
{

    
    public tutorialmouse()
    {    
 
        super(960, 540, 1); 
    
        GreenfootImage bg = new GreenfootImage("5.jpg"); 
        
        bg.scale(960, 540);
        
        setBackground(bg);
        
        Buttonnav();
    
    }
    
        private void Buttonnav(){
    nextbut2 next2 = new nextbut2();
    prevbut2 prev2 = new prevbut2();
    
    addObject(prev2, 103, 467); 
    addObject(next2,    864, 467); 
    }
    
}
