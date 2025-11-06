import greenfoot.*;  


public class tutorialkeyboard extends World
{

    
    public tutorialkeyboard()
    {    

        super(960, 540, 1); 
        

        GreenfootImage bg = new GreenfootImage("4.jpg"); 
        

        bg.scale(960, 540);
        

        setBackground(bg);
        
        Buttonnav();
    }
    
    private void Buttonnav(){
    nextbut next = new nextbut();
    prevbut prev = new prevbut();
    
    addObject(next,    864, 467); 
    }
}
    

