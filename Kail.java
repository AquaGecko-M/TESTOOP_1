import greenfoot.*;

public class Kail extends Actor {
    private final Boat owner;
    private int downSpeed = 3;
    private int upSpeed   = 3;

    private int minY;  
    private int maxY;  
    
    public Kail(Boat owner) {
        this.owner = owner;
        setImage("Kail.png"); 
        
        GreenfootImage image = getImage();
        image.scale(40, 50); 
        setImage(image); 
    }

    protected void addedToWorld(World w) {
        
        minY = owner.getY() + 40;
        maxY = w.getHeight() - 40;
    }

    public void act() {
        followBoatX();
        handleVertical();
        clampVertical();
        
        
        CommonFish common = (CommonFish) getOneIntersectingObject(CommonFish.class);
        if (common != null) {
            
            ((GameWorld) getWorld()).addScore(common.getValue());
            ((GameWorld) getWorld()).addFishCollected(1);
            addCoinsToWorld(common.getCoinReward());
            getWorld().removeObject(common);
            return; 
        }
        
        
        RareFish rare = (RareFish) getOneIntersectingObject(RareFish.class);
        if (rare != null) {
            
            ((GameWorld) getWorld()).addScore(rare.getValue());
            ((GameWorld) getWorld()).addFishCollected(1);
            addCoinsToWorld(rare.getCoinReward());
            getWorld().removeObject(rare);
            return;
        }
        
        
        EpicFish epic = (EpicFish) getOneIntersectingObject(EpicFish.class);
        if (epic != null) {
            
            ((GameWorld) getWorld()).addScore(epic.getValue());
            ((GameWorld) getWorld()).addFishCollected(1);
            addCoinsToWorld(epic.getCoinReward());
            getWorld().removeObject(epic);
            return;
        }
        
        GoldFish gold = (GoldFish) getOneIntersectingObject(GoldFish.class);
        if(gold != null) {
            
        addScoreToWorld(gold.getValue());
        addCoinsToWorld(gold.getCoinReward());
            
            
        addKeyItemToWorld();

         getWorld().removeObject(gold);
        return; 
         }
        
        Treasure treasure = (Treasure) getOneIntersectingObject(Treasure.class);

        
        if (treasure != null && treasure.isInteractable()) {
            GameWorld currentWorld = (GameWorld) getWorld();
            
            int difficulty = currentWorld.getCurrentLevel();
            
            Greenfoot.setWorld(new QuizWorld(currentWorld, treasure,difficulty));
            
            return; 
        }
    }

    private void followBoatX() {
        setLocation(owner.getX(), getY()); 
    }

    private void handleVertical() {
        
        if (Greenfoot.isKeyDown("Down") || (Greenfoot.isKeyDown("s"))) {
            setLocation(getX(), getY() + downSpeed);
        } else if (Greenfoot.isKeyDown("Up") || (Greenfoot.isKeyDown("w"))) {
            setLocation(getX(), getY() - downSpeed);
        } else {
            if (getY() > minY) setLocation(getX(), getY() - 1);
        }
    }

    private void clampVertical() {
        int y = Math.max(minY, Math.min(getY(), maxY));
        setLocation(getX(), y);
    }
    
    private void addCoinsToWorld(int amount) {
    ((GameWorld)getWorld()).addCoins(amount);
    }
    
    private void addScoreToWorld(int score) {
        ((GameWorld)getWorld()).addScore(score);
    }
    
    private void addFishCollectedToWorld(int amount) {
        ((GameWorld)getWorld()).addFishCollected(amount);
    }
    private void addKeyItemToWorld() {
        World world = getWorld(); 
        if (world instanceof GameWorld) {
            ((GameWorld)world).addKeyItem();
        }
    
    }
}