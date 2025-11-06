import greenfoot.*;  


public class Menu extends World
{

    
    public Menu()
    {    

        super(960, 540, 1); 

 
        GreenfootImage bg = new GreenfootImage("menu_ui_1.jpg"); 

        bg.scale(960, 540);

        setBackground(bg);


        ButtonMenu();
        prepare();
    }

    private void ButtonMenu(){
        BtnTutor Tutorial = new BtnTutor();
        BtnStart Start = new BtnStart();
        BtnExit Exit = new BtnExit();

        addObject(Tutorial, 220, 445); 
        addObject(Start,    475, 440); 
        addObject(Exit,     740, 450); 
    }
    
    
    private void prepare()
    {
    }
}
